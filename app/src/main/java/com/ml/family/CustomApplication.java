package com.ml.family;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.ml.family.call2.NimInitHelper;
import com.ml.family.utils.LocalShared;
import com.ml.family.utils.T;
import com.ml.family.utils.ToastUtil;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;
import com.qiniu.android.storage.UploadManager;
import com.squareup.leakcanary.LeakCanary;
import com.vondear.rxtools.RxTool;


import cn.jpush.android.api.JPushInterface;


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
        ToastUtil.init(this);
        //初始化极光
        JPushInterface.setDebugMode(BuildConfig.DEBUG);
        JPushInterface.init(this);
        //初始化日志库
        FormatStrategy formatStrategy= PrettyFormatStrategy.newBuilder()
                .showThreadInfo(true)  // (Optional) Whether to show thread info or not. Default true
                .methodOffset(7)        // (Optional) Hides internal method calls up to offset. Default 5
                .build();
        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy){
            @Override
            public boolean isLoggable(int priority, String tag) {
                return BuildConfig.DEBUG;
            }
        });
        //初始化工具库
        RxTool.init(this);
    }
    public static UploadManager getQiniuUploadManager(){
        return uploadManager;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
