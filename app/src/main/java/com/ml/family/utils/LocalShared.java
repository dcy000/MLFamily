package com.ml.family.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

public class LocalShared {
    private final String SharedName = "ScopeMediaPrefsFile";
    private static LocalShared mInstance;
    private SharedPreferences mShared;
    /**
     * 登录用户的id
     */
    private final String UserId = "user_id";
    /**
     * token值
     */
    private final String UserToken = "user_token";
    /**
     * 用户的昵称
     */
    private final String UserNick="user_nick";
    /**
     * 用户的电话号码
     */
    private final String UserPhone="user_phone";
    /**
     * 仪器id
     */
    private final String EqID="eq_id";


    private LocalShared(Context context){
        mShared = context.getSharedPreferences(SharedName, Context.MODE_PRIVATE);
    }

    public static LocalShared getInstance(Context context){
        if (mInstance == null){
            mInstance = new LocalShared(context);
        }
        return mInstance;
    }

    public String getUserToken(){
        return mShared.getString(UserToken, "");
    }

    public String getUserId(){
        return mShared.getString(UserId, "");
    }
    public String getUserNick() {
        return mShared.getString(UserNick,"");
    }

    public String getUserPhone() {
        return mShared.getString(UserPhone,"");
    }

    /**
     * 保存userId到SP
     * @param userId
     */
    public void setUserId(String userId){
        if (TextUtils.isEmpty(userId)) {
            return;
        }
        mShared.edit().putString(UserId, userId).commit();
    }

    /**
     * 保存userToken到SP
     * @param userToken
     */
    public void setUserToken(String userToken){
        if (TextUtils.isEmpty(userToken)) {
            return;
        }
        mShared.edit().putString(UserToken, userToken).commit();
    }

    /**
     * 保存userNick到SP
     * @param userNick
     */
    public void setUserNick(String userNick){
        if (TextUtils.isEmpty(userNick)) {
            return;
        }
        mShared.edit().putString(UserNick, userNick).commit();
    }
    /**
     * 保存userNick到SP
     * @param userPhone
     */
    public void setUserPhone(String userPhone){
        if (TextUtils.isEmpty(userPhone)) {
            return;
        }
        mShared.edit().putString(UserPhone, userPhone).commit();
    }

    /**
     * 将仪器id保存到sp中去
     * @param eqid
     */
    public void setEqID(String eqid){
        if(TextUtils.isEmpty(eqid))
            return;
        mShared.edit().putString(EqID,eqid).commit();
    }
    public String getEqID(){
        return mShared.getString(EqID,"");
    }
    public void loginOut(){
        mShared.edit().clear();
    }

    private static final String NIM_ACCOUNT = "nim_account";
    private static final String NIM_TOKEN = "nim_token";

    public void setNimAccount(String account) {
        mShared.edit().putString(NIM_ACCOUNT, account).apply();
    }

    public String getNimAccount() {
        return mShared.getString(NIM_ACCOUNT, "");
    }

    public void setNimToken(String token) {
        mShared.edit().putString(NIM_TOKEN, token).apply();
    }

    public String getNimToken() {
        return mShared.getString(NIM_TOKEN, "");
    }

    /**
     * 清空SP
     */
    public void clearSP(){
        mShared.edit().clear().commit();
    }
}
