package com.wokebryant.anythingdemo.widget.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wokebryant.anythingdemo.R;
import com.wokebryant.anythingdemo.mapper.EndPageDataMapper;
import com.wokebryant.anythingdemo.model.EndPageModel;

import java.util.HashMap;
import java.util.Map;

public class EndPageView extends LinearLayout {

    private static final String TAG = "EndView";

    private TextView mActorNickTv;
    private ImageView mActorAvatar;
    private TextView mCoinNumTv;
    private TextView mWatchNumTv;
    private TextView mPopularityTv;
    private Button mExitPageBtn;
    private Button mReplayBtn;
    private EndShortVideoView mFirstVideoView;
    private EndShortVideoView mSecondVideoView;
    private EndRecommendView mFirstRecommendView;
    private EndRecommendView mSecondRecommendView;

    private EndPageModel mEndPageModel;


    public EndPageView(Context context) {
        this(context, null);
    }

    public EndPageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EndPageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.lf_end_page_view, this, true);
        mActorNickTv = findViewById(R.id.end_page_nick);
        mActorAvatar = findViewById(R.id.end_page_avatar);
        mCoinNumTv = findViewById(R.id.end_page_coins);
        mWatchNumTv = findViewById(R.id.end_page_watch_num);
        mPopularityTv = findViewById(R.id.end_page_popularity);
        mExitPageBtn = findViewById(R.id.end_page_exit);
        mReplayBtn = findViewById(R.id.end_page_replay);
        mFirstVideoView = findViewById(R.id.end_page_shortVideo_1);
        mSecondVideoView = findViewById(R.id.end_page_shortVideo_2);
        mFirstRecommendView = findViewById(R.id.end_page_recommend_1);
        mSecondRecommendView = findViewById(R.id.end_page_recommend_2);

        mExitPageBtn.setOnClickListener(mClickListener);
        mReplayBtn.setOnClickListener(mClickListener);
        mFirstVideoView.setOnClickListener(mClickListener);
        mSecondVideoView.setOnClickListener(mClickListener);
        mFirstRecommendView.setOnClickListener(mClickListener);
        mSecondRecommendView.setOnClickListener(mClickListener);
    }

    private OnClickListener mClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.end_page_exit) {
                exitEndPage();
            } else if (v.getId() == R.id.end_page_replay) {
                jumpToReplayPage();
            } else if (v.getId() == R.id.end_page_shortVideo_1) {
                jumpToShortVideoPage("");
            } else if (v.getId() == R.id.end_page_shortVideo_2) {
                jumpToShortVideoPage("");
            } else if (v.getId() == R.id.end_page_recommend_1) {
                jumpToRecommendPage("");
            } else if (v.getId() == R.id.end_page_recommend_2) {
                jumpToRecommendPage("");
            }
        }
    };

    private void exitEndPage() {

    }

    private void jumpToReplayPage() {

    }

    private void jumpToShortVideoPage(String playUrl) {

    }

    private void jumpToRecommendPage(String liveUrl) {

    }

    private void doMtopRequest() {
        Map<String, String> params= new HashMap<>();
        params.put("liveId", "");
        EndPageDataMapper.getEndPageData(params, mListener);
    }

    private EndPageDataMapper.GetEndPageDataListener mListener = new EndPageDataMapper.GetEndPageDataListener() {
        @Override
        public void onCompleted(String response) {
            mEndPageModel = EndPageDataMapper.transformData(response);
            if (mEndPageModel != null) {
                setData(mEndPageModel);
            }
        }

        @Override
        public void onException(String error) {

        }
    };

    private void setData(EndPageModel model) {
        mActorNickTv.setText(model.nickName);

        mCoinNumTv.setText(model.coins + "");
        mWatchNumTv.setText(model.likeNum + "");
        mPopularityTv.setText(model.fansCount + "");

        if (0 == model.replay) {
            mReplayBtn.setVisibility(GONE);
        } else {
            mReplayBtn.setVisibility(VISIBLE);
        }

        if (model.shortVideoList != null && !model.shortVideoList.isEmpty()) {
            if (1 == model.shortVideoList.size()) {
                mFirstVideoView.setData(model.shortVideoList.get(0));
            } else if (2 == model.shortVideoList.size()) {
                mFirstVideoView.setData(model.shortVideoList.get(0));
                mSecondVideoView.setData(model.shortVideoList.get(1));
            }
        }

        if (model.recommendLiveList != null && !model.recommendLiveList.isEmpty()) {
            if (1 == model.recommendLiveList.size()) {
                mFirstRecommendView.setData(model.recommendLiveList.get(0));
            } else if (2 == model.recommendLiveList.size()) {
                mFirstRecommendView.setData(model.recommendLiveList.get(0));
                mSecondRecommendView.setData(model.recommendLiveList.get(1));
            }
        }
    }


}
