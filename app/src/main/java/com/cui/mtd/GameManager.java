package com.cui.mtd;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

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
    private List<Tower> mBulletTowerList;

    private GameManager() {
        mAppContext = AppContext.getInstance();
        mCurrentEnemyList = new ArrayList<>();
        mCurrentTowerList = new ArrayList<>();
        mBulletPaint = new Paint();
        mBulletPaint.setColor(mAppContext.getResources().getColor(R.color.black));
        mBulletPaint.setStrokeWidth(mAppContext.getResources().getDimension(R.dimen.bullet_width));
        mBulletTowerList = new ArrayList<>();
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
                    calculateDistance(tower, enemy);
                } else {
                    if (tower.getTarget().equals(enemy)) {
                        calculateDistance(tower, enemy);
                    }
                }
            }
        }
        handleBullet();
    }

    private void calculateDistance(Tower tower, Enemy enemy) {
        float towerX = tower.getNodeObject().getLocationX() * mAppContext.getGridWidth();
        float towerY = tower.getNodeObject().getLocationY() * mAppContext.getGridHeight();
        float enemyX = enemy.getLocationX();
        float enemyY = enemy.getLocationY();
        double dis = Math.sqrt(Math.pow(towerX - enemyX, 2) + Math.pow(towerY - enemyY, 2));
        if (dis < tower.getRange()) {//表示到达射击距离
            if (tower.getBullet() == null) {
                Bullet bullet = tower.createBullet();
//                int centerTowerX = (int) (towerX + mAppContext.getGridWidth() / 2);
//                int centerTowerY = (int) (towerY + mAppContext.getGridHeight() / 2);
//                int centerEnemyX = (int) (enemyX + enemy.getWidth() / 2);
//                int centerEnemyY = (int) (enemyY + enemy.getHeight() / 2);
//                int left = (centerTowerX + centerEnemyX) / 2 - mAppContext.getGridHeight() / 8;
//                int top = (centerTowerY + centerEnemyY) / 2 - mAppContext.getGridHeight() / 8;
//                int right = (centerTowerX + centerEnemyX) / 2 + mAppContext.getGridHeight() / 8;
//                int bottom = (centerTowerY + centerEnemyY) / 2 + mAppContext.getGridHeight() / 8;
//                Rect rectDest = new Rect(left, top, right, bottom);
//                mCanvas.drawBitmap(tower.getBulletBitmap(), tower.getRectRes(), rectDest, null);
                tower.setTarget(enemy);
                mBulletTowerList.add(tower);
            }
        } else {
            if (tower.getTarget() != null && tower.getTarget().equals(enemy)) {
                tower.setTarget(null);
            }
        }
    }


    private void handleBullet(){
        for (Tower tower:mBulletTowerList) {
            Bullet bullet = tower.getBullet();
            Enemy enemy = tower.getTarget();
            float enemyX = enemy.getLocationX();
            float enemyY = enemy.getLocationY();
            float bulletX = bullet.getLocationX();
            float bulletY = bullet.getLocationY();
            double distance = mAppContext.caculateDistance(enemyX, enemyY, bulletX, bulletY);
            if (distance >= 3d) {
                double degree = Math.toDegrees (Math.atan ((enemy.getLocationY() - bullet.getLocationY()) / (enemy.getLocationX() - bullet.getLocationX())));
                bullet.setLocationX((float) (bullet.getLocationX() + bullet.getSpeed() * Math.cos(degree)));
                bullet.setLocationY((float) (bullet.getLocationY() + bullet.getSpeed() * Math.sin(degree)));
                int left = (int) (bullet.getLocationX() - mAppContext.getGridHeight() / 8);
                int top = (int) (bullet.getLocationY() - mAppContext.getGridHeight() / 8);
                int right = (int) (bullet.getLocationX() + mAppContext.getGridHeight() / 8);
                int bottom = (int) (bullet.getLocationY() + mAppContext.getGridHeight() / 8);
                Rect rectDest = new Rect(left, top, right, bottom);
                mCanvas.drawBitmap(bullet.getPic(), tower.getRectRes(), rectDest, null);
            } else {
                bullet = null;
            }

        }
    }





}
