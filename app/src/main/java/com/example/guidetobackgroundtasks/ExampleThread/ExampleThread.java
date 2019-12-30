package com.example.guidetobackgroundtasks.ExampleThread;

import android.util.Log;


class ExampleThread extends Thread {
    private static final String TAG = "MyLogExampleThread";
    int mSeconds;

    public ExampleThread(int mSeconds) {
        this.mSeconds = mSeconds;
    }

    @Override
    public void run() {
        for (int i = 0; i < mSeconds; i++) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Log.d(TAG, "onCreateView: Threading " + i);
        }
    }
}

