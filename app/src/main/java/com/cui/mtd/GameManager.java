package com.cui.mtd;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import java.util.ArrayList;
import java.util.Iterator;
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
    private List<Bullet> mBulletList;

    private GameManager() {
        mAppContext = AppContext.getInstance();
        mCurrentEnemyList = new ArrayList<>();
        mCurrentTowerList = new ArrayList<>();
        mBulletPaint = new Paint();
        mBulletPaint.setColor(mAppContext.getResources().getColor(R.color.black));
        mBulletPaint.setStrokeWidth(mAppContext.getResources().getDimension(R.dimen.bullet_width));
        mBulletTowerList = new ArrayList<>();
        mBulletList = new ArrayList<>();
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
        //遍历防御塔list和敌人list，判断是否射击
        for (Tower tower:mCurrentTowerList) {
            for (Enemy enemy : mCurrentEnemyList) {
                //没有目标就判断当前敌人与防御塔
                if (tower.getTarget() == null) {
                    calculateDistance(tower, enemy);
                } else {
                    //有目标就判断当前敌人是不是目标，是的话才判断距离
                    if (tower.getTarget().equals(enemy)) {
                        calculateDistance(tower, enemy);
                    }
                }
            }
        }
        handleBullet();
    }

    /**
     * 计算防御塔和敌人间的距离，判断是否应该射击
     * @param tower
     * @param enemy
     */
    private void calculateDistance(Tower tower, Enemy enemy) {
        float towerX = tower.getNodeObject().getLocationX() * mAppContext.getGridWidth();
        float towerY = tower.getNodeObject().getLocationY() * mAppContext.getGridHeight();
        float enemyX = enemy.getLocationX();
        float enemyY = enemy.getLocationY();
        double dis = Math.sqrt(Math.pow(towerX - enemyX, 2) + Math.pow(towerY - enemyY, 2));
        if (dis < tower.getRange()) {//表示到达射击距离
            //如果防御塔没有子弹就生成子弹
            //有子弹了就让子弹继续飞行，直到子弹到达目标就重新生成
            if (tower.getBullet() == null) {
                Bullet bullet = tower.createBullet();
                tower.setTarget(enemy);
                bullet.setTarget(enemy);
                mBulletList.add(bullet);
            }
        } else {
            //判断是不是当前目标走出攻击距离
            if (tower.getTarget() != null && tower.getTarget().equals(enemy)) {
                tower.setTarget(null);
            }
        }
    }

    /**
     * 处理子弹事件
     */
    private void handleBullet(){
        Iterator iterator = mBulletList.iterator();
        while (iterator.hasNext()) {
            Bullet bullet = (Bullet) iterator.next();
            Enemy enemy = bullet.getTarget();
            float enemyX = enemy.getLocationX() + enemy.getWidth() / 2;
            float enemyY = enemy.getLocationY() + enemy.getHeight() / 2;
            float bulletX = bullet.getLocationX();
            float bulletY = bullet.getLocationY();
            double distance = mAppContext.caculateDistance(enemyX, enemyY, bulletX, bulletY);
            if (distance >= bullet.getSpeed()) {//判断子弹是否到达敌人
                double degree = Math.atan2 (enemyY - bulletY, enemyX - bulletX);
                bullet.setLocationX((float) (bulletX + bullet.getSpeed() * Math.cos(degree)));
                bullet.setLocationY((float) (bulletY + bullet.getSpeed() * Math.sin(degree)));
                int left = (int) (bullet.getLocationX() - mAppContext.getGridHeight() / 8);
                int top = (int) (bullet.getLocationY() - mAppContext.getGridHeight() / 8);
                int right = (int) (bullet.getLocationX() + mAppContext.getGridHeight() / 8);
                int bottom = (int) (bullet.getLocationY() + mAppContext.getGridHeight() / 8);
                Rect rectDest = new Rect(left, top, right, bottom);
                mCanvas.drawBitmap(bullet.getPic(), bullet.getRectRes(), rectDest, null);
            } else {
                //到达敌人后移除子弹
                iterator.remove();
                bullet = null;
            }
        }

    }
}
