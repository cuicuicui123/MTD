package com.cui.mtd;

/**
 * Created by Cui on 2017/2/9.
 * 地图网格节点类，判断是否可以防止防御塔
 */

public class GridNode {
    private boolean mCanPlace;
    private NodeObject mNodeObject;

    public GridNode() {
        mCanPlace = false;
    }

    public boolean canPlace(){
        return mCanPlace;
    }

    public void setCanPlace(boolean canPlace){
        mCanPlace = canPlace;
    }

    public void setTag(NodeObject nodeObject){
        mNodeObject = nodeObject;
    }

    public NodeObject getNodeObject(){
        return mNodeObject;
    }

}
