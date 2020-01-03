package com.example.guidetobackgroundtasks.Runnable;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Button;

public class RunnableRunnableThree implements Runnable {

    private static final String TAG = "MyLogMyRunnable1a";

    int mSeconds;
    Activity mActivity;

    Button btn;

    private volatile boolean stop = false;

    public RunnableRunnableThree(Activity activity, int mSeconds, Button btn) {
        this.mActivity = activity;
        this.mSeconds = mSeconds;
        this.btn = btn;
    }

    @Override
    public void run() {
        Log.d(TAG, "run: ");
        for(int i = 0; i < mSeconds; i++) {
            if(stop) {
                return ;
            }
            if(i == 5) {
                mActivity.runOnUiThread(() -> {
                    btn.setText("50%");
                });

            }
            Log.d(TAG, "run: " + i);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    public void setStop(boolean stop) {
        this.stop = stop;
    }
}
