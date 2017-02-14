package com.cui.mtd;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

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
