package com.cui.mtd;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

/**
 * Created by Cui on 2017/2/16.
 */

public class Bullet {
    private int mPicId;
    private int mWidth;
    private int mHeight;
    private AppContext mAppContext;
    private float mSpeed;
    private Enemy mTarget;
    private Bitmap mPic;
    private float mLocationX;
    private float mLocationY;
    private boolean mIsAlive;
    private int mDamage;

    private Rect mRectRes;

    public Bullet() {
        mPicId = R.drawable.bullet3;
        mAppContext = AppContext.getInstance();
        mPic = BitmapFactory.decodeResource(mAppContext.getResources(), mPicId);
        mWidth = mPic.getWidth();
        mHeight = mPic.getHeight();
        mSpeed = mAppContext.getWindowWidth() / 20 / 24 * 10;
        mRectRes = new Rect(0, 0, mWidth, mHeight);
        mIsAlive = true;
        mDamage = 4;
    }

    public int getPicId() {
        return mPicId;
    }

    public int getWidth() {
        return mWidth;
    }

    public int getHeight() {
        return mHeight;
    }

    public float getSpeed() {
        return mSpeed;
    }

    public Enemy getTarget() {
        return mTarget;
    }

    public void setTarget(Enemy target) {
        mTarget = target;
    }

    public Bitmap getPic(){
        return mPic;
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


    public Rect getRectRes() {
        return mRectRes;
    }

    public boolean isAlive() {
        return mIsAlive;
    }

    public void setAlive(boolean alive) {
        mIsAlive = alive;
    }

    public int getDamage() {
        return mDamage;
    }

    public void setDamage(int damage) {
        mDamage = damage;
    }
}
