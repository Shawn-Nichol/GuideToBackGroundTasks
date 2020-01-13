package com.example.guidetobackgroundtasks.Service;


import android.app.IntentService;
import android.content.Intent;
import android.os.Build;
import android.os.SystemClock;
import android.util.Log;


public class MyIntentService extends IntentService {

    private static final String TAG = "MyLogMyServiceBackground";


    public MyIntentService() {
        super("MyIntentService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate: ");

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(TAG, "onHandleIntent: ");

        for(int i = 0; i < 10; i++) {
            Log.d(TAG, "onHandleIntent: count " + i);
            SystemClock.sleep(1000);
        }
    }
}
