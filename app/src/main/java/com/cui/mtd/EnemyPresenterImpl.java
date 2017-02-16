package com.cui.mtd;

import android.graphics.Canvas;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Cui on 2017/2/14.
 */

public class EnemyPresenterImpl implements EnemyPresenter {
    private List<Enemy> mAllEnemyList;
    private List<Enemy> mCurrentEnemyList;
    private AppContext mAppContext;
    private Canvas mCanvas;
    private PathNode mRootNode;
    private PathNode mFirstNode;

    private int mCurrentWave = 0;
    private double mWait;
    private long mCurrentTime;

    public EnemyPresenterImpl(PathNode rootNode) {
        mAllEnemyList = new ArrayList<>();
        mCurrentEnemyList = new ArrayList<>();
        mAppContext = AppContext.getInstance();
        mRootNode = rootNode;
        mFirstNode = mRootNode.getThatNode();
        initEnemy();
    }

    @Override
    public void initEnemy() {
        String result = getFromAssets("enemy");
        try {
            JSONObject jsonObject = new JSONObject(result);
            JSONArray jsonArray = jsonObject.getJSONArray("Enemies");
            int length = jsonArray.length();
            for (int i = 0;i < length;i ++) {
                JSONObject enemyJson = jsonArray.getJSONObject(i);
                Enemy enemy = new Enemy();
                enemy.setWave(enemyJson.getInt("Wave"));
                enemy.setName(enemyJson.getString("EnemyName"));
                enemy.setLevel(enemyJson.getInt("Level"));
                enemy.setWait(enemyJson.getDouble("Wait"));
                enemy.setTargetNode(mFirstNode);
                enemy.setLocationX(mFirstNode.getLocationX() * mAppContext.getGridWidth());
                enemy.setLocationY(mFirstNode.getLocationY() * mAppContext.getGridHeight());
                mAllEnemyList.add(enemy);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //从assets 文件夹中获取文件并读取数据
    public String getFromAssets(String fileName){
        String result = "";
        try {
            InputStream in = mAppContext.getResources().getAssets().open(fileName);
            //获取文件的字节数
            int length = in.available();
            //创建byte数组
            byte[]  buffer = new byte[length];
            //将文件中的数据读到byte数组中
            in.read(buffer);
            result = new String(buffer);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public List<Enemy> getAllEnemyList() {
        return mAllEnemyList;
    }

    @Override
    public void removeEnemy(Enemy enemy) {

    }

    @Override
    public void setCanvas(Canvas canvas) {
        mCanvas = canvas;
        enemyAppear();
        for (Enemy enemy: mCurrentEnemyList) {
            enemy.drawSelf(canvas);
        }
    }

    @Override
    public void move() {
        //先计算移动方向，上下左右四个方向
        for (Enemy enemy: mCurrentEnemyList) {
            PathNode thisNode = enemy.getTargetNode();
            PathNode thatNode = thisNode.getThatNode();
            double dis = calculateDistance(enemy, thatNode);
            if (dis < 3d) {
                if (thatNode.getThatNode() != null) {
                    enemy.setTargetNode(thatNode);
                    thisNode = thatNode;
                    thatNode = thisNode.getThatNode();
                } else {
                    mCurrentEnemyList.remove(enemy);
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

    /**
     * 出怪
     */
    @Override
    public void enemyAppear() {
        if (mAllEnemyList.size() > 0) {
            if (mCurrentWave == 0) {
                Enemy enemy = mAllEnemyList.get(0);
                mCurrentEnemyList.add(enemy);
                mAllEnemyList.remove(0);
                mCurrentWave = enemy.getWave();
                mCurrentTime = System.currentTimeMillis();
                mWait = enemy.getWait();
            } else {
                Enemy enemy = mAllEnemyList.get(0);
                if (enemy.getWave() == mCurrentWave) {
                    if (System.currentTimeMillis() - mCurrentTime >= enemy.getWait() * 1000) {
                        mCurrentEnemyList.add(enemy);
                        mAllEnemyList.remove(0);
                        mCurrentTime = System.currentTimeMillis();
                    }
                } else {
                    if (mCurrentEnemyList.size() <= 0) {
                        mCurrentEnemyList.add(enemy);
                        mCurrentWave = enemy.getWave();
                        mAllEnemyList.remove(0);
                        mCurrentTime = System.currentTimeMillis();
                    }
                }
            }
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
