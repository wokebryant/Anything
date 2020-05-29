package com.wokebryant.anythingdemo.model;

import java.io.Serializable;
import java.util.ArrayList;

public class EndPageModel implements Serializable {

    public String nickName;
    public String avatar;
    public long coins;
    public long likeNum;
    public long fansCount;
    public int replay;
    public String replayCode;
    public String replayUrl;
    public ArrayList<EndPageShortVideoModel> shortVideoList;
    public ArrayList<EndPageRecommendModel> recommendLiveList;

}
