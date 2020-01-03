package com.example.guidetobackgroundtasks.Runnable;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Button;

import com.example.guidetobackgroundtasks.R;

public class RunnableRunnableTwo implements Runnable {

    private static final String TAG = "MyLogMyRunnableTwo";

    int mSeconds;

    Button btn;

    private volatile boolean stop = false;

    public RunnableRunnableTwo(int mSeconds, Button btn) {
        this.mSeconds = mSeconds;
        this.btn = btn;
    }

    @Override
    public void run() {
        Log.d(TAG, "run: ");
        for(int i = 0; i < mSeconds; i++) {
            if(stop) {
                return;
            }
            if(i == 5) {
                btn.post(() -> btn.setText("50%"));

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
