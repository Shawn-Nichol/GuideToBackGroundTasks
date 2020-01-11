# WorkManager

WorkManager is  a library used to enqueue deferrable work that is guaranteed to execute sometime after
its constraints are met. There are two types of WorkManager, OneTimeWorkRequest, PeriodicWorkRequest,
WorkRequest can also be combined to run at once.
WorkRequest hav and associated ID that can be used for look ups, WorkRequests can also be canceled


## Constraints
A specification of the requirements that need to be met before a WorkRequest can run. By default 
WorkRequest do not have any constraints.

### Create a constraints object
Create a new constraints object.  
  - add Parameters
```
Constraints myConstrains = new Constraints.Builder()
    .build();
```

## OneTimeWorkRequest
A non-repeating WorkRequest.

### Create OneTimeWorkRequest in a fragment
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