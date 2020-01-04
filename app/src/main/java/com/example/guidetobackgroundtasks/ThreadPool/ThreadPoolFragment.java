package com.example.guidetobackgroundtasks.ThreadPool;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.guidetobackgroundtasks.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ThreadPoolFragment extends Fragment {


    public ThreadPoolFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_thread_pool, container, false);
    }

}
