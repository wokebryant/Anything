package com.wokebryant.anythingdemo.newerchannel.presenter;

import android.text.TextUtils;
import android.util.Log;

import com.wokebryant.anythingdemo.newerchannel.NewerChannelContract;
import com.wokebryant.anythingdemo.newerchannel.NewerChannelUtil;
import com.wokebryant.anythingdemo.newerchannel.interactor.NewerChannelInteractor;
import com.wokebryant.anythingdemo.newerchannel.model.ShortVideoFeedData;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wb-lj589732
 *  新人频道互动层逻辑处理
 */
public class NewerChannelPresenter implements NewerChannelContract.Presenter<ShortVideoFeedData> {

    public static final int SHOW_CONTENT = 0;
    public static final int SHOW_TAG = 1;
    public static final int SHOW_SIGN = 2;

    private NewerChannelContract.View mView;
    private NewerChannelContract.Interactor mInteractor;
    private ShortVideoFeedData mModel;
    private HashMap<String, Object> mDataMap;

    private int position;
    private boolean isAttention;

    public NewerChannelPresenter() {
        mInteractor = new NewerChannelInteractor();
    }

    @Override
    public void handleRemoteData() {
        if (isViewAttached(mView)) {
            return;
        }
        Map<String,String> params = new HashMap<>();
        mInteractor.requestRemoteData(params, new NewerChannelContract.RequestDataListener() {
            @Override
            public void requestSuc(ShortVideoFeedData model) {
                handleData(model);
            }

            @Override
            public void requestFail(String error) {
                mView.onError();
            }
        });
    }

    @Override
    public void handleLocalData(ShortVideoFeedData model) {
        handleData(model);
    }

    private void handleData(ShortVideoFeedData model) {
        //if (model == null) {
        //    return;
        //}
        model = NewerChannelUtil.getInstance().getMockModel();
        mModel = model;
        mDataMap = parseData(model);
        if (NewerChannelUtil.getInstance().isFirstShowNewerChannel()) {
            mView.onRenderView(mDataMap, true, NewerChannelUtil.getInstance().getShowBottomType(model, 0));
            NewerChannelUtil.getInstance().updateShowNewerChannel(false);
        } else {
            mView.onRenderView(mDataMap, false, NewerChannelUtil.getInstance().getShowBottomType(model, 0));
        }
    }

    private HashMap<String, Object> parseData(ShortVideoFeedData model) {
        if (model == null) {
            return null;
        }
        HashMap<String, Object> data = new HashMap<>();
        if (model.extraExtend != null) {
            data.put("nick", model.extraExtend.anchorName);
        }
        data.put("smallPhoto", NewerChannelUtil.getInstance().getFeedPhotoList(model.feeds));
        data.put("bigPhoto", NewerChannelUtil.getInstance().getFeedPhotoList(model.feeds));
        data.put("liveContent", model.extraExtend != null && 1 == model.extraExtend.liveState ? "直播中" : "去主页");
        data.put("isAttention", model.isFollow);
        data.put("contentList", NewerChannelUtil.getInstance().getFeedContentList(model.feeds));
        data.put("sign", model.signature);
        data.put("tagList", model.tags);
        data.put("anchorMask", !model.mark.isEmpty() ? (model.mark.get(0)).androidContent : "");
        data.put("locationMask", !model.mark.isEmpty() && model.mark.size() > 1 ? (model.mark.get(1)).androidContent : "");
        data.put("avatar", model.img);
        data.put("giftAnim", model.liveType != null && 2 == model.liveType.type ? model.liveType.url : "");
        data.put("liveAnim", model.liveType != null && 3 == model.liveType.type ? model.liveType.url : "");
        data.put("bottomData", getBottomData(data, NewerChannelUtil.getInstance().getShowBottomType(model, position)));
        data.put("showType", NewerChannelUtil.getInstance().getShowTypeList(model.feeds).get(position));

        return data;
    }

    private Object getBottomData(HashMap<String, Object> map, int showBottomType) {
        Object o = null;
        if (SHOW_CONTENT == showBottomType) {
            o = ((List<String>)map.get("contentList")).get(position);
        } else if (SHOW_TAG == showBottomType) {
            o = map.get("tagList");
        } else if (SHOW_SIGN == showBottomType) {
            o = map.get("sign");
        }

        return o;
    }

    @Override
    public void doAttention() {
        mDataMap.put("isAttention", true);
        mView.onUpdateAttentionView(true);
    }

    /**
     *  去个人主页或者直播间
     */
    @Override
    public void goPage() {

    }

    @Override
    public void flingPhoto(int position) {
        this.position = position;
        int showBottomType = NewerChannelUtil.getInstance().getShowBottomType(mModel, position);
        int showType = NewerChannelUtil.getInstance().getShowTypeList(mModel.feeds).get(position);
        mView.onUpdateContentView(getBottomData(mDataMap, showBottomType), showBottomType, showType);
        mView.onUpdateSmallPicListView(position);
        Log.i("flingPosition", position + "");
    }

    @Override
    public void showDialog() {
        HashMap<String, Object> data = new HashMap<>();
        data.put("contentList", mDataMap.get("contentList"));
        data.put("nick", mDataMap.get("nick"));
        data.put("photoList", mDataMap.get("smallPhoto"));
        data.put("position", position);
        data.put("attention", mDataMap.get("isAttention"));
        mView.onShowMoreContentDialog(data);
    }

    @Override
    public void packUpDialog(int position) {
        mView.onHideMoreContentDialog(position);
        //mView.onUpdateSmallPicListView(position);
    }

    @Override
    public void clickSmallPicItem(int position) {
        this.position = position;
        int showBottomType = NewerChannelUtil.getInstance().getShowBottomType(mModel, position);
        int showType = NewerChannelUtil.getInstance().getShowTypeList(mModel.feeds).get(position);
        mView.onUpdateContentView(getBottomData(mDataMap, showBottomType), showBottomType, showType);
    }

    @Override
    public void watchAgain() {

    }

    @Override
    public void attachView(NewerChannelContract.View view) {
        mView = view;
    }

    @Override
    public void detachView(NewerChannelContract.View view) {
        mView = null;
    }

    @Override
    public boolean isViewAttached(NewerChannelContract.View view) {
        return mView == null;
    }

}
