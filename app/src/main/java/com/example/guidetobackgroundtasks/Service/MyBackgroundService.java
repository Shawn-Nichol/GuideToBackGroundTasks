package com.example.guidetobackgroundtasks.Service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;

import androidx.annotation.NonNull;

public class MyBackgroundService extends Service {


    private static final String TAG = "MyLogMyBackgroundService";
    private Looper mLooper;
    private ServiceHandler mServiceHandler;

    private final class ServiceHandler extends Handler {
        public ServiceHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            for(int i = 0; i < 10; i++) {
                Log.d(TAG, "handleMessage: count " + i);
                SystemClock.sleep(1000);
            }
            // Stop the service using the startId, so that another service  isn't stopped in the middle
            // of a job.
            stopSelf(msg.arg1);
        }
    }

    public MyBackgroundService() {
    }

    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate: ");
        // Start the handler thread running the service.
        HandlerThread thread = new HandlerThread("StartBackgroundService",
                Pro);
        thread.start();

        // Get the handlers thread looper
        mLooper = thread.getLooper();
        mServiceHandler = new ServiceHandler(mLooper);


    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand: ");
        // Send a message to start a job and deliver the start ID so we know which service to stop.
        Message msg = mServiceHandler.obtainMessage();
        msg.arg1 = startId;
        mServiceHandler.sendMessage(msg);

        // Restart service if killed from here.
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind: ");
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
    }
}
