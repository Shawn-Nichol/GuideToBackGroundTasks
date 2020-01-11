package com.example.guidetobackgroundtasks.WorkManager;

import android.content.Context;
import android.os.SystemClock;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class MyWorkerWithParameters extends Worker {

    // Define parameter keys
    public static final String KEY_X_ARG = "X";
    public static final String KEY_Y_ARG = "Y";
    public static final String KEY_Z_ARG = "Z";
    public static final String KEY_RESULT = "result";

    public MyWorkerWithParameters(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        int x = getInputData().getInt(KEY_X_ARG, 0);
        int y = getInputData().getInt(KEY_Y_ARG, 0);
        int z = getInputData().getInt(KEY_Z_ARG, 0);

        int result = x + y + z;

        Data output = new Data.Builder()
                .putInt(KEY_RESULT, result)
                .build();
        return Result.success(output);
    }
}
