package com.wokebryant.anythingdemo.DynamicsItem.viewholder;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.wokebryant.anythingdemo.DynamicsItem.activity.PersonalDynamicsActivity;
import com.wokebryant.anythingdemo.DynamicsItem.item.DynamicsShortVideoItem;
import com.wokebryant.anythingdemo.R;

public class DynamicsShortVideoViewHolder extends BaseDynamicsViewHolder<DynamicsShortVideoItem>{

    private Context mContext;
    private ImageView mShortVideoBg;
    private ImageView mShortVideoPlay;
    private TextView mShortVideoDuration;
    private TextView mShortVideoOnlineNum;

    public DynamicsShortVideoViewHolder(Context context, View itemView) {
        super(itemView);
        mContext = context;
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
    }
}
