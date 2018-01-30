package com.ml.family.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.ml.family.R;
import com.ml.family.adapter.PatientListAdapter;
import com.ml.family.bean.UserBean;
import com.ml.family.call2.NimAccountHelper;
import com.ml.family.network.NetworkApi;
import com.ml.family.network.NetworkManager;
import com.ml.family.utils.LocalShared;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {


    @BindView(R.id.list)
    RecyclerView list;
    @BindView(R.id.login_out)
    TextView loginOut;
    private int limit = 500;
    private static String TAG = "MainActivity";
    private PatientListAdapter adapter;
    private List<UserBean> mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        NimAccountHelper.getInstance().login(LocalShared.getInstance(this).getEqID(), "123456",null);
        setHat();
        mData=new ArrayList<>();
        setAdapter(mData);
        getData(null);

        loginOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LocalShared.getInstance(MainActivity.this).clearSP();
                NimAccountHelper.getInstance().logout();
                finish();
            }
        });
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
                new NetworkManager.SuccessCallback<List<UserBean>>() {
                    @Override
                    public void onSuccess(List<UserBean> response) {
                        mData .addAll(response);
                        adapter.notifyDataSetChanged();
                        hideLoadingDialog();
                    }
                }, new NetworkManager.FailedCallback() {
                    @Override
                    public void onFailed(String message) {
                        Log.e(TAG, message);
                        hideLoadingDialog();
                    }
                });

    }

    private void setAdapter(List<UserBean> response) {
        list.setLayoutManager(new LinearLayoutManager(this));
//        list.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        list.setAdapter(adapter = new PatientListAdapter(R.layout.patient_item, response));

        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                //跳转到患者详情页
                startActivity(new Intent(MainActivity.this, PatientDetailsActivity.class)
                        .putExtra("data", mData.get(position)));
            }
        });
    }

    @Override
    protected void hideLeftImg(ImageView mLeftImage) {
        mLeftImage.setVisibility(View.GONE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        NimAccountHelper.getInstance().logout();
    }
}
