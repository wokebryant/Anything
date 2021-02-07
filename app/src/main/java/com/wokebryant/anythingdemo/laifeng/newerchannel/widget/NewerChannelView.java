package com.wokebryant.anythingdemo.laifeng.newerchannel.widget;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.wokebryant.anythingdemo.R;
import com.wokebryant.anythingdemo.laifeng.newerchannel.ISmallPhotoClickCallBack;
import com.wokebryant.anythingdemo.laifeng.newerchannel.NewerChannelContract;
import com.wokebryant.anythingdemo.laifeng.newerchannel.SerMap;
import com.wokebryant.anythingdemo.laifeng.newerchannel.model.ShortVideoFeedData;
import com.wokebryant.anythingdemo.laifeng.newerchannel.presenter.NewerChannelPresenter;
import com.wokebryant.anythingdemo.widget.view.MultiStateView;

import java.util.HashMap;
import java.util.List;

/**
 * @author wb-lj589732
 *  新人频道互动层
 */
public class NewerChannelView extends FrameLayout implements NewerChannelContract.View {

    public static final int SHOW_CONTENT = 0;
    public static final int SHOW_TAG = 1;
    public static final int SHOW_SIGN = 2;
    // 1: 图文，16：视频
    public static final int SHOW_IMAGE = 1;

    private MultiStateView mStateView;
    private LinearLayout mLeftSpaceLL;
    private LinearLayout mRightSpaceLL;
    private LinearLayout mImageTagIv;
    private ImageView mAnchorTagIv;
    private ImageView mLocationIv;
    private TextView mNickTv;
    private LinearLayout mAttentionLl;
    private ImageView mAttentionIv;
    private TextView mAttentionTv;
    private FoldTextView mFoldTv;
    private SmallPhotoRecycleView mPhotoRv;
    private TagListView mTagListView;
    private FrameLayout mGoLiveFl;
    private ImageView mGiftAnimView;
    private ImageView mLiveAnimView;
    private ImageView mAvatarIv;
    private TextView mGoLiveTv;
    private LinearLayout mFirstGuideView;
    private FrameLayout mEmptySureFl;
    private NewerChannelContentDialog mContentDialog;

    private Context mContext;
    private NewerChannelContract.Presenter mPresenter;
    private ISmallPhotoClickCallBack mCallBack;

    public NewerChannelView(Context context) {
        this(context, null);
    }

