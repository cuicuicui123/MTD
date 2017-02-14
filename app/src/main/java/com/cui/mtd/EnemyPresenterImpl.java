package com.cui.mtd;

import android.graphics.Canvas;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Cui on 2017/2/14.
 */

public class EnemyPresenterImpl implements EnemyPresenter {
    private List<Enemy> mEnemyList;
    private AppContext mAppContext;
    private Canvas mCanvas;
    private PathNode mRootNode;
    private PathNode mFirstNode;

    public EnemyPresenterImpl(PathNode rootNode) {
        mEnemyList = new ArrayList<>();
        mAppContext = AppContext.getInstance();
        mRootNode = rootNode;
        mFirstNode = mRootNode.getThatNode();
        initEnemy();
    }

    @Override
    public void initEnemy() {
        Enemy enemy = new Enemy();
        PathNode thatNode = mRootNode.getThatNode();
        enemy.setLocationX(thatNode.getLocationX() * mAppContext.getGridWidth());
        enemy.setLocationY(thatNode.getLocationY() * mAppContext.getGridHeight());
        enemy.setTargetNode(mFirstNode);
        mEnemyList.add(enemy);
    }

    @Override
    public List<Enemy> getEnemyList() {
        return mEnemyList;
    }

    @Override
    public void removeEnemy(Enemy enemy) {
        if (mEnemyList.contains(enemy)) {
            mEnemyList.remove(enemy);
        }
    }

    @Override
    public void setCanvas(Canvas canvas) {
        mCanvas = canvas;
        for (Enemy enemy:mEnemyList) {
            enemy.drawSelf(canvas);
        }
    }

    @Override
    public void move() {
        //先计算移动方向，上下左右四个方向
        for (Enemy enemy:mEnemyList) {
            PathNode thisNode = enemy.getTargetNode();
            PathNode thatNode = thisNode.getThatNode();
            double dis = calculateDistance(enemy, thatNode);
            if (dis < 3d) {
                if (thatNode.getThatNode() != null) {
                    enemy.setTargetNode(thatNode);
                    thisNode = thatNode;
                    thatNode = thisNode.getThatNode();
                } else {
                    mEnemyList.remove(enemy);
                    return;
                }
            }
            if (thisNode.getLocationX() < thatNode.getLocationX()) {//右
                enemy.setLocationX(enemy.getLocationX() + enemy.getSpeed());
                enemy.setPosition(mAppContext.RIGHT);
            }

            if (thisNode.getLocationY() < thatNode.getLocationY()) {//下
                enemy.setLocationY(enemy.getLocationY() + enemy.getSpeed());
                enemy.setPosition(mAppContext.BOTTOM);
            }

            if (thisNode.getLocationX() > thatNode.getLocationX()) {//左
                enemy.setLocationX(enemy.getLocationX() - enemy.getSpeed());
                enemy.setPosition(mAppContext.LEFT);
            }

            if (thisNode.getLocationY() > thatNode.getLocationY()) {//上
                enemy.setLocationY(enemy.getLocationY() - enemy.getSpeed());
                enemy.setPosition(mAppContext.TOP);
            }
            enemy.drawSelf(mCanvas);
        }
    }

    private double calculateDistance(Enemy enemy, PathNode thatNode){
        float enemyX = enemy.getLocationX();
        float enemyY = enemy.getLocationY();
        float nodeX = (float) ((thatNode.getLocationX() - 0.5) * mAppContext.getGridWidth());
        float nodeY = thatNode.getLocationY() * mAppContext.getGridHeight();
        double distance = Math.sqrt(Math.pow(enemyX - nodeX, 2) + Math.pow(enemyY - nodeY, 2));
        return distance;
    }


}
