package com.ml.family.activity;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;
import com.ml.family.R;
import com.ml.family.bean.RecordBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WatchImageActivity extends AppCompatActivity {
    @BindView(R.id.tv_id)
    TextView tvId;
    @BindView(R.id.viewpage)
    ViewPager viewpage;
    @BindView(R.id.description)
    TextView description;
    private List<RecordBean> imgurls;
    private int position;
    private List<ImageView> imageViews;
    private String TAG=WatchImageActivity.class.getSimpleName();

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watch_image);
        ButterKnife.bind(this);
        imgurls=getIntent().getParcelableArrayListExtra("imgUrls");
        position = getIntent().getIntExtra("position", 0);
        imageViews=new ArrayList<>();
        for (int i=0;i<imgurls.size();i++){
            PhotoView imageView=new PhotoView(this);
            Glide.with(this)
                    .load(imgurls.get(i).getPurl())
                    .into(imageView);
            imageViews.add(imageView);
        }
        viewpage.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return imgurls==null? 0:imgurls.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view==object;
            }
            @Override
            public Object instantiateItem(ViewGroup container, int position) {//必须实现，实例化
                container.addView(imageViews.get(position));
                return imageViews.get(position);
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {//必须实现，销毁
                container.removeView(imageViews.get(position));
            }
        });
        viewpage.setCurrentItem(position);
        description.setText(imgurls.get(position).getPmessage());
        tvId.setText(position+1+"/"+imgurls.size());
        viewpage.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                description.setText(imgurls.get(position).getPmessage());
                tvId.setText(position+1+"/"+imgurls.size());
                Log.e(TAG, "onPageSelected: "+position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }
}
