package com.example.guidetobackgroundtasks.WorkManager;

import android.content.Context;
import android.os.SystemClock;
import android.util.Log;


import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class MyWorker extends Worker {
    private static final String TAG = "MyLogMyWorker";


    public MyWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        Log.d(TAG, "doWork: ");
        // Do work here,
        SystemClock.sleep(3000);


        // Indicate whether the task finished successfully with the result.
        return Result.success();

    }
}
