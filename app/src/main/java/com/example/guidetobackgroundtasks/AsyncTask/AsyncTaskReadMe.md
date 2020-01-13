#Async Task
**AsyncTask** allows you to perform background operations and publish results on the UI thread without
having to manipulate threads or handlers. AsyncTask is designed to be a helper class around **Thread**
and **Handler** and does not constitute a generic threading framework. **AsyncTask** should be used
for short operation. , and 4 steps called **onPreExecute**, **doInBackground**, 
**onProgressUpdate** and **onPostExecute**.

0
**AsyncTask** must be subclassed to be used and Override **doInBackground**. 

**AsyncTask** is defined by computation that runs on a background thread and 
whose result is published on the UI thread. **AsyncTask** is defined by 3 generic types, called
**Params**, **Progress**, **Result**

**Params:** The type of parameter sent to the task upon execution.
**Progress:** the type of progress units published during the background computation.
**Result:** The type of result of the background thread.


When **AsyncTask** is executed it will go through four steps. **AsyncTask** should be used for short
operation. , and 4 steps called **onPreExecute**, **doInBackground**, **onProgressUpdate** and **onPostExecute**.

1) **onPreExecute()**, invoked on the UI thread before the task is executed. This step is normally used to 
setup the task, for instance by showing a progress bar.

2) **doInBackground()**, invoked on the background thread after onPreExecute(). This step is used to preform
tasks that can take a long time, **Params** are passed to the method.

3) **onProgressUpdate**, invoked on the UI thread after a call to publishProgress. Used to display any
form of progress.

4) **onPostExecute**, invoked on the UI thread after the background computation finishes. The result of
the background computation is passed to this step as a parameter.

**Cancel**, A task can be cancelled at any time by invoking cancel(boolean). 


## Threading Rules
There are a few threading rules that must be followed for this class to work properly
- Async task must load on the UI thread.
- The task instance must be created on the UI thread.
- Execute must be invoked on the UI thread.
- Do not call onPreExecute, onPostExecute, doInBackground, onProgressUpdate manually.
- The task can be execute only once, an exception will be thrown