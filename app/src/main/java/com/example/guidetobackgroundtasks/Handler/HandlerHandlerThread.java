package com.example.guidetobackgroundtasks.Handler;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.os.Process;
import android.os.SystemClock;
import android.util.Log;

import androidx.annotation.NonNull;

public class HandlerHandlerThread extends HandlerThread {
    private static final String TAG = "MyLogHandlerHandlerThread";

    public static final int TASK_ONE = 1;
    public static final int  TASK_TWO = 2;

    private Handler handler;

    public HandlerHandlerThread() {
        super("HandlerHandlerThread", Process.THREAD_PRIORITY_BACKGROUND);

    }

    @SuppressLint("HandlerLeak")
    @Override
    protected void onLooperPrepared() {
        handler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                switch (msg.what) {
                    case TASK_ONE:
                        Log.d(TAG, "handleMessage msg.arg1: " + msg.arg1 + ", Obj: " + msg.obj);
                        for(int i  = 0; i < 4; i++) {
                            Log.d(TAG, "handleMessage: " + i);
                            SystemClock.sleep(1000);
                        }
                        break;
                    case TASK_TWO:
                        Log.d(TAG, "handleMessage msg.arg1: " + msg.arg1 + ", Obj: " + msg.obj);
                        for(int i  = 0; i < 8; i++) {
                            Log.d(TAG, "handleMessage: " + i);
                            SystemClock.sleep(1000);
                        }
                }
            }
        };
    }

    public Handler getHandler() {
        return handler;
    }
}
