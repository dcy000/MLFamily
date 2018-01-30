package com.ml.family.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by gzq on 2018/1/30.
 */

public class RecordBean  implements Parcelable{
    private String pmessage;
    private String pid;
    private String purl;
    private String pu2;
    private String userid;
    private String ptime;
    private String pu1;
    private String pu3;

    public String getPmessage() {
        return pmessage;
    }

    public void setPmessage(String pmessage) {
        this.pmessage = pmessage;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getPurl() {
        return purl;
    }

    public void setPurl(String purl) {
        this.purl = purl;
    }

    public String getPu2() {
        return pu2;
    }

    public void setPu2(String pu2) {
        this.pu2 = pu2;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getPtime() {
        return ptime;
    }

    public void setPtime(String ptime) {
        this.ptime = ptime;
    }

    public String getPu1() {
        return pu1;
    }

    public void setPu1(String pu1) {
        this.pu1 = pu1;
    }

    public String getPu3() {
        return pu3;
    }

    public void setPu3(String pu3) {
        this.pu3 = pu3;
    }

    protected RecordBean(Parcel in) {
        pmessage = in.readString();
        pid = in.readString();
        purl = in.readString();
        pu2 = in.readString();
        userid = in.readString();
        ptime = in.readString();
        pu1 = in.readString();
        pu3 = in.readString();
    }

    public static final Creator<RecordBean> CREATOR = new Creator<RecordBean>() {
        @Override
        public RecordBean createFromParcel(Parcel in) {
            return new RecordBean(in);
        }

        @Override
        public RecordBean[] newArray(int size) {
            return new RecordBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(pmessage);
        dest.writeString(pid);
        dest.writeString(purl);
        dest.writeString(pu2);
        dest.writeString(userid);
        dest.writeString(ptime);
        dest.writeString(pu1);
        dest.writeString(pu3);
    }
//    private UserBean user;


}
