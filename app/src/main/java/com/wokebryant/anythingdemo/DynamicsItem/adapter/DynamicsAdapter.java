package com.wokebryant.anythingdemo.DynamicsItem.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.wokebryant.anythingdemo.DynamicsItem.PersonalDynamicsItemView;
import com.wokebryant.anythingdemo.DynamicsItem.decorate.DynamicsItemClickCallBack;
import com.wokebryant.anythingdemo.DynamicsItem.model.DynamicsItemModel;
import com.wokebryant.anythingdemo.R;

import java.util.List;

public class DynamicsAdapter extends RecyclerView.Adapter<DynamicsAdapter.ViewHolder> {

    public Context mContext;
    public List<DynamicsItemModel> mDataList;
    private OnItemClickListener mOnItemClickListener;

    public DynamicsAdapter(Context context, List<DynamicsItemModel> dataList) {
        mContext = context;
        mDataList = dataList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(mContext, R.layout.lf_dynamics_item, null);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.mDynamicsItemView.setData(mDataList.get(i));
        viewHolder.mDynamicsItemView.setDynamicsItemClickCallBack(callBack);
    }

    @Override
    public int getItemCount() {
        return mDataList != null ? mDataList.size() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public PersonalDynamicsItemView mDynamicsItemView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mDynamicsItemView = itemView.findViewById(R.id.lf_dynamics_item_view);
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener{

        void onItemClick(int position);
    }

    public static DynamicsItemClickCallBack callBack = new DynamicsItemClickCallBack() {
        @Override
        public void onDetailClick() {

        }

        @Override
        public void onShortVideoClick(String playUrl) {

        }

        @Override
        public void onReplayClick(String playUrl) {

        }

        @Override
        public void onPhotoListClick(int position) {

        }

        @Override
        public void onCommentClick() {

        }

        @Override
        public void onPraiseClick() {

        }

        @Override
        public void onTeaseClick() {

        }
    };
}
