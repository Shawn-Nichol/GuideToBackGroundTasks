package com.example.guidetobackgroundtasks.Looper;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;

public class LooperHandler extends Handler {

    private static final String TAG = "MyLogExampleHandler";
    
    public static final int TASK_A = 1;
    public static final int TASK_B = 2;
    
    @Override
    public void handleMessage(@NonNull Message msg) {
        switch (msg.what) {
            case TASK_A:
                Log.d(TAG, "handleMessage: Task A executed");
                break;
            case TASK_B:
                Log.d(TAG, "handleMessage: Task B executed");
                break;
        }
    }
}
