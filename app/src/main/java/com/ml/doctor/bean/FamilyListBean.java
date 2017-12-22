package com.ml.doctor.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**患者列表
 * Created by gzq on 2017/11/21.
 */

public class FamilyListBean implements Parcelable{
    private String exercise_habits;
    private String sfz;
    private String sex;
    private String eqid;
    private String smoke;
    private String doid;
    private String weight;
    private String drink;
    private String xfid;
    private String bname;
    private String dz;
    private String blood_type;
    private String tel;
    private String mh;
    private String state;
    private String eating_habits;
    private String bid;
    private String user_photo;
    private String age;
    private String categoryid;
    private String height;

    public FamilyListBean() {
    }

    protected FamilyListBean(Parcel in) {
        exercise_habits = in.readString();
        sfz = in.readString();
        sex = in.readString();
        eqid = in.readString();
        smoke = in.readString();
        doid = in.readString();
        weight = in.readString();
        drink = in.readString();
        xfid = in.readString();
        bname = in.readString();
        dz = in.readString();
        blood_type = in.readString();
        tel = in.readString();
        mh = in.readString();
        state = in.readString();
        eating_habits = in.readString();
        bid = in.readString();
        user_photo = in.readString();
        age = in.readString();
        categoryid = in.readString();
        height = in.readString();
    }

    public static final Creator<FamilyListBean> CREATOR = new Creator<FamilyListBean>() {
        @Override
        public FamilyListBean createFromParcel(Parcel in) {
            return new FamilyListBean(in);
        }

        @Override
        public FamilyListBean[] newArray(int size) {
            return new FamilyListBean[size];
        }
    };

    public String getExercise_habits() {
        return exercise_habits;
    }

    public void setExercise_habits(String exercise_habits) {
        this.exercise_habits = exercise_habits;
    }

    public String getSfz() {
        return sfz;
    }

    public void setSfz(String sfz) {
        this.sfz = sfz;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getEqid() {
        return eqid;
    }

    public void setEqid(String eqid) {
        this.eqid = eqid;
    }

    public String getSmoke() {
        return smoke;
    }

    public void setSmoke(String smoke) {
        this.smoke = smoke;
    }

    public String getDoid() {
        return doid;
    }

    public void setDoid(String doid) {
        this.doid = doid;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getDrink() {
        return drink;
    }

    public void setDrink(String drink) {
        this.drink = drink;
    }

    public String getXfid() {
        return xfid;
    }

    public void setXfid(String xfid) {
        this.xfid = xfid;
    }

    public String getBname() {
        return bname;
    }

    public void setBname(String bname) {
        this.bname = bname;
    }

    public String getDz() {
        return dz;
    }

    public void setDz(String dz) {
        this.dz = dz;
    }

    public String getBlood_type() {
        return blood_type;
    }

    public void setBlood_type(String blood_type) {
        this.blood_type = blood_type;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getMh() {
        return mh;
    }

    public void setMh(String mh) {
        this.mh = mh;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getEating_habits() {
        return eating_habits;
    }

    public void setEating_habits(String eating_habits) {
        this.eating_habits = eating_habits;
    }

    public String getBid() {
        return bid;
    }

    public void setBid(String bid) {
        this.bid = bid;
    }

    public String getUser_photo() {
        return user_photo;
    }

    public void setUser_photo(String user_photo) {
        this.user_photo = user_photo;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getCategoryid() {
        return categoryid;
    }

    public void setCategoryid(String categoryid) {
        this.categoryid = categoryid;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(exercise_habits);
        parcel.writeString(sfz);
        parcel.writeString(sex);
        parcel.writeString(eqid);
        parcel.writeString(smoke);
        parcel.writeString(doid);
        parcel.writeString(weight);
        parcel.writeString(drink);
        parcel.writeString(xfid);
        parcel.writeString(bname);
        parcel.writeString(dz);
        parcel.writeString(blood_type);
        parcel.writeString(tel);
        parcel.writeString(mh);
        parcel.writeString(state);
        parcel.writeString(eating_habits);
        parcel.writeString(bid);
        parcel.writeString(user_photo);
        parcel.writeString(age);
        parcel.writeString(categoryid);
        parcel.writeString(height);
    }
}
