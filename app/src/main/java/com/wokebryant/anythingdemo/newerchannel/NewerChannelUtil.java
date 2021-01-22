package com.wokebryant.anythingdemo.newerchannel;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.wokebryant.anythingdemo.application.BaseApplication;
import com.wokebryant.anythingdemo.newerchannel.model.ShortVideoFeedData;
import com.wokebryant.anythingdemo.utils.BaseUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wb-lj589732
 */
public class NewerChannelUtil {

    private static final String FIRST_SHOWER_CHANNEL = "first_show_channel";
    //主播左下角内容区域     0：内容，1：标签，2：个性签名，3：空
    private int bottomInfoType;

    private Context context;

    private volatile static NewerChannelUtil instance;

    protected NewerChannelUtil() {
        this.context = BaseUtil.getApplicationContext();
    }

    public static NewerChannelUtil getInstance() {
        if (instance == null) {
            synchronized (NewerChannelUtil.class) {
                if (instance == null) {
                    instance = new NewerChannelUtil();
                }
            }
        }

        return instance;
    }

    public boolean isFirstShowNewerChannel() {
        if (context == null) {
            return false;
        }
        SharedPreferences sharedPreferences = context.getSharedPreferences(FIRST_SHOWER_CHANNEL, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean("isFirstShow", true);
    }

    public void updateShowNewerChannel(boolean show) {
        if (context == null) {
            return;
        }
        SharedPreferences sharedPreferences = context.getSharedPreferences(FIRST_SHOWER_CHANNEL, Context.MODE_PRIVATE);
        SharedPreferences.Editor mEditor = sharedPreferences.edit();
        mEditor.putBoolean("isFirstShow", show).apply();
    }

    public int getShowBottomType(ShortVideoFeedData data, int position) {
        if (haveContent(data.feeds, position)) {
            bottomInfoType = 0;
        } else {
            if (data.tags != null) {
                bottomInfoType = 1;
            } else {
                if (!TextUtils.isEmpty(data.signature)) {
                    bottomInfoType = 2;
                } else {
                    bottomInfoType = 3;
                }
            }
        }
        return bottomInfoType;
    }

    /**
     *  动态是否还有content
     */
    public boolean haveContent(List<ShortVideoFeedData.Feed> feedList, int position) {
        if (!feedList.isEmpty() && feedList.size() > position) {
            return !TextUtils.isEmpty(feedList.get(position).content);
        }
        return false;
    }

    /**
     *  获取图片列表
     * @param feedList
     * @return
     */
    public List<String> getFeedPhotoList(List<ShortVideoFeedData.Feed> feedList) {
        List<String> photoList = new ArrayList<>();
        if (!feedList.isEmpty()) {
            for (ShortVideoFeedData.Feed feed : feedList) {
                if (feed != null && feed.img != null) {
                    photoList.add(feed.img.url);
                }
            }
        }
        return photoList;
    }

    /**
     *  获取内容列表
     * @param feedList
     * @return
     */
    public List<String> getFeedContentList(List<ShortVideoFeedData.Feed> feedList) {
        List<String> contentList = new ArrayList<>();
        if (!feedList.isEmpty()) {
            for (ShortVideoFeedData.Feed feed : feedList) {
                if (feed != null) {
                    contentList.add(feed.content);
                }
            }
        }
        return contentList;
    }

    /**
     *  获取播放类型
     * @return
     */
    public List<Integer> getShowTypeList(List<ShortVideoFeedData.Feed> feedList) {
        List<Integer> showTypeList = new ArrayList<>();
        if (!feedList.isEmpty()) {
            for (ShortVideoFeedData.Feed feed : feedList) {
                if (feed != null) {
                    showTypeList.add(feed.dataType);
                }
            }
        }
        return showTypeList;
    }

    /**
     *  获取主播角标图片列表
     * @param markList
     * @return
     */
    public List<String> getMarkTagList(List<ShortVideoFeedData.Mark> markList) {
        List<String> markTagList = new ArrayList<>();
        if (!markList.isEmpty()) {
            for (ShortVideoFeedData.Mark mark : markList) {
                if (mark != null) {
                    markTagList.add(mark.androidContent);
                }
            }
        }
        return markTagList;
    }

    public ShortVideoFeedData getMockModel() {
        ShortVideoFeedData data = new ShortVideoFeedData();
        data.isFollow = false;
        data.extraExtend = getExtra();
        data.img = "https://image.laifeng.com/image/A46BE93017364E2AB27BDE300EE1C7D9?x-oss-process=image/crop,x_0,y_620,w_2160,h_2160/resize,w_600,h_600";
        data.liveType = getLiveType();
        data.signature = "相处起来舒服的关系是一份礼物";
        data.feeds = getFeedList();
        data.title = getMockContent();
        data.tags = null;
        data.mark = getMockMarkList();
        return data;
    }

    private ShortVideoFeedData.Extra getExtra() {
        ShortVideoFeedData.Extra extra = new ShortVideoFeedData.Extra();
        extra.anchorName = "你吼那么大声干嘛";
        extra.liveState = 1;
        return extra;
    }

    private ShortVideoFeedData.LiveType getLiveType() {
        ShortVideoFeedData.LiveType liveType = new ShortVideoFeedData.LiveType();
        liveType.type = 3;
        liveType.url = "https://img.alicdn.com/tfs/TB1PFUN0oz1gK0jSZLeXXb9kVXa-76-240.png";
        return liveType;
    }

    private List<ShortVideoFeedData.Feed> getFeedList() {
        List<ShortVideoFeedData.Feed> feedList = new ArrayList<>();
        for (int i = 0 ; i < 2; i++) {
            ShortVideoFeedData.Feed feed = new ShortVideoFeedData.Feed();
            ShortVideoFeedData.Feed.ImageInfo imageInfo = new ShortVideoFeedData.Feed.ImageInfo();
            imageInfo.url = "https://image.laifeng.com/image/9914D0B66A0B4C15B68DEDFE62E8A1D9?x-oss-process=image/crop,x_131,y_0,w_600,h_600/resize,w_600,h_600";
            feed.img = imageInfo;
            feed.dataType = 16;
            feed.content = getMockContent();
            feedList.add(feed);
        }
        for (int i = 0 ; i < 2; i++) {
            ShortVideoFeedData.Feed feed = new ShortVideoFeedData.Feed();
            ShortVideoFeedData.Feed.ImageInfo imageInfo = new ShortVideoFeedData.Feed.ImageInfo();
            imageInfo.url = "https://image.laifeng.com/image/0430A2C363634DBC96CBEB19EE6D937C?x-oss-process=image/crop,x_432,y_0,w_1728,h_1728/resize,w_600,h_600";
            feed.img = imageInfo;
            feed.dataType = 1;
            feed.content = "";
            feedList.add(feed);
        }
        for (int i = 0 ; i < 2; i++) {
            ShortVideoFeedData.Feed feed = new ShortVideoFeedData.Feed();
            ShortVideoFeedData.Feed.ImageInfo imageInfo = new ShortVideoFeedData.Feed.ImageInfo();
            imageInfo.url = "https://image.laifeng.com/image/AEFD05A35EEF4AE5904EAF2D2BE5FECA?x-oss-process=image/crop,x_0,y_284,w_1060,h_1064/resize,w_60,h_60";
            feed.img = imageInfo;
            feed.dataType = 1;
            feed.content = "嘻嘻嘻嘻嘻嘻嘻嘻嘻嘻嘻嘻嘻嘻嘻嘻嘻嘻嘻嘻嘻嘻嘻嘻嘻嘻嘻嘻嘻嘻嘻嘻嘻嘻嘻嘻嘻嘻嘻嘻嘻嘻";
            feedList.add(feed);
        }
        return feedList;
    }


    public List<String> getMockTagList() {
        List tagList = new ArrayList();
        tagList.add("古灵精怪");
        tagList.add("喜欢周杰伦");
        tagList.add("最强王者");
        return tagList;
    }

    public List<ShortVideoFeedData.Mark> getMockMarkList() {
        List<ShortVideoFeedData.Mark> markList = new ArrayList<>();
        ShortVideoFeedData.Mark mark = new ShortVideoFeedData.Mark();
        mark.androidContent = "https://img.alicdn.com/imgextra/i4/O1CN01bw72J41zoaSJRom2d_!!6000000006761-2-tps-148-40.png";
        markList.add(mark);
        markList.add(mark);
        return markList;
    }

    public String getMockContent() {
        String content = "今天的不开心就止于此吧，明天依旧万丈光芒～～不开心就今天的不开心就止于此吧，明天依旧万丈光芒～～不开心就今天的不开心就止于此吧，明天依旧万丈光芒～～不开心就今天的不开心就止于此吧，明天依旧万丈光芒～～不开心就今天的不开心就止于此吧，明天依旧万丈光芒～～不开心就这样吧";
        return content;
    }

}
