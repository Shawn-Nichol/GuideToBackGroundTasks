package com.example.guidetobackgroundtasks.Handler;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.guidetobackgroundtasks.ExampleThread.ExampleThreadingFragment;
import com.example.guidetobackgroundtasks.R;

import static com.example.guidetobackgroundtasks.Handler.HandlerHandlerThread.MY_TASK;

/**
 * A simple {@link Fragment} subclass.
 */
public class HandlerFragment extends Fragment {

    private static final String TAG = "MyLogHandlerFragment";
    View v;


    //private HandlerThread handlerThread = new HandlerThread("HandlerThread");
    private HandlerHandlerThread handlerThread = new HandlerHandlerThread();
//    private Handler threadHandler;
    private myRunnable1 runnable1 = new myRunnable1();
    private Object token = new Object();


    Button btnDoWork;
    Button btnRemoveMsg;

    public HandlerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_handler, container, false);

        handlerThread.start();
        //threadHandler = new Handler(handlerThread.getLooper());

        btnDoWork = v.findViewById(R.id.frag_handler_btn_do_work);
        btnDoWork.setOnClickListener(view -> {

            Message msg = Message.obtain();
            msg.what = MY_TASK;
            msg.arg1 = 23;
            msg.obj = "MY String Obj";
            handlerThread.getHandler().sendMessage(msg);
//            handlerThread.getHandler().sendEmptyMessage(1);


            handlerThread.getHandler().postAtTime(new myRunnable1(), token, SystemClock.uptimeMillis());
            handlerThread.getHandler().post(runnable1);
//            handlerThread.getHandler().post(new myRunnable1());
//            handlerThread.getHandler().postAtFrontOfQueue(new myRunnable2());
        });

        btnRemoveMsg = v.findViewById(R.id.frag_handler_btn_remove_message);
        btnRemoveMsg.setOnClickListener(view -> {
            handlerThread.getHandler().removeCallbacks(runnable1, token);
        });

        return v;
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        handlerThread.quitSafely();
    }

    static class myRunnable1 implements Runnable {
        @Override
        public void run() {
            for(int i = 0; i < 4; i++) {
                Log.d(TAG, "myRunnable 1: " + i);
                SystemClock.sleep(1000);
            }
        }
    }

    static class myRunnable2 implements Runnable {
        @Override
        public void run() {
            for(int i = 0; i < 4; i++) {
                Log.d(TAG, "myRunnable 2: " + i);
                SystemClock.sleep(1000);
            }
        }
    }
}
