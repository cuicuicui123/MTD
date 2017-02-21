package com.cui.mtd;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Cui on 2017/2/14.
 * 敌人逻辑处理类
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
    private GameManager mGameManager;

    private int mExpResId;

    public EnemyPresenterImpl(PathNode rootNode) {
        mAllEnemyList = new ArrayList<>();

        mAppContext = AppContext.getInstance();
        mRootNode = rootNode;
        mFirstNode = mRootNode.getThatNode();
        mGameManager = GameManager.getInstance();
        mCurrentEnemyList = mGameManager.getCurrentEnemyList();
        mExpResId = R.drawable.explosion;
        initEnemy();
    }

    /**
     * 根据json确定怪物
     */
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

    /**
     * 从assets 文件夹中获取文件并读取数据确定出怪顺序
     * @param fileName
     * @return
     */
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
    }

    /**
     * 敌人移动
     */
    @Override
    public void move() {
        //先计算移动方向，上下左右四个方向
        Iterator iterator = mCurrentEnemyList.iterator();
        while (iterator.hasNext()) {
            Enemy enemy = (Enemy) iterator.next();
            if (enemy.getHp() > 0) {//判断是否血量大于0，小于0爆炸
                PathNode thisNode = enemy.getTargetNode();//当前节点
                PathNode thatNode = thisNode.getThatNode();//下一个节点根据这两个节点判断移动方向
                double dis = calculateDistance(enemy, thatNode);
                if (dis < 3d) {//表示移动到下一个节点
                    if (thatNode.getThatNode() != null) {//判断是否走完了
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
            } else {//爆炸
                if (enemy.getExpTime() < 10) {
                    drawExplosion(enemy);
                } else {
                    iterator.remove();
                }
            }
        }
    }

    /**
     * boom
     * @param enemy
     */
    private void drawExplosion(Enemy enemy){
        int expTime = enemy.getExpTime();
        Bitmap bitmap = BitmapFactory.decodeResource(mAppContext.getResources(), mExpResId);
        int mExpWidth = bitmap.getWidth() / 10;
        float x = enemy.getLocationX();
        float y = enemy.getLocationY();
        Rect srcRect = new Rect(expTime * mExpWidth, 0, (expTime + 1) * mExpWidth, mExpWidth);
        Rect destRect = new Rect((int) x, (int)y, (int) x + enemy.getWidth(), (int) y + enemy.getHeight());
        mCanvas.drawBitmap(bitmap, srcRect, destRect, null);
        enemy.setExpTime(expTime + 1);
    }

    /**
     * 出怪
     */
    @Override
    public void enemyAppear() {
        if (mAllEnemyList.size() > 0) {
            if (mCurrentWave == 0) {//表示当前是第一波
                Enemy enemy = mAllEnemyList.get(0);
                mCurrentEnemyList.add(enemy);
                mAllEnemyList.remove(0);
                mCurrentWave = enemy.getWave();
                mCurrentTime = System.currentTimeMillis();
                mWait = enemy.getWait();
            } else {
                Enemy enemy = mAllEnemyList.get(0);
                if (enemy.getWave() == mCurrentWave) {//如果是当前波次等待给定时间间隔后出怪
                    if (System.currentTimeMillis() - mCurrentTime >= enemy.getWait() * 1000) {
                        mCurrentEnemyList.add(enemy);
                        mAllEnemyList.remove(0);
                        mCurrentTime = System.currentTimeMillis();
                    }
                } else {//不是当前波次等待这波怪全都走完之后生成下一波
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

    @Override
    public void enemyDie(Enemy enemy) {
        mCurrentEnemyList.remove(enemy);
    }

    private double calculateDistance(Enemy enemy, PathNode thatNode){
        float enemyX = enemy.getLocationX();
        float enemyY = enemy.getLocationY();
        float nodeX = (float) ((thatNode.getLocationX() - 0.5) * mAppContext.getGridWidth() + enemy.getDistance());
        float nodeY = thatNode.getLocationY() * mAppContext.getGridHeight();
        double distance = Math.sqrt(Math.pow(enemyX - nodeX, 2) + Math.pow(enemyY - nodeY, 2));
        return distance;
    }


}
