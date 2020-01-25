#Handler
A Handler allows you to send and process Message and Runnable objects associated with a 
threads MessageQueue. Each Handler instance is associated with a single thread and that thread's
message queue. When a Handler is created, it is bound to the MessageQueue that created it, 
from this point on, it will deliver Messages and Runnable to the MessageQueue and execute
them as they come out of the message queue.

**Main use.** 
1) To Schedule Messages and Runnable to be executed at some point.
2) To enqueue and action to be performed on a different thread.

## Builder a Handler. 
- Create Handler Thread class extend HandlerThread
  - include a constructor.
  - Create handler object
  - Override onLooperPrepared()
    - run handler through switch statement
  - Create getter for handler
  
```
public class MyHandlerThread extends HandlerThread {
    
    private handler;
    
    public MyHandlerThread() {
        super("MyHandler", Process.THREAD_PRIORITY_BACKGROUND)
    }    
    
    @Override 
    protected void onLooperPrepared() {
        handler = new Handler() {
            @Override
            public void handleMessage(Messsage msg) {
                switch (mst) {
                    cast TASK_ONE:
                        // DO work
                        break;
                    case TASK_TWO:
                        // Do work
                        break;
                    
                }
                    
            }
        }
    }
    
    public Handler getHandler() {return handler;}
    
}
```
  
- Start Handler
  - Create HandlerThread object
  - start HandlerThread
  - Create Message
  - attach handler and message to HandlerThread.
```
    private MyHandlerThread myHandlerThread() = new MyHandlerThread();
    
    myHandlerThread().start
    
    btn.setOnClickListener(view ->  {
        Message msg = Message.obtain();
        msg.what = "Task One"
        msg.arg = 19
        myHandlerThread.getHandler().sendMessage();
            
    });
``` 
  

## Descriptions
**HandlerThread** is a Thread that has Looper. The Looper can then be used to create Handlers.

**Message** contains a description and arbitrary dta object that can be sent to a Handler. This
object contains two extra int fields and an extra object field that allows you to not do allocation 
in many cases.

**MessageQueue** is a low-level class holding the list of messages to be dispatched by a **Looper**.

**Messages** are not added directly to a **MessageQueue**, but rather through a **Handler** object associated 
with the **Looper**