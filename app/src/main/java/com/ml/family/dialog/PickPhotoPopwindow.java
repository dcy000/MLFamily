package com.ml.family.dialog;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.ml.family.R;

/**
 * Created by sqzj on 2017/8/9.
 */
public class PickPhotoPopwindow {
    private Activity context;
    private LayoutInflater inflater;
    private InterfaceDialog dialog;


    public void setOnChooseSrcListener(InterfaceDialog dialog) {
        this.dialog = dialog;
    }
    public PickPhotoPopwindow(Activity context) {
        this.context = context;
        inflater=LayoutInflater.from(context);
    }

    public void startDialog() {
        View view = inflater.inflate(R.layout.choose_image, null);
        final PopupWindow window = new PopupWindow(view,
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
        window.setFocusable(true);
        window.setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED);
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        ColorDrawable dw = new ColorDrawable(0x00000000);
        window.setBackgroundDrawable(dw);
        backgroundAlpha(context, 0.5f);
        window.setAnimationStyle(R.style.mypopwindow_anim_style);
        window.showAtLocation(context.getWindow().getDecorView(),
                Gravity.BOTTOM, 0, 0);
        window.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(context, 1.0f);
            }
        });

        TextView camera = (TextView) view.findViewById(R.id.camera);
        TextView album = (TextView) view.findViewById(R.id.album);
        TextView cancel = (TextView) view.findViewById(R.id.cancel);
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cameraOnClickListener(v,window);
            }
        });

        album.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.pictureOnClickListener(v,window);
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                window.dismiss();
            }
        });
    }
    public void backgroundAlpha(Activity context, float bgAlpha) {
        WindowManager.LayoutParams lp = context.getWindow().getAttributes();
        lp.alpha = bgAlpha;
        context.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        context.getWindow().setAttributes(lp);
    }
}
