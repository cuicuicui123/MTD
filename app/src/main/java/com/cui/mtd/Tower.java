package com.cui.mtd;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.util.Size;

/**
 * Created by Cui on 2017/2/15.
 */

public class Tower {
    private int mPicId;
    private NodeObject mNodeObject;
    private double mRange;
    private AppContext mAppContext;
    private Enemy mTarget;
    private Bullet mBullet;
    private Rect mRectRes;
    private int mWidth;
    private int mHeight;
    private int mRealWidth;
    private int mRealHeight;
    private Bitmap mPic;

    public Tower() {
        mPicId = R.drawable.tower;
        mAppContext = AppContext.getInstance();
        mPic = BitmapFactory.decodeResource(mAppContext.getResources(), mPicId);
        mWidth = mPic.getWidth();
        mHeight = mPic.getHeight();
        SizeHelper sizeHelper = new SizeHelper(mWidth, mHeight);
        mRealWidth = sizeHelper.getRealWidth();
        mRealHeight = sizeHelper.getRealHeight();
        mRange = mAppContext.getGridHeight() * 3 / 2;
    }

    public NodeObject getNodeObject() {
        return mNodeObject;
    }

    public void setNodeObject(NodeObject nodeObject) {
        mNodeObject = nodeObject;
    }


    public double getRange() {
        return mRange;
    }

    public Enemy getTarget() {
        return mTarget;
    }

    public void setTarget(Enemy target) {
        mTarget = target;
    }


    public Bullet getBullet() {
        return mBullet;
    }

    public Bitmap getBulletBitmap(){
        return mBullet.getPic();
    }

    public Bullet createBullet(){
        mBullet = new Bullet();
        mBullet.setLocationX(mNodeObject.getLocationX() * mAppContext.getGridWidth() + mAppContext.getGridWidth() / 2);
        mBullet.setLocationY(mNodeObject.getLocationY() * mAppContext.getGridHeight() + mAppContext.getGridHeight() / 2);
        mRectRes = new Rect(0, 0, mBullet.getWidth(), mBullet.getHeight());
        return mBullet;
    }

    public Rect getRectRes(){
        return mRectRes;
    }

    public int getWidth() {
        return mWidth;
    }

    public int getHeight() {
        return mHeight;
    }

    public int getRealWidth() {
        return mRealWidth;
    }

    public int getRealHeight() {
        return mRealHeight;
    }

    public Bitmap getPic() {
        return mPic;
    }
}
