package com.ml.family.activity;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.model.TResult;
import com.ml.family.CustomApplication;
import com.ml.family.R;
import com.ml.family.dialog.InterfaceDialog;
import com.ml.family.dialog.LoadingDialog;
import com.ml.family.dialog.PickPhotoPopwindow;
import com.ml.family.network.NetworkApi;
import com.ml.family.network.NetworkManager;
import com.ml.family.utils.ToastUtil;
import com.ml.family.utils.Utils;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;

import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MoreRecordActivity extends BaseActivity {

    @BindView(R.id.list)
    RecyclerView list;
    private TakePhoto takePhoto;
    private Uri imageUri;
    private PickPhotoPopwindow pickPhotoPopwindow;
    private String TAG = MoreRecordActivity.class.getSimpleName();
    private LoadingDialog loadingDialog;
    private String userid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_record);
        ButterKnife.bind(this);
        setTopTitle("TA的档案");
        setRightText("新增");
        userid=getIntent().getStringExtra("userid");
        setAdapter();
        initTakePhoto();
    }

    private void initTakePhoto() {
        takePhoto = getTakePhoto();
        pickPhotoPopwindow = new PickPhotoPopwindow(this);
        File file = new File(Environment.getExternalStorageDirectory(), "/gcml/" + System.currentTimeMillis() + ".jpg");
        if (!file.getParentFile().exists()) file.getParentFile().mkdirs();
        imageUri = Uri.fromFile(file);
    }

    private void setAdapter() {
        list.setLayoutManager(new GridLayoutManager(this, 3));
        list.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        list.setAdapter(new BaseQuickAdapter<String, BaseViewHolder>(R.layout.more_record_item, new ArrayList<String>()) {
            @Override
            protected void convert(BaseViewHolder helper, String item) {
                Glide.with(MoreRecordActivity.this)
                        .load(item)
                        .into((ImageView) helper.getView(R.id.img));
            }
        });
    }

    @Override
    protected void onRightTextClick() {
        pickPhotoPopwindow.startDialog();
        pickPhotoPopwindow.setOnChooseSrcListener(new InterfaceDialog() {
            @Override
            public void cameraOnClickListener(View view, PopupWindow popupWindow) {
                popupWindow.dismiss();
                takePhoto.onPickFromCapture(imageUri);
            }

            @Override
            public void pictureOnClickListener(View view, PopupWindow popupWindow) {
                popupWindow.dismiss();
                takePhoto.onPickFromGallery();
            }
        });

    }

    @Override
    public void takeSuccess(final TResult result) {
        super.takeSuccess(result);
        if(!Utils.isConnected(this)){
            ToastUtil.showShort(this,"请检查您的网络");
            return;
        }
        if(!Utils.isWifiConnected(this)){

        }
        loadingDialog = new LoadingDialog(this, "正在上传...");
        loadingDialog.show();

        NetworkApi.get_token(new NetworkManager.SuccessCallback<String>() {
            @Override
            public void onSuccess(String response) {
                uploadFile(result, response);
                Log.e(TAG, "onSuccess: " + "获取token成功");
            }

        }, new NetworkManager.FailedCallback() {
            @Override
            public void onFailed(String message) {
                Log.e(TAG, "onFailed: " + "");
            }
        });

    }

    private void uploadFile(TResult result, String token) {
        Date date = new Date();
        SimpleDateFormat simple = new SimpleDateFormat("yyyyMMddhhmmss");
        StringBuilder str = new StringBuilder();//定义变长字符串
        Random random = new Random();
        for (int i = 0; i < 8; i++) {
            str.append(random.nextInt(10));
        }
        //将字符串转换为数字并输出
        String key = simple.format(date) + str + ".jpg";
        CustomApplication.getQiniuUploadManager().put(new File(result.getImage().getOriginalPath()), key, token, new UpCompletionHandler() {
            @Override
            public void complete(String key, ResponseInfo info, JSONObject response) {
                loadingDialog.dismiss();
                Log.e(TAG, "complete: " + info.toString());
                if (info.isOK()) {
                    String imageUrl = "http://oyptcv2pb.bkt.clouddn.com/" + key;
                    recordImgUrl(imageUrl);
                }
            }
        }, null);
    }

    /**
     * 将上传返回的图片地址上传我们的服务器
     *
     * @param imageUrl
     */
    private void recordImgUrl(String imageUrl) {
//        NetworkApi.uploadImg(imageUrl,userid,);
    }

    @Override
    public void takeCancel() {
        super.takeCancel();
    }

    @Override
    public void takeFail(TResult result, String msg) {
        super.takeFail(result, msg);
    }
}
