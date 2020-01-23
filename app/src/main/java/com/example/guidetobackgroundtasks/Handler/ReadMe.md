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



## Descriptions
**HandlerThread** is a Thread that has Looper. The Looper can then be used to create Handlers.

**Message** contains a description and arbitrary dta object that can be sent to a Handler. This
object contains two extra int fields and an extra object field that allows you to not do allocation 
in many cases.

**MessageQueue** is a low-level class holding the list of messages to be dispatched by a **Looper**.

**Messages** are not added directly to a **MessageQueue**, but rather through a **Handler** object associated 
with the **Looper**