package com.example.guidetobackgroundtasks.Handler;

import android.os.SystemClock;
import android.util.Log;

public class HandlerRunnableOne implements Runnable {
    private static final String TAG = "MyLogMyRunnableOne";

    @Override
    public void run() {
        for (int i = 0; i < 4; i++) {
            Log.d(TAG, "myRunnable 1: " + i);
            SystemClock.sleep(1000);
        }
    }

}
