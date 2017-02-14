package com.cui.mtd;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

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

    private Canvas mCanvas;
    private AppContext mAppContext;

    private NodeObject[][] mNodeObjects;
    private PathNode mRootNode;

    private Paint mGridPaint;
    private Paint mBacPaint;
    private Paint mGridRectPaint;
    private Paint mPathPaint;

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
        mBacPaint = new Paint();
        mBacPaint.setColor(mAppContext.getResources().getColor(R.color.white));
        mNodeObjects = new NodeObject[12][8];
        mGridRectPaint = new Paint();
        mPathPaint = new Paint();
        mPathPaint.setColor(mAppContext.getResources().getColor(R.color.black));
        initNodeObject();
        initPath();
    }

    public void setCanvas(Canvas canvas){
        mCanvas = canvas;
        mCanvas.drawRect(0, 0, mDisplayWidth, mDisplayHeight, mBacPaint);
//        drawBackGround();
        drawColorRect();
        drawGrid();
        drawPathNode(mRootNode.getThatNode());
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

    /**
     * 绘制网格
     */
    private void drawGrid(){
        for (int i = 0;i < mGridSizeY;i ++) {
            mCanvas.drawLine(0, mDisplayHeight - i * mGridHeight - mGridPaintWidth / 2, mDisplayWidth,
                    mDisplayHeight - i * mGridHeight + mGridPaintWidth / 2, mGridPaint);
        }
        for (int i = 0;i < mGridSizeX;i ++) {
            mCanvas.drawLine(i * mGridWidth - mGridPaintWidth / 2, 0, i * mGridWidth + mGridPaintWidth / 2,
                    mDisplayHeight, mGridPaint);
        }
    }


    /**
     * 绘制颜色块
     */
    private void drawColorRect(){
        int red = mAppContext.getResources().getColor(R.color.red);
        int green = mAppContext.getResources().getColor(R.color.green);
        for (int i = 0;i < 12;i ++) {
            for (int j = 0;j < 8;j ++) {
                NodeObject nodeObject = mNodeObjects[i][j];
                mGridRectPaint.setColor(nodeObject.isPlace() ? green : red);
                float right = i == 11 ? mDisplayWidth : (i + 1) * mGridWidth;
                float bottom = j == 7 ? mDisplayHeight : (j + 1) * mGridHeight;
                mCanvas.drawRect(i * mGridWidth, j * mGridHeight, right, bottom, mGridRectPaint);
            }
        }
    }

    /**
     * 初始化地图中的格子
     */
    private void initNodeObject(){
        for (int i = 0;i < 12;i ++) {
            for (int j = 0;j < 8;j ++) {
                NodeObject nodeObject = new NodeObject();
                nodeObject.setPlace(isGridOnPath(i, j));
                mNodeObjects[i][j] = nodeObject;
            }
        }
    }

    public NodeObject[][] getNodeObjects(){
        return mNodeObjects;
    }

    /**
     * 判断格子是否出去路径之中
     * @param i 横坐标
     * @param j 纵坐标
     * @return
     */
    private boolean isGridOnPath(int i, int j){
        if (i >= 0 && i < 4) {
            if (j == 4) {
                return true;
            }
        }

        if (i == 4) {
            if (j >= 4 && j < 7) {
                return true;
            }
        }

        if (i > 4 && i < 8) {
            if (j == 6) {
                return true;
            }
        }

        if (i == 8) {
            if (j >= 4 && j <= 6) {
                return true;
            }
        }

        if (i > 8) {
            if (j == 4) {
                return true;
            }
        }
        return false;
    }

    /**
     * 初始化rootNode
     */
    private void initPath(){
        mRootNode = new PathNode();
        mRootNode.setLocationX(0);
        mRootNode.setLocationY(0);
        initPathNode(mRootNode);
    }

    /**
     * 初始化路径信息
     * @param pathNode
     */
    private void initPathNode(PathNode pathNode){
        PathNode thisNode = pathNode;
        PathNode thatNode = new PathNode();
        thatNode.setLocationX(0);
        thatNode.setLocationY(4);
        thisNode.setThatNode(thatNode);
        PathNode thatNode2 = new PathNode();
        thatNode2.setLocationX((float) 4.5);
        thatNode2.setLocationY(4);
        thatNode.setThatNode(thatNode2);
        PathNode thatNode3 = new PathNode();
        thatNode3.setLocationX((float) 4.5);
        thatNode3.setLocationY(6);
        thatNode2.setThatNode(thatNode3);
        PathNode thatNode4 = new PathNode();
        thatNode4.setLocationX((float) 8.5);
        thatNode4.setLocationY(6);
        thatNode3.setThatNode(thatNode4);
        PathNode thatNode5 = new PathNode();
        thatNode5.setLocationX((float) 8.5);
        thatNode5.setLocationY(4);
        thatNode4.setThatNode(thatNode5);
        PathNode thatNode6 = new PathNode();
        thatNode6.setLocationX(12);
        thatNode6.setLocationY(4);
        thatNode5.setThatNode(thatNode6);
    }

    /**
     * 绘制路径
     * @param pathNode 节点
     */
    private void drawPathNode(PathNode pathNode){
        if (pathNode.getThatNode() != null) {
            PathNode thatNode = pathNode.getThatNode();
            float startX = pathNode.getLocationX();
            float startY = pathNode.getLocationY();

            float endX = thatNode.getLocationX();
            float endY = thatNode.getLocationY();
            mCanvas.drawLine((startX * mGridWidth), (float)( startY + 0.5) * mGridHeight, endX * mGridWidth, (float)(endY + 0.5) * mGridHeight, mPathPaint);
            drawPathNode(thatNode);
        }
    }


}
