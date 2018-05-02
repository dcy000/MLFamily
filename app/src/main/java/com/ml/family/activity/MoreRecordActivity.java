package com.ml.family.activity;

import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
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
import com.ml.family.adapter.RecordAdapter;
import com.ml.family.adapter.divider.GridViewDividerItemDecoration;
import com.ml.family.bean.RecordBean;
import com.ml.family.dialog.InterfaceDialog;
import com.ml.family.dialog.LoadingDialog;
import com.ml.family.dialog.PickPhotoPopwindow;
import com.ml.family.dialog.RxDialogEditSureCancel;
import com.ml.family.dialog.RxDialogSureCancel;
import com.ml.family.network.NetworkApi;
import com.ml.family.network.NetworkManager;
import com.ml.family.utils.DateUtil;
import com.ml.family.utils.ToastUtil;
import com.ml.family.utils.Utils;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.truizlop.sectionedrecyclerview.SectionedSpanSizeLookup;

import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
    private int start = 0, limit = 8;
    private RecordAdapter adapter;
    private HashMap<String, ArrayList<RecordBean>> group;
    private ArrayList<String> keys;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_record);
        ButterKnife.bind(this);
        setTopTitle("TA的档案");
        setRightView(R.drawable.add_record);
        userid = getIntent().getStringExtra("userid");
        group = new HashMap<>();
        keys = new ArrayList<>();
        setAdapter();
        initTakePhoto();
        getData(true);
    }

    private void getData(final boolean isMore) {

        NetworkApi.getRecordData(userid, start + "", limit + "", new NetworkManager.SuccessCallback<ArrayList<RecordBean>>() {
            @Override
            public void onSuccess(ArrayList<RecordBean> response) {
                if (!isMore) {//刷新
                    group.clear();
                    start = 0;
                } else {//加载更多
                    group.clear();
                    if (keys.size() % (limit + 1) != 0) {
                        start += limit;
                    }
                }
                divideData(response);
                adapter.notifyDataSetChanged();
            }
        }, new NetworkManager.FailedCallback() {
            @Override
            public void onFailed(String message) {
                Log.e(TAG, "onFailed: " + "请求失败");
            }
        });
    }

    private void divideData(ArrayList<RecordBean> response) {
        for (RecordBean recordBean : response) {
            String time = recordBean.getPtime().split("\\s+")[0];
            if (group.containsKey(time)) {
                group.get(time).add(recordBean);
            } else {
                ArrayList<RecordBean> beans = new ArrayList<>();
                beans.add(recordBean);
                group.put(time, beans);
                keys.add(time);
            }
        }
    }

    private void initTakePhoto() {
        takePhoto = getTakePhoto();
        pickPhotoPopwindow = new PickPhotoPopwindow(this);
        File file = new File(Environment.getExternalStorageDirectory(), "/gcml/" + System.currentTimeMillis() + ".jpg");
        if (!file.getParentFile().exists()) file.getParentFile().mkdirs();
        imageUri = Uri.fromFile(file);
    }

    private void setAdapter() {
        adapter = new RecordAdapter(this, keys, group);
        list.addItemDecoration(new GridViewDividerItemDecoration(5, 5));
        list.setAdapter(adapter);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        SectionedSpanSizeLookup lookup = new SectionedSpanSizeLookup(adapter, layoutManager);
        layoutManager.setSpanSizeLookup(lookup);
        list.setLayoutManager(layoutManager);
    }

    @Override
    protected void onRightViewClick() {
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
    public void takeSuccess(TResult result) {
        super.takeSuccess(result);
        if (!Utils.isConnected(this)) {
            ToastUtil.showShort( "请检查您的网络");
            return;
        }
        if (!Utils.isWifiConnected(this)) {
            showWarningDialog(result);
        } else {
            uploadFile(result);
        }
    }

    private void showWarningDialog(final TResult result) {
        final RxDialogSureCancel dialogSureCancel = new RxDialogSureCancel(this);
        dialogSureCancel.getTitleView().setVisibility(View.GONE);
        dialogSureCancel.getContentView().setText("您正在使用移动网络，确定上传吗？");
        dialogSureCancel.getSureView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogSureCancel.cancel();
                uploadFile(result);
            }
        });
        dialogSureCancel.getCancelView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogSureCancel.cancel();
            }
        });

        dialogSureCancel.show();
    }

    /**
     * 上传文件到七牛
     *
     * @param result
     */
    private void uploadFile(final TResult result) {
        loadingDialog = new LoadingDialog(MoreRecordActivity.this, "正在上传...");
        loadingDialog.show();

        Date date = new Date();
        SimpleDateFormat simple = new SimpleDateFormat("yyyyMMddhhmmss");
        StringBuilder str = new StringBuilder();//定义变长字符串
        Random random = new Random();
        for (int i = 0; i < 8; i++) {
            str.append(random.nextInt(10));
        }
        //将字符串转换为数字并输出
        final String key = simple.format(date) + str + ".jpg";

        NetworkApi.get_token(new NetworkManager.SuccessCallback<String>() {
            @Override
            public void onSuccess(String response) {
                Log.e(TAG, "onSuccess: " + "获取token成功");
                CustomApplication.getQiniuUploadManager().put(new File(result.getImage().getOriginalPath()), key, response, new UpCompletionHandler() {
                    @Override
                    public void complete(String key, ResponseInfo info, JSONObject response) {
                        loadingDialog.dismiss();
                        Log.e(TAG, "complete: " + info.toString());
                        if (info.isOK()) {
                            String imageUrl = "http://oyptcv2pb.bkt.clouddn.com/" + key;
                            showTipsDialog(imageUrl);
                        }
                    }
                }, null);
            }

        }, new NetworkManager.FailedCallback() {
            @Override
            public void onFailed(String message) {
                Log.e(TAG, "onFailed: " + "");
            }
        });

    }

    /**
     * 将上传返回的图片地址上传我们的服务器
     *
     * @param imageUrl
     */
    private void showTipsDialog(final String imageUrl) {
        final RxDialogEditSureCancel dialogEditSureCancel = new RxDialogEditSureCancel(this);
        dialogEditSureCancel.getTitleView().setText("希望您能备注一下，方便以后查看");
        dialogEditSureCancel.getSureView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogEditSureCancel.cancel();
                String tipsText = dialogEditSureCancel.getEditText().getText().toString().trim();
                String tipsMessage;
                if (TextUtils.isEmpty(tipsText)) {
                    tipsMessage = DateUtil.format(new Date(), "yyyy.MM.dd HH:mm") + "上传";
                } else {
                    tipsMessage = tipsText;
                }
                NetworkApi.uploadImg(imageUrl, userid, tipsMessage, null, null, null, new NetworkManager.SuccessCallback<String>() {
                    @Override
                    public void onSuccess(String response) {
                        ToastUtil.showShort( "上传成功");
                        getData(true);
                    }
                }, new NetworkManager.FailedCallback() {
                    @Override
                    public void onFailed(String message) {
                        ToastUtil.showShort( "上传失败");
                    }
                });
            }
        });
        dialogEditSureCancel.getCancelView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogEditSureCancel.cancel();
            }
        });
        dialogEditSureCancel.show();

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
