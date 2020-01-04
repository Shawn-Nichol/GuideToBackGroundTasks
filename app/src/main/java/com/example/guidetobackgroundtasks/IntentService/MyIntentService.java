package com.example.guidetobackgroundtasks.IntentService;

import android.app.IntentService;
import android.app.Notification;
import android.content.Intent;
import android.os.Build;
import android.os.PowerManager;
import android.os.SystemClock;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.example.guidetobackgroundtasks.R;

import static com.example.guidetobackgroundtasks.IntentService.App.CHANNEL_ID;

public class MyIntentService extends IntentService {

    private static final String TAG = "MyLogMyIntentService";

    private PowerManager.WakeLock wakeLock;
    
    public MyIntentService() {
        super("MyIntentService");
        setIntentRedelivery(true);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate: ");

        PowerManager powerManager = (PowerManager) getSystemService(POWER_SERVICE);
        wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "MyAPP:Wakelock");
        wakeLock.acquire();
        Log.d(TAG, "onCreate: WakeLockRequired");


        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setContentTitle("My IntentService")
                    .setContentText("Running...")
                    .setSmallIcon(R.drawable.ic_android)
                    .build();

            startForeground(1, notification);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        wakeLock.release();
        Log.d(TAG, "onDestroy: WakeLockReleased");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(TAG, "onHandleIntent: ");
        
        String input = intent.getStringExtra("inputExtra");
        for(int i = 0; i < 10; i++) {
            Log.d(TAG, input + " - " + i);
            SystemClock.sleep(1000);
        }
    }
}
