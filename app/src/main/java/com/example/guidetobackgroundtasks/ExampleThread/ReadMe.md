This fragment will show you what will happen if you try and run an action on the UI thread well a
long action is running. 

## No Threading button
After a button press the UI thread will sleep for 10 seconds. If you try and change the switch well
the UI is sleeping the APP will crash.

## Threading Button
After a button press the ExampleThread will run for 10 seconds not affecting actions on the UI thread.