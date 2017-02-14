package com.cui.mtd;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by Cui on 2017/2/9.
 */

public class GameView extends SurfaceView implements SurfaceHolder.Callback {
    private AppContext mAppContext;
    private Context mContext;
    private SurfaceHolder mHolder;
    private MainThread mMainThread;

    public boolean mHasSurface;
    private int mDisplayWidth;
    private int mDisplayHeight;

    private GridMap mGridMap;

    public GameView(Context context) {
        super(context);
        init(context);
    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public GameView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        mAppContext = AppContext.getInstance();
        mContext = context;
        mDisplayWidth = mAppContext.getWindowWidth();
        mDisplayHeight = mAppContext.getWindowHeight();
        mGridMap = new GridMap();
        mHolder = getHolder();
        mHolder.addCallback(this);
        mHasSurface = false;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        widthMeasureSpec = MeasureSpec.makeMeasureSpec(mDisplayWidth, MeasureSpec.EXACTLY);
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(mDisplayHeight, MeasureSpec.EXACTLY);
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mHasSurface = true;
        resume();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        mHasSurface = false;
        pause();
    }

    public void resume(){
        if (mMainThread == null) {
            mMainThread = new MainThread();
            if (mHasSurface) {
                mMainThread.start();
            }
        }
    }

    public void pause() {
        if (mMainThread != null) {
            mMainThread.stopThread();
            mMainThread = null;
        }
    }

    class MainThread extends Thread{
        public boolean mStart;

        public MainThread() {
            super();
            mStart = true;
        }

        @Override
        public void run() {
            super.run();
            while (mStart) {
                Canvas canvas = mHolder.lockCanvas();
                if (canvas != null) {
                    mGridMap.setCanvas(canvas);
                    mHolder.unlockCanvasAndPost(canvas);
                }
                try {
                    Thread.sleep(AppContext.TIME_SPAN);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        public void stopThread(){
            mStart = false;
        }
    }

}
