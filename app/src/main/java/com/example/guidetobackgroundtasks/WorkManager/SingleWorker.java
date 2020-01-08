package com.example.guidetobackgroundtasks.WorkManager;

import android.content.Context;
import android.os.SystemClock;
import android.util.Log;


import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class SingleWorker extends Worker {
    private static final String TAG = "MyLogSingleWorker";

    int RUNNING_KEY;

    public SingleWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @Override
    public void onStopped() {
        super.onStopped();
        Log.d(TAG, "onStopped: ");
        RUNNING_KEY = 0;
    }

    @NonNull
    @Override
    public Result doWork() {
        Log.d(TAG, "doWork: ");
        RUNNING_KEY = 1;

        // Do work here,
        for(int i = 0; i < 5; i++) {
            if(RUNNING_KEY == 0) {
                Log.d(TAG, "doWork: incomplete");
                return Result.failure();
            }
            SystemClock.sleep(1000);
            Log.d(TAG, "doWork: " + i);
        }


        // Indicate whether the task finished successfully with the result.
        return Result.success();

    }
}
