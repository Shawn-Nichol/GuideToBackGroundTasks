# Runnable 
The **Runnable** interface should be implemented by any class whose instances are intended to be 
executed by a thread. This interface is designed to provide a common protocol for objects that wish 
to execute code while they are active.

## how to create runnable
- Create a new class that implements Runnable.
  - Override run()
    - Do action inside **run()**. 
- In the Activity/Fragment create a Runnable object
  - call **new Thread(myRunnable).start()
```
public class MyRunnable implements Runnable {
    
    public MyRunnable() {
    
    }
    
    @Override
    public void run() {
        // Do work
    }
}
```
