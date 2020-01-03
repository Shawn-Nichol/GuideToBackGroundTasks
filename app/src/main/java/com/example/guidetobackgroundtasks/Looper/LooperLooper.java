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

        // Initializes the current thread as a looper.
        Looper.prepare();

        // Returns the looper object associated with the current thread.
        looper = Looper.myLooper();

        handler = new LooperHandler();

        // Returns the message queue in this thread, creates an infinite loop.
        Looper.loop();

        Log.d(TAG, "run: end of run()");
    }
}
