# Guide to background processing.

This is a guide for background processing in android. 



Every Android app has a main thread in charge of handling UI, coordinating user interactions and 
receiving lifecycle events. Long running computations and operations should be handled on a background
thread. 

##Choosing the right solution.
- Can Work be deferred, or does it need to happen right away? 
- Is work dependent on system conditions?
- Does the job need to run at a precise time?

##WorkManger
For work that is deferrable and expected to run even if the device or application restarts. WorkManager
is an Android library that gracefully runs deferrable background work when the work's conditions are 
met.  

## Foreground services
For user-initiated work that need to run immediately and must execute to completion. Foreground service
tells the system that the app is doing somethign important and it shouldn't be killed. Foreground
services are visible to users via a non-dismissible notification in the Notification tray.

## AlarmManager
If you need to run a job at precise time. AlarmManager launches your app, if necessary to do the job
at the time you specify.

## DownloadManager
If your app is preforming long running HTTP downloads. Clients may request that a URI be downloaded to
a particular destination file that may be outside of the app process. The download manager will
conduct the download in teh background, take care of HTTP interactions.


## MessageQueue 
is a queue that has tasks called messages wich should be rpocessed.
## Handler 
enqueues tasks in teh MessageQuesue using Looper and also executes them when the task come out of the MessageQueue
## Looper
is a worker that keeps a thread alive, loops through MessageQueue and sends messages to the corresponding
handler to process
## Thread
It is never legal to start a thread more than once, In particular a thread may not be restarted once 
it has completed execution.

A Thread can have one unique looper and many unique Handlers associated with it.

## Create a looper
A thread gets a looper and MessageQueue by calling **Looper.prepare()** after its running.
**Looper.prepare()** must be called to start the associated looper. Similarly the looper must be
terminated explicitly through **looper.quit()**.

## Create a handler
A **Handler** get implicitly associated with the thread that instantiates it via thread's **Looper**,
but we can explicitly tie it to a thread by passing the thread's **looper** in the constructor of the
handler.

## MessageQueue sending a message
**Message:** is a class that defines various useful methods to deal with message data. To send an 
object we set the obj variable.

## Create a HandlerThread

1. **Looper** is only prepared after HandlerThread's **start()** is called, after the thread is running.
2. **Handler** can be associated with a **HandlerThread**, only after it's **Looper** is prepared.
Note **HandlerThread** needs to call **myHandlerThread.quit()** to free the resources and stop the 
execution of th thread.