package com.example.guidetobackgroundtasks.Handler;

import android.os.SystemClock;
import android.util.Log;

public class HandlerRunnableTwo implements Runnable {
    private static final String TAG = "MyLogMyRunnableTwo";

    @Override
    public void run() {
        for (int i = 0; i < 4; i++) {
            Log.d(TAG, "myRunnable 2: " + i);
            SystemClock.sleep(1000);
        }
    }

}
