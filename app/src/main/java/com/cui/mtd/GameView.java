package com.cui.mtd;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by Cui on 2017/2/9.
 * 游戏界面类，surfaceView
 */

public class GameView extends SurfaceView implements SurfaceHolder.Callback {
    private AppContext mAppContext;
    private Context mContext;
    private SurfaceHolder mHolder;
    private MainThread mMainThread;

    public boolean mHasSurface;
    private int mDisplayWidth;
    private int mDisplayHeight;
    private long mTime;

    private GridMap mGridMap;
    private GameManager mGameManager;
    private EnemyPresenter mEnemyPresenter;
    private TowerPresenter mTowerPresenter;
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

    /**
     * 初始化数据
     * @param context 传入context
     */
    private void init(Context context){
        mAppContext = AppContext.getInstance();
        mContext = context;
        mDisplayWidth = mAppContext.getWindowWidth();
        mDisplayHeight = mAppContext.getWindowHeight();
        mGridMap = GridMap.getInstance();
        mGameManager = GameManager.getInstance();

        mEnemyPresenter = new EnemyPresenterImpl(mGridMap.getRootNode());
        mHolder = getHolder();
        mHolder.addCallback(this);
        mHasSurface = false;
        mTowerPresenter = new TowerPresenterImpl();
    }

    /**
     * 在onMeasure里面确定主界面的大小
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        widthMeasureSpec = MeasureSpec.makeMeasureSpec(mDisplayWidth, MeasureSpec.EXACTLY);
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(mDisplayHeight, MeasureSpec.EXACTLY);
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
    }

    /**
     * 在surfaceCreated中开始运行线程
     * @param holder
     */
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mHasSurface = true;
        resume();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int i, int i1, int i2) {

    }

    /**
     * surfaceDestroyed之后停止线程
     * @param holder
     */
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        mHasSurface = false;
        pause();
    }

    /**
     * 开始线程的方法
     */
    public void resume(){
        if (mMainThread == null) {
            mMainThread = new MainThread();
            if (mHasSurface) {
                mMainThread.start();
            }
        }
    }

    /**
     * 停止线程的方法
     */
    public void pause() {
        if (mMainThread != null) {
            mMainThread.stopThread();
            mMainThread = null;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mTowerPresenter.handleTouchEvent(event);
    }

    /**
     * surfaceView的主要线程
     */
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
                    mEnemyPresenter.setCanvas(canvas);
                    mEnemyPresenter.move();
                    mTowerPresenter.setCanvas(canvas);
                    mGameManager.setCanvas(canvas);
                    mHolder.unlockCanvasAndPost(canvas);
                }
                try {
                    Thread.sleep(AppContext.TIME_SPAN);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        /**
         * 线程提供停止自己的方法
         */
        public void stopThread(){
            mStart = false;
        }
    }

}
