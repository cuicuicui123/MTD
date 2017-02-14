package com.cui.mtd;

/**
 * Created by Cui on 2017/2/9.
 * 地图网格节点类，判断是否可以防止防御塔
 */

public class GridNode {
    private boolean mCanPlace;

    public GridNode() {
        mCanPlace = true;
    }

    private boolean canPlace(){
        return mCanPlace;
    }

    private void setCanPlace(boolean canPlace){
        mCanPlace = canPlace;
    }

}
