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