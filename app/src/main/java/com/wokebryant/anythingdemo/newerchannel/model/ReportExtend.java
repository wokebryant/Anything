package com.wokebryant.anythingdemo.newerchannel.model;

import java.io.Serializable;

public class ReportExtend implements Serializable {

    public String spmAB;
    public String spmC;
    public String spmD;

    public String scmAB;
    public String scmC;
    public String scmD;

    public String pageName;
    public int index;

    public TrackInfo trackInfo;

    public class TrackInfo implements Serializable {
        public String component_id;

        public long scg_id;

        public long servertime;

        public long drawerid;

        public String bizKey;

        public String cms_req_id;
    }

}
