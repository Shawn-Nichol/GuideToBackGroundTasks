# Looper
**Looper** Is a class that is used to keep a thread alive and manage message queue to execute tasks on that thread.
Threads by default do not have a message loop associated with them but can be assigned to them. The
purpose of a **Looper** is to keep the thread alive waiting for the next **Message** object, 


**Looper.prepare():** Initialize the current thread as a looper. This gives you a chance to create handlers
that then reference this looper, before starting the loop. 

**Looper.loop():** Run the message queue in this thread, creates an infinite loop.

**Looper.quit():** Causes the **loop()** method to terminate without processing anymore messages in
the queue.


## How to create Looper.
A thread gets a Looper and MessageQueue by calling **Looper.prepare()** and **Looper.loop()** in the 
run method().







