package com.ml.doctor.network;

import com.google.gson.reflect.TypeToken;
import com.ml.doctor.CustomApplication;
import com.ml.doctor.bean.BloodOxygenHistory;
import com.ml.doctor.bean.BloodPressureHistory;
import com.ml.doctor.bean.BloodSugarHistory;
import com.ml.doctor.bean.FamilyListBean;
import com.ml.doctor.bean.LoginBean;
import com.ml.doctor.bean.PatientDetailsBean;
import com.ml.doctor.bean.TemperatureHistory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NetworkApi {

    public static final String BasicUrl = "http://192.168.200.117:8080/ZZB/";
//    public static final String BasicUrl = "http://118.31.238.207:8080/ZZB/";
    /**
     * 用户登录
     */
    public static final String Login = BasicUrl + "login/dependentslogin";
    /**
     * 患者列表
     */
    public static final String FamilyList=BasicUrl+"eq/eq_users";
    /**
     * 患者详情页
     */
    public static final String PatientDetails=BasicUrl+"br/docter_oneuser";
    private static String Get_HealthRecord=BasicUrl+"br/cl_data";

    /**
     * 用户登录
     */
    public static void login(String username,String password,NetworkManager.SuccessCallback<LoginBean> listener,NetworkManager.FailedCallback failedCallback){
        Map<String,String> map=new HashMap<>();
        map.put("username",username);
        map.put("password",password);
        NetworkManager.getInstance().postResultClass(Login,map, LoginBean.class,listener,failedCallback);
    }

    /**
     * 家人列表
     * @param eqid
     * @param listener
     * @param failedCallback
     */
    public static void familyList(String eqid, NetworkManager.SuccessCallback<List<FamilyListBean>> listener, NetworkManager.FailedCallback failedCallback){

        Map<String,String> map=new HashMap<>();
        map.put("eqid",eqid);
        NetworkManager.getInstance().getResultClass(FamilyList,map,new TypeToken<List<FamilyListBean>>() {
        }.getType(),listener,failedCallback);
    }

    /**
     * 患者详情页
     * @param start
     * @param limit
     * @param listener
     * @param failedCallback
     */
    public static void patientDetails(String bid, int start, int limit, NetworkManager.SuccessCallback<List<PatientDetailsBean>> listener, NetworkManager.FailedCallback failedCallback){

        Map<String,String> map=new HashMap<>();
        map.put("bid",bid);
        map.put("start",start+"");
        map.put("limit",limit+"");
        NetworkManager.getInstance().getResultClass(PatientDetails,map,new TypeToken<List<PatientDetailsBean>>() {
        }.getType(),listener,failedCallback);
    }

    /**
     * 获取体温历史数据
     *
     * @param successCallback
     */


    public static void getTemperatureHistory(String userid,String start, String end, String temp, NetworkManager.SuccessCallback<ArrayList<TemperatureHistory>> successCallback, NetworkManager.FailedCallback failedCallback
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
}
