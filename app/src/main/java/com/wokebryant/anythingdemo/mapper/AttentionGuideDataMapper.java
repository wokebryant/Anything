package com.wokebryant.anythingdemo.mapper;

import com.wokebryant.anythingdemo.model.AttentionGuideModel;

import org.json.JSONException;
import org.json.JSONObject;

public class AttentionGuideDataMapper {

    public interface OnRequestSuccessListener {
        void requestSuccess(AttentionGuideModel model);

        void requestFail();
    }

    public void requestAttentionMtop(OnRequestSuccessListener listener) {
        //todo
    }

    public AttentionGuideModel transformResponse(MtopResponse mtopResponse) {
        if (mtopResponse == null) {
            throw new IllegalArgumentException("Cannot transform a null value");
        }
        AttentionGuideModel attentionGuideModel = null;

        final JSONObject dataObj = mtopResponse.getDataJsonObject();
        if (dataObj != null) {
            try{
                attentionGuideModel = FastJsonTools.deserialize(dataObj.toString(),AttentionGuideModel.class);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return attentionGuideModel;
    }


}
