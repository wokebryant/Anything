package com.wokebryant.anythingdemo.laifeng.newerchannel;


import com.wokebryant.anythingdemo.mvp.BasePresenter;
import com.wokebryant.anythingdemo.mvp.BaseView;
import com.wokebryant.anythingdemo.laifeng.newerchannel.model.ShortVideoFeedData;

import java.util.HashMap;
import java.util.Map;

/**
 *  新人频道互动层接口管理
 *  @author wb-lj589732
 */
public interface NewerChannelContract {

    interface View extends BaseView {
        void onRenderView(HashMap<String, Object> model, boolean showGuide, int showBottomType);

        void onUpdateAttentionView(boolean isAttention);

        void onShowMoreContentDialog(HashMap<String, Object> data);

        void onHideMoreContentDialog(int position);

        void onUpdateSmallPicListView(int position);

        void onUpdateContentView(Object model, int bottomType, int showType);

        void onShowEmptyView();

        void onHideEmptyView();

    }

    interface Presenter<M> extends BasePresenter<View> {

        void handleRemoteData();

        void handleLocalData(M model);

        void doAttention();

        void goPage();

        void flingPhoto(int position);

        void showDialog();

        void packUpDialog(int position);

        void clickSmallPicItem(int position);

        void watchAgain();

    }

    interface Interactor {
        void requestRemoteData(Map<String, String> params, RequestDataListener requestNetListener);
    }

    interface RequestDataListener {

        void requestSuc(ShortVideoFeedData model);

        void requestFail(String error);

    }

    /**
     *  主播内容“更多”点击监听
     */
    interface OnContentMoreDialogClickLisenter {
        void onPackUpClick(int position);

        void onSmallItemClick(int position);

        void onAttentionClick();
    }

    /**
     *  照片轮播滑动监听
     */
    interface OnPhotoWallFlipperListener{
        void onFling(int displayPosition);
    }

    /**
     *  小图RecycleView item点击监听
     */
    interface OnSmallPicItemClickListener{
        void onClick(int position);
    }

}

