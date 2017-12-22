package com.ml.doctor.bean;

/**
 * 图表设置的基本配置
 * Created by gzq on 2017/12/20.
 */

public class ChartSetting {
    /**
     * 是否显示右下角的文字描述
     */
    private boolean isShowDescription;
    /**
     * 是否可以触摸
     */
    private boolean isTouchEnable;
    /**
     * 是否可以拖动
     */
    private boolean isDragEnable;
    /**
     * 是否可以缩放
     */
    private boolean isScaleEnable;
    /**
     * 是否启用Y轴的缩放
     */
    private boolean isScaleYEnable;
    /**
     * 图表距离左边的margin值
     */
    private int  extraLeftOff;
    /**
     * 图表距离右边的margin值
     */
    private int extraRightOff;

}
