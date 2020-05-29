package com.wokebryant.anythingdemo.mapper;

import com.wokebryant.anythingdemo.model.EndPageModel;

import java.util.Map;

public class EndPageDataMapper {

    public static void getEndPageData(Map<String, String> params, GetEndPageDataListener listener) {


    }



    public static EndPageModel transformData(String responseData) {
        EndPageModel endPageModel = null;

        try {
            if (responseData != null) {
                //endPageModel = FastJsonTools.deserialize(responseData, EndPageModel.class);
            }
        } catch (Exception e) {

        }

        return endPageModel;
    }

    public interface GetEndPageDataListener {

        void onCompleted(String response);

        void onException(String error);

    }

}
