# Services
A service is an application component that can perform operation in the background and doesn't provide
a user interface. Application component can start a service, and it continues to run in the background
even if the user switches to another application. Additionally a component can bind to a service to 
interact with it nad even perform interprocess communication (IPC).  

## Fragment Instructions
This fragment will run through different version and scenarios of services. Flip the switch back and 
forth to show that the service isn't running on the main thread.

**SERVICE Button**, will start and stop the service. This service is running on the main thread, 
if change the state of the switch you will notice it does not move. If you close the app the 
for loop will finish running and then the service will destroy it self, the service will also destroy itself
after minute. 

**Foreground SERVICE Button**, Will start a service for 10 seconds and display a notification during
the services life cycle. Notification does not stop the service. 


## ForeGround Intent-Service.
A Foreground service performs operations that are noticeable to the user.

### How to create ForeGround intent service
- Create Notification channel.
  - Create a new java class that extends **Application**.
    - Check App Build level
    - Create new NotificationChannel(CHANNEL_ID, name, Importance);
    - Create NotificationManager
      - Add channel to NotificationManager.
    
```
public class App extends Application {
    public static final String CHANNEL_ID = "myServiceChannel";
    
    @Override
    public void onCreate() {
        super.onCreate();
        
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel myServiceChannel = new NotificationChannel(CHANNEL_ID, 
                "MyChannel",
                 NotifiationManager.IMPORTANCE_DEFAULT);
            
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(myServiceChannel);
        }
    }
}
```    

- Create Intent-Service
    - WakeLock: Is a mechanism to indicate that your application needs to have the device stay on. Any
        device that use WakeLock must request permission to use it in the manifest. 
        - Call acquire() to force the device to stay on at the level that was requested when the lock
        was created.
        - Call release() when you are done and don't need the lock anymore.

  - Create a new java class that extends IntentService.
    - Bind WakeLock
    - Add a Constructor
      - add super
      - what todo when the phone turns off and the service is still running.
    - Override onHandleIntent
      - code you want to run goes here
    - Override onCreate
      - Allow the service to run if the phone is turned off
      - Create Notification
      -run service in ForeGround.
    - Override onDestroy 
      - Turn off wakeLock 
```
public class MyIntentService extends IntentService {
    
    private PowerManager.WakeLock lock;
    
    public MyIntentService() {
        super("ClassName");
        setIntentRedelivery(true);
    }
    
    @Override
    protected void onHandle(Intent intent){
        // Run background code here.
    }
    
    @Override
    public void onStart() {
        PowerManager pm = (PowerManager) getSystemService(POWER_SERVICE);
        wakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "MyTag");
        wakeLock.acquire();
        
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("MyTitle")
                .setContentText("Running...")
                .sestSmallIcon(R.drawable.ic_andriod)
                .build();
                
                startForeground(1, notification);
        }
    }
    
    @Override
    public void onDestroy() {
        wakelock.release();
    }

}
```
      
      
- Manifest.xml
   - Grant permission to wakeLock and ForeGround service. <br/>
     ```
     <uses-permission android:name="android.permission.WAKE_LOCK"/>
     <uses-permission android:name="android.permission.FOREGROUND_SERVICE" />
     ```
   - add Service tag in application <br/>
     ```<service android:name="MyIntentService"/>```
    
- Start IntentService
In a button 
- Create Intent object (context, ServiceClass)
- add data to the service
- Start foreground service

```
Intent serviceIntent = new Intent(getActivity(), MyIntentService.class);
serviceIntent.putExtra("name", "input value");

ContextCompat.startForegroundService(getActivity(), serviceIntent);

```
 
 
## Description
**startService()** 
Requests that a given application service be started. The intent should either contain the complete 
class name of a specific service implementation to start, or specific package name to target.
Every call to startService() results in a corresponding call to the target Service.onStartCommand().
The service will remain running until a call to stopService. If any errors occur in the services process
it will automatically be restarted.

**startForegroundService()**
Unlike startService this method can be used at any time, regardless of whether the app hosting the service
is in the foreground state.