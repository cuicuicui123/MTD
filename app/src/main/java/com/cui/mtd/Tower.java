package com.cui.mtd;

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

    public Tower() {
        mPicId = R.drawable.tower;
        mAppContext = AppContext.getInstance();
        mRange = mAppContext.getGridHeight() * 3 / 2;
        mBullet = new Bullet();
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



}
