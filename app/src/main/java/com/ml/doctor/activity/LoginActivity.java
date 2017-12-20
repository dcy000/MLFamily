package com.ml.doctor.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ml.doctor.CustomApplication;
import com.ml.doctor.R;
import com.ml.doctor.bean.LoginBean;
import com.ml.doctor.call2.NimAccountHelper;
import com.ml.doctor.dialog.LoadingDialog;
import com.ml.doctor.network.NetworkApi;
import com.ml.doctor.network.NetworkManager;
import com.ml.doctor.utils.LocalShared;
import com.ml.doctor.utils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private static String TAG="LoginActivity";
    @BindView(R.id.welcome_text)
    TextView welcomeText;
    @BindView(R.id.name)
    EditText name;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.register)
    Button register;
    @BindView(R.id.login)
    Button login;
    private LoadingDialog mLoadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        setOnclick();

    }

    private void setOnclick() {
        register.setOnClickListener(this);
        login.setOnClickListener(this);

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.register://注册 跳转到注册页面
                ToastUtil.showShort(this,"注册");
                break;
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
            ToastUtil.showShort(LoginActivity.this,getString(R.string.name_tip));
            return;
        }
        if(TextUtils.isEmpty(pwd)){
            ToastUtil.showShort(LoginActivity.this,getString(R.string.pwd_tip));
            return;
        }
        //显示加载dialog
        showLoadingDialog("正在登陆");
        NetworkApi.login(user,pwd, new NetworkManager.SuccessCallback<LoginBean>() {
            @Override
            public void onSuccess(LoginBean response) {
                int userId = CustomApplication.getInstance().userId;
                NimAccountHelper.getInstance().login("docter_" + userId, "123456",null);
                startActivity(new Intent(LoginActivity.this,MainActivity.class));//跳转到列表界面
                ToastUtil.showShort(LoginActivity.this,getString(R.string.login_success));
                Log.e(TAG,response.toString());
                saveToLocal(response);
                hideLoadingDialog();
            }
        }, new NetworkManager.FailedCallback() {
            @Override
            public void onFailed(String message) {
                ToastUtil.showShort(LoginActivity.this,message);
                hideLoadingDialog();
            }
        });

    }

    /**
     * 保存一些基本信心到SP中
     */
    private void saveToLocal(LoginBean response) {
        LocalShared.getInstance(this).setUserId(response.getDocterid());
        LocalShared.getInstance(this).setUserNick(response.getDoctername());
        LocalShared.getInstance(this).setUserPhone(response.getTel());
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
