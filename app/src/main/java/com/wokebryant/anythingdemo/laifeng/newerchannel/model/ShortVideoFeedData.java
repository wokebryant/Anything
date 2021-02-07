package com.wokebryant.anythingdemo.laifeng.newerchannel.model;

import java.io.Serializable;
import java.util.List;

/**
 * @author wb-lj589732
 *  新人频道互动层数据模型
 */
public class ShortVideoFeedData implements Serializable {

    public String img;

    // 标题-直播间名
    public String title;

    // 主播标签信息
    public List<String> tags;

    public boolean isFollow;

    // 个性签名
    public String signature;

    public String distiance;

    public Extra extraExtend;

    public Action action;

    // 直播间类型
    public LiveType liveType;

    public List<Feed> feeds;

    // 角标信息
    public List<Mark> mark;


    public static class LiveType implements Serializable {
        public int type;

        public String url;
    }


    public static class Extra implements Serializable{

        // 直播间ID
        public long liveId;

        // 主播名称
        public String anchorName;

        // 场次ID
        public long screenId;

        // 直播间状态 0 预约 1 直播中 2 直播结束
        public int liveState;
    }


    public static class Feed implements Serializable {

        // 动态类型 1 图文 16 短视频
        public int dataType;

        public String content;

        // 视频Id
        public String vid;

        // 动态数据ID
        public int dataId;

        public String videoUrl;

        // 动态id
        public long feedId;

        public ImageInfo img;

        public static class ImageInfo {
            public String url;

            public String width;

            public String height;
        }
    }

    public static class Mark implements Serializable {
        public String androidContent;

        // 左中偏下(大卡角标)
        public int position;

        // 类型 1 图片
        public int type;
    }


}
