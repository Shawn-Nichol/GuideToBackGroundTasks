# Job Scheduler

This is an API for scheduling various types of jobs against the framework that will be executed in the
application process. When the criteria declared is met, the system will execute this job on your applications
JobService. Framework will be intelligent about when it executes jobs, and attempt to batch and defer
them as much as possible. While a job is running the system holds a wakelock on behalf of the app. For
this reason you don't need to take an action to guarantee that the device stays awake for the duration
of the job.

## How to setup a Job Scheduler
- Create a new class that extends **JobService**
  - **Override onStartJob**
      - Create a new thread, and run the function
  - **Override onStopJob**
    - Call onStopJob when you want to pause the job.
```
public class MyJobService extneds JobService {

    private boolean jobCancelled = false;

    @Override
    public boolean onStartJob(JobParameters params) {
        new Thread(() -> {
            if(jobCancelled == true) {
                return;
            } 
            
            // Run code here. 
            
        }).start();
    }
    
    @Override
    public boolean onStopJob(JobParameters params) {
        jobCancelled true; 
    }
    
}
```

- Fragment/Activity
  - Setup Job scheduler in a button. 
    - Create ComponentName
    - Create JobInfo
    - Create JobScheduler object.
    - Check results of JobScheduler
  - Stop job in a button
    - Get JobScheduler object.
    - Cancel job.
- **AndroidManifest.xml**
    - Add service with permission **BIND_JOB_SERVICE**.
```
 <service android:name=".MyJobService" android:permission="android.permission.BIND_JOB_SERVICE"/>
```

```

btnStart.setOnclickListener(view -> {
    ComponenetName componenetName = ComponentName(Context, MyService.class);
    
    JobInfo info = new JobInfo.Builder(id, componenetName)
        .settings
        .build();
        
    JobScheduler scheduler = (JobScheduler) getActivity.getSystemService(JOB_SCHEDULER_SERVICE);
    
    int resultCode = scheduler.schedule(info);
    if(resultCode == JobScheduler.RESULT_SUCCESS) {
        Log.d(TAG, "onCreateView: success");
    } else {
        Log.d(TAG, "onCreateView: failed");
    }
}

btnStop.setOnClickListner(view -> {
    JobScheduler scheduler = (JobScheduler) getActivity.getSystemSerivce(JOB_SCHEDULER_SERVICE);
    scheudler.cancel(id of service)
}
```
    
### Description
    
**ComponentName**
Identifier for a specific application component(Activity, service, BroadcastReceiver, ContentProvider) 
that is available. Requires the package and the class name. 

**JobFinished()**
Informs the the JobScheduler the work is done.

**JobInfo()**
Container of data passed to the JobScheduler fully encapsulating the parameters required to schedule 
work against the calling application. Constructed with JobInfo.Builder. At least one sort of constraint
is required.


 
 