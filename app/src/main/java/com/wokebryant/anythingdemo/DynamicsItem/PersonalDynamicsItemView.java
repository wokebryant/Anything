package com.wokebryant.anythingdemo.DynamicsItem;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wokebryant.anythingdemo.DynamicsItem.adapter.DynamicsItemAdapter;
import com.wokebryant.anythingdemo.DynamicsItem.decorate.DynamicsItemClickCallBack;
import com.wokebryant.anythingdemo.DynamicsItem.item.BaseDynamicsItem;
import com.wokebryant.anythingdemo.DynamicsItem.item.DynamicsPhotoItem;
import com.wokebryant.anythingdemo.DynamicsItem.item.DynamicsShortVideoItem;
import com.wokebryant.anythingdemo.DynamicsItem.model.DynamicsItemModel;
import com.wokebryant.anythingdemo.DynamicsItem.model.DynamicsShortVideoModel;
import com.wokebryant.anythingdemo.PersonSetting.MulitTypeRV.GridSpacingItemDecoration;
import com.wokebryant.anythingdemo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 个人动态item
 * @author wb-lj589732
 */

public class PersonalDynamicsItemView extends LinearLayout {

    private static final String TAG = "PersonalDynamicsItemVie";

    private DynamicsItemAdapter mAdapter;
    private List<BaseDynamicsItem> mPhotoList;

    private ImageView mAvatarView;
    private TextView mNickView;
    private TextView mGenderAgeView;
    private TextView mTimestampDistanceView;
    private TextView mContextTextView;
    private RecyclerView mMediaView;
    private TextView mCommentView;
    private ImageView mPraiseImageView;
    private TextView mPraiseTextView;
    private ImageView mTeaseView;

    private DynamicsItemModel mDynamicsItemModel;

    private GridSpacingItemDecoration mItemDecoration;
    private DynamicsItemClickCallBack dynamicsItemClickCallBack;



    public PersonalDynamicsItemView(Context context) {
        this(context, null);
    }

    public PersonalDynamicsItemView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PersonalDynamicsItemView(Context context, AttributeSet attrs,
                                    int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.lf_personal_dynamics_item, this, true);
        mAvatarView = findViewById(R.id.lf_dynamics_avatar);
        mNickView = findViewById(R.id.lf_dynamics_nick);
        mGenderAgeView = findViewById(R.id.lf_dynamics_gender_age);
        mTimestampDistanceView = findViewById(R.id.lf_dynamics_timestamp_distance);
        mContextTextView = findViewById(R.id.lf_dynamics_text);
        mMediaView = findViewById(R.id.lf_dynamics_media);
        mCommentView = findViewById(R.id.lf_dynamics_comment);
        mPraiseImageView = findViewById(R.id.lf_dynamics_praise_iv);
        mPraiseTextView = findViewById(R.id.lf_dynamics_praise_tv);
        mTeaseView = findViewById(R.id.dynamics_tease);

        mAvatarView.setOnClickListener(onClickListener);
        mCommentView.setOnClickListener(onClickListener);
        mPraiseImageView.setOnClickListener(onClickListener);
        mPraiseTextView.setOnClickListener(onClickListener);
        mTeaseView.setOnClickListener(onClickListener);
        this.setOnClickListener(onClickListener);
    }

    public void setData(DynamicsItemModel model) {
        if (model == null) {
            return;
        }
        mDynamicsItemModel = model;
        mAvatarView.setImageResource(mDynamicsItemModel.avatar);
        mNickView.setText(mDynamicsItemModel.nick);
        mGenderAgeView.setText(mDynamicsItemModel.age);
        mTimestampDistanceView.setText(mDynamicsItemModel.publishTime + "·" + mDynamicsItemModel.distance);
        mContextTextView.setText(mDynamicsItemModel.textContent);
        mCommentView.setText(mDynamicsItemModel.commentNum + "");
        mPraiseTextView.setText(mDynamicsItemModel.praise + "");
        setPhotoData(mDynamicsItemModel.photoList);
        setShortVideoData(mDynamicsItemModel.shortVideo);
    }

    private void setPhotoData(List<Integer> photoList) {
        if (photoList != null && !photoList.isEmpty()) {
            mAdapter = new DynamicsItemAdapter(transformPhotoList(photoList));
            mAdapter.setOnItemClickListener(onItemClickListener);
            mMediaView.setLayoutManager(getGridLayoutManager(photoList.size()));
            if (photoList.size() != 1) {
                mMediaView.removeItemDecoration(mItemDecoration);
                mMediaView.addItemDecoration(getSpaceItemDecoration(photoList.size()));
            }
            mMediaView.setAdapter(mAdapter);
        }
    }

    private void setShortVideoData(DynamicsShortVideoModel shortVideoModel) {
        if(shortVideoModel != null) {
            mAdapter = new DynamicsItemAdapter(transformShortVideo(shortVideoModel));
            mAdapter.setOnItemClickListener(onItemClickListener);
            mMediaView.setLayoutManager(getGridLayoutManager(1));
            mMediaView.setAdapter(mAdapter);
        }
    }

    private List<DynamicsPhotoItem> transformPhotoList(List<Integer> list) {
        List<DynamicsPhotoItem> photoItemList = new ArrayList<>();
        for (Integer integer : list) {
            DynamicsPhotoItem dynamicsItem = new DynamicsPhotoItem(integer.intValue());
            photoItemList.add(dynamicsItem);
        }
        return photoItemList;
    }

    private List<DynamicsShortVideoItem> transformShortVideo(DynamicsShortVideoModel shortVideoModel) {
        List<DynamicsShortVideoItem> shortVideoItemList = new ArrayList<>();
        DynamicsShortVideoItem shortVideoItem = new DynamicsShortVideoItem(shortVideoModel.playUrl
                , shortVideoModel.duration, shortVideoModel.onlineNum);
        shortVideoItemList.add(shortVideoItem);
        return shortVideoItemList;
    }

    /**
     * 设置图片布局
     * @param size
     * @return
     */
    private GridLayoutManager getGridLayoutManager(int size) {
        GridLayoutManager manager;
        if (1 == size) {
            manager = new GridLayoutManager(getContext(), 1);
        } else if (2 == size || 4 == size) {
            manager = new GridLayoutManager(getContext(), 2);
        } else {
            manager = new GridLayoutManager(getContext(), 3);
        }
        return manager;
    }

    /**
     * 获取图片间隔
     * @param size
     * @return
     */
    private GridSpacingItemDecoration getSpaceItemDecoration(int size) {
        if (size == 2 || size == 4) {
            mItemDecoration = new GridSpacingItemDecoration(2, 10, false);
        } else {
            mItemDecoration = new GridSpacingItemDecoration(3, 10, false);
        }
        return mItemDecoration;
    }

    private DynamicsItemAdapter.OnItemClickListener onItemClickListener = new DynamicsItemAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(String mediaType, int position) {
            if (BaseDynamicsItem.TYPE_SHORT_VIDEO.equals(mediaType)) {
                playShortVideo();
                Toast.makeText(getContext(), "点击播放短视频", Toast.LENGTH_SHORT).show();
            } else if (BaseDynamicsItem.TYPE_REPLAY.equals(mediaType)) {
                playReplay();
                Toast.makeText(getContext(), "点击播放回放", Toast.LENGTH_SHORT).show();
            } else if (BaseDynamicsItem.TYPE_PHOTO.equals(mediaType)) {
                Toast.makeText(getContext(), "这是第" + position + "张图片", Toast.LENGTH_SHORT).show();
            }
        }
    };

    private View.OnClickListener onClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.lf_dynamics_comment) {
                commentDynamics();
            }else if (v.getId() == R.id.lf_dynamics_praise_iv || v.getId() == R.id.lf_dynamics_praise_tv) {
                praiseDynamics();
            } else if (v.getId() == R.id.dynamics_tease) {
                teaseDynamics();
            } else {
                enterDynamicsDetails();
            }
        }
    };

    /**
     * 进入详情页
     */
    private void enterDynamicsDetails() {
        Toast.makeText(getContext(), "进入详情页", Toast.LENGTH_SHORT).show();
    }

    /**
     * 播放回放
     */
    private void playReplay() {
        if (dynamicsItemClickCallBack != null) {
            dynamicsItemClickCallBack.onReplayClick("");
        }
    }

    /**
     * 播放短视频
     *
     */
    private void playShortVideo() {
        if (dynamicsItemClickCallBack != null) {
            dynamicsItemClickCallBack.onShortVideoClick("");
        }
    }


    /**
     * 评论
     */
    private void commentDynamics() {
        if (dynamicsItemClickCallBack != null) {
            dynamicsItemClickCallBack.onCommentClick();
        }
    }

    /**
     * 点赞
     */
    private void praiseDynamics() {
        if (dynamicsItemClickCallBack != null) {
            dynamicsItemClickCallBack.onPraiseClick();
        }
    }

    /**
     * 撩一撩
     */
    private void teaseDynamics() {
        if (dynamicsItemClickCallBack != null) {
            dynamicsItemClickCallBack.onTeaseClick();
        }
    }

    /**
     * 设置Item点击回调
     * @param callBack
     */
    public void setDynamicsItemClickCallBack(DynamicsItemClickCallBack callBack) {
        dynamicsItemClickCallBack = callBack;
    }


}
