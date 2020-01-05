# Intent-Service
**Intent-Service** is a base class for Services that handle asynchronous requests on demand. Clients
send requests through **Context.startService(intent)** calls; the service is started as needed, handles
each intent in turn using a **Worker thread**, and stops itself when the work runs out.

All requests are handled on a single worker thread, they may take as long as necessary and will not block 
the main loop, but only one request will be processed at time.

**Intent-Service** is subject to all the background execution limits imposed in API 26. In most cases
you are better off using JobIntentService, which uses jobs instead of services when running.

## How to create Intent-Service.
### Notification channel.
  - Create a new java class that extends **Application**.
    - Check App Build level
    - Create new NotificationChannel <br/>
    ```NotificationChannel myChannel = new NotificationChannel(ID, Name, NotificationManager.level)```
    - C NotificationManager <br/>
    ```NotificationManager manager = getSystemService(NotificationManager.class)```
    - Add channel to NotificationManager. <br/>
    ```manager.createNotificationChannel(myChannel)```

### Intent-Service
  - Create a new java class that extends **IntentService**.
    - Add a Constructor
      - add **super** <br/>
      ```super(className)```
      - what todo when the phone turns off and the service is still running. <br/>
        ```setIntentRedelivery(true)```
    - **Override onHandleIntent**
      - code you want to run goes here
    - **Override onCreate**
      - Allow the service to run if the phone is turned off
        ```
        PowerManager pm = (PowerManager) getSystemService(POWER_SERVICE);
           wakeLock = powerManager.newWakeLock(PowerManager.Partial_WAKE_LOCK, "MY:wakeLock")
           wakeLock.acquire()
           ```
      - **Create Notification**
        ```
        Notification notification = NotificationCompat.Builder(this, CHANNEL_ID)
              .setContentTitle("My title")
              .setContentText("My Text")
              .setSmallIcon(R.drawable.my_icon)
              .build()
         ```
      -run service in ForeGround. <br/>
        ```startForeGround(1, notification)```
    - **Override onDestroy** <br/>
      - Turn off wakeLock
        ```wakeLock.Release```
 
### **Manifest.xml**
   - Grant permission to wakeLock and ForeGround service. <br/>
     ```
     <uses-permission android:name="android.permission.WAKE_LOCK"/>
     <uses-permission android:name="android.permission.FOREGROUND_SERVICE" />
     ```
   - add Service tag in application <br/>
     ```<service android:name="MyIntentService"/>```
    
    
 