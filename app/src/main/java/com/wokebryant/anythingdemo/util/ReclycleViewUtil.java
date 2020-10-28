package com.wokebryant.anythingdemo.util;

import android.app.Activity;
import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.wokebryant.anythingdemo.DynamicsItem.adapter.DynamicsAdapter;
import com.wokebryant.anythingdemo.DynamicsItem.item.BaseDynamicsItem;
import com.wokebryant.anythingdemo.DynamicsItem.item.DynamicsShortVideoItem;
import com.wokebryant.anythingdemo.DynamicsItem.model.DynamicsItemModel;
import com.wokebryant.anythingdemo.DynamicsItem.viewholder.DynamicsShortVideoViewHolder;

public class ReclycleViewUtil {

    public static boolean hasPlay;

    /*
     * 获取当前屏幕上可见的view
     * */
    public static void getVisibleViews(RecyclerView reView) {
        hasPlay = false;
        if (reView == null || reView.getVisibility() != View.VISIBLE ||
            !reView.isShown() || !reView.getGlobalVisibleRect(new Rect())) {
            return;
        }
        //保险起见，为了不让统计影响正常业务，这里做下try-catch
        try {
            int[] range = new int[2];
            RecyclerView.LayoutManager manager = reView.getLayoutManager();
            if (manager instanceof LinearLayoutManager) {
                range = findRangeLinear((LinearLayoutManager) manager);
            } else if (manager instanceof GridLayoutManager) {
                range = findRangeGrid((GridLayoutManager) manager);
            } else if (manager instanceof StaggeredGridLayoutManager) {
                range = findRangeStaggeredGrid((StaggeredGridLayoutManager) manager);
            }
            if (range == null || range.length < 2) {
                return;
            }
            //Log.i("qcl0402", "屏幕内可见条目的起始位置：" + range[0] + "---" + range[1]);
            for (int i = range[0]; i <= range[1]; i++) {
                View view = manager.findViewByPosition(i);
                RecyclerView.ViewHolder viewHolder = reView.findViewHolderForAdapterPosition(i);
                recordViewCount(viewHolder, view);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //获取view绑定的数据
    public static void recordViewCount(RecyclerView.ViewHolder viewHolder, View view) {
        if (view == null || view.getVisibility() != View.VISIBLE ||
            !view.isShown() || !view.getGlobalVisibleRect(new Rect()) || viewHolder == null) {
            return;
        }

        DynamicsItemModel tag = (DynamicsItemModel)view.getTag();
        DynamicsAdapter.ViewHolder shortVideoViewHolder = (DynamicsAdapter.ViewHolder)viewHolder;
        final ImageView playView = shortVideoViewHolder.mDynamicsItemView.getShortVideoPlayerView();
        if (playView == null) {
            return;
        }

        //int height = playView.getHeight();
        //Rect rect = new Rect();
        //view.getLocalVisibleRect(rect);
        //if (rect.top > 0 && rect.left == 0 && rect.bottom == height) {
        //    return;
        //}
        //if ((rect.top == 0 && rect.left == 0 && rect.bottom < height)) {
        //    return;
        //}
        int top = view.getTop();
        Log.i("playHeight= " ,String.valueOf(top));
        playView.postDelayed(new Runnable() {
            @Override
            public void run() {

            }
        }, 200);
        int halfHeight = 3 * view.getHeight() / 4;
        int screenHeight = UIUtil.getScreenHeight((Activity) view.getContext());
        int statusBarHeight = UIUtil.getStatusBarHeight(view.getContext());
        //顶部遮挡
        if (top < 0 && Math.abs(top) > halfHeight / 3) {
            return;
        }
        //底部遮挡
        if (top > screenHeight - halfHeight - statusBarHeight) {
            return;
        }

        if (hasPlay) {
            return;
        }

        if (tag != null && tag.shortVideo != null && shortVideoViewHolder != null) {
            shortVideoViewHolder.mDynamicsItemView.autoPlayVideo();
            hasPlay = true;
        }
    }

    public static int[] findRangeLinear(LinearLayoutManager manager) {
        int[] range = new int[2];
        range[0] = manager.findFirstVisibleItemPosition();
        range[1] = manager.findLastVisibleItemPosition();
        return range;
    }

    public static int[] findRangeGrid(GridLayoutManager manager) {
        int[] range = new int[2];
        range[0] = manager.findFirstVisibleItemPosition();
        range[1] = manager.findLastVisibleItemPosition();
        return range;

    }

    public static int[] findRangeStaggeredGrid(StaggeredGridLayoutManager manager) {
        int[] startPos = new int[manager.getSpanCount()];
        int[] endPos = new int[manager.getSpanCount()];
        manager.findFirstVisibleItemPositions(startPos);
        manager.findLastVisibleItemPositions(endPos);
        int[] range = findRange(startPos, endPos);
        return range;
    }

    public static int[] findRange(int[] startPos, int[] endPos) {
        int start = startPos[0];
        int end = endPos[0];
        for (int i = 1; i < startPos.length; i++) {
            if (start > startPos[i]) {
                start = startPos[i];
            }
        }
        for (int i = 1; i < endPos.length; i++) {
            if (end < endPos[i]) {
                end = endPos[i];
            }
        }
        int[] res = new int[]{start, end};
        return res;
    }

}
