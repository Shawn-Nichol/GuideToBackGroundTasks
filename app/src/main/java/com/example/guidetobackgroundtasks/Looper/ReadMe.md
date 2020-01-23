# Looper
**Looper** Is a class that is used to keep a thread alive and manage message queue to execute tasks on that thread.
Threads by default do not have a message loop associated with them but can be assigned to them. The
purpose of a **Looper** is to keep the thread alive waiting for the next **Message** object, 





## How to create Looper.
A thread gets a Looper and MessageQueue by calling **Looper.prepare()** and **Looper.loop()** in the 
run method().

- Create Looper class that extends Threads
  - Override run()
    - Initialize the current thread as a looper.
    - return the looper object associated with the current thread.
    - Returning the MessageQueue in this thread, creates an infinite loop.
```
public class MyLooper extends Thread {
    public Loooper looper;
    public Handler handler;
    
    @Override
    public void run() {
        Looper.prepare();
        
        looper = Looper.myLooper();
        handler = new LooperHandler();
        Looper.loop();
        
    }
}
```   
- Start/Stop Looper and run tasks
  - Start looperThread.
  - Stop looperThread.
  - Start Task A
  - Start Task B
```
MyLooper myLooper = new MyLopper();
btnStart.setOnClickListener(view -> {
    myLopper.start();
}

btnStop.setOnClickListener(view -> {
    myLooper.looper.quit();
}

btnTaskA.setOnClickListener(view -> {
    myLooper.handler.sendMessage("TASK A");
}

btnTaskB.setOnClickListener(view -> {
    myLooper.handler.sendMessage("Task B");
}
```
  





## Description
**Looper.prepare()** 
Initialize the current thread as a looper. This gives you a chance to create handlers that then
 reference this looper, before starting the loop. 

**Looper.loop()**
Run the message queue in this thread, creates an infinite loop.

**Looper.quit()** 
Causes the loop() method to terminate without processing anymore messages in
the queue.
