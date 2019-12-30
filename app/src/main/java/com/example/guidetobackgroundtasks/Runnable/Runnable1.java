package com.example.guidetobackgroundtasks.Runnable;

import android.util.Log;

public class Runnable1 implements Runnable {

    private static final String TAG = "MyLogRunnable1";
    int mSeconds;

    public Runnable1(int mSeconds) {
        this.mSeconds = mSeconds;
    }

    @Override
    public void run() {
        for(int i = 0; i < mSeconds; i++) {
            Log.d(TAG, "run: " + i);

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
