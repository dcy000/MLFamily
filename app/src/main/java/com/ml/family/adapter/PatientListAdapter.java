package com.ml.family.adapter;

import android.support.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.ml.family.R;
import com.ml.family.bean.FamilyListBean;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by gzq on 2017/11/21.
 */

public class PatientListAdapter extends BaseQuickAdapter<FamilyListBean,BaseViewHolder>{
    public PatientListAdapter(int layoutResId, @Nullable List<FamilyListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, FamilyListBean item) {
        helper.setText(R.id.name,item.getBname());
        helper.setText(R.id.sex,item.getSex());
        helper.setText(R.id.age,item.getAge()+"岁");
        helper.setText(R.id.disc,item.getMh());
        Glide.with(mContext)
                .load(item.getUser_photo())
                .error(R.color.textColor_undertint)
                .into((CircleImageView)helper.getView(R.id.head));
    }
}
