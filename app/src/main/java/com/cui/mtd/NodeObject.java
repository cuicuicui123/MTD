package com.cui.mtd;

/**
 * Created by Cui on 2017/2/14.
 * 代表每个格子的空物体
 */

public class NodeObject {
    private boolean mPlace;
    private boolean mSelectTower;
    private int mLocationX;
    private int mLocationY;

    public NodeObject() {
        mPlace = true;
        mSelectTower = false;
    }

    public boolean isPlace() {
        return mPlace;
    }

    public void setPlace(boolean place) {
        mPlace = place;
    }

    public boolean isSelectTower() {
        return mSelectTower;
    }

    public void setSelectTower(boolean selectTower) {
        mSelectTower = selectTower;
    }

    public int getLocationX() {
        return mLocationX;
    }

    public void setLocationX(int locationX) {
        mLocationX = locationX;
    }

    public int getLocationY() {
        return mLocationY;
    }

    public void setLocationY(int locationY) {
        mLocationY = locationY;
    }
}
