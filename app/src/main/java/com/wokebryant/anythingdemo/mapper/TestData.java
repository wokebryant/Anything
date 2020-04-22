package com.wokebryant.anythingdemo.mapper;

import com.wokebryant.anythingdemo.R;
import com.wokebryant.anythingdemo.model.ChiefPanelHostModel;
import com.wokebryant.anythingdemo.model.ChiefPanelManagerModel;

import java.util.ArrayList;
import java.util.List;

public class TestData {

    public static List<ChiefPanelManagerModel> getManagerData() {
        List<ChiefPanelManagerModel> managerModelList = new ArrayList<>();
        for (int i = 0;i<10;i++) {
            ChiefPanelManagerModel managerModel = new ChiefPanelManagerModel(i, R.drawable.round_head_test);
            managerModelList.add(managerModel);
        }
        return managerModelList;
    }

    public static List<ChiefPanelHostModel> getHostData() {
        List<ChiefPanelHostModel> hostModelList = new ArrayList<>();
        for (int i = 0;i < 10; i++) {
            ChiefPanelHostModel hostModel = new ChiefPanelHostModel(i,R.drawable.round_head_test);
            hostModelList.add(hostModel);
        }
        return hostModelList;
    }

}
