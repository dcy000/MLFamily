package com.ml.family.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ml.family.R;
import com.ml.family.activity.WatchImageActivity;
import com.ml.family.adapter.record_holder.CountFooterViewHolder;
import com.ml.family.adapter.record_holder.CountHeaderViewHolder;
import com.ml.family.adapter.record_holder.CountItemViewHolder;
import com.ml.family.bean.RecordBean;
import com.ml.family.dialog.RxDialogSureCancel;
import com.ml.family.network.NetworkApi;
import com.ml.family.network.NetworkManager;
import com.ml.family.utils.ToastUtil;
import com.truizlop.sectionedrecyclerview.SectionedRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by gzq on 2018/1/30.
 */

public class RecordAdapter extends SectionedRecyclerViewAdapter<CountHeaderViewHolder,CountItemViewHolder,CountFooterViewHolder>{
    private Context context;
    private HashMap<String,ArrayList<RecordBean>> group;
    private ArrayList<String> keys;
    public RecordAdapter(Context context, ArrayList<String> keys,HashMap<String,ArrayList<RecordBean>> group) {
        this.context = context;
        this.keys=keys;
        this.group = group;
    }

    @Override
    protected int getSectionCount() {
        return group==null? 0:group.size();
    }

    @Override
    protected int getItemCountForSection(int i) {
        return group.get(keys.get(i)).size();
    }

    @Override
    protected boolean hasFooterInSection(int i) {
        return true;
    }
    protected LayoutInflater getLayoutInflater(){
        return LayoutInflater.from(context);
    }
    @Override
    protected CountHeaderViewHolder onCreateSectionHeaderViewHolder(ViewGroup viewGroup, int i) {
        View view = getLayoutInflater().inflate(R.layout.view_count_header, viewGroup, false);
        return new CountHeaderViewHolder(view);
    }

    @Override
    protected CountFooterViewHolder onCreateSectionFooterViewHolder(ViewGroup viewGroup, int i) {
        View view = getLayoutInflater().inflate(R.layout.view_count_footer, viewGroup, false);
        return new CountFooterViewHolder(view);
    }

    @Override
    protected CountItemViewHolder onCreateItemViewHolder(ViewGroup viewGroup, int i) {
        View view = getLayoutInflater().inflate(R.layout.view_count_item, viewGroup, false);
        return new CountItemViewHolder(view);
    }

    @Override
    protected void onBindSectionHeaderViewHolder(CountHeaderViewHolder countHeaderViewHolder, int i) {
        countHeaderViewHolder.render(keys.get(i));
    }

    @Override
    protected void onBindSectionFooterViewHolder(CountFooterViewHolder countFooterViewHolder, int i) {
        countFooterViewHolder.render(i==group.size()-1);
    }

    @Override
    protected void onBindItemViewHolder(CountItemViewHolder countItemViewHolder, final int i, final int i1) {
        countItemViewHolder.render(context,group.get(keys.get(i)).get(i1).getPurl());

        countItemViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, WatchImageActivity.class)
                        .putParcelableArrayListExtra("imgUrls",group.get(keys.get(i)) )
                        .putExtra("position",i1));
            }
        });
        /**
         * 长按删除
         */
        countItemViewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                final RxDialogSureCancel dialogSureCancel=new RxDialogSureCancel(context);
                dialogSureCancel.getTitleView().setVisibility(View.GONE);
                dialogSureCancel.setContent("确定删除此次记录吗？");
                dialogSureCancel.setSureListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogSureCancel.cancel();
                        NetworkApi.deleteRecord(group.get(keys.get(i)).get(i1).getPid(), new NetworkManager.SuccessCallback<String>() {
                            @Override
                            public void onSuccess(String response) {
                                ToastUtil.showShort(context,"删除成功");
                                group.get(keys.get(i)).remove(i1);
                                RecordAdapter.this.notifyDataSetChanged();
                            }
                        }, new NetworkManager.FailedCallback() {
                            @Override
                            public void onFailed(String message) {
                                ToastUtil.showShort(context,"删除失败");
                            }
                        });
                    }
                });
                dialogSureCancel.setCancelListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogSureCancel.cancel();
                    }
                });
                dialogSureCancel.show();
                return true;//不加短按
            }
        });
    }


}
