package com.cui.mtd;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

/**
 * Created by Cui on 2017/2/9.
 * 主界面
 */

public class MainActivity extends AppCompatActivity {
    private GameView mGameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mGameView= new GameView(this);
        setContentView(mGameView);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mGameView != null) {
            mGameView.pause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mGameView != null && mGameView.mHasSurface) {
            mGameView.resume();
        }
    }
}
