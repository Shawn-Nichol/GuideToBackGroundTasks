package com.example.guidetobackgroundtasks.WorkManager;

import android.content.Context;
import android.os.SystemClock;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class MyWorkerThree extends Worker {

    private static final String TAG = "MyLogWorkerThree";
    private int RUNNING_KEY;



    public MyWorkerThree(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @Override
    public void onStopped() {
        super.onStopped();
        RUNNING_KEY = 0;
    }

    @NonNull
    @Override
    public Result doWork() {
        RUNNING_KEY = 1;
        for(int i = 0; i < 5; i++) {

            if(RUNNING_KEY == 0) {
                Log.d(TAG, "doWork: incomplete");
                return Result.failure();
            }

            SystemClock.sleep(1000);
            Log.d(TAG, "doWork: sleep " + i);
        }

        Log.d(TAG, "doWork: end");
        return Result.success();

    }
}
