package com.wokebryant.anythingdemo.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wokebryant.anythingdemo.R;
import com.wokebryant.anythingdemo.model.EndPageShortVideoModel;

public class EndShortVideoView extends RelativeLayout {

    private static final String TAG = "EndShortVideoView";

    private EndPageShortVideoModel mModel;

    private ImageView mCoverIv;
    private TextView mPlayCountTv;
    private TextView mCommentTv;
    private TextView mDurationTv;
    private TextView mTitleTv;
    private TextView mPublishTimeTv;
    private ImageView mMoreIv;


    public EndShortVideoView(Context context) {
        this(context, null);
    }

    public EndShortVideoView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EndShortVideoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.lf_end_item_view_short_video, this, true);
        mCoverIv = findViewById(R.id.end_short_video_cover);
        mPlayCountTv = findViewById(R.id.end_short_video_num);
        mCommentTv = findViewById(R.id.end_short_video_num2);
        mDurationTv = findViewById(R.id.end_short_video_time);
        mTitleTv = findViewById(R.id.end_short_video_title);
        mPublishTimeTv = findViewById(R.id.end_short_video_time2);
        mMoreIv = findViewById(R.id.end_short_video_more);
    }

    public void setData(EndPageShortVideoModel model) {
        if (model != null) {
            mModel = model;

            mPlayCountTv.setText(model.playCount + "");
            mCommentTv.setText(model.commentCount + "");
            mDurationTv.setText(model.duration + "");
            mTitleTv.setText(model.title);
            mPublishTimeTv.setText(transformDate(model.createTime));
        }
    }

    private String transformDate(long time) {
        String hour = "";

        return hour;
    }
}
