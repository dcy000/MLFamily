package com.ml.family.network;

import com.google.gson.reflect.TypeToken;
import com.ml.family.bean.BUA;
import com.ml.family.bean.BloodOxygenHistory;
import com.ml.family.bean.BloodPressureHistory;
import com.ml.family.bean.BloodSugarHistory;
import com.ml.family.bean.CholesterolHistory;
import com.ml.family.bean.ECGHistory;
import com.ml.family.bean.FamilyListBean;
import com.ml.family.bean.LoginBean;
import com.ml.family.bean.PatientDetailsBean;
import com.ml.family.bean.TemperatureHistory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NetworkApi {

    //    public static final String BasicUrl = "http://192.168.200.112:8080/ZZB/";
//    public static final String BasicUrl = "http://118.31.238.207:8080/ZZB/";
    public static final String BasicUrl = "http://116.62.36.12:8080/ZZB/";
    /**
     * 用户登录
     */
    public static final String Login = BasicUrl + "login/dependentslogin";
    /**
     * 患者列表
     */
    public static final String FamilyList = BasicUrl + "eq/eq_users";
    /**
     * 患者详情页
     */
    public static final String PatientDetails = BasicUrl + "br/docter_oneuser";
    private static String Get_HealthRecord = BasicUrl + "br/cl_data";
    public static final String TOKEN_URL = BasicUrl + "br/seltoken";

    public static final String UPLOAD_IMG = BasicUrl + "rep/insert_report";

    /**
     * 用户登录
     */
    public static void login(String username, String password, NetworkManager.SuccessCallback<LoginBean> listener, NetworkManager.FailedCallback failedCallback) {
        Map<String, String> map = new HashMap<>();
        map.put("username", username);
        map.put("password", password);
        NetworkManager.getInstance().postResultClass(Login, map, LoginBean.class, listener, failedCallback);
    }

    /**
     * 家人列表
     *
     * @param eqid
     * @param listener
     * @param failedCallback
     */
    public static void familyList(String eqid, NetworkManager.SuccessCallback<List<FamilyListBean>> listener, NetworkManager.FailedCallback failedCallback) {

        Map<String, String> map = new HashMap<>();
        map.put("eqid", eqid);
        NetworkManager.getInstance().getResultClass(FamilyList, map, new TypeToken<List<FamilyListBean>>() {
        }.getType(), listener, failedCallback);
    }

    /**
     * 患者详情页
     *
     * @param start
     * @param limit
     * @param listener
     * @param failedCallback
     */
    public static void patientDetails(String bid, int start, int limit, NetworkManager.SuccessCallback<List<PatientDetailsBean>> listener, NetworkManager.FailedCallback failedCallback) {

        Map<String, String> map = new HashMap<>();
        map.put("bid", bid);
        map.put("start", start + "");
        map.put("limit", limit + "");
        NetworkManager.getInstance().getResultClass(PatientDetails, map, new TypeToken<List<PatientDetailsBean>>() {
        }.getType(), listener, failedCallback);
    }

    /**
     * 获取体温历史数据
     *
     * @param successCallback
     */


    public static void getTemperatureHistory(String userid, String start, String end, String temp, NetworkManager.SuccessCallback<ArrayList<TemperatureHistory>> successCallback, NetworkManager.FailedCallback failedCallback
    ) {
        HashMap<String, String> params = new HashMap<>();

        params.put("bid", userid);
//        params.put("bid","100001");
        params.put("temp", temp);
        params.put("starttime", start);
        params.put("endtime", end);
        NetworkManager.getInstance().getResultClass(Get_HealthRecord, params, new TypeToken<ArrayList<TemperatureHistory>>() {
                }.getType(),
                successCallback, failedCallback);
    }

    /**
     * 血糖
     *
     * @param temp
     * @param successCallback
     */

    public static void getBloodSugarHistory(String userId, String start, String end, String temp, NetworkManager.SuccessCallback<ArrayList<BloodSugarHistory>> successCallback, NetworkManager.FailedCallback failedCallback
    ) {
        HashMap<String, String> params = new HashMap<>();
        params.put("bid", userId);
        params.put("temp", temp);
        params.put("starttime", start);
        params.put("endtime", end);
        NetworkManager.getInstance().getResultClass(Get_HealthRecord, params, new TypeToken<ArrayList<BloodSugarHistory>>() {
                }.getType(),
                successCallback, failedCallback);
    }

    /**
     * 血氧
     *
     * @param temp
     * @param successCallback
     */
    public static void getBloodOxygenHistory(String userid, String start, String end, String temp, NetworkManager.SuccessCallback<ArrayList<BloodOxygenHistory>> successCallback, NetworkManager.FailedCallback failedCallback
    ) {
        HashMap<String, String> params = new HashMap<>();
        params.put("bid", userid);
        params.put("temp", temp);
        params.put("starttime", start);
        params.put("endtime", end);
        NetworkManager.getInstance().getResultClass(Get_HealthRecord, params, new TypeToken<ArrayList<BloodOxygenHistory>>() {
                }.getType(),
                successCallback, failedCallback);
    }

    /**
     * 获取血压的历史数据
     *
     * @param temp
     * @param successCallback
     */
    public static void getBloodpressureHistory(String userid, String start, String end, String temp, NetworkManager.SuccessCallback<ArrayList<BloodPressureHistory>> successCallback, NetworkManager.FailedCallback failedCallback
    ) {
        HashMap<String, String> params = new HashMap<>();
        params.put("bid", userid);
//        params.put("bid","100001");
        params.put("temp", temp);
        params.put("starttime", start);
        params.put("endtime", end);
        NetworkManager.getInstance().getResultClass(Get_HealthRecord, params, new TypeToken<ArrayList<BloodPressureHistory>>() {
                }.getType(),
                successCallback, failedCallback);
    }

    /**
     * 血尿酸
     *
     * @param temp
     * @param successCallback
     */
    public static void getBUAHistory(String userId, String start, String end, String temp, NetworkManager.SuccessCallback<ArrayList<BUA>> successCallback, NetworkManager.FailedCallback failedCallback
    ) {
        HashMap<String, String> params = new HashMap<>();
        params.put("bid", userId);
//        params.put("bid","100001");
        params.put("temp", temp);
        params.put("starttime", start);
        params.put("endtime", end);
        NetworkManager.getInstance().getResultClass(Get_HealthRecord, params, new TypeToken<ArrayList<BUA>>() {
                }.getType(),
                successCallback, failedCallback);
    }

    /**
     * 胆固醇
     *
     * @param temp
     * @param successCallback
     */
    public static void getCholesterolHistory(String userId, String start, String end, String temp, NetworkManager.SuccessCallback<ArrayList<CholesterolHistory>> successCallback, NetworkManager.FailedCallback failedCallback
    ) {
        HashMap<String, String> params = new HashMap<>();
        params.put("bid", userId);
//        params.put("bid","100001");
        params.put("temp", temp);
        params.put("starttime", start);
        params.put("endtime", end);
        NetworkManager.getInstance().getResultClass(Get_HealthRecord, params, new TypeToken<ArrayList<CholesterolHistory>>() {
                }.getType(),
                successCallback, failedCallback);
    }

    /**
     * 心电
     *
     * @param temp
     * @param successCallback
     */
    public static void getECGHistory(String userId, String start, String end, String temp, NetworkManager.SuccessCallback<ArrayList<ECGHistory>> successCallback, NetworkManager.FailedCallback failedCallback
    ) {
        HashMap<String, String> params = new HashMap<>();
        params.put("bid", userId);
//        params.put("bid", "100001");
        params.put("temp", temp);
        params.put("starttime", start);
        params.put("endtime", end);
        NetworkManager.getInstance().getResultClass(Get_HealthRecord, params, new TypeToken<ArrayList<ECGHistory>>() {
                }.getType(),
                successCallback, failedCallback);
    }

    /**
     * 获取七牛token
     *
     * @param listener
     * @param failedCallback
     */
    public static void get_token(NetworkManager.SuccessCallback<String> listener, NetworkManager.FailedCallback failedCallback) {
        Map<String, String> paramsMap = new HashMap<>();
        NetworkManager.getInstance().postResultString(TOKEN_URL, paramsMap, listener, failedCallback);
    }

    /**
     * 将七牛获取到的地址上传到我们的服务器
     *
     * @param purl
     * @param userid
     * @param pmessage
     * @param pu1
     * @param pu2
     * @param pu3
     * @param successCallback
     * @param failedCallback
     */
    public static void uploadImg(String purl, String userid, String pmessage, String pu1, String pu2, String pu3,
                                 NetworkManager.SuccessCallback<String> successCallback,
                                 NetworkManager.FailedCallback failedCallback) {
        HashMap<String, String> params = new HashMap<>();
        params.put("purl", purl);
        params.put("userid", userid);
        params.put("pmessage", pmessage);
        params.put("pu1", pu1);
        params.put("pu2", pu2);
        params.put("pu3", pu3);
        NetworkManager.getInstance().postResultString(UPLOAD_IMG, params, successCallback, failedCallback);
    }
}
