package com.ml.family.bean;

/**
 * Created by gzq on 2017/12/2.
 */

public class BloodOxygenHistory {
    public long time;
    public float blood_oxygen;

    public BloodOxygenHistory(float blood_oxygen, long time) {
        this.time = time;
        this.blood_oxygen = blood_oxygen;
    }
}
