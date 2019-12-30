package com.example.guidetobackgroundtasks.ExampleThread;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.guidetobackgroundtasks.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ExampleThreadingFragment extends Fragment {


    private static final String TAG = "MyLogExampleThreadingFragment";
    View v;

    Button btnThreading;
    Button btnNoThreading;

    public ExampleThreadingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_example_threading, container, false);

        btnThreading = v.findViewById(R.id.frag_example_threading_btn_threading);
        btnThreading.setOnClickListener(view -> {
            Log.d(TAG, "onCreateView: btn threading");
            ExampleThread thread = new ExampleThread(10);
            thread.start();

        });

        btnNoThreading = v.findViewById(R.id.frag_example_threading_btn_no_threading);
        btnNoThreading.setOnClickListener(view -> {
            Log.d(TAG, "onCreateView: btn no Threading");
            for(int i = 0; i < 10; i++) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Log.d(TAG, "onCreateView: No Threading" + i);
            }

        });

        return v;
    }



}
