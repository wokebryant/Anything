package com.wokebryant.anythingdemo.dynamicsitem.model;

import com.wokebryant.anythingdemo.R;

import java.util.ArrayList;
import java.util.List;

public class MockDataUtil {

    public static DynamicsModel getDynamicsData() {
        DynamicsModel dynamicsModel = new DynamicsModel();

        List<DynamicsItemModel> itemModelList = new ArrayList<>();
        itemModelList.add(getDynamicsItemModel(0, true));
        itemModelList.add(getDynamicsItemModel(1, true));
        itemModelList.add(getDynamicsItemModel(1, false));
        itemModelList.add(getDynamicsItemModel(2, false));
        itemModelList.add(getDynamicsItemModel(3, true));
        itemModelList.add(getDynamicsItemModel(4, false));
        itemModelList.add(getDynamicsItemModel(5, false));
        itemModelList.add(getDynamicsItemModel(6, false));
        itemModelList.add(getDynamicsItemModel(7, false));
        itemModelList.add(getDynamicsItemModel(8, false));
        itemModelList.add(getDynamicsItemModel(9, false));

        dynamicsModel.setData(itemModelList);
        return dynamicsModel;
    }

    public static DynamicsItemModel getDynamicsItemModel(int size, boolean hasVideo) {
        DynamicsItemModel itemModel = new DynamicsItemModel();
        itemModel.setAvatar(R.drawable.lf_avatar);
        itemModel.setNick("带带大师兄");
        itemModel.setGender("男");
        itemModel.setAge("30");
        itemModel.setPublishTime("10月1日 20:00");
        itemModel.setDistance("222km");
        itemModel.setTextContent("你吼那么大声干嘛，那你去找物管啊，你再骂？***");
        itemModel.setCommentNum(105);
        itemModel.setPraise(1747);
        if (hasVideo) {
            itemModel.setShortVideo(getDynamicsShortVideoModel());
        }

        itemModel.setPhotoList(getDynamicsPhotoList(size));
        return itemModel;
    }

    public static List<Integer> getDynamicsPhotoList(int size) {
        List<Integer> photoList = new ArrayList<>();
        for (int i=0; i<size; i++) {
            photoList.add(R.drawable.sxc);
        }
        return photoList;
    }

    public static DynamicsShortVideoModel getDynamicsShortVideoModel() {
        DynamicsShortVideoModel shortVideoModel = new DynamicsShortVideoModel();
        shortVideoModel.setPlayUrl("url");
        shortVideoModel.setDuration("03:23:45");
        shortVideoModel.setOnlineNum("14w");
        return shortVideoModel;
    }
}
