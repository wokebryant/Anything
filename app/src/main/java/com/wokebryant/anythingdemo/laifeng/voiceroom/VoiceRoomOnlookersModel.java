package com.wokebryant.anythingdemo.laifeng.voiceroom;

import java.io.Serializable;
import java.util.List;

/**
 *  语音房间围观面板数据model
 */
public class VoiceRoomOnlookersModel implements Serializable {

    public int totalNum;
    public List<OnlookersListItem> onlookersList;

    public static class OnlookersListItem implements Serializable {

        public String yid;
        public String ytid;
        public String serialNum;
        public String avatarUrl;
        public String nick;
        public List<String> medalList;
        public boolean isAttention;

    }

}
