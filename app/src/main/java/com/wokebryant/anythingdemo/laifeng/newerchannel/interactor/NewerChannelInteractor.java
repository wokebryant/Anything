package com.wokebryant.anythingdemo.laifeng.newerchannel.interactor;

import com.wokebryant.anythingdemo.laifeng.newerchannel.NewerChannelContract;
import com.wokebryant.anythingdemo.laifeng.newerchannel.mapper.NewerChannelMapper;
import com.wokebryant.anythingdemo.laifeng.newerchannel.model.ShortVideoFeedData;

import java.util.Map;

/**
 * @author wb-lj589732
 *  新人频道互动层数据请求
 */
public class NewerChannelInteractor implements NewerChannelContract.Interactor {

    @Override
    public void requestRemoteData(Map<String, String> params, NewerChannelContract.RequestDataListener requestNetListener) {
        ShortVideoFeedData model = NewerChannelMapper.transFormDataToViewModel("");
        if (requestNetListener != null) {
            if (model != null) {
                requestNetListener.requestSuc(model);
            } else {
                requestNetListener.requestFail("");
            }
        }
    }

}
