package com.cui.mtd;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

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

    public Enemy() {
        mAppContext = AppContext.getInstance();
        mPicRes = R.drawable.enemy2;
        mSpeed = (float) mAppContext.getWindowWidth() / 20 / 24;
        mHp = 40;
        mWidth = mAppContext.getWindowHeight() / 8;
        mHeight = mWidth;
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

    public void drawSelf(Canvas canvas){
        mPosition ++;
        Rect rectRes = new Rect(mPosition % 16 * mEnemyWidth, mDirection * mEnemyHeight,
                (mPosition % 4 + 1) * mEnemyWidth, (mDirection + 1) * mEnemyHeight);
        Rect rectDest = new Rect((int)mLocationX, (int)mLocationY, (int)mLocationX + mWidth, (int)mLocationY + mHeight);
        canvas.drawBitmap(BitmapFactory.decodeResource(mAppContext.getResources(), getPicRes()), rectRes, rectDest, null);
    }


}
