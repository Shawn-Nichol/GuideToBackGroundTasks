package com.example.guidetobackgroundtasks.Runnable;


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
public class RunnableFragment extends Fragment {
    private static final String TAG = "MyLogRunnableFragment";
    View v;
    Button btnRunnable;
    Button btnHandler;
    Button btnHandler2;
    Button btnHandler3;
    Button btnNoHandler;


    private Handler myHandler = new Handler();


    public RunnableFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_runnable, container, false);

        btnRunnable = v.findViewById(R.id.frag_runnable_btn);
        btnRunnable.setOnClickListener(view -> {
            Runnable1 runnable = new Runnable1(10);
            new Thread(runnable).start();
        });

        btnHandler = v.findViewById(R.id.frag_runnable_btn_handler);
        btnHandler.setOnClickListener(view -> {
            Handler1 handler1 = new Handler1(10);
            new Thread(handler1).start();

        });

        btnHandler2 = v.findViewById(R.id.frag_runnable_btn_handler2);
        btnHandler2.setOnClickListener(view -> {
            Handler2 handler2 = new Handler2(10);
            new Thread(handler2).start();
        });

        btnHandler3 = v.findViewById(R.id.frag_runnable_btn_handler3);
        btnHandler3.setOnClickListener(view -> {
            Handler3 handler3 = new Handler3(10);
            new Thread(handler3).start();
        });

        btnNoHandler = v.findViewById(R.id.frag_runnable_btn_no_handler);
        btnNoHandler.setOnClickListener(view -> {
            NoHandler noHandler = new NoHandler(v, 10);
            new Thread(noHandler).start();
        });



        return v;
    }

    public class Handler1 implements Runnable {

        private static final String TAG = "MyLogHandler";
        int mSeconds;
        Button btn;


        public Handler1(int mSeconds) {
            this.mSeconds = mSeconds;
        }


        @Override
        public void run() {
            btn = v.findViewById(R.id.frag_runnable_btn_handler);
            for(int i = 0; i < mSeconds; i++) {

                if(i == 5) {
                    Handler threadHandler = new Handler(Looper.getMainLooper());
                    threadHandler.post(() -> btnHandler.setText("50%"));

                }
                Log.d(TAG, "run: " + i);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public class Handler2 implements Runnable {

        private static final String TAG = "MyLogHandler";
        int mSeconds;
        Button btn;


        public Handler2(int mSeconds) {
            this.mSeconds = mSeconds;
        }


        @Override
        public void run() {
            btn = v.findViewById(R.id.frag_runnable_btn_handler);
            for(int i = 0; i < mSeconds; i++) {

                if(i == 5) {
                    btnHandler2.post(() -> btnHandler2.setText("50%"));

                }
                Log.d(TAG, "run: " + i);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public class Handler3 implements Runnable {

        private static final String TAG = "MyLogHandler";
        int mSeconds;
        Button btn;


        public Handler3(int mSeconds) {
            this.mSeconds = mSeconds;
        }


        @Override
        public void run() {
            btn = v.findViewById(R.id.frag_runnable_btn_handler);
            for(int i = 0; i < mSeconds; i++) {

                if(i == 5) {
                    getActivity().runOnUiThread(() -> {
                        btnHandler3.setText("50%");
                    });

                }
                Log.d(TAG, "run: " + i);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
