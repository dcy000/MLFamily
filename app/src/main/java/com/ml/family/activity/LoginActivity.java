package com.ml.family.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.ml.family.R;
import com.ml.family.bean.LoginBean;
import com.ml.family.call2.NimAccountHelper;
import com.ml.family.dialog.LoadingDialog;
import com.ml.family.network.NetworkApi;
import com.ml.family.network.NetworkManager;
import com.ml.family.permissions.PermissionsManager;
import com.ml.family.permissions.PermissionsResultAction;
import com.ml.family.utils.LocalShared;
import com.ml.family.utils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private static String TAG="LoginActivity";
    @BindView(R.id.name)
    EditText name;
    @BindView(R.id.password)
    EditText password;
    private LoadingDialog mLoadingDialog;
    @BindView(R.id.login)
    TextView login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        PermissionsManager.getInstance().requestAllManifestPermissionsIfNecessary(this, new PermissionsResultAction() {
            @Override
            public void onGranted() {
            }

            @Override
            public void onDenied(String permission) {
            }
        });
        setOnclick();
        if(!TextUtils.isEmpty(LocalShared.getInstance(this).getUserId())){
            NimAccountHelper.getInstance().login(LocalShared.getInstance(this).getEqID(), "123456",null);
            startActivity(new Intent(LoginActivity.this,MainActivity.class));//跳转到列表界面
            ToastUtil.showShort(getString(R.string.login_success));
            finish();
        }
    }

    private void setOnclick() {
        login.setOnClickListener(this);

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login:
                login();
                break;
        }
    }

    /**
     * 用户登录
     */
    private void login() {
        final String user=name.getText().toString().trim();
        String pwd=password.getText().toString().trim();
        if(TextUtils.isEmpty(user)){
            ToastUtil.showShort(getString(R.string.name_tip));
            return;
        }
        if(TextUtils.isEmpty(pwd)){
            ToastUtil.showShort(getString(R.string.pwd_tip));
            return;
        }
        //显示加载dialog
        showLoadingDialog("正在登陆");
        NetworkApi.login(user,pwd, new NetworkManager.SuccessCallback<LoginBean>() {
            @Override
            public void onSuccess(LoginBean response) {
                NimAccountHelper.getInstance().login(response.getEqid(), "123456",null);
                startActivity(new Intent(LoginActivity.this,MainActivity.class));//跳转到列表界面
                ToastUtil.showShort(getString(R.string.login_success));
                Log.e(TAG,response.toString());
                saveToLocal(response);
                hideLoadingDialog();
                finish();
            }
        }, new NetworkManager.FailedCallback() {
            @Override
            public void onFailed(String message) {
                ToastUtil.showShort(message);
                hideLoadingDialog();
            }
        });

    }

    /**
     * 保存一些基本信心到SP中
     */
    private void saveToLocal(LoginBean response) {
        LocalShared.getInstance(this).setUserId(response.getBid());
        LocalShared.getInstance(this).setUserNick(response.getBname());
        LocalShared.getInstance(this).setUserPhone(response.getTel());
        LocalShared.getInstance(this).setEqID(response.getEqid());
    }

    public void showLoadingDialog(String message) {
        if (mLoadingDialog == null) {
            mLoadingDialog = new LoadingDialog(this, message);
        }
        mLoadingDialog.show();
    }
    public void hideLoadingDialog() {
        if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
            mLoadingDialog.dismiss();
        }
    }
}
