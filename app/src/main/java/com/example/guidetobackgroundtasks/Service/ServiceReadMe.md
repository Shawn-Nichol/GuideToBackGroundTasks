# Services
A service is an application component that can perform without a user interface. Application component
can start a service, and has lifecycle independent of the Application. There are three types of 
services Background, Foreground, Bound.  

## Fragment Instructions
This fragment will run through different version and scenarios of services. Flip the switch back and 
forth to show that the service isn't running on the main thread.

**SERVICE Button**, will start and stop the service. This service is running on the main thread, 
if you change the state of the switch you will notice it does not move well the service is running.
If you close the app the for loop will finish running and then the service will destroy it self, 
the service will also destroy itself after a minute. 

**BACKGROUND SERVICE Button**, runs a service on the background thread, you will notice that when the
service is running you are able to change states of the switch on the bottom of the fragment.

**Foreground SERVICE Button**, Will start a service for 10 seconds and display a notification during
the services life cycle. Notification does not stop the service. 


## Background Service.
A Background service performs an operation that isn't directly noticed by the user.

### How to create Background service
- Create a Java class that extends Service.
  - Create variables
    - Looper
    - ServiceHandler
  - Create Inner class that extends Handler
    - Override handleMessage()
      - Create Constructor that passes Looper.
      - doWork
      - Stop thread when work is completed.
  - Create empty constructor.
  - Override onCreate()
    - Create HandlerThread(name, process)
    - Start Thread
    - Get looper from thread
    - Create serviceHandler object and pass looper
  - Override onStartCommand()
    - Create Message
    - Send Message to Handler
    - Return what todo if the service does not finish. 
  - Override onBind()
    - return null;
  - Override onDestroy()
    - Log, or provide user notification.
- Manifest
  - Add service to manifest
```
public class MyBackgroundService extends Service {
    private Looper mLooper;
    private ServiceHandler mServiceHandler;
    
    private final class ServiceHandler extends Handler {
        public ServiceHandler(Looper looper) {
            super(looper);
        }
        
        @Override
        public void handleMessage(Message msg) {
            // Work you want to run in the background
            
            stopSelf(msg.ar1);
        }
    }
    
    public MyBackgroundService() {
    }
    
    @Override
    public void onCreate() {
        HandlerThread thread = new HandlerThread(name, process);
        thread.start();
        
        mLooper = thread.getLooper();
        mServiceHandler = new ServiceHandler(mLooper);
    }
    
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Message msg = mServiceHandler.obtainMessage();
        msg.arg1 = startId;
        mServiceHandler.sendMessage(msg);
        
        return START_STICKY;
    }
    
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    
    @Override
    public void onDestroy() {
    super.onDestroy();
    }
}
```    
    
    
## Foreground Service.
A Foreground service performs operations that are noticeable to the user.

### How to create ForeGround service
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

**Looper**
A class used to run a message loop for a thread. Threads by default do not have a message loop associated
with them; to create one, call prepare() in the thread that needs a loop, and then loop() to have it 
process messages until the loop is stopped.

**Handler**
A Handler allows you to send process Message and runnable objects associated with a threads's MessageQueue.
Each Handler instance is associated with a single thread and that thread's message queue. When a new 
Handler is created it is bound to that message queue.

**HandlerThread**
A Thread that has a looper, the looper can then be used to create Handlers

**Message**
Defines a message containing a description and arbitrary data object that can be sent to a handler.
This object contains two extra int fields and an extra object field that allows you to not do 
allocation in many cases.

**Intent-Service**

TODO: 
Create Bound service
Background service pass data back to UI thread
Foreground service pass data back to UI thread