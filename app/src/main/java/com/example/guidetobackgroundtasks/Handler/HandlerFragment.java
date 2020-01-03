package com.example.guidetobackgroundtasks.Handler;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Message;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.guidetobackgroundtasks.R;

import static com.example.guidetobackgroundtasks.Handler.HandlerHandlerThread.TASK_ONE;

/**
 * A simple {@link Fragment} subclass.
 */
public class HandlerFragment extends Fragment {

    private static final String TAG = "MyLogHandlerFragment";
    View v;


    private HandlerHandlerThread handlerThread = new HandlerHandlerThread();

    private HandlerRunnableTwo runnableTwo = new HandlerRunnableTwo();

    private Object token = new Object();


    Button btnMessageOne;
    Button btnMessageTwo;
    Button btnRunnableOne;
    Button btnRunnableTwo;
    Button btnRemoveMsg;

    public HandlerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_handler, container, false);

        // Causes this thread to begin execution.
        handlerThread.start();

        btnMessageOne = v.findViewById(R.id.frag_handler_btn_send_message_one);
        btnMessageOne.setOnClickListener(view -> {
            // Best way to get on of these.
            Message msg = Message.obtain();
            // User-defined message code so that the recipient can identify what this message is about.
            msg.what = TASK_ONE;

            // arg1/arg2 are lower-cost alternatives to using setData() if you only need to store a few
            // integers.
            msg.arg1 = 19;
            // An arbitrary object to send to the recipient.
            msg.obj = "Message One";
            // Pushes a message onto the end of the message queue after all pending messages before
            // the current time.
            handlerThread.getHandler().sendMessage(msg);
        });

        btnMessageTwo = v.findViewById(R.id.frag_handler_btn_send_empty_message);
        btnMessageTwo.setOnClickListener(view -> {
            handlerThread.getHandler().sendEmptyMessage(1);
        });

        btnRunnableOne = v.findViewById(R.id.frag_handler_btn_runnable_one);
        btnRunnableOne.setOnClickListener(view -> {
            handlerThread.getHandler().postAtTime(new HandlerRunnableOne(), token, SystemClock.uptimeMillis());
        });

        btnRunnableTwo = v.findViewById(R.id.frag_handler_btn_runnable_two);
        btnRunnableTwo.setOnClickListener(view -> {
            handlerThread.getHandler().post(runnableTwo);
        });

        btnRemoveMsg = v.findViewById(R.id.frag_handler_btn_remove_message);
        btnRemoveMsg.setOnClickListener(view -> {
            handlerThread.getHandler().removeCallbacks(runnableTwo, token);
        });

        return v;
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        handlerThread.quitSafely();
    }


}
