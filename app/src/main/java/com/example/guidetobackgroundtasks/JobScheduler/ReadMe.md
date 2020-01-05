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
      ```
      new Thread(() -> {
          // Do something
      }).start();
      ```
      - Call **jobFinished()** </br>
      ```jobFinsished(params, false)```
  - **Override onStopJob**
    - Call onStopJob when you want to pause the job.

- Fragment/Activity
  - Setup Job scheduler in a button. 
    - Create ComponentName </br>
    ```ComponentName componentName = ComponentName(context, MyServiceClass.class);```
    - Create JobInfo </br>
    ```
    JobInfo info = new JobInfo.Builder(id, componentName)
        .settings
        .build();
    ```
    - Create JobScheduler object. </br>
    ```JobScheduler scheduler = (JobScheduler) getActivity.getSystemService(JOB_SCHEDULER_SERVICE);```
    - Check results of JobScheduler </br>
    ```
    int resultCode = scheduler.schedule(info);
    if(resultCode = JobScheduler.RESULT_SUCCESS) {
        Log success
    } else {
        Log failer
    }
    ```
  - Stop job in a button
    - Get JobScheduler object. </br>
    ```JobScheduler scheduler = (JobScheduler) getActivity.getSystemSerivce(JOB_SCHEDULER_SERVICE);```
    - Cancel job. </br>
    ```scheudler.cancel(id of service)``` 
    
    
    
    
    
    
    
    
    
### ComponentName:
Identifier for a specific application component(Activity, service, BroadcastReceiver, ContentProvider) 
that is available. Requires the package and the class name. 

###  JobFinished()
Informs the the JobScheduler the work is done.

### JobInfo()
Container of data passed to the JobScheduler fully encapsulating the parameters required to schedule 
work against the calling application. Constructed with JobInfo.Builder. At least one sort of constraint
is required.


 
 