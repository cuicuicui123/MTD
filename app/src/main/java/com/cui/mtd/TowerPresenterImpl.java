package com.cui.mtd;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.MotionEvent;

import java.util.List;

/**
 * Created by Cui on 2017/2/14.
 * 防御塔逻辑处理类
 */

public class TowerPresenterImpl implements TowerPresenter {
    private Canvas mCanvas;
    private AppContext mAppContext;

    private int mSelectTowerWidth;
    private int mSelectTowerHeight;
    private int mRealWidth;
    private int mRealHeight;

    private int mWhite;
    private int mGreen;
    private boolean mHasSelectTower = false;

    private List<Tower> mTowerList;

    private GridMap mGridMap;
    private GameManager mGameManager;


    public TowerPresenterImpl() {
        mAppContext = AppContext.getInstance();
        mGridMap = GridMap.getInstance();
        mGreen = mAppContext.getResources().getColor(R.color.green);
        mWhite = mAppContext.getResources().getColor(R.color.white);

        mGameManager = GameManager.getInstance();
        mTowerList = mGameManager.getCurrentTowerList();

        initSelectTowerWidthHeight();
        NodeObject nodeObject = mGridMap.getNodeObjects()[11][7];
        nodeObject.setPlace(false);
        nodeObject.setSelectTower(true);
    }

    /**
     * 获得底部选择防御塔的宽高
     */
    private void initSelectTowerWidthHeight(){
        Bitmap bitmap = BitmapFactory.decodeResource(mAppContext.getResources(), R.drawable.tower);
        mSelectTowerWidth = bitmap.getWidth();
        mSelectTowerHeight = bitmap.getHeight();
        SizeHelper sizeHelper = new SizeHelper(mSelectTowerWidth, mSelectTowerHeight);
        mRealWidth = sizeHelper.getRealWidth();
        mRealHeight = sizeHelper.getRealHeight();
        bitmap.recycle();
    }

    @Override
    public void setCanvas(Canvas canvas) {
        mCanvas = canvas;
        drawSelectTower();
        drawTower();
    }

    /**
     * 绘制底部选择防御塔
     */
    @Override
    public void drawSelectTower() {
        Rect rectSrc = new Rect(0, 0, mSelectTowerWidth, mSelectTowerHeight);
        int left = mAppContext.getWindowWidth() - (mAppContext.getGridWidth() + mRealWidth) / 2;
        int top = mAppContext.getWindowHeight() - (mAppContext.getGridHeight() + mRealHeight) / 2;
        int right = mAppContext.getWindowWidth() - (mAppContext.getGridWidth() - mRealWidth) / 2;
        int bottom = mAppContext.getWindowHeight() - (mAppContext.getGridHeight() - mRealHeight) / 2;
        Rect rectDest = new Rect(left, top, right, bottom);
        mCanvas.drawBitmap(BitmapFactory.decodeResource(mAppContext.getResources(), R.drawable.tower), rectSrc, rectDest, null);
    }

    /**
     * 处理点击事件，选择建造防御塔
     * @param event
     * @return
     */
    @Override
    public boolean handleTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                NodeObject nodeObject = mGridMap.findNodeObjectByLocation(event.getX(), event.getY());
                if (!mHasSelectTower) {//没有选择塔
                    if (nodeObject.isSelectTower()) {
                        if (mGridMap.getMoney() >= new Tower().getValue()) {//判断钱够不够
                            mGridMap.setBacColor(mGreen);
                            mHasSelectTower = true;
                        } else {
                            mAppContext.makeToast("金钱不足！");
                        }
                    } else {//如果没有选择防御塔，也没有点到建造防御塔图标，则这里就不处理点击事件，交给地图类去判断是否点击暂停按钮
                        mGridMap.handleTouchEvent(event);
                    }
                } else {//选择了防御塔
                    if (nodeObject.isPlace()) {
                        mGridMap.setBacColor(mWhite);
                        Tower tower = new Tower();
                        tower.setNodeObject(nodeObject);
                        nodeObject.setPlace(false);
                        mTowerList.add(tower);
                        mGridMap.setMoney(mGridMap.getMoney() - tower.getValue());
                        mHasSelectTower = false;
                    } else {
                        mAppContext.makeToast("此处不可建造防御塔！");
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
            default:
                break;
        }
        return true;
    }

    /**
     * 绘制防御塔
     */
    @Override
    public void drawTower() {
        for (Tower tower:mTowerList) {
            NodeObject nodeObject = tower.getNodeObject();
            Rect rectSrc = new Rect(0, 0, tower.getWidth(), tower.getHeight());
            int left = nodeObject.getLocationX() * mAppContext.getGridWidth() + (mAppContext.getGridWidth() - tower.getRealWidth()) / 2;
            int top = nodeObject.getLocationY() * mAppContext.getGridHeight() + (mAppContext.getGridHeight() - tower.getRealHeight()) / 2;
            int right = (nodeObject.getLocationX() + 1) * mAppContext.getGridWidth() - (mAppContext.getGridWidth() - tower.getRealWidth()) / 2;
            int bottom = (nodeObject.getLocationY() + 1) * mAppContext.getGridHeight() - (mAppContext.getGridHeight() - tower.getRealHeight()) / 2;
            Rect rectDest = new Rect(left, top, right, bottom);
            mCanvas.drawBitmap(BitmapFactory.decodeResource(mAppContext.getResources(), R.drawable.tower), rectSrc, rectDest, null);
        }
    }


}
