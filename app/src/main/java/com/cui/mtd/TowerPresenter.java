package com.cui.mtd;

import android.graphics.Canvas;
import android.view.MotionEvent;

/**
 * Created by Cui on 2017/2/14.
 */

public interface TowerPresenter {
    void setCanvas(Canvas canvas);
    void drawSelectTower();
    boolean handleTouchEvent(MotionEvent event);
    void drawTower();
}
