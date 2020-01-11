package com.example.guidetobackgroundtasks.WorkManager;

import android.content.Context;
import android.os.SystemClock;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class MyPeriodicWork extends Worker {

    private static final String TAG = "MyLogMyPeriodicWork";
    private int RUNNING_KEY;

    public MyPeriodicWork(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @Override
    public void onStopped() {
        super.onStopped();
        RUNNING_KEY = 1;
    }

    @NonNull
    @Override
    public Result doWork() {
        Log.d(TAG, "doWork: ");
        RUNNING_KEY = 1;

        for(int i = 0; i < 5; i++) {
            if(RUNNING_KEY == 0) {
                Log.d(TAG, "doWork: incomplete");
                return Result.failure();
            }
            Log.d(TAG, "doWork: " + i);
            SystemClock.sleep(1000);
        }

        Log.d(TAG, "doWork: finished");
        return Result.success();
    }
}
