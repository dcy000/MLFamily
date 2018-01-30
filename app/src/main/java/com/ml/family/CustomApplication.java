package com.ml.family;

import android.app.Application;

import com.ml.family.call2.NimInitHelper;
import com.ml.family.utils.LocalShared;
import com.ml.family.utils.T;
import com.qiniu.android.storage.UploadManager;
import com.squareup.leakcanary.LeakCanary;


public class CustomApplication extends Application {
    private static CustomApplication mInstance;
    public String userToken;
    public String userId;

    public static CustomApplication getInstance(){
        return mInstance;
    }
    private static UploadManager uploadManager;
    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        LocalShared mLocalShared = LocalShared.getInstance(this);
        userToken = mLocalShared.getUserToken();
        userId = mLocalShared.getUserId();
        T.init(this);
        NimInitHelper.getInstance().init(this, true);
        LeakCanary.install(this);
        uploadManager=new UploadManager();
    }
    public static UploadManager getQiniuUploadManager(){
        return uploadManager;
    }
}
