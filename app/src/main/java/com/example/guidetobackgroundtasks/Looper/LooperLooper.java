package com.example.guidetobackgroundtasks.Looper;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

public class LooperLooper extends Thread {

    private static final String TAG = "MyLogLooper";

    public Looper looper;
    public Handler handler;

    @Override
    public void run() {
        Log.d(TAG, "run: ");
        Looper.prepare();

        looper = Looper.myLooper();

        handler = new LooperHandler();

        Looper.loop(); // infinite loop

        Log.d(TAG, "run: end of run()");
    }
}
