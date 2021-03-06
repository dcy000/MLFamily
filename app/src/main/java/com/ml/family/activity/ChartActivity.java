package com.ml.family.activity;

import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.Utils;
import com.ml.family.R;
import com.ml.family.adapter.XindianAdapter;
import com.ml.family.bean.BUA;
import com.ml.family.bean.BloodOxygenHistory;
import com.ml.family.bean.BloodPressureHistory;
import com.ml.family.bean.BloodSugarHistory;
import com.ml.family.bean.CholesterolHistory;
import com.ml.family.bean.ECGHistory;
import com.ml.family.bean.TemperatureHistory;
import com.ml.family.network.NetworkApi;
import com.ml.family.network.NetworkManager;
import com.ml.family.utils.MyFloatNumFormatter;
import com.ml.family.utils.MyMarkerView;
import com.ml.family.utils.TimeFormatter;
import com.ml.family.utils.ToastUtil;

import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChartActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener, View.OnClickListener {
    @BindView(R.id.one_week)
    RadioButton oneWeek;
    @BindView(R.id.one_month)
    RadioButton oneMonth;
    @BindView(R.id.one_season)
    RadioButton oneSeason;
    @BindView(R.id.one_year)
    RadioButton oneYear;
    @BindView(R.id.ll_time)
    RadioGroup llTime;
    @BindView(R.id.color_1)
    TextView color1;
    @BindView(R.id.indicator_1)
    TextView indicator1;
    @BindView(R.id.color_2)
    TextView color2;
    @BindView(R.id.indicator_2)
    TextView indicator2;
    @BindView(R.id.ll_second)
    LinearLayout llSecond;
    @BindView(R.id.rb_kongfu)
    RadioButton rbKongfu;
    @BindView(R.id.rb_one_hour)
    RadioButton rbOneHour;
    @BindView(R.id.rb_two_hour)
    RadioButton rbTwoHour;
    @BindView(R.id.rg_xuetang_time)
    RadioGroup rgXuetangTime;
    @BindView(R.id.ll_indicator)
    LinearLayout llIndicator;
    @BindView(R.id.tiwen_chart)
    LineChart tiwenChart;
    @BindView(R.id.xueya_chart)
    LineChart xueyaChart;
    @BindView(R.id.xuetang_chart)
    LineChart xuetangChart;
    @BindView(R.id.xueyang_chart)
    LineChart xueyangChart;
    @BindView(R.id.xinlv_chart)
    LineChart xinlvChart;
    @BindView(R.id.maibo_chart)
    LineChart maiboChart;
    @BindView(R.id.danguchun_chart)
    LineChart danguchunChart;
    @BindView(R.id.xueniaosuan_chart)
    LineChart xueniaosuanChart;
    @BindView(R.id.xindiantu)
    RecyclerView xindianList;
    private String type,userid;
    private long currentTime = 0L, weekAgoTime = 0L, monthAgoTime, seasonAgoTime = 0L, yearAgoTime = 0L;
    private String temp="1";
    private int timeFlag = 1;//默认最近一周：1；一个月：2；一季度：3；一年：4；
    private int eatedTime = 0;//默认空腹：0；饭后一小时：1；饭后两小时
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);
        ButterKnife.bind(this);

        currentTime = System.currentTimeMillis();
        Calendar curr = Calendar.getInstance();
        curr.set(Calendar.DAY_OF_MONTH, curr.get(Calendar.DAY_OF_MONTH) - 7);
        weekAgoTime = curr.getTimeInMillis();
        curr.set(Calendar.MONTH, curr.get(Calendar.MONTH) - 1);
        monthAgoTime = curr.getTimeInMillis();
        curr.set(Calendar.MONTH, curr.get(Calendar.MONTH) - 3);
        seasonAgoTime = curr.getTimeInMillis();
        curr.set(Calendar.YEAR, curr.get(Calendar.YEAR) - 1);
        yearAgoTime = curr.getTimeInMillis();
        type = getIntent().getStringExtra("type");
        userid=getIntent().getStringExtra("userid");
        setChart();

        llTime.setOnCheckedChangeListener(this);
        rbKongfu.setOnClickListener(this);
        rbOneHour.setOnClickListener(this);
        rbTwoHour.setOnClickListener(this);
    }

    private void setChart() {
        switch (type) {
            case "心电":
                setTopTitle("心电历史");
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                temp = "9";
                tiwenChart.setVisibility(View.GONE);
                xueyaChart.setVisibility(View.GONE);
                xuetangChart.setVisibility(View.GONE);
                xueyangChart.setVisibility(View.GONE);
                xinlvChart.setVisibility(View.GONE);
                maiboChart.setVisibility(View.GONE);
                danguchunChart.setVisibility(View.GONE);
                xueniaosuanChart.setVisibility(View.GONE);
                xindianList.setVisibility(View.VISIBLE);
                llIndicator.setVisibility(View.GONE);
                getXindian(weekAgoTime + "", currentTime + "");
                llTime.check(R.id.one_week);
                break;
            case "血尿酸":
                setTopTitle("血尿酸历史");
                temp="8";
                tiwenChart.setVisibility(View.GONE);
                xueyaChart.setVisibility(View.GONE);
                xuetangChart.setVisibility(View.GONE);
                xueyangChart.setVisibility(View.GONE);
                xinlvChart.setVisibility(View.GONE);
                maiboChart.setVisibility(View.GONE);
                danguchunChart.setVisibility(View.GONE);
                xueniaosuanChart.setVisibility(View.VISIBLE);
                xindianList.setVisibility(View.GONE);
                llIndicator.setVisibility(View.VISIBLE);
                getXueniaosuan(weekAgoTime + "", currentTime + "");
                llTime.check(R.id.one_week);
                break;
            case "血糖":
                setTopTitle("血糖历史");
                temp = "4";
                tiwenChart.setVisibility(View.GONE);
                xueyaChart.setVisibility(View.GONE);
                xuetangChart.setVisibility(View.VISIBLE);
                xueyangChart.setVisibility(View.GONE);
                xinlvChart.setVisibility(View.GONE);
                maiboChart.setVisibility(View.GONE);
                danguchunChart.setVisibility(View.GONE);
                xueniaosuanChart.setVisibility(View.GONE);
                xindianList.setVisibility(View.GONE);
                llIndicator.setVisibility(View.VISIBLE);
                llTime.check(R.id.one_week);
                getXuetang(weekAgoTime + "", currentTime + "", eatedTime);
                break;
            case "血氧":
                setTopTitle("血氧历史");

                temp = "5";
                tiwenChart.setVisibility(View.GONE);
                xueyaChart.setVisibility(View.GONE);
                xuetangChart.setVisibility(View.GONE);
                xueyangChart.setVisibility(View.VISIBLE);
                xinlvChart.setVisibility(View.GONE);
                maiboChart.setVisibility(View.GONE);
                danguchunChart.setVisibility(View.GONE);
                xueniaosuanChart.setVisibility(View.GONE);
                xindianList.setVisibility(View.GONE);
                llIndicator.setVisibility(View.VISIBLE);
                llTime.check(R.id.one_week);
                getXueyang(weekAgoTime + "", currentTime + "");
                break;
            case "血压":
                setTopTitle("血压历史");
                temp = "2";
                tiwenChart.setVisibility(View.GONE);
                xueyaChart.setVisibility(View.VISIBLE);
                xuetangChart.setVisibility(View.GONE);
                xueyangChart.setVisibility(View.GONE);
                xinlvChart.setVisibility(View.GONE);
                maiboChart.setVisibility(View.GONE);
                danguchunChart.setVisibility(View.GONE);
                xueniaosuanChart.setVisibility(View.GONE);
                xindianList.setVisibility(View.GONE);
                llIndicator.setVisibility(View.VISIBLE);
                llTime.check(R.id.one_week);
                getXueya(weekAgoTime + "", currentTime + "");
                break;
            case "胆固醇":
                temp = "7";
                tiwenChart.setVisibility(View.GONE);
                xueyaChart.setVisibility(View.GONE);
                xuetangChart.setVisibility(View.GONE);
                xueyangChart.setVisibility(View.GONE);
                xinlvChart.setVisibility(View.GONE);
                maiboChart.setVisibility(View.GONE);
                danguchunChart.setVisibility(View.VISIBLE);
                xueniaosuanChart.setVisibility(View.GONE);
//                xindianList.setVisibility(View.GONE);
                llIndicator.setVisibility(View.VISIBLE);
                getDangucun(weekAgoTime + "", currentTime + "");
                llTime.check(R.id.one_week);
                break;
            case "体温":
                setTopTitle("体温历史");
                temp = "1";
                tiwenChart.setVisibility(View.VISIBLE);
                xueyaChart.setVisibility(View.GONE);
                xuetangChart.setVisibility(View.GONE);
                xueyangChart.setVisibility(View.GONE);
                xinlvChart.setVisibility(View.GONE);
                maiboChart.setVisibility(View.GONE);
                danguchunChart.setVisibility(View.GONE);
                xueniaosuanChart.setVisibility(View.GONE);
                xindianList.setVisibility(View.GONE);
                llIndicator.setVisibility(View.VISIBLE);
                llTime.check(R.id.one_week);
                getTiwen(weekAgoTime + "", currentTime + "");
                break;

        }
    }
    private void getXindian(String start, String end) {

        NetworkApi.getECGHistory(userid,start, end, temp, new NetworkManager.SuccessCallback<ArrayList<ECGHistory>>() {
            @Override
            public void onSuccess(ArrayList<ECGHistory> response) {
                xindianList.setLayoutManager(new LinearLayoutManager(ChartActivity.this));
                xindianList.setAdapter(new XindianAdapter(R.layout.item_message, response));
            }
        }, new NetworkManager.FailedCallback() {
            @Override
            public void onFailed(String message) {
                ToastUtil.showShort(message);
            }
        });
    }
    private void setTiwenChart() {

        //x轴右下角文字描述
        tiwenChart.getDescription().setEnabled(false);
        // enable touch gestures 启用触
        tiwenChart.setTouchEnabled(true);

        //启用坐标轴是否可以上下拖动
        tiwenChart.setDragEnabled(true);
        //启用缩放
        tiwenChart.setScaleEnabled(true);
        //禁止y轴缩放
        tiwenChart.setScaleYEnabled(false);
        tiwenChart.setExtraRightOffset(30);
        //20个数据以后不再显示注释标签
        tiwenChart.setMaxVisibleValueCount(20);
        tiwenChart.setNoDataText(getResources().getString(R.string.noData));

        XAxis xAxis = tiwenChart.getXAxis();
        //绘制底部的X轴
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        //启用X轴的网格虚线
        xAxis.setDrawGridLines(false);
        //缩放时候的粒度
        xAxis.setGranularity(1);
        xAxis.setTextSize(14f);
        //在可见范围只显示四个
        xAxis.setLabelCount(4);

        LimitLine ll1 = new LimitLine(37.2f, "37.2℃");
        ll1.setLineWidth(1f);
        ll1.setLineColor(getResources().getColor(R.color.picket_line));
        ll1.enableDashedLine(10.0f, 10f, 0f);
        ll1.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
        ll1.setTextSize(12f);


        LimitLine ll2 = new LimitLine(36f, "36.0℃");
        ll2.setLineWidth(1f);
        ll2.setLineColor(getResources().getColor(R.color.picket_line));
        ll2.enableDashedLine(10f, 10f, 0f);
        ll2.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
        ll2.setTextSize(12f);

        //Y轴设置
        YAxis leftAxis = tiwenChart.getAxisLeft();
        leftAxis.removeAllLimitLines(); // reset all limit lines to avoid overlapping lines
        leftAxis.addLimitLine(ll1);
        leftAxis.addLimitLine(ll2);
        leftAxis.resetAxisMaximum();
        leftAxis.resetAxisMinimum();
        leftAxis.setAxisMinimum(35f);
        leftAxis.setTextSize(14f);


        //网格线
        leftAxis.setDrawGridLines(false);//不启用y轴的参考线
        //启用零线
        leftAxis.setDrawZeroLine(false);

        //绘制警戒线在绘制数据之后
        leftAxis.setDrawLimitLinesBehindData(false);

        //禁用右边的Y轴
        tiwenChart.getAxisRight().setEnabled(false);
        //动画时间
        tiwenChart.animateX(2500);
    }

    /**
     * 血糖设置
     */
    private void setXueTangChart() {

        //x轴右下角文字描述
        xuetangChart.getDescription().setEnabled(false);
        // enable touch gestures 启用触
        xuetangChart.setTouchEnabled(true);

        // 启用坐标轴是否可以上下拖动
        xuetangChart.setDragEnabled(true);
        //启用缩放
        xuetangChart.setScaleEnabled(true);
        //禁止y轴缩放
        xuetangChart.setScaleYEnabled(false);

        xuetangChart.setExtraLeftOffset(20);
        xuetangChart.setExtraRightOffset(30);
        xuetangChart.setNoDataText(getResources().getString(R.string.noData));


        XAxis xAxis = xuetangChart.getXAxis();
        //绘制底部的X轴
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        //启用X轴的网格虚线
        xAxis.setDrawGridLines(false);
        xAxis.setTextSize(14f);
        xAxis.setGranularity(1);
        xAxis.setLabelCount(4);

        LimitLine ll1 = new LimitLine(7.0f, "7.0mmol/L");
        ll1.setLineWidth(1f);
        ll1.setLineColor(getResources().getColor(R.color.picket_line));
        ll1.enableDashedLine(10.0f, 10f, 0f);
        ll1.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
        ll1.setTextSize(12f);


        LimitLine ll2 = new LimitLine(3.61f, "3.61mmol/L");
        ll2.setLineWidth(1f);
        ll2.setLineColor(getResources().getColor(R.color.picket_line));
        ll2.enableDashedLine(10f, 10f, 0f);
        ll2.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
        ll2.setTextSize(12f);

        LimitLine ll3 = new LimitLine(11.1f, "11.1mmol/L");
        ll3.setLineWidth(1f);
        ll3.setLineColor(Color.parseColor("#F0FC6D9A"));
        ll3.enableDashedLine(10.0f, 10f, 0f);
        ll3.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
        ll3.setTextSize(12f);

        LimitLine ll4 = new LimitLine(3.61f, "3.61mmol/L");
        ll4.setLineWidth(1f);
        ll4.setLineColor(Color.parseColor("#F0FC6D9A"));
        ll4.enableDashedLine(10.0f, 10f, 0f);
        ll4.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
        ll4.setTextSize(12f);

        LimitLine ll5 = new LimitLine(7.8f, "7.8mmol/L");
        ll5.setLineWidth(1f);
        ll5.setLineColor(Color.parseColor("#F0FC6D9A"));
        ll5.enableDashedLine(10.0f, 10f, 0f);
        ll5.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
        ll5.setTextSize(12f);

        LimitLine ll6 = new LimitLine(3.61f, "3.61mmol/L");
        ll6.setLineWidth(1f);
        ll6.setLineColor(Color.parseColor("#F0FC6D9A"));
        ll6.enableDashedLine(10.0f, 10f, 0f);
        ll6.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
        ll6.setTextSize(12f);

        //Y轴设置
        YAxis leftAxis = xuetangChart.getAxisLeft();
        leftAxis.removeAllLimitLines();
        if (eatedTime == 0) {
            leftAxis.addLimitLine(ll1);
            leftAxis.addLimitLine(ll2);
        } else if (eatedTime == 1) {
            leftAxis.addLimitLine(ll3);
            leftAxis.addLimitLine(ll4);
        } else if (eatedTime == 2) {
            leftAxis.addLimitLine(ll5);
            leftAxis.addLimitLine(ll6);
        }

        leftAxis.setAxisMinimum(0f);
        leftAxis.setAxisMaximum(10f);
        //网格线
        leftAxis.setDrawGridLines(false);
        leftAxis.enableGridDashedLine(10f, 10f, 0f);

        //启用零线
        leftAxis.setDrawZeroLine(false);
        //绘制警戒线在绘制数据之后
        leftAxis.setDrawLimitLinesBehindData(false);
        //禁用右边的Y轴
        xuetangChart.getAxisRight().setEnabled(false);
        xuetangChart.animateX(2500);

    }


    /**
     * 血氧基本设置
     */
    private void setXueyangChart() {

        //x轴右下角文字描述
        xueyangChart.getDescription().setEnabled(false);
        // enable touch gestures 启用触
        xueyangChart.setTouchEnabled(true);

        // 启用坐标轴是否可以上下拖动
        xueyangChart.setDragEnabled(true);
        //启用缩放
        xueyangChart.setScaleEnabled(true);

        //禁止y轴缩放
        xueyangChart.setScaleYEnabled(false);

        xueyangChart.setExtraRightOffset(30);
        xueyangChart.setMaxVisibleValueCount(20);
        xueyangChart.setNoDataText("");

        XAxis xAxis = xueyangChart.getXAxis();
        //绘制底部的X轴
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        //启用绘制轴标签 //默认是true
        xAxis.setDrawLabels(true);
        //启用轴线实体线 //默认是true
        xAxis.setDrawAxisLine(true);
        //启用X轴的网格虚线
        xAxis.setDrawGridLines(false);
        xAxis.enableGridDashedLine(10f, 10f, 0f);
        xAxis.setTextSize(14f);
        xAxis.setGranularity(1);
        xAxis.setLabelCount(4);


        LimitLine ll1 = new LimitLine(94f, "最低94%");
        ll1.setLineWidth(1f);
        ll1.setLineColor(getResources().getColor(R.color.picket_line));
        ll1.enableDashedLine(10.0f, 10f, 0f);
        ll1.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
        ll1.setTextSize(12f);

        //Y轴设置
        YAxis leftAxis = xueyangChart.getAxisLeft();
        leftAxis.removeAllLimitLines(); // reset all limit lines to avoid overlapping lines
        leftAxis.addLimitLine(ll1);
        leftAxis.setAxisMinimum(90f);
        leftAxis.setAxisMaximum(101f);
        //网格线
        leftAxis.setDrawGridLines(false);
        leftAxis.enableGridDashedLine(10f, 10f, 0f);
        //启用零线
        leftAxis.setDrawZeroLine(false);

        //绘制警戒线在绘制数据之后
        leftAxis.setDrawLimitLinesBehindData(false);
        leftAxis.setTextSize(14f);
        //禁用右边的Y轴
        xueyangChart.getAxisRight().setEnabled(false);
        xueyangChart.animateX(2500);

    }

    /**
     * 血压图的基本设置
     */
    private void setXueyaChart() {

        xueyaChart.getDescription().setEnabled(false);

        // enable touch gestures
        xueyaChart.setTouchEnabled(true);

        xueyaChart.setDragDecelerationFrictionCoef(0.9f);

        // enable scaling and dragging
        xueyaChart.setDragEnabled(true);
        xueyaChart.setScaleEnabled(true);
        xueyaChart.setDrawGridBackground(false);
        xueyaChart.setHighlightPerDragEnabled(true);
        xueyaChart.animateX(2500);
        //禁止y轴缩放
        xueyaChart.setScaleYEnabled(false);
        xueyaChart.setExtraRightOffset(30f);
        xueyaChart.setMaxVisibleValueCount(20);
        xueyaChart.setNoDataText("");


        LimitLine ll1 = new LimitLine(130f, "130mmHg");
        ll1.setLineWidth(1f);
        ll1.setLineColor(getResources().getColor(R.color.picket_line1));
        ll1.enableDashedLine(10.0f, 10f, 0f);
        ll1.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
        ll1.setTextSize(12f);


        LimitLine ll2 = new LimitLine(90f, "90mmHg");
        ll2.setLineWidth(1f);
        ll2.setLineColor(getResources().getColor(R.color.picket_line1));
        ll2.enableDashedLine(10f, 10f, 0f);
        ll2.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
        ll2.setTextSize(12f);


        XAxis xAxis = xueyaChart.getXAxis();
        xAxis.setTextSize(14f);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawAxisLine(true);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);
        xAxis.setLabelCount(4);

        LimitLine ll3 = new LimitLine(85f, "85mmHg");
        ll3.setLineWidth(1f);
        ll3.setLineColor(getResources().getColor(R.color.picket_line2));
        ll3.enableDashedLine(10.0f, 10f, 0f);
        ll3.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
        ll3.setTextSize(12f);


        LimitLine ll4 = new LimitLine(60f, "60mmHg");
        ll4.setLineWidth(1f);
        ll4.setLineColor(getResources().getColor(R.color.picket_line2));
        ll4.enableDashedLine(10f, 10f, 0f);
        ll4.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
        ll4.setTextSize(12f);

        YAxis leftAxis = xueyaChart.getAxisLeft();
        leftAxis.setDrawGridLines(false);
        leftAxis.enableGridDashedLine(10f, 10f, 0f);
        leftAxis.setGranularityEnabled(true);
        leftAxis.addLimitLine(ll1);
        leftAxis.addLimitLine(ll2);
        leftAxis.addLimitLine(ll3);
        leftAxis.addLimitLine(ll4);
        leftAxis.setAxisMinimum(50f);
        leftAxis.setDrawLimitLinesBehindData(false);
        leftAxis.setTextSize(14f);
        xueyaChart.getAxisRight().setEnabled(false);


    }
    /**
     * 设置血尿酸
     */
    private void setBUAChart() {

        //x轴右下角文字描述
        xueniaosuanChart.getDescription().setEnabled(false);
        // enable touch gestures 启用触
        xueniaosuanChart.setTouchEnabled(true);

        //启用坐标轴是否可以上下拖动
        xueniaosuanChart.setDragEnabled(true);
        //启用缩放
        xueniaosuanChart.setScaleEnabled(true);
        //禁止y轴缩放
        xueniaosuanChart.setScaleYEnabled(false);
        xueniaosuanChart.setExtraLeftOffset(10);
        xueniaosuanChart.setExtraRightOffset(30);
        xueniaosuanChart.setNoDataText("");

        XAxis xAxis = xueniaosuanChart.getXAxis();
        //绘制底部的X轴
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        //启用X轴的网格虚线
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1);
        xAxis.setTextSize(14f);
        xAxis.setLabelCount(4);


        LimitLine ll1 = new LimitLine(149f, "149μmol/L");
        ll1.setLineWidth(1f);
        ll1.setLineColor(Color.parseColor("#CFD8F1"));
        ll1.enableDashedLine(10.0f, 10f, 0f);
        ll1.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
        ll1.setTextSize(12f);


        LimitLine ll2 = new LimitLine(416f, "416μmol/L");
        ll2.setLineWidth(1f);
        ll2.setLineColor(Color.parseColor("#CFD8F1"));
        ll2.enableDashedLine(10f, 10f, 0f);
        ll2.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
        ll2.setTextSize(12f);

        LimitLine ll3 = new LimitLine(357f, "357μmol/L");
        ll3.setLineWidth(1f);
        ll3.setLineColor(Color.parseColor("#D3EFD0"));
        ll3.enableDashedLine(10.0f, 10f, 0f);
        ll3.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
        ll3.setTextSize(12f);

        LimitLine ll4 = new LimitLine(89f, "89μmol/L");
        ll4.setLineWidth(1f);
        ll4.setLineColor(Color.parseColor("#D3EFD0"));
        ll4.enableDashedLine(10.0f, 10f, 0f);
        ll4.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
        ll4.setTextSize(12f);

        //Y轴设置
        YAxis leftAxis = xueniaosuanChart.getAxisLeft();
        leftAxis.removeAllLimitLines();
        leftAxis.addLimitLine(ll1);
        leftAxis.addLimitLine(ll2);
        leftAxis.addLimitLine(ll3);
        leftAxis.addLimitLine(ll4);
        leftAxis.resetAxisMaximum();
        leftAxis.resetAxisMinimum();
        leftAxis.setAxisMaximum(450);
        leftAxis.setAxisMinimum(70);
        //网格线
        leftAxis.setDrawGridLines(false);
        leftAxis.enableGridDashedLine(10f, 10f, 0f);
        //启用零线
        leftAxis.setDrawZeroLine(false);
        //绘制警戒线在绘制数据之后
        leftAxis.setDrawLimitLinesBehindData(false);
        leftAxis.setTextSize(14f);
        //禁用右边的Y轴
        xueniaosuanChart.getAxisRight().setEnabled(false);
        xueniaosuanChart.animateX(2500);

    }

    /**
     * 设置胆固醇
     */
    private void setDanguchunChart() {

        //x轴右下角文字描述
        danguchunChart.getDescription().setEnabled(false);
        // enable touch gestures 启用触
        danguchunChart.setTouchEnabled(true);

        danguchunChart.setDragEnabled(true);
        //启用缩放
        danguchunChart.setScaleEnabled(true);
        //禁止y轴缩放
        danguchunChart.setScaleYEnabled(false);
        danguchunChart.setExtraLeftOffset(10);
        danguchunChart.setExtraRightOffset(30);
        danguchunChart.setNoDataText("");

        XAxis xAxis = danguchunChart.getXAxis();
        //绘制底部的X轴
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        //启用X轴的网格虚线
        xAxis.setDrawGridLines(false);
        xAxis.setGranularity(1);
        xAxis.setTextSize(14f);
        xAxis.setLabelCount(4);


        LimitLine ll1 = new LimitLine(2.9f, "2.9mmol/L");
        ll1.setLineWidth(1f);
        ll1.setLineColor(Color.parseColor("#9CD793"));
        ll1.enableDashedLine(10.0f, 10f, 0f);
        ll1.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
        ll1.setTextSize(12f);


        LimitLine ll2 = new LimitLine(6.0f, "6.0mmol/L");
        ll2.setLineWidth(1f);
        ll2.setLineColor(Color.parseColor("#9CD793"));
        ll2.enableDashedLine(10f, 10f, 0f);
        ll2.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
        ll2.setTextSize(12f);

        LimitLine ll3 = new LimitLine(3.1f, "3.1mmol/L");
        ll3.setLineWidth(1f);
        ll3.setLineColor(Color.parseColor("#6D80E2"));
        ll3.enableDashedLine(10.0f, 10f, 0f);
        ll3.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
        ll3.setTextSize(12f);


        LimitLine ll4 = new LimitLine(5.2f, "5.2mmol/L");
        ll4.setLineWidth(1f);
        ll4.setLineColor(Color.parseColor("#6D80E2"));
        ll4.enableDashedLine(10f, 10f, 0f);
        ll4.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
        ll4.setTextSize(12f);

        //Y轴设置
        YAxis leftAxis = danguchunChart.getAxisLeft();
        leftAxis.removeAllLimitLines();
        leftAxis.addLimitLine(ll1);
        leftAxis.addLimitLine(ll2);
        leftAxis.addLimitLine(ll3);
        leftAxis.addLimitLine(ll4);

        leftAxis.resetAxisMaximum();
        leftAxis.resetAxisMinimum();

        //网格线
        leftAxis.setDrawGridLines(false);
        leftAxis.enableGridDashedLine(10f, 10f, 0f);

        //启用零线
        leftAxis.setDrawZeroLine(false);

        //绘制警戒线在绘制数据之后
        leftAxis.setDrawLimitLinesBehindData(false);
        leftAxis.setTextSize(14f);

        //禁用右边的Y轴
        danguchunChart.getAxisRight().setEnabled(false);
        danguchunChart.animateX(2500);

    }
    private void getDangucun(String start, String end) {
        rgXuetangTime.setVisibility(View.GONE);
        //指示器的颜色
        color1.setBackgroundColor(Color.parseColor("#9CD793"));
        indicator1.setText("成人(mmol/L)");
        color2.setBackgroundColor(Color.parseColor("#6D80E2"));
        indicator2.setText("儿童(mmol/L)");
        llSecond.setVisibility(View.VISIBLE);
        setDanguchunChart();
        NetworkApi.getCholesterolHistory(userid,start, end, temp, new NetworkManager.SuccessCallback<ArrayList<CholesterolHistory>>() {
            @Override
            public void onSuccess(ArrayList<CholesterolHistory> response) {
                ArrayList<Entry> value = new ArrayList<Entry>();
                ArrayList<Long> times = new ArrayList<>();
                ArrayList<Integer> colors = new ArrayList<>();
                for (int i = 0; i < response.size(); i++) {
                    if (response.get(i).cholesterol < 2.9 || response.get(i).cholesterol > 6.0) {
                        colors.add(Color.RED);
                    } else {
                        colors.add(getResources().getColor(R.color.node_text_color));//正常字体的颜色
                    }
                    value.add(new Entry(i, response.get(i).cholesterol));
                    times.add(response.get(i).time);
                }
                if (times.size() != 0) {
                    danguchunChart.getXAxis().setValueFormatter(new TimeFormatter(times));
                    MyMarkerView mv = new MyMarkerView(ChartActivity.this, R.layout.custom_marker_view, temp, times);
                    mv.setChartView(danguchunChart); // For bounds control
                    danguchunChart.setMarker(mv); // Set the marker to the chart
                    setDanguchun(value, colors);
                }
            }
        }, new NetworkManager.FailedCallback() {
            @Override
            public void onFailed(String message) {
                ToastUtil.showShort(message);
                if(danguchunChart!=null){
                    danguchunChart.setNoDataText(getResources().getString(R.string.noData));
                    danguchunChart.setData(null);
                    danguchunChart.invalidate();
                }
            }
        });
    }
    /**
     * 获得体温的数据
     */

    private void getTiwen(String start, String end) {
        rgXuetangTime.setVisibility(View.GONE);
        //指示器的颜色
        color1.setBackgroundColor(getResources().getColor(R.color.node_color));
        indicator1.setText("体温(℃)");
        llSecond.setVisibility(View.GONE);
        NetworkApi.getTemperatureHistory(userid,start, end, temp, new NetworkManager.SuccessCallback<ArrayList<TemperatureHistory>>() {
            @Override
            public void onSuccess(ArrayList<TemperatureHistory> response) {
                ArrayList<Entry> values = new ArrayList<Entry>();
                ArrayList<Long> times = new ArrayList<>();
                ArrayList<Integer> colors = new ArrayList<>();
                for (int i = 0; i < response.size(); i++) {

                    if (response.get(i).temper_ature > 37.2 || response.get(i).temper_ature < 36.0) {//超出正常范围的数据用红色表明
                        colors.add(Color.RED);
                    } else {
                        colors.add(getResources().getColor(R.color.node_text_color));//正常字体的颜色
                    }
                    values.add(new Entry(i, response.get(i).temper_ature));
                    times.add(response.get(i).time);
                }
                if (times.size() != 0) {
                    setTiwenChart();
                    tiwenChart.getXAxis().setValueFormatter(new TimeFormatter(times));
                    MyMarkerView mv = new MyMarkerView(ChartActivity.this, R.layout.custom_marker_view, temp, times);
                    mv.setChartView(tiwenChart);
                    tiwenChart.setMarker(mv);
                    setTiwen(values, colors);
                }
            }
        }, new NetworkManager.FailedCallback() {
            @Override
            public void onFailed(String message) {
                ToastUtil.showShort(message);
                tiwenChart.setNoDataText(getResources().getString(R.string.noData));
                tiwenChart.setData(null);
                tiwenChart.invalidate();
            }
        });
    }
    private void getXuetang(String start, String end, final int flag) {
        rgXuetangTime.setVisibility(View.VISIBLE);
        //指示器的颜色
        color1.setBackgroundColor(getResources().getColor(R.color.node_color));
        indicator1.setText("血糖(mmol/L)");
        llSecond.setVisibility(View.GONE);
        setXueTangChart();
        NetworkApi.getBloodSugarHistory(userid,start, end, temp, new NetworkManager.SuccessCallback<ArrayList<BloodSugarHistory>>() {
            @Override
            public void onSuccess(ArrayList<BloodSugarHistory> response) {
                ArrayList<Entry> value = new ArrayList<Entry>();
                ArrayList<Long> times = new ArrayList<>();
                ArrayList<Integer> colors = new ArrayList<>();

                for (int i = 0; i < response.size(); i++) {
                    times.add(response.get(i).time);
                    switch (flag) {
                        case 0://空腹
                            if (response.get(i).sugar_time == 0) {
                                value.add(new Entry(i, response.get(i).blood_sugar));
                                if (response.get(i).blood_sugar > 7.0 || response.get(i).blood_sugar < 3.61) {
                                    colors.add(Color.RED);
                                } else {
                                    colors.add(getResources().getColor(R.color.node_text_color));//正常字体的颜色
                                }
                            }
                            break;
                        case 1://饭后一小时
                            if (response.get(i).sugar_time == 1) {
                                times.add(response.get(i).time);
                                value.add(new Entry(i, response.get(i).blood_sugar));
                                if (response.get(i).blood_sugar > 11.1 || response.get(i).blood_sugar < 3.61) {
                                    colors.add(Color.RED);
                                } else {
                                    colors.add(getResources().getColor(R.color.node_text_color));//正常字体的颜色
                                }
                            }
                            break;
                        case 2://饭后两小时
                            if (response.get(i).sugar_time == 2) {
                                times.add(response.get(i).time);
                                value.add(new Entry(i, response.get(i).blood_sugar));
                                if (response.get(i).blood_sugar > 7.8 || response.get(i).blood_sugar < 3.61) {
                                    colors.add(Color.RED);
                                } else {
                                    colors.add(getResources().getColor(R.color.node_text_color));//正常字体的颜色
                                }
                            }
                            break;
                    }


                }

                if (value.size() != 0) {
                    xuetangChart.getXAxis().setValueFormatter(new TimeFormatter(times));
                    MyMarkerView mv = new MyMarkerView(ChartActivity.this, R.layout.custom_marker_view, temp, times);
                    mv.setChartView(xuetangChart); // For bounds control
                    xuetangChart.setMarker(mv); // Set the marker to the chart
                    setXuetang(value, colors);
                } else {
                    xuetangChart.setNoDataText(getResources().getString(R.string.noData));
                    xuetangChart.setData(null);
                    xuetangChart.invalidate();
                }

            }
        }, new NetworkManager.FailedCallback() {
            @Override
            public void onFailed(String message) {
                ToastUtil.showShort(message);
                xuetangChart.setNoDataText(getResources().getString(R.string.noData));
                xuetangChart.setData(null);
                xuetangChart.invalidate();
            }
        });
    }

    private void getXueyang(String start, String end) {
        rgXuetangTime.setVisibility(View.GONE);
        //指示器的颜色
        color1.setBackgroundColor(getResources().getColor(R.color.node_color));
        indicator1.setText("血氧");
        llSecond.setVisibility(View.GONE);
        setXueyangChart();
        NetworkApi.getBloodOxygenHistory(userid,start, end, temp, new NetworkManager.SuccessCallback<ArrayList<BloodOxygenHistory>>() {
            @Override
            public void onSuccess(ArrayList<BloodOxygenHistory> response) {
                ArrayList<Entry> value = new ArrayList<Entry>();
                ArrayList<Long> times = new ArrayList<>();
                ArrayList<Integer> colors = new ArrayList<>();
                for (int i = 0; i < response.size(); i++) {
                    if (response.get(i).blood_oxygen < 94) {
                        colors.add(Color.RED);
                    } else {
                        colors.add(getResources().getColor(R.color.node_text_color));//正常字体的颜色
                    }
                    value.add(new Entry(i, response.get(i).blood_oxygen));
                    times.add(response.get(i).time);
                }
                if (times.size() != 0) {
                    xueyangChart.getXAxis().setValueFormatter(new TimeFormatter(times));
                    MyMarkerView mv = new MyMarkerView(ChartActivity.this, R.layout.custom_marker_view, temp, times);
                    mv.setChartView(xueyangChart); // For bounds control
                    xueyangChart.setMarker(mv); // Set the marker to the chart
                    setXueyang(value, colors);
                }
            }
        }, new NetworkManager.FailedCallback() {
            @Override
            public void onFailed(String message) {
                ToastUtil.showShort(message);
                xueyangChart.setNoDataText(getResources().getString(R.string.noData));
                xueyangChart.setData(null);
                xueyangChart.invalidate();
            }
        });
    }
    private void getXueya(String start, String end) {
        rgXuetangTime.setVisibility(View.GONE);
        //指示器的颜色
        color1.setBackgroundColor(getResources().getColor(R.color.node_color));
        indicator1.setText("高压(mmHg)");
        color2.setBackgroundColor(getResources().getColor(R.color.node2_color));
        indicator2.setText("低压(mmHg)");
        llSecond.setVisibility(View.VISIBLE);
        setXueyaChart();
        NetworkApi.getBloodpressureHistory(userid,start, end, temp, new NetworkManager.SuccessCallback<ArrayList<BloodPressureHistory>>() {
            @Override
            public void onSuccess(ArrayList<BloodPressureHistory> response) {
                ArrayList<Entry> yVals1 = new ArrayList<Entry>();
                ArrayList<Entry> yVals2 = new ArrayList<Entry>();
                ArrayList<Long> times = new ArrayList<>();
                ArrayList<Integer> colors1 = new ArrayList<>();
                ArrayList<Integer> colors2 = new ArrayList<>();

                for (int i = 0; i < response.size(); i++) {
                    if (response.get(i).high_pressure > 130 || response.get(i).high_pressure < 90) {
                        colors1.add(Color.RED);
                    } else {
                        colors1.add(getResources().getColor(R.color.node_text_color));//正常字体的颜色
                    }

                    if (response.get(i).low_pressure > 85 || response.get(i).low_pressure < 60) {
                        colors2.add(Color.RED);
                    } else {
                        colors2.add(getResources().getColor(R.color.node2_color));
                    }
                    yVals1.add(new Entry(i, response.get(i).high_pressure));
                    yVals2.add(new Entry(i, response.get(i).low_pressure));
                    times.add(response.get(i).time);
                }
                if (times.size() != 0) {
                    xueyaChart.getXAxis().setValueFormatter(new TimeFormatter(times));
                    MyMarkerView mv = new MyMarkerView(ChartActivity.this, R.layout.custom_marker_view, temp, times, response);
                    mv.setChartView(xueyaChart);
                    xueyaChart.setMarker(mv);
                    setXueya(yVals1, yVals2, colors1, colors2);
                }
            }
        }, new NetworkManager.FailedCallback() {
            @Override
            public void onFailed(String message) {
                ToastUtil.showShort(message);
                xueyaChart.setNoDataText(getResources().getString(R.string.noData));
                xueyaChart.setData(null);
                xueyaChart.invalidate();
            }
        });
    }
    private void getXueniaosuan(String start, String end) {
        rgXuetangTime.setVisibility(View.GONE);
        //指示器的颜色
        color2.setBackgroundColor(Color.parseColor("#9CD793"));
        indicator2.setText("女性");
        color1.setBackgroundColor(Color.parseColor("#6D80E2"));
        indicator1.setText("男性");
        llSecond.setVisibility(View.VISIBLE);
        setBUAChart();
        NetworkApi.getBUAHistory(userid,start, end, temp, new NetworkManager.SuccessCallback<ArrayList<BUA>>() {
            @Override
            public void onSuccess(ArrayList<BUA> response) {
                ArrayList<Entry> value = new ArrayList<Entry>();
                ArrayList<Long> times = new ArrayList<>();
                ArrayList<Integer> colors = new ArrayList<>();
                for (int i = 0; i < response.size(); i++) {
                    if (response.get(i).uric_acid < 149 || response.get(i).uric_acid > 416) {
                        colors.add(Color.RED);
                    } else {
                        colors.add(getResources().getColor(R.color.node_text_color));//正常字体的颜色
                    }
                    value.add(new Entry(i, response.get(i).uric_acid));
                    times.add(response.get(i).time);
                }
                if (times.size() != 0) {
                    xueniaosuanChart.getXAxis().setValueFormatter(new TimeFormatter(times));
                    MyMarkerView mv = new MyMarkerView(ChartActivity.this, R.layout.custom_marker_view, temp, times);
                    mv.setChartView(xueniaosuanChart); // For bounds control
                    xueniaosuanChart.setMarker(mv); // Set the marker to the chart
                    setXueniaosuan(value, colors);
                }
            }
        }, new NetworkManager.FailedCallback() {
            @Override
            public void onFailed(String message) {
                ToastUtil.showShort(message);
                if(xueniaosuanChart!=null){
                    xueniaosuanChart.setNoDataText(getResources().getString(R.string.noData));
                    xueniaosuanChart.setData(null);
                    xueniaosuanChart.invalidate();
                }
            }
        });
    }
    /**
     * 设置胆固醇
     *
     * @param values
     */
    private void setDanguchun(ArrayList<Entry> values, ArrayList<Integer> colors) {

        LineDataSet set1;
        if (danguchunChart.getData() != null &&
                danguchunChart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) danguchunChart.getData().getDataSetByIndex(0);
            set1.setValues(values);
            if (values.size() <= 3)
                set1.setMode(LineDataSet.Mode.LINEAR);
            danguchunChart.getData().notifyDataChanged();
            danguchunChart.notifyDataSetChanged();
        } else {
            set1 = new LineDataSet(values, "");
            set1.setDrawIcons(false);
            //设置选中指示线的样式
            set1.setHighLightColor(Color.rgb(244, 117, 117));


            //走势线的样式
            set1.setColor(getResources().getColor(R.color.line_color));
            set1.setCircleColor(getResources().getColor(R.color.node_color));


            set1.setValueTextColors(colors);
            //走势线的粗细
            set1.setLineWidth(4f);
            //封顶圆圈的直径
            set1.setCircleRadius(6f);
            //是否镂空
            set1.setDrawCircleHole(true);
            set1.setCircleHoleRadius(2f);
            set1.setValueTextSize(16f);

            //左下角指示器样式
            set1.setFormLineWidth(0f);
            set1.setFormLineDashEffect(new DashPathEffect(new float[]{0f, 0f}, 0f));
            set1.setFormSize(0f);
//
            //曲线区域颜色填充
            set1.setDrawFilled(false);
            if (Utils.getSDKInt() >= 18) {
                // fill drawable only supported on api level 18 and above
                Drawable drawable = ContextCompat.getDrawable(this, R.drawable.fade_tiwen);
                set1.setFillDrawable(drawable);
            } else {
                set1.setFillColor(Color.parseColor("#B3DCE2F3"));
            }
            if (values.size() <= 3)
                set1.setMode(LineDataSet.Mode.LINEAR);
            else
                set1.setMode(LineDataSet.Mode.CUBIC_BEZIER);
            ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
            dataSets.add(set1);
            LineData data = new LineData(dataSets);
            danguchunChart.setData(data);
        }
    }
    /**
     * 设置血尿酸
     *
     * @param values
     */
    private void setXueniaosuan(ArrayList<Entry> values, ArrayList<Integer> colors) {

        LineDataSet set1;
        if (xueniaosuanChart.getData() != null &&
                xueniaosuanChart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) xueniaosuanChart.getData().getDataSetByIndex(0);
            set1.setValues(values);
            if (values.size() <= 3)
                set1.setMode(LineDataSet.Mode.LINEAR);
            xueniaosuanChart.getData().notifyDataChanged();
            xueniaosuanChart.notifyDataSetChanged();
        } else {
            set1 = new LineDataSet(values, "");
            set1.setDrawIcons(false);
            //设置选中指示线的样式
            set1.setHighLightColor(Color.rgb(244, 117, 117));


            //走势线的样式
            set1.setColor(getResources().getColor(R.color.line_color));
            set1.setCircleColor(getResources().getColor(R.color.node_color));
            set1.setValueTextColors(colors);


            //走势线的粗细
            set1.setLineWidth(4f);
            //封顶圆圈的直径
            set1.setCircleRadius(6f);
            //是否镂空
            set1.setDrawCircleHole(true);
            set1.setCircleHoleRadius(2f);
            set1.setValueTextSize(16f);

            //左下角指示器样式
            set1.setFormLineWidth(0f);
            set1.setFormLineDashEffect(new DashPathEffect(new float[]{0f, 0f}, 0f));
            set1.setFormSize(0f);
//
            //曲线区域颜色填充
            set1.setDrawFilled(false);
            if (Utils.getSDKInt() >= 18) {
                // fill drawable only supported on api level 18 and above
                Drawable drawable = ContextCompat.getDrawable(this, R.drawable.fade_tiwen);
                set1.setFillDrawable(drawable);
            } else {
                set1.setFillColor(Color.parseColor("#B3DCE2F3"));
            }
            if (values.size() <= 3)
                set1.setMode(LineDataSet.Mode.LINEAR);
            else
                set1.setMode(LineDataSet.Mode.CUBIC_BEZIER);
            ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
            dataSets.add(set1);

            LineData data = new LineData(dataSets);
            xueniaosuanChart.setData(data);
        }
    }
    /**
     * 设置体温的走势
     *
     * @param values
     */
    private void setTiwen(ArrayList<Entry> values, ArrayList<Integer> colors) {

        LineDataSet set1;
        if (tiwenChart.getData() != null &&
                tiwenChart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) tiwenChart.getData().getDataSetByIndex(0);
            set1.setValues(values);
            if (values.size() <= 3)
                set1.setMode(LineDataSet.Mode.LINEAR);
            tiwenChart.getData().notifyDataChanged();
            tiwenChart.notifyDataSetChanged();
        } else {
            set1 = new LineDataSet(values, "");
            //不画节点上的图案
            set1.setDrawIcons(false);

            //设置选中指示线的样式
            set1.enableDashedHighlightLine(10f, 0f, 0f);
            set1.setHighLightColor(Color.rgb(244, 117, 117));


            set1.setValueTextColors(colors);
            set1.setValueTextSize(16f);
            set1.setValueFormatter(new MyFloatNumFormatter(temp));

            //走势线的样式
//            set1.enableDashedLine(10f, 0f, 0f);
            //走势线的颜色
            set1.setColor(getResources().getColor(R.color.line_color));
            //节点圆圈的颜色
            set1.setCircleColor(getResources().getColor(R.color.node_color));

            //走势线的粗细
            set1.setLineWidth(4f);
            //封顶圆圈的直径
            set1.setCircleRadius(6f);
            //是否镂空
            set1.setDrawCircleHole(true);
            set1.setCircleHoleRadius(2f);


            //左下角指示器样式
            set1.setFormLineWidth(0f);
            set1.setFormLineDashEffect(new DashPathEffect(new float[]{0f, 0f}, 0f));
            set1.setFormSize(0f);
            //曲线区域颜色填充
            set1.setDrawFilled(false);
            if (values.size() <= 3)
                set1.setMode(LineDataSet.Mode.LINEAR);
            else
                set1.setMode(LineDataSet.Mode.CUBIC_BEZIER);
            if (Utils.getSDKInt() >= 18) {
                // fill drawable only supported on api level 18 and above
                Drawable drawable = ContextCompat.getDrawable(this, R.drawable.fade_tiwen);
                set1.setFillDrawable(drawable);
            } else {
                set1.setFillColor(Color.parseColor("#B3DCE2F3"));
            }

            ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
            dataSets.add(set1);
            LineData data = new LineData(dataSets);
            tiwenChart.setData(data);
        }
    }

    /**
     * 设置血糖的走势
     *
     * @param values
     */
    private void setXuetang(ArrayList<Entry> values, ArrayList<Integer> colors) {

        LineDataSet set1;
        if (xuetangChart.getData() != null &&
                xuetangChart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) xuetangChart.getData().getDataSetByIndex(0);
            set1.setValues(values);
            if (values.size() <= 3)
                set1.setMode(LineDataSet.Mode.LINEAR);
            xuetangChart.getData().notifyDataChanged();
            xuetangChart.notifyDataSetChanged();
        } else {
            set1 = new LineDataSet(values, "");
            set1.setDrawIcons(false);

            //设置选中指示线的样式
            set1.setHighLightColor(Color.rgb(244, 117, 117));
            //走势线的样式
            set1.setColor(getResources().getColor(R.color.line_color));
            set1.setCircleColor(getResources().getColor(R.color.node_color));
            set1.setValueTextColors(colors);
            //走势线的粗细
            set1.setLineWidth(4f);
            //封顶圆圈的直径
            set1.setCircleRadius(6f);
            //是否镂空
            set1.setDrawCircleHole(true);
            set1.setCircleHoleRadius(2f);
            set1.setValueTextSize(16f);

            //左下角指示器样式
            set1.setFormLineWidth(0f);
            set1.setFormLineDashEffect(new DashPathEffect(new float[]{0f, 0f}, 0f));
            set1.setFormSize(0f);
//
            //曲线区域颜色填充
            set1.setDrawFilled(false);
            if (Utils.getSDKInt() >= 18) {
                // fill drawable only supported on api level 18 and above
                Drawable drawable = ContextCompat.getDrawable(this, R.drawable.fade_red);
                set1.setFillDrawable(drawable);
            } else {
                set1.setFillColor(Color.BLACK);
            }
            if (values.size() <= 3)
                set1.setMode(LineDataSet.Mode.LINEAR);
            else
                set1.setMode(LineDataSet.Mode.CUBIC_BEZIER);

            ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
            dataSets.add(set1);

            LineData data = new LineData(dataSets);

            xuetangChart.setData(data);
        }
    }
    /**
     * 设置血氧的走势
     *
     * @param values
     */
    private void setXueyang(ArrayList<Entry> values, ArrayList<Integer> colors) {

        LineDataSet set1;
        if (xueyangChart.getData() != null &&
                xueyangChart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) xueyangChart.getData().getDataSetByIndex(0);
            set1.setValues(values);
            if (values.size() <= 3)
                set1.setMode(LineDataSet.Mode.LINEAR);
            xueyangChart.getData().notifyDataChanged();
            xueyangChart.notifyDataSetChanged();
        } else {
            set1 = new LineDataSet(values, "");
            set1.setDrawIcons(false);
            //设置选中指示线的样式
            set1.setHighLightColor(Color.rgb(244, 117, 117));
            //走势线的样式
            set1.setValueTextColors(colors);
            set1.setColor(getResources().getColor(R.color.line_color));
            set1.setCircleColor(getResources().getColor(R.color.node_color));

            //走势线的粗细
            set1.setLineWidth(4f);
            //封顶圆圈的直径
            set1.setCircleRadius(6f);
            //是否镂空
            set1.setDrawCircleHole(true);
            set1.setCircleHoleRadius(2f);
            set1.setValueTextSize(16f);

            //左下角指示器样式
            set1.setFormLineWidth(0f);
            set1.setFormLineDashEffect(new DashPathEffect(new float[]{0f, 0f}, 0f));
            set1.setFormSize(0f);
            //曲线区域颜色填充
            set1.setDrawFilled(false);
            if (Utils.getSDKInt() >= 18) {
                // fill drawable only supported on api level 18 and above
                Drawable drawable = ContextCompat.getDrawable(this, R.drawable.fade_tiwen);
                set1.setFillDrawable(drawable);
            } else {
                set1.setFillColor(Color.parseColor("#B3DCE2F3"));
            }
            if (values.size() <= 3)
                set1.setMode(LineDataSet.Mode.LINEAR);
            else
                set1.setMode(LineDataSet.Mode.CUBIC_BEZIER);
            ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
            dataSets.add(set1);
            LineData data = new LineData(dataSets);
            xueyangChart.setData(data);
        }

    }
    /**
     * 设置血压走势
     *
     * @param yVals1
     * @param yVals2
     */
    private void setXueya(ArrayList<Entry> yVals1, ArrayList<Entry> yVals2, ArrayList<Integer> colors1, ArrayList<Integer> colors2) {
        LineDataSet set1, set2;

        if (xueyaChart.getData() != null &&
                xueyaChart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) xueyaChart.getData().getDataSetByIndex(0);
            set2 = (LineDataSet) xueyaChart.getData().getDataSetByIndex(1);
            set1.setValues(yVals1);
            set2.setValues(yVals2);
            if (yVals1.size() <= 3)
                set1.setMode(LineDataSet.Mode.LINEAR);
            if (yVals2.size() <= 3)
                set2.setMode(LineDataSet.Mode.LINEAR);
            xueyaChart.getData().notifyDataChanged();
            xueyaChart.notifyDataSetChanged();
        } else {
            set1 = new LineDataSet(yVals1, "");
            //设置数据依赖左边的Y轴
            set1.setAxisDependency(YAxis.AxisDependency.LEFT);
            set1.setColor(getResources().getColor(R.color.line_color));
            set1.setValueTextColors(colors1);
            set1.setCircleColor(getResources().getColor(R.color.node_color));
            set1.setLineWidth(4f);

            set1.setCircleRadius(6f);
            set1.setDrawCircleHole(true);
            set1.setCircleHoleRadius(2f);

            set1.setHighLightColor(Color.rgb(244, 117, 117));
            //设置直线圆滑过渡
//            set1.setMode(LineDataSet.Mode.CUBIC_BEZIER);
            //曲线区域颜色填充
            set1.setDrawFilled(false);
            if (Utils.getSDKInt() >= 18) {
                // fill drawable only supported on api level 18 and above
                Drawable drawable = ContextCompat.getDrawable(this, R.drawable.fade_xueya_gaoya);
                set1.setFillDrawable(drawable);
            } else {
                set1.setFillColor(Color.parseColor("#B3CBF8C3"));
            }
            //左下角指示器样式
            set1.setFormLineWidth(0f);
            set1.setFormLineDashEffect(new DashPathEffect(new float[]{0f, 0f}, 0f));
            set1.setFormSize(0f);
            if (yVals1.size() <= 3)
                set1.setMode(LineDataSet.Mode.LINEAR);
            else
                set1.setMode(LineDataSet.Mode.CUBIC_BEZIER);

            set2 = new LineDataSet(yVals2, "");
            set2.setAxisDependency(YAxis.AxisDependency.LEFT);
            set2.setColor(getResources().getColor(R.color.line2_color));
            set2.setValueTextColors(colors2);
            set2.setCircleColor(getResources().getColor(R.color.node2_color));

            set2.setLineWidth(4f);
            set2.setCircleRadius(6f);
            set2.setDrawCircleHole(true);
            set2.setCircleHoleRadius(2f);
            set2.setHighLightColor(Color.rgb(244, 117, 117));
            //设置直线圆滑过渡
//            set2.setMode(LineDataSet.Mode.CUBIC_BEZIER);
            //曲线区域颜色填充
            set2.setDrawFilled(false);
            if (Utils.getSDKInt() >= 18) {
                // fill drawable only supported on api level 18 and above
                Drawable drawable = ContextCompat.getDrawable(this, R.drawable.fade_xueya_diya);
                set2.setFillDrawable(drawable);
            } else {
                set2.setFillColor(Color.parseColor("#B3DCE2F3"));
            }
            //左下角指示器样式
            set2.setFormLineWidth(0f);
            set2.setFormLineDashEffect(new DashPathEffect(new float[]{0f, 0f}, 0f));
            set2.setFormSize(0f);
            if (yVals2.size() <= 3)
                set2.setMode(LineDataSet.Mode.LINEAR);
            else
                set2.setMode(LineDataSet.Mode.CUBIC_BEZIER);
            LineData data = new LineData(set1, set2);
            data.setValueTextSize(16f);
            xueyaChart.setData(data);
        }
    }
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.one_week://默认是1：温度；2：血压；3：心率；4：血糖，5：血氧，6：脉搏,7:胆固醇，8：血尿酸
                timeFlag = 1;
                switch (temp) {
                    case "1":
                        getTiwen(weekAgoTime + "", currentTime + "");
                        break;
                    case "2":
                        getXueya(weekAgoTime + "", currentTime + "");
                        break;
                    case "4":
                        getXuetang(weekAgoTime + "", currentTime + "", eatedTime);
                        break;
                    case "5":
                        getXueyang(weekAgoTime + "", currentTime + "");
                        break;
                    case "7":
//                        getDangucun(weekAgoTime + "", currentTime + "");
                        break;
                    case "8":
//                        getXueniaosuan(weekAgoTime + "", currentTime + "");
                        break;
                    case "9":
//                        getXindian(weekAgoTime+"",currentTime+"");
                        break;
                }
                break;
            case R.id.one_month:
                timeFlag = 2;
                switch (temp) {
                    case "1":
                        getTiwen(monthAgoTime + "", currentTime + "");
                        break;
                    case "2":
                        getXueya(monthAgoTime + "", currentTime + "");
                        break;
                    case "4":
                        getXuetang(monthAgoTime + "", currentTime + "", eatedTime);
                        break;
                    case "5":
                        getXueyang(monthAgoTime + "", currentTime + "");
                        break;
                    case "7":
//                        getDangucun(monthAgoTime + "", currentTime + "");
                        break;
                    case "8":
//                        getXueniaosuan(monthAgoTime + "", currentTime + "");
                        break;
                    case "9":
//                        getXindian(monthAgoTime + "", currentTime + "");
                        break;
                }
                break;
            case R.id.one_season:
                timeFlag = 3;
                switch (temp) {
                    case "1":
                        getTiwen(seasonAgoTime + "", currentTime + "");
                        break;
                    case "2":
                        getXueya(seasonAgoTime + "", currentTime + "");
                        break;
                    case "4":
                        getXuetang(seasonAgoTime + "", currentTime + "", eatedTime);
                        break;
                    case "5":
                        getXueyang(seasonAgoTime + "", currentTime + "");
                        break;
                    case "7":
//                        getDangucun(seasonAgoTime + "", currentTime + "");
                        break;
                    case "8":
//                        getXueniaosuan(seasonAgoTime + "", currentTime + "");
                        break;
                    case "9":
//                        getXindian(monthAgoTime + "", currentTime + "");
                        break;
                }
                break;
            case R.id.one_year:
                timeFlag = 4;
                switch (temp) {
                    case "1":
                        getTiwen(yearAgoTime + "", currentTime + "");
                        break;
                    case "2":
                        getXueya(yearAgoTime + "", currentTime + "");
                        break;
                    case "4":
                        getXuetang(yearAgoTime + "", currentTime + "", eatedTime);
                        break;
                    case "5":
                        getXueyang(yearAgoTime + "", currentTime + "");
                        break;
                    case "7":
//                        getDangucun(yearAgoTime + "", currentTime + "");
                        break;
                    case "8":
//                        getXueniaosuan(yearAgoTime + "", currentTime + "");
                        break;
                    case "9":
//                        getXindian(yearAgoTime + "", currentTime + "");
                        break;
                }
                break;
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.rb_kongfu:
                eatedTime = 0;
                switch (timeFlag) {
                    case 1://一周
                        getXuetang(weekAgoTime + "", currentTime + "", eatedTime);
                        break;
                    case 2://一个月
                        getXuetang(monthAgoTime + "", currentTime + "", eatedTime);
                        break;
                    case 3:
                        getXuetang(seasonAgoTime + "", currentTime + "", eatedTime);
                        break;
                    case 4:
                        getXuetang(yearAgoTime + "", currentTime + "", eatedTime);
                        break;
                }
                break;
            case R.id.rb_one_hour:
                eatedTime = 1;
                switch (timeFlag) {
                    case 1://一周
                        getXuetang(weekAgoTime + "", currentTime + "", eatedTime);
                        break;
                    case 2://一个月
                        getXuetang(monthAgoTime + "", currentTime + "", eatedTime);
                        break;
                    case 3:
                        getXuetang(seasonAgoTime + "", currentTime + "", eatedTime);
                        break;
                    case 4:
                        getXuetang(yearAgoTime + "", currentTime + "", eatedTime);
                        break;
                }
                break;
            case R.id.rb_two_hour:
                eatedTime = 2;
                switch (timeFlag) {
                    case 1://一周
                        getXuetang(weekAgoTime + "", currentTime + "", eatedTime);
                        break;
                    case 2://一个月
                        getXuetang(monthAgoTime + "", currentTime + "", eatedTime);
                        break;
                    case 3:
                        getXuetang(seasonAgoTime + "", currentTime + "", eatedTime);
                        break;
                    case 4:
                        getXuetang(yearAgoTime + "", currentTime + "", eatedTime);
                        break;
                }
                break;
        }
    }
}
