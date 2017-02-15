package com.cui.mtd;

/**
 * Created by Cui on 2017/2/15.
 */

public class Tower {
    private int mPicId;
    private NodeObject mNodeObject;

    public Tower() {
        mPicId = R.drawable.tower;
    }

    public NodeObject getNodeObject() {
        return mNodeObject;
    }

    public void setNodeObject(NodeObject nodeObject) {
        mNodeObject = nodeObject;
    }
}
