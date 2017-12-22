package com.ml.doctor.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.ml.doctor.R;
import com.ml.doctor.adapter.PatientListAdapter;
import com.ml.doctor.bean.FamilyListBean;
import com.ml.doctor.network.NetworkApi;
import com.ml.doctor.network.NetworkManager;
import com.ml.doctor.utils.LocalShared;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {


    @BindView(R.id.list)
    RecyclerView list;
    private int limit=500;
    private static String TAG="MainActivity";
    private PatientListAdapter adapter;
    private List<FamilyListBean> mData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setHat();
        getData(null);
    }


    private void setHat() {
        setTopTitle("我的家人");

    }

    @Override
    protected void onLeftViewClick() {
        finish();
    }

    //请求网络数据
    private void getData(String bname) {
        showLoadingDialog();
        NetworkApi.familyList(LocalShared.getInstance(this).getEqID(),
                new NetworkManager.SuccessCallback<List<FamilyListBean>>() {
            @Override
            public void onSuccess(List<FamilyListBean> response) {
                mData=response;
                setAdapter(response);
                hideLoadingDialog();
            }
        }, new NetworkManager.FailedCallback() {
            @Override
            public void onFailed(String message) {
                Log.e(TAG,message);
                hideLoadingDialog();
            }
        });

    }

    private void setAdapter(List<FamilyListBean> response) {
        list.setLayoutManager(new LinearLayoutManager(this));
//        list.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        list.setAdapter(adapter=new PatientListAdapter(R.layout.patient_item,response));

        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                //跳转到患者详情页
                startActivity(new Intent(MainActivity.this,PatientDetailsActivity.class)
                        .putExtra("data",mData.get(position)));
            }
        });
    }

    @Override
    protected void hideLeftImg(ImageView mLeftImage) {
        mLeftImage.setVisibility(View.GONE);
    }
}
