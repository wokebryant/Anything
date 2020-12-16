package com.wokebryant.anythingdemo.laifeng.personsetting.MulitTypeRV.model;

import java.io.Serializable;
import java.util.List;

/**
 * @author wb-lj589732
 */
public class SettingModel implements Serializable {

    public String yid;
    public String ytid;
    public String faceUrl;
    public String voice;
    public List<String> phone;
    public Status status;
    public String signature;
    public String age;
    public String anchorLevel;
    public String userLevel;
    public Gender gender;
    public String constellation;
    public String height;
    public String nickName;
    public String duration;
    public City city;
    public Job job;
    public Emotion emotion;
    public String distance;
    public List<String> tag;
    public List<String> topFans;
    public String fanCount;
    public String attentionCount;
    public String hometown;
    public Edu edu;
    public String income;
    public HasCar hasCar;
    public HasHouse hasHouse;
    public HomeInfo homeInfo;
    public Appointment appointment;
    public Smoke smoke;
    public Drink drink;
    public Ext ext;


    public static class Status {



    }

    public static class Gender {

    }

    public static class City {

    }

    public static class Job {

    }

    public static class Emotion {

    }

    public static class Edu {

    }

    public static class HasCar {

    }

    public static class HasHouse {

    }

    public static class HomeInfo {

    }

    public static class Appointment {

    }

    public static class Smoke {

    }

    public static class Drink {

    }

    public static class Ext {

    }


}
