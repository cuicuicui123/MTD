package com.cui.mtd;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

/**
 * Created by Cui on 2017/2/14.
 */

public class TowerPresenterImpl implements TowerPresenter {
    private Canvas mCanvas;
    private AppContext mAppContext;

    private int mSelectTowerWidth;
    private int mSelectTowerHeight;

    public TowerPresenterImpl() {
        mAppContext = AppContext.getInstance();
        initSelectTowerWidthHeight();
    }

    @Override
    public void setCanvas(Canvas canvas) {
        mCanvas = canvas;
        drawSelectTower();
    }

    @Override
    public void drawSelectTower() {
        Rect rectSrc = new Rect(0, 0, mSelectTowerWidth, mSelectTowerHeight);
        Rect rectDest = new Rect(mAppContext.getWindowWidth() - mSelectTowerWidth, mAppContext.getWindowHeight() - mSelectTowerHeight
                , mAppContext.getWindowWidth(), mAppContext.getWindowHeight());
        mCanvas.drawBitmap(BitmapFactory.decodeResource(mAppContext.getResources(), R.drawable.tower1_ico), rectSrc, rectDest, null);
    }

    private void initSelectTowerWidthHeight(){
        Bitmap bitmap = BitmapFactory.decodeResource(mAppContext.getResources(), R.drawable.tower1_ico);
        mSelectTowerWidth = bitmap.getWidth();
        mSelectTowerHeight = bitmap.getHeight();
        bitmap.recycle();
    }


}
