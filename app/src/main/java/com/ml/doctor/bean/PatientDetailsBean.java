package com.ml.doctor.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by gzq on 2017/11/22.
 */

public class PatientDetailsBean implements Parcelable{
    private String zid,eq,user,time,temper_ature,high_pressure,low_pressure,
            heart_rate,blood_sugar,blood_oxygen,pulse,ecg,yz,diagnosis,eqid,bname,state,hl;

    protected PatientDetailsBean(Parcel in) {
        zid = in.readString();
        eq = in.readString();
        user = in.readString();
        time = in.readString();
        temper_ature = in.readString();
        high_pressure = in.readString();
        low_pressure = in.readString();
        heart_rate = in.readString();
        blood_sugar = in.readString();
        blood_oxygen = in.readString();
        pulse = in.readString();
        ecg = in.readString();
        yz = in.readString();
        diagnosis = in.readString();
        eqid = in.readString();
        bname = in.readString();
        state = in.readString();
        hl = in.readString();
    }

    public static final Creator<PatientDetailsBean> CREATOR = new Creator<PatientDetailsBean>() {
        @Override
        public PatientDetailsBean createFromParcel(Parcel in) {
            return new PatientDetailsBean(in);
        }

        @Override
        public PatientDetailsBean[] newArray(int size) {
            return new PatientDetailsBean[size];
        }
    };

    public String getZid() {
        return zid;
    }

    public void setZid(String zid) {
        this.zid = zid;
    }

    public String getEq() {
        return eq;
    }

    public void setEq(String eq) {
        this.eq = eq;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTemper_ature() {
        return temper_ature;
    }

    public void setTemper_ature(String temper_ature) {
        this.temper_ature = temper_ature;
    }

    public String getHigh_pressure() {
        return high_pressure;
    }

    public void setHigh_pressure(String high_pressure) {
        this.high_pressure = high_pressure;
    }

    public String getLow_pressure() {
        return low_pressure;
    }

    public void setLow_pressure(String low_pressure) {
        this.low_pressure = low_pressure;
    }

    public String getHeart_rate() {
        return heart_rate;
    }

    public void setHeart_rate(String heart_rate) {
        this.heart_rate = heart_rate;
    }

    public String getBlood_sugar() {
        return blood_sugar;
    }

    public void setBlood_sugar(String blood_sugar) {
        this.blood_sugar = blood_sugar;
    }

    public String getBlood_oxygen() {
        return blood_oxygen;
    }

    public void setBlood_oxygen(String blood_oxygen) {
        this.blood_oxygen = blood_oxygen;
    }

    public String getPulse() {
        return pulse;
    }

    public void setPulse(String pulse) {
        this.pulse = pulse;
    }

    public String getEcg() {
        return ecg;
    }

    public void setEcg(String ecg) {
        this.ecg = ecg;
    }

    public String getYz() {
        return yz;
    }

    public void setYz(String yz) {
        this.yz = yz;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getEqid() {
        return eqid;
    }

    public void setEqid(String eqid) {
        this.eqid = eqid;
    }

    public String getBname() {
        return bname;
    }

    public void setBname(String bname) {
        this.bname = bname;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getHl() {
        return hl;
    }

    public void setHl(String hl) {
        this.hl = hl;
    }

    @Override
    public String toString() {
        return "PatientDetailsBean{" +
                "zid='" + zid + '\'' +
                ", eq='" + eq + '\'' +
                ", user='" + user + '\'' +
                ", time='" + time + '\'' +
                ", temper_ature='" + temper_ature + '\'' +
                ", high_pressure='" + high_pressure + '\'' +
                ", low_pressure='" + low_pressure + '\'' +
                ", heart_rate='" + heart_rate + '\'' +
                ", blood_sugar='" + blood_sugar + '\'' +
                ", blood_oxygen='" + blood_oxygen + '\'' +
                ", pulse='" + pulse + '\'' +
                ", ecg='" + ecg + '\'' +
                ", yz='" + yz + '\'' +
                ", diagnosis='" + diagnosis + '\'' +
                ", eqid='" + eqid + '\'' +
                ", bname='" + bname + '\'' +
                ", state='" + state + '\'' +
                ", hl='" + hl + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(zid);
        dest.writeString(eq);
        dest.writeString(user);
        dest.writeString(time);
        dest.writeString(temper_ature);
        dest.writeString(high_pressure);
        dest.writeString(low_pressure);
        dest.writeString(heart_rate);
        dest.writeString(blood_sugar);
        dest.writeString(blood_oxygen);
        dest.writeString(pulse);
        dest.writeString(ecg);
        dest.writeString(yz);
        dest.writeString(diagnosis);
        dest.writeString(eqid);
        dest.writeString(bname);
        dest.writeString(state);
        dest.writeString(hl);
    }
}
