package com.wokebryant.anythingdemo.DynamicsItem.model;

import com.wokebryant.anythingdemo.DynamicsItem.item.DynamicsShortVideoItem;

import java.io.Serializable;
import java.util.List;

public class DynamicsItemModel implements Serializable{

    public int avatar;

    public String nick;

    public String gender;

    public String age;

    public String publishTime;

    public String distance;

    public String textContent;

    public List<Integer> photoList;

    public DynamicsShortVideoModel shortVideo;

    public String replayUrl;

    public int commentNum;

    public int praise;

    public void setAvatar(int avatar) {
        this.avatar = avatar;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public void setTextContent(String textContent) {
        this.textContent = textContent;
    }

    public void setPhotoList(List<Integer> photoList) {
        this.photoList = photoList;
    }

    public void setShortVideo(DynamicsShortVideoModel shortVideo) {
        this.shortVideo = shortVideo;
    }

    public void setReplayUrl(String replayUrl) {
        this.replayUrl = replayUrl;
    }

    public void setCommentNum(int commentNum) {
        this.commentNum = commentNum;
    }

    public void setPraise(int praise) {
        this.praise = praise;
    }


}
