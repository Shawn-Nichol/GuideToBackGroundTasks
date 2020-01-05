package com.example.guidetobackgroundtasks.JobScheduler;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.SystemClock;
import android.util.Log;

public class MyJobService extends JobService {

    private static final String TAG = "MyLogMyJobService";
    private boolean jobCancelled = false;

    @Override
    public boolean onStartJob(JobParameters params) {
        Log.d(TAG, "onStartJob: ");

        doBackgroundWork(params);

        return true;
    }

    private void doBackgroundWork(JobParameters params) {
        new Thread(() -> {

            if(jobCancelled == true) {
                return;
            }

            for(int i = 0; i < 10; i++) {
                Log.d(TAG, "doBackgroundWork: " + i);
                SystemClock.sleep(1000);
            }
        }).start();

        Log.d(TAG, "doBackgroundWork: job finished");
        jobFinished(params, false);
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Log.d(TAG, "onStopJob: job cancelled before completion");
        jobCancelled = true;
        return true;
    }

}