    public NewerChannelView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NewerChannelView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
        initView();
    }

    private void init(Context context) {
        mContext = context;
        mPresenter = new NewerChannelPresenter();
        mPresenter.attachView(this);
    }

    private void initView() {
        View.inflate(mContext, R.layout.lf_newer_channel_layout, this);
        mStateView = findViewById(R.id.newer_channel_view);
        mLeftSpaceLL = findViewById(R.id.newer_channel_left_space_ll);
        mRightSpaceLL = findViewById(R.id.newer_channel_right_space_ll);
        mImageTagIv = findViewById(R.id.newer_channel_image_tag);
        mAnchorTagIv = findViewById(R.id.newer_channel_anchor_tag_iv);
        mLocationIv = findViewById(R.id.newer_channel_location_iv);
        mNickTv = findViewById(R.id.newer_channel_nick_tv);
        mAttentionLl = findViewById(R.id.newer_channel_attention_ll);
        mAttentionIv = findViewById(R.id.newer_channel_attention_iv);
        mAttentionTv = findViewById(R.id.newer_channel_attention_tv);
        mFoldTv = findViewById(R.id.newer_channel_short_content_tv);
        mPhotoRv = findViewById(R.id.newer_channel_small_pic_rv);
        mTagListView = findViewById(R.id.newer_channel_tag_list_view);
        mGoLiveFl = findViewById(R.id.newer_channel_go_live_fl);
        mGiftAnimView = findViewById(R.id.newer_channel_gift_anim_view);
        mLiveAnimView = findViewById(R.id.newer_channel_live_anim_view);
        mAvatarIv = findViewById(R.id.newer_channel_avatar_iv);
        mGoLiveTv = findViewById(R.id.newer_channel_go_live_tv);
        mFirstGuideView = findViewById(R.id.newer_channel_first_guide_view);
        mEmptySureFl = findViewById(R.id.newer_channel_empty__sure_fl);

        mPhotoRv.setOnSmallPicItemClickListener(smallPicItemClickListener);
        mFoldTv.setOnTipClickListener(onTipClickListener);
        mAttentionLl.setOnClickListener(onClickListener);
        mGoLiveFl.setOnClickListener(onClickListener);
        mFirstGuideView.setOnClickListener(onClickListener);
        mEmptySureFl.setOnClickListener(onClickListener);
    }

    private View.OnClickListener onClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mPresenter == null) {
                return;
            }
            if (v.getId() == R.id.newer_channel_attention_ll) {
                mPresenter.doAttention();
            } else if (v.getId() == R.id.newer_channel_go_live_fl) {
                // TODO: 2021/1/15 直播态进直播间/非直播态进个人主页
                mPresenter.goPage();

            } else if (v.getId() == R.id.newer_channel_first_guide_view) {
                mFirstGuideView.setVisibility(GONE);
            } else if (v.getId() == R.id.newer_channel_empty__sure_fl) {
                mPresenter.watchAgain();
            }
        }
    };

    /**
     *  查看更多监听
     */
    private FoldTextView.onTipClickListener onTipClickListener = new FoldTextView.onTipClickListener() {
        @Override
        public void onTipClick(boolean flag) {
            mPresenter.showDialog();
        }
    };

    /**
     *  小图点击监听
     */
    private NewerChannelContract.OnSmallPicItemClickListener smallPicItemClickListener = new NewerChannelContract
        .OnSmallPicItemClickListener() {
        @Override
        public void onClick(int position) {
            mPresenter.clickSmallPicItem(position);
            if (mCallBack != null) {
                mCallBack.onClick(position);
            }
        }
    };

    /**
     *  更多面板点击监听
     */
    private NewerChannelContract.OnContentMoreDialogClickLisenter moreDialogClick = new NewerChannelContract
        .OnContentMoreDialogClickLisenter() {
        @Override
        public void onPackUpClick(int position) {
            mPresenter.packUpDialog(position);
        }

        @Override
        public void onSmallItemClick(int position) {
            mPresenter.clickSmallPicItem(position);
            if (mCallBack != null) {
                mCallBack.onClick(position);
            }
        }

        @Override
        public void onAttentionClick() {
            mPresenter.doAttention();
        }
    };

    /**
     *  数据入口
     */
    public void initWithData(ShortVideoFeedData data) {
        if (mPresenter != null) {
            mPresenter.handleLocalData(data);
        }
    }

    /**
     *  大图切换调用
     * @param position
     */
    public void updateSmallPhotoPosition(int position) {
        mPresenter.flingPhoto(position);
    }

    /**
     *  点击小图调用
     */
    public void setOnSmallPhotoClickCallBack(ISmallPhotoClickCallBack callBack) {
        mCallBack = callBack;
    }

    @Override
    public void onRenderView(HashMap<String, Object> map, boolean showGuide, int showBottomType) {
        mFirstGuideView.setVisibility(showGuide ? VISIBLE : GONE);
        mNickTv.setText((CharSequence)map.get("nick"));
        mPhotoRv.setAdapter((List<String>)map.get("bigPhoto"), 0);
        mGoLiveTv.setText((CharSequence)map.get("liveContent"));

        onUpdateAttentionView((Boolean)map.get("isAttention"));
        onUpdateContentView(map.get("bottomData"), showBottomType, (Integer)map.get("showType"));

        ImageLoader.getInstance().displayImage((String)map.get("anchorMask"), mAnchorTagIv);
        ImageLoader.getInstance().displayImage((String)map.get("locationMask"), mLocationIv);
        ImageLoader.getInstance().displayImage((String)map.get("avatar"), mAvatarIv);
        ImageLoader.getInstance().displayImage((String)map.get("giftAnim"), mLiveAnimView);
        ImageLoader.getInstance().displayImage((String)map.get("liveAnim"), mGiftAnimView);
    }

    @Override
    public void onUpdateAttentionView(boolean isAttention) {
        if (isAttention) {
            mAttentionIv.setImageResource(R.drawable.lf_newer_channel_done);
            mAttentionTv.setVisibility(GONE);
        } else {
            mAttentionIv.setImageResource(R.drawable.lf_newer_channel_add);
            mAttentionTv.setVisibility(VISIBLE);
        }
    }

    @Override
    public void onShowMoreContentDialog(HashMap<String, Object> data) {
        if (data != null) {
            try {
                if (mContentDialog == null) {
                    mContentDialog = NewerChannelContentDialog.newInstance();
                    mContentDialog.setOnContentMoreDialogClickListener(moreDialogClick);
                }
                if (!mContentDialog.isAdded()) {
                    if (mContext instanceof FragmentActivity) {
                        SerMap serMap = new SerMap();
                        serMap.setMap(data);
                        Activity activity = (FragmentActivity)mContext;
                        FragmentManager manager = ((FragmentActivity)activity).getSupportFragmentManager();
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("userData", serMap);
                        mContentDialog.setArguments(bundle);
                        mContentDialog.show(manager, "NewerChannelContentDialog");
                    }
                }
                mLeftSpaceLL.setVisibility(GONE);
                mRightSpaceLL.setVisibility(GONE);
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onHideMoreContentDialog(int position) {
        if (mContentDialog != null && !mContentDialog.isHidden()) {
            mContentDialog.dismissAllowingStateLoss();
        }

        mLeftSpaceLL.setVisibility(VISIBLE);
        mRightSpaceLL.setVisibility(VISIBLE);
    }

    @Override
    public void onUpdateSmallPicListView(int position) {
        if (mPhotoRv != null) {
            mPhotoRv.updateSelectedItem(position);
        }
        if (mContentDialog != null && mContentDialog.isAdded()) {
            mContentDialog.updateSelectedItem(position);
        }
     }

    /**
     *  切换时更新底部内容和图片标签
     * @param content
     * @param showBottomType
     * @param showType
     */
    @Override
    public void onUpdateContentView(Object content, int showBottomType, int showType) {
        if (SHOW_CONTENT == showBottomType) {
            mFoldTv.setVisibility(VISIBLE);
            mTagListView.setVisibility(GONE);
            mFoldTv.setText((CharSequence)content);
        } else if (SHOW_TAG == showBottomType) {
            mFoldTv.setVisibility(GONE);
            mTagListView.setVisibility(VISIBLE);
            mTagListView.setTagAdapter((List<String>)content);
        } else if (SHOW_SIGN == showBottomType) {
            mFoldTv.setVisibility(VISIBLE);
            mTagListView.setVisibility(GONE);
            mFoldTv.setText((CharSequence)content);
        } else {
            mFoldTv.setVisibility(GONE);
            mTagListView.setVisibility(GONE);
        }

        if (SHOW_IMAGE == showType) {
            mImageTagIv.setVisibility(VISIBLE);
        } else {
            mImageTagIv.setVisibility(GONE);
        }
    }


    @Override
    public void onShowEmptyView() {
        mStateView.setViewState(MultiStateView.ViewState.EMPTY);
    }

    @Override
    public void onHideEmptyView() {
        mStateView.setViewState(MultiStateView.ViewState.CONTENT);
    }

    @Override
    public void onError() {

    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mPresenter != null) {
            mPresenter.detachView(this);
        }
    }

}
