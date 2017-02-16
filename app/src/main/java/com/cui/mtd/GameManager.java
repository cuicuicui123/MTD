package com.cui.mtd;

import android.graphics.Canvas;
import android.graphics.Paint;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Cui on 2017/2/16.
 * 游戏中防御塔与怪物之间事件的处理
 */

public class GameManager {
    private static GameManager mInstance;
    private List<Tower> mCurrentTowerList;
    private List<Enemy> mCurrentEnemyList;
    private AppContext mAppContext;
    private Canvas mCanvas;
    private Paint mBulletPaint;

    private GameManager() {
        mAppContext = AppContext.getInstance();
        mCurrentEnemyList = new ArrayList<>();
        mCurrentTowerList = new ArrayList<>();
        mBulletPaint = new Paint();
        mBulletPaint.setColor(mAppContext.getResources().getColor(R.color.black));
        mBulletPaint.setStrokeWidth(mAppContext.getResources().getDimension(R.dimen.bullet_width));
    }

    public static GameManager getInstance(){
        if (mInstance == null) {
            synchronized (GameManager.class) {
                if (mInstance == null) {
                    mInstance = new GameManager();
                }
            }
        }
        return mInstance;
    }

    public List<Enemy> getCurrentEnemyList(){
        return mCurrentEnemyList;
    }

    public List<Tower> getCurrentTowerList(){
        return mCurrentTowerList;
    }

    public void setCanvas(Canvas canvas){
        mCanvas = canvas;
        for (Tower tower:mCurrentTowerList) {
            for (Enemy enemy : mCurrentEnemyList) {
                if (tower.getTarget() == null) {
                    if (calculateDistance(tower, enemy)) {
                        tower.setTarget(enemy);
                    }
                } else {
                    if (tower.getTarget().equals(enemy)) {
                        if (!calculateDistance(tower, enemy)) {
                            tower.setTarget(null);
                        }
                    }
                }
            }
        }
    }

    private boolean calculateDistance(Tower tower, Enemy enemy) {
        float towerX = tower.getNodeObject().getLocationX() * mAppContext.getGridWidth();
        float towerY = tower.getNodeObject().getLocationY() * mAppContext.getGridHeight();
        float enemyX = enemy.getLocationX();
        float enemyY = enemy.getLocationY();
        double dis = Math.sqrt(Math.pow(towerX - enemyX, 2) + Math.pow(towerY - enemyY, 2));
        if (dis < tower.getRange()) {
            mCanvas.drawLine(towerX + mAppContext.getGridWidth() / 2, towerY + mAppContext.getGridHeight() / 2,
                    enemyX + mAppContext.getGridWidth() / 2, enemyY + mAppContext.getGridHeight() / 2, mBulletPaint);
            return true;
        } else {
            return false;
        }
    }





}
