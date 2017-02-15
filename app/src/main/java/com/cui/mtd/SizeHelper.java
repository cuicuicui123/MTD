package com.cui.mtd;

/**
 * Created by Cui on 2017/2/15.
 */

public class SizeHelper {
    private int mWidth;
    private int mHeight;
    private AppContext mAppContext;
    private int mRealWidth;
    private int mRealHeight;

    public SizeHelper(int width, int height) {
        mWidth = width;
        mHeight = height;
        mAppContext = AppContext.getInstance();
        handleWidthHeight();
    }

    private void handleWidthHeight(){
        mRealWidth = mWidth;
        mRealHeight = mHeight;
        double scaleWidth = mWidth > (mAppContext.getGridWidth() - 10) ? (double) mWidth / (double) (mAppContext.getGridWidth() - 10) : 0;
        double scaleHeight = mHeight > (mAppContext.getGridHeight() - 10) ? (double) mHeight / (double) (mAppContext.getGridHeight() - 10) : 0;

        if (scaleHeight != 0 || scaleWidth != 0) {
            double scale = scaleHeight > scaleWidth ? scaleHeight : scaleWidth;
            mRealWidth = (int) (mWidth / scale);
            mRealHeight = (int) (mHeight / scale);
        }
    }

    public int getRealWidth() {
        return mRealWidth;
    }

    public int getRealHeight() {
        return mRealHeight;
    }
}
