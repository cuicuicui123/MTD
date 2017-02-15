package com.cui.mtd;

/**
 * Created by m1888 on 2016/4/13.
 */

import android.app.Application;
import android.content.Context;
import android.view.Display;
import android.view.WindowManager;

import java.io.File;

/**
 * 全局应用程序类：用于保存和调用全局应用配置及访问网络数据
 */
public class AppContext extends Application {
    private static final String TAG = AppContext.class.getSimpleName();
    private static final String APP_CACAHE_DIRNAME = "/webcache";
    public static long TIME_SPAN = 1000 / 24;
    public int mGridWidth;
    public int mGridHeight;

    public int LEFT = 1;
    public int RIGHT = 2;
    public int TOP = 3;
    public int BOTTOM = 0;
    private int n = 2;
    //singleton
    private static AppContext appContext = null;
    private Display display;

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = this;
        init();
    }

    public static AppContext getInstance() {
        return appContext;
    }

    /**
     * 初始化
     */
    private void init() {
        if (display == null) {
            WindowManager windowManager = (WindowManager)
                    getSystemService(Context.WINDOW_SERVICE);
            display = windowManager.getDefaultDisplay();
        }
        mGridWidth = display.getWidth() / 12;
        mGridHeight = display.getHeight() / 8;
    }

    public String getCachePath() {
        File cacheDir;
        if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED))
            cacheDir = getExternalCacheDir();
        else
            cacheDir = getCacheDir();
        if (!cacheDir.exists())
            cacheDir.mkdirs();
        return cacheDir.getAbsolutePath();
    }
    /**
     * @return
     * @Description： 获取当前屏幕的宽度
     */
    public int getWindowWidth() {
        return display.getWidth();
    }

    /**
     * @return
     * @Description： 获取当前屏幕的高度
     */
    public int getWindowHeight() {
        return display.getHeight();
    }

    /**
     * @return
     * @Description： 获取当前屏幕一半宽度
     */
    public int getHalfWidth() {
        return display.getWidth() / 2;
    }

    /**
     * @return
     * @Description： 获取当前屏幕1/4宽度
     */
    public int getQuarterWidth() {
        return display.getWidth() / 4;
    }

    public int getGridWidth() {
        return mGridWidth;
    }

    public int getGridHeight() {
        return mGridHeight;
    }
}

