package com.example.guidetobackgroundtasks.Looper;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.guidetobackgroundtasks.R;

import static com.example.guidetobackgroundtasks.Looper.LooperHandler.TASK_A;
import static com.example.guidetobackgroundtasks.Looper.LooperHandler.TASK_B;

/**
 * A simple {@link Fragment} subclass.
 */
public class LooperFragment extends Fragment {
    private static final String TAG = "MyLogLooperFragment";

    View v;
    Button btnStart;
    Button btnStop;
    Button btnTaskA;
    Button btnTaskB;

    private LooperLooper looperThread = new LooperLooper();

    public LooperFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_looper, container, false);

        btnStart = v.findViewById(R.id.frag_looper_btn_start);
        btnStart.setOnClickListener(view -> {
            looperThread.start();
        });

        btnStop = v.findViewById(R.id.frag_looper_btn_stop);
        btnStop.setOnClickListener(view -> {
            looperThread.looper.quit();
            // looperThread.handler.getLooper(); Alternative
        });

        btnTaskA = v.findViewById(R.id.frag_looper_btn_task_a);
        btnTaskA.setOnClickListener(view -> {
            Message msg = Message.obtain();
            msg.what = TASK_A;
            looperThread.handler.sendMessage(msg);

            //            Handler threadHandler = new Handler(looperThread.looper);
//
//            threadHandler.post(() -> {
//                for (int i = 0; i < 5; i++) {
//                    Log.d(TAG, "run: " + i);
//                    SystemClock.sleep(1000);
//                }
//            });
        });

        btnTaskB = v.findViewById(R.id.frag_looper_btn_task_b);
        btnTaskB.setOnClickListener(view -> {
            Message msg = Message.obtain();
            msg.what = TASK_B;
            looperThread.handler.sendMessage(msg);
        });

        return v;

    }

}
