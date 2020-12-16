package com.wokebryant.anythingdemo.dynamicsitem.viewholder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.wokebryant.anythingdemo.dynamicsitem.decorate.ListItem;
import com.wokebryant.anythingdemo.dynamicsitem.item.DynamicsShortVideoItem;
import com.wokebryant.anythingdemo.R;

public class DynamicsShortVideoViewHolder extends BaseDynamicsViewHolder<DynamicsShortVideoItem> implements ListItem {

    private Context mContext;
    public ImageView mShortVideoBg;
    private ImageView mShortVideoPlay;
    private TextView mShortVideoDuration;
    private TextView mShortVideoOnlineNum;
    private View mItemView;

    public DynamicsShortVideoViewHolder(Context context, View itemView) {
        super(itemView);
        mContext = context;
        mItemView = itemView;
        mShortVideoBg = itemView.findViewById(R.id.lf_dynamics_short_video_bg);
        mShortVideoPlay = itemView.findViewById(R.id.lf_dynamics_short_video_play);
        mShortVideoDuration = itemView.findViewById(R.id.lf_dynamics_short_video_duration);
        mShortVideoOnlineNum = itemView.findViewById(R.id.lf_dynamics_short_video_num);

    }

    @Override
    public void bindViewData(final DynamicsShortVideoItem data, int size, int position) {
//        mShortVideoBg.setImageResource(R.color.colorAccent);
        mShortVideoDuration.setText(data.duration);
        mShortVideoOnlineNum.setText(data.onlineNum);

        if (mItemView != null) {
            mItemView.setTag(data);
        }
    }

    @Override
    public void setActive(View newActiveView, int newActiveViewPosition) {
        Toast.makeText(mContext, "小视频自动播放", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void deactivate(View currentView, int position) {

    }
}
