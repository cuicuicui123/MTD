package com.cui.mtd;

/**
 * Created by Cui on 2017/2/14.
 * 路径类，类似于一个链表结构，thatNode指向下一个节点
 */

public class PathNode {
    private PathNode mThatNode;

    private float mLocationX;
    private float mLocationY;

    public PathNode getThisNode(){
        return this;
    }

    public PathNode getThatNode() {
        return mThatNode;
    }

    public void setThatNode(PathNode thatNode) {
        mThatNode = thatNode;
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

    public void setLocationY(int locationY) {
        mLocationY = locationY;
    }
}
