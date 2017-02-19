package com.cui.mtd;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Cui on 2017/2/14.
 */

public class Enemy {
    private int mPicRes;
    private float mSpeed;
    private int mHp;
    private int mWidth;
    private int mHeight;
    private int mEnemyWidth;
    private int mEnemyHeight;
    private int mDirection;
    private int mPosition;
    private int mWave;
    private String mName;
    private int mLevel;
    private double mWait;

    private float mLocationX;
    private float mLocationY;

    private PathNode mTargetNode;

    private AppContext mAppContext;

    private Paint mHpPaint;

    private float mDistance;

    private List<Tower> mTowerList;

    private int mExpTime;

    public Enemy() {
        mAppContext = AppContext.getInstance();
        mPicRes = R.drawable.enemy2;
        mSpeed = (float) mAppContext.getWindowWidth() / 20 / 24;
        mHp = 40;
        mWidth = mAppContext.getWindowHeight() / 8;
        mHeight = mWidth;
        mPosition = mAppContext.RIGHT;
        mDistance = (mAppContext.getGridWidth() - mWidth) / 2;
        mHpPaint = new Paint();
        mHpPaint.setColor(mAppContext.getResources().getColor(R.color.red));
        mTowerList = new ArrayList<>();
        mExpTime = 0;
        getEnemyWidthAndHeight();
    }

    private void getEnemyWidthAndHeight(){
        Bitmap bitmap = BitmapFactory.decodeResource(mAppContext.getResources(), mPicRes);
        mEnemyWidth = bitmap.getWidth() / 4;
        mEnemyHeight = bitmap.getHeight() / 4;
    }

    public int getEnemyWidth() {
        return mEnemyWidth;
    }


    public int getEnemyHeight() {
        return mEnemyHeight;
    }

    public float getLocationX() {
        return mLocationX;
    }

    public void setLocationX(float locationX) {
        mLocationX = locationX;
    }

    public float getLocationY() {
        return mLocationY;
    }

    public void setLocationY(float locationY) {
        mLocationY = locationY;
    }

    public int getPicRes() {
        return mPicRes;
    }

    public float getSpeed() {
        return mSpeed;
    }

    public int getHp() {
        return mHp;
    }

    public int getWidth() {
        return mWidth;
    }

    public int getHeight() {
        return mHeight;
    }

    public PathNode getTargetNode() {
        return mTargetNode;
    }

    public void setTargetNode(PathNode targetNode) {
        mTargetNode = targetNode;
    }

    public void setPosition(int direction){
        mDirection = direction;
        mPosition = 0;
    }

    public void setPicRes(int picRes) {
        mPicRes = picRes;
    }

    public int getWave() {
        return mWave;
    }

    public void setWave(int wave) {
        mWave = wave;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public int getLevel() {
        return mLevel;
    }

    public void setLevel(int level) {
        mLevel = level;
    }

    public double getWait() {
        return mWait;
    }

    public void setWait(double wait) {
        mWait = wait;
    }

    public float getDistance() {
        return mDistance;
    }

    public void setHp(int hp) {
        mHp = hp;
    }

    public List<Tower> getTowerList() {
        return mTowerList;
    }

    public void setTowerList(List<Tower> towerList) {
        mTowerList = towerList;
    }


    public int getExpTime() {
        return mExpTime;
    }

    public void setExpTime(int expTime) {
        mExpTime = expTime;
    }

    public void drawSelf(Canvas canvas){
        mPosition ++;
        Rect rectRes = new Rect(mPosition % 16 * mEnemyWidth, mDirection * mEnemyHeight,
                (mPosition % 4 + 1) * mEnemyWidth, (mDirection + 1) * mEnemyHeight);
        int left = (int) (mLocationX);
        int top = (int) mLocationY;
        int right = (int) (mLocationX + mWidth);
        int bottom = (int) (mLocationY + mHeight);
        Rect rectDest = new Rect(left, top, right, bottom);
        canvas.drawBitmap(BitmapFactory.decodeResource(mAppContext.getResources(), getPicRes()), rectRes, rectDest, null);
        if (mHp > 0) {
            canvas.drawRect(mLocationX, mLocationY - 5, mLocationX + mHp * mWidth / 40 , mLocationY, mHpPaint);
        }
    }


}
