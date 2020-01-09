package com.example.guidetobackgroundtasks.WorkManager;

import android.content.Context;
import android.os.SystemClock;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class MyPeriodicWork extends Worker {

    private static final String TAG = "MyLogMyPeriodicWork";

    public MyPeriodicWork(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        Log.d(TAG, "doWork: ");

        for(int i = 0; i < 5; i++) {
            Log.d(TAG, "doWork: " + i);
            SystemClock.sleep(1000);
        }

        Log.d(TAG, "doWork: finished");
        return Result.success();
    }
}
