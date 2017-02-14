package com.cui.mtd;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * Created by Cui on 2016/10/30.
 * 像素和密度转换的工具
 */

public class DensityUtil {
    private static float dmDensityDpi = 0.0f;
    private static DisplayMetrics dm;
    private static float scale = 0.0f;

    public DensityUtil(Context context) {
        dm = new DisplayMetrics();
        dm = context.getApplicationContext().getResources().getDisplayMetrics();
        setDmDensityDpi(dm.densityDpi);
        scale = getDmDensityDpi() / 160;
    }

    public static float getDmDensityDpi() {
        return dmDensityDpi;
    }

    public static void setDmDensityDpi(float dmDensityDpi) {
        DensityUtil.dmDensityDpi = dmDensityDpi;
    }

    /**
     * 像素转换密度
     * */
    public int dip2px(float dipValue) {
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * 密度转换像素
     * */
    public int px2dip(float pxValue) {
        return (int) (pxValue / scale + 0.5f);
    }

    @Override
    public String toString() {
        return " dmDensityDpi:" + dmDensityDpi;
    }
}
