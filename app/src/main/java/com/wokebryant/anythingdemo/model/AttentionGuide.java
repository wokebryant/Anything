package com.wokebryant.anythingdemo.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class AttentionGuide implements Parcelable {

    public ArrayList<GuideInfo> guideArray;

    protected AttentionGuide(Parcel in) {
        this.guideArray = in.createTypedArrayList(GuideInfo.CREATOR);
    }

    public static final Creator<AttentionGuide> CREATOR = new Creator<AttentionGuide>() {
        @Override
        public AttentionGuide createFromParcel(Parcel in) {
            return new AttentionGuide(in);
        }

        @Override
        public AttentionGuide[] newArray(int size) {
            return new AttentionGuide[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.guideArray);
    }


    public static class GuideInfo implements Parcelable{

        public Conditions conditions;
        public int hideTime;
        public boolean open;
        public int period;
        public String type;


        protected GuideInfo(Parcel in) {
            this.conditions = in.readParcelable(Thread.currentThread().getContextClassLoader());
            this.hideTime = in.readInt();
            this.open = in.readByte() != 0;
            this.period = in.readInt();
            this.type = in.readString();
        }

        public static final Creator<GuideInfo> CREATOR = new Creator<GuideInfo>() {
            @Override
            public GuideInfo createFromParcel(Parcel in) {
                return new GuideInfo(in);
            }

            @Override
            public GuideInfo[] newArray(int size) {
                return new GuideInfo[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeParcelable(this.conditions,0);
            dest.writeInt(this.hideTime);
            dest.writeByte((byte)(this.open ? 1:0));
            dest.writeInt(this.period);
            dest.writeString(this.type);
        }
    }



    public static class Conditions implements Parcelable{

        public boolean gift;
        public int chatCount;
        public int liveTime;

        protected Conditions(Parcel in) {
            this.gift = in.readByte() != 0;
            this.chatCount = in.readInt();
            this.liveTime = in.readInt();
        }

        public static final Creator<Conditions> CREATOR = new Creator<Conditions>() {
            @Override
            public Conditions createFromParcel(Parcel in) {
                return new Conditions(in);
            }

            @Override
            public Conditions[] newArray(int size) {
                return new Conditions[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeByte((byte)(this.gift ? 1:0));
            dest.writeInt(this.chatCount);
            dest.writeInt(this.liveTime);
        }
    }

}
