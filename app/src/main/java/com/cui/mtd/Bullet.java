package com.cui.mtd;

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

    public Bullet() {
        mPicId = R.drawable.bullet3;
        mAppContext = AppContext.getInstance();
        mWidth = mAppContext.getWindowHeight() / 4;
        mHeight = mWidth;
        mSpeed = mAppContext.getWindowWidth() / 20 / 24 * 2;
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
}
