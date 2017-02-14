package com.cui.mtd;

/**
 * Created by Cui on 2017/2/14.
 * 代表每个格子的空物体
 */

public class NodeObject {
    private boolean mPlace;

    public NodeObject() {
        mPlace = true;
    }

    public boolean isPlace() {
        return mPlace;
    }

    public void setPlace(boolean place) {
        mPlace = place;
    }
}
