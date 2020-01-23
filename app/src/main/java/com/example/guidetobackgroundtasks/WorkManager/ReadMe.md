# WorkManager

WorkManager is  a library used to enqueue deferrable work that is guaranteed to execute sometime after
its constraints are met. There are two types of WorkManager, OneTimeWorkRequest, PeriodicWorkRequest,
WorkRequest can also be combined to run at once.
WorkRequest have an associated ID that can be used for look ups, WorkRequests can also be canceled


**OneTimeWorkRequest**
A non-repeating WorkRequest.

## Create OneTimeWorkRequest in a fragment
Create new work request object, This object will only run once unless you recreate it every time it
runs.
- OneTimeWorkRequest(WorkerClass)
  - setConstraints
  - setTag
  - can add additional parameters.
  - build
```
OneTimeWorkRequest workRequest = new OneTimeWorkRequest.Builder(MyWorker.class)
    .setConstraints(myConstraints)
    .addTag("Tag One")
    .addTag("Yes you can add more than one tag")
    .build();
```


## Create WorkerClass
- Gradle, add dependencies.
  - implementation "androidx.work:work-runtime:$work_version"

- Worker
- Create Java class extend Worker
  - Add Constructor (Context, WorkerParameters)
  - Override doWork(), required
  - Override onStopped
  
```
public class MyWorker extends Worker {
        
        public MyWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
            super(context, workerParams);
        }
        
        @NonNull
        @Override
        public Result doWork() {
            // Run background code here
        }
        
        @Override
        public void onStopped() {
            super.onStopped();
            // Run code if background task was cancleded.
        }
}
```

## PeriodicWorkRequest
A WorkRequest for repeating work. This work executes multiple times unit it is cancelled, first execution
will happen as soon as the constraints are met. The next execution will happen after the time interval
(minimum 15 minutes).

### Create a Periodic WorkRequest in a fragment
- PeriodicWorkRequest(WorkerClass, repeatInterval, )
  - setConstrains
  - addTag
  - build
```
PeriodicWorkRequest workRequest = new PeriodicWorkRequest.Builder(MyWorker.class, 3, TimeUnit.MINUTES)
    .setConstraints(myConstraints)
    .addTag("My Tag")
    .build();
```

## Enqueue()
This method enqueues one or more items for background processing.

### add Single enqueue
- Add enqueue to WorkManager
  - add WorkRequest
```
WorkManager.getInstance(getActivity()).enqueue(workRequest);
```
### List enqueue
List enqueue will run the WorkRequests in parallel. 
- Add enqueue to WorkManager
  - add WorkRequest
  - add WorkRequest
```
Workmanager.getInstance(getActivity()).enqueue(workRequestOne, workRequestTwo, WorkRequestThree);
```

### Chain enqueue
Chaining enqueue will run the WorkRequest in the order they are received. Every invocation of the 
WorkContinuation.then returns a new instance of WorkContinuation. Parent WorkRequest must run successfully 
in order for child WorkRequest to run.
```
WorkManager.getInstancte(getActivity()).beginWith(Arrays.asList(workRequestOne, workRequestTwo, workRequestThree)
    .then(workRequestFour)
    .then(workRequestFive)
    .enqueue();
```

## getWorkInfoByIdLiveData()
Get LiveData of the WorkInfo for give WorkRequest.

- WorkInfo: Information about a particular WorkRequest containing the id of the WorkRequest, current 
WorkInfo.state, output, tags, and run attempt count
```
WorkManager.getInstance(getActivity()).getWorkInfoByIdLiveData(workRequest.getId())
    .observe(getActivity, workInfo -> {
        if(workInfo.getState == WorkInfo.State.SUCCEEDED) {
            // Work completed succesfully.
        }
    }
});
```

## Cancel WorkRequest
You can cancel a WorkRequest by calling "cancelWorkById(workRequest.getId())" on the WorkManager. All
dependent WorkRequest will also be canceled, in addition the "onStopped()" in the WorkerClass will
be called
```
WorkManager.getInstance(getActivity()).cancelWorkById(workRequest.getId());
```

## Passing Parameters
You can pass parameters to your WorkRequest and have the request return results. Passed and returned
values are key-value pairs. To pass data call ".setInputData(myData)" method in the WorkRequest.Builder.
The Worker class can access those parameters by calling "Worker.getInputData()". Include the dataResult
in the return statement, dataResults can be accessed by observing the WorkInfo.

- Data: A Persistable set of key/value pairs which are used as input and outputs. 

### Worker pull Data
- Worker, see WorkerClass for details on building WorkerClass
  - Define Parameter Keys
  - doWork()
    - Get parameters.
    - Create Data object to 
    - Return results with data object
```
public class MyWorker extends Worker {
    public static final String KEY_X_ARG = "x";
    public static final String KEY_RESULT = "result";
    
    @Override
    public Result doWork() {
        int x = getIntputdat().getInt(KEY_X_ARG, 0);
        
        int multiple = x * 2;
        
        Data dataResults = new Data.Builder()
            put.Int(KEY_RESULT, result)
            .build()
        
        return Result.success(dataResult);
    }
    
}
```

### Worker send data
Inside a button.
- Create data
- Create WorkRequest
  - setInputData
- Enqueue work
```
Data myData = new Data.Builder()
    .putInt(KEY_X_ARG, 2)
    .build();
    
OneTimeWorkRequest workRequest = new OneTimeWorkRequest.Builder(MyWorker)
    .setInputData(myData)
    .build();

Workmanger.getInstance(getActivity()).enqueue(workRequest);
```

### Receive the result from the workRequest
- WorkManager getWorkInfoByIdLiveData(WorkerClass.getInfo)
- check to see if work completed successfully
  - get data with key value pair.
```
WorkManager.getInstance(getActivity()).getWorkInfoByIdLiveData(myWorker.getId())
    .observe(getActivity, workinfo -> {
        if(workinfo.getState == WorkInfo.State.SUCCEEDED) {
            int myResult = workinfo.getOutputData().getInt(KEY_RESULT, defaultValue);
        }
    }
``` 

## Description

**Constraints**
A specification of the requirements that need to be met before a WorkRequest can run. By default 
WorkRequest do not have any constraints.

## WorkerClass
WorkerClass is the where the background work is done in WorkManager. It doesn't run or have communication
with the UI thread.