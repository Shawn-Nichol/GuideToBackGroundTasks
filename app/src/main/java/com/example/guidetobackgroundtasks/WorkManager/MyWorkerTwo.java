package com.example.guidetobackgroundtasks.WorkManager;

import android.content.Context;
import android.os.SystemClock;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class MyWorkerTwo extends Worker {
    private static final String TAG = "MyLogWorkerTwo";

    public MyWorkerTwo(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        for(int i = 0; i < 5; i++) {
            SystemClock.sleep(1000);
            Log.d(TAG, "doWork: sleep " + i);
        }

        Log.d(TAG, "doWork: end");
        return Result.success();
    }
}
