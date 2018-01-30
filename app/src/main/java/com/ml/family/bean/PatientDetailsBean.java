package com.ml.family.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by gzq on 2017/11/22.
 */

public class PatientDetailsBean implements Parcelable{
    private String zid,eq,time,temper_ature,high_pressure,low_pressure,
            heart_rate,blood_sugar,blood_oxygen,pulse,ecg,yz,diagnosis,eqid,bname,state,hl;
    private User user;

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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    protected PatientDetailsBean(Parcel in) {
        zid = in.readString();
        eq = in.readString();
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
        user = in.readParcelable(User.class.getClassLoader());
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(zid);
        dest.writeString(eq);
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
        dest.writeParcelable(user, flags);
    }

    public static class User implements Parcelable{
        private String bid;
        private String categoryid;
        private String doct;
        private String eq;
        private String bname;
        private String sex;
        private String dz;
        private String age;
        private String sfz;
        private String tel;
        private String mh;
        private String eqid;
        private String state;
        private String qyzt;
        private String height;
        private String weight;
        private String blood_type;
        private String eating_habits;
        private String smoke;
        private String drink;
        private String exercise_habits;
        private String user_photo;

        protected User(Parcel in) {
            bid = in.readString();
            categoryid = in.readString();
            doct = in.readString();
            eq = in.readString();
            bname = in.readString();
            sex = in.readString();
            dz = in.readString();
            age = in.readString();
            sfz = in.readString();
            tel = in.readString();
            mh = in.readString();
            eqid = in.readString();
            state = in.readString();
            qyzt = in.readString();
            height = in.readString();
            weight = in.readString();
            blood_type = in.readString();
            eating_habits = in.readString();
            smoke = in.readString();
            drink = in.readString();
            exercise_habits = in.readString();
            user_photo = in.readString();
        }

        public static final Creator<User> CREATOR = new Creator<User>() {
            @Override
            public User createFromParcel(Parcel in) {
                return new User(in);
            }

            @Override
            public User[] newArray(int size) {
                return new User[size];
            }
        };

        public String getBid() {
            return bid;
        }

        public void setBid(String bid) {
            this.bid = bid;
        }

        public String getCategoryid() {
            return categoryid;
        }

        public void setCategoryid(String categoryid) {
            this.categoryid = categoryid;
        }

        public String getDoct() {
            return doct;
        }

        public void setDoct(String doct) {
            this.doct = doct;
        }

        public String getEq() {
            return eq;
        }

        public void setEq(String eq) {
            this.eq = eq;
        }

        public String getBname() {
            return bname;
        }

        public void setBname(String bname) {
            this.bname = bname;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getDz() {
            return dz;
        }

        public void setDz(String dz) {
            this.dz = dz;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }

        public String getSfz() {
            return sfz;
        }

        public void setSfz(String sfz) {
            this.sfz = sfz;
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

        public String getEqid() {
            return eqid;
        }

        public void setEqid(String eqid) {
            this.eqid = eqid;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getQyzt() {
            return qyzt;
        }

        public void setQyzt(String qyzt) {
            this.qyzt = qyzt;
        }

        public String getHeight() {
            return height;
        }

        public void setHeight(String height) {
            this.height = height;
        }

        public String getWeight() {
            return weight;
        }

        public void setWeight(String weight) {
            this.weight = weight;
        }

        public String getBlood_type() {
            return blood_type;
        }

        public void setBlood_type(String blood_type) {
            this.blood_type = blood_type;
        }

        public String getEating_habits() {
            return eating_habits;
        }

        public void setEating_habits(String eating_habits) {
            this.eating_habits = eating_habits;
        }

        public String getSmoke() {
            return smoke;
        }

        public void setSmoke(String smoke) {
            this.smoke = smoke;
        }

        public String getDrink() {
            return drink;
        }

        public void setDrink(String drink) {
            this.drink = drink;
        }

        public String getExercise_habits() {
            return exercise_habits;
        }

        public void setExercise_habits(String exercise_habits) {
            this.exercise_habits = exercise_habits;
        }

        public String getUser_photo() {
            return user_photo;
        }

        public void setUser_photo(String user_photo) {
            this.user_photo = user_photo;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(bid);
            dest.writeString(categoryid);
            dest.writeString(doct);
            dest.writeString(eq);
            dest.writeString(bname);
            dest.writeString(sex);
            dest.writeString(dz);
            dest.writeString(age);
            dest.writeString(sfz);
            dest.writeString(tel);
            dest.writeString(mh);
            dest.writeString(eqid);
            dest.writeString(state);
            dest.writeString(qyzt);
            dest.writeString(height);
            dest.writeString(weight);
            dest.writeString(blood_type);
            dest.writeString(eating_habits);
            dest.writeString(smoke);
            dest.writeString(drink);
            dest.writeString(exercise_habits);
            dest.writeString(user_photo);
        }
    }
}
