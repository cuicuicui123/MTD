package com.cui.mtd;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import java.util.List;

/**
 * Created by Cui on 2017/2/9.
 * 地图类，用于绘制网格判断哪些地方可以摆放防御塔
 */

public class GridMap {
    private int mGridSizeX;
    private int mGridSizeY;
    private int mDisplayWidth;
    private int mDisplayHeight;
    private float mGridWidth;
    private float mGridHeight;
    private int mGridPaintWidth;

    private List<GridNode> mGridNodeList;

    private Canvas mCanvas;
    private AppContext mAppContext;

    private Paint mGridPaint;

    public GridMap() {
        mAppContext = AppContext.getInstance();
        mDisplayWidth = mAppContext.getWindowWidth();
        mDisplayHeight = mAppContext.getWindowHeight();
        mGridSizeY = 8;
        mGridSizeX = 12;
        mGridHeight = mDisplayHeight / mGridSizeY;
        mGridWidth = mDisplayWidth / mGridSizeX;
        mGridPaint = new Paint();
        mGridPaint.setColor(mAppContext.getResources().getColor(R.color.colorPrimary));
        mGridPaintWidth = (int) mAppContext.getResources().getDimension(R.dimen.grid_width);
        mGridPaint.setStrokeWidth(mGridPaintWidth);
    }

    public void setCanvas(Canvas canvas){
        mCanvas = canvas;
        drawBackGround();
        drawGrid();
    }

    /**
     * 绘制背景地图
     */
    private void drawBackGround(){
        Bitmap bitmap = BitmapFactory.decodeResource(mAppContext.getResources(), R.drawable.map);
        Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        Rect rectF = new Rect(0, 0, mDisplayWidth, mDisplayHeight);
        mCanvas.drawBitmap(bitmap, rect, rectF, null);
        Paint paint = new Paint();
        paint.setColor(0x000000);
        mCanvas.drawRect(0, 0, mDisplayWidth, mDisplayHeight, paint);
    }

    private void drawGrid(){
        for (int i = 0;i < mGridSizeY;i ++) {
            mCanvas.drawLine(0, mDisplayHeight - i * mGridHeight - mGridPaintWidth / 2, mDisplayWidth, mDisplayHeight - i * mGridHeight + mGridPaintWidth / 2, mGridPaint);
        }
        for (int i = 0;i < mGridSizeX;i ++) {
            mCanvas.drawLine(i * mGridWidth - mGridPaintWidth / 2, 0, i * mGridWidth + mGridPaintWidth / 2, mDisplayHeight, mGridPaint);
        }
    }


}
