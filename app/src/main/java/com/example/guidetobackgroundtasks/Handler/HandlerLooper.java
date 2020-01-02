package com.example.guidetobackgroundtasks.Handler;

import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.util.Log;

public class HandlerLooper extends Thread {

    private static final String TAG = "MyLogHandlerLooper";

    public Handler handler;

    @Override
    public void run() {
        Looper.prepare();

        handler = new Handler();

        Looper.loop();

        for(int i = 0; i < 5; i++) {
            Log.d(TAG, "run: " + i);
            SystemClock.sleep(1000);
        }

        Log.d(TAG, "run: End of Run()");
    }
}
