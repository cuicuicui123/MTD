package com.cui.mtd;

import android.graphics.Canvas;

import java.util.List;

/**
 * Created by Cui on 2017/2/14.
 * 处理敌人的公共接口
 */

public interface EnemyPresenter {

    void initEnemy();
    List<Enemy> getAllEnemyList();
    void removeEnemy(Enemy enemy);
    void setCanvas(Canvas canvas);
    void move();
    void enemyAppear();
    void enemyDie(Enemy enemy);
}
