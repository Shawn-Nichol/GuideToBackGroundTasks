package com.example.guidetobackgroundtasks.MultipleThreads;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.guidetobackgroundtasks.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ThreadingFragment extends Fragment {

    private static final String TAG = "MyLogThreadingFragment";
    View v;

    Button btnStart;

    private Handler mainHandler = new Handler(); // Android class.
    public ThreadingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: ");
        v = inflater.inflate(R.layout.fragment_threading, container, false);

        btnStart = v.findViewById(R.id.frag_example_threading_btn_start);
        btnStart.setOnClickListener(view -> {
            Log.d(TAG, "onCreateView: btn Start");
//            ExampleThread thread = new ExampleThread(10);
//            thread.start();
            ExampleRunnable runnable = new ExampleRunnable(10);
            new Thread(runnable).start();

        });

        Button btnStop = v.findViewById(R.id.frag_threading_btn_stop);
        btnStop.setOnClickListener(view -> {
            Log.d(TAG, "onCreateView: btn stop");
        });

        return v;
    }

    class ExampleThread extends Thread {
        int mSeconds;

        public ExampleThread(int mSeconds) {
            this.mSeconds = mSeconds;
        }

        @Override
        public void run() {
            for(int i = 0; i < mSeconds; i++) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Log.d(TAG, "onCreateView: " + i);
            }
        }
    }

    class ExampleRunnable implements Runnable {
        int mSeconds;

        private static final String TAG = "MyLogExampleRunnable";

        public ExampleRunnable(int mSeconds) {
            this.mSeconds = mSeconds;
        }

        @Override
        public void run() {

            for(int i = 0; i < mSeconds; i++) {
                if(i == 5) {
                    Handler threadHandler = new Handler(Looper.getMainLooper());

                    threadHandler.post(() ->
                            btnStart.setText("50%"));
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Log.d(TAG, "onCreateView: " + i);
            }
        }
    }

}
