package com.example.guidetobackgroundtasks.MultipleThreads;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.guidetobackgroundtasks.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MultipleThreadsFragment extends Fragment {
    private static final String TAG = "MyLogMultipleThreadsFragment";

    View v;

    static {

    }

    public MultipleThreadsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       v = inflater.inflate(R.layout.fragment_multiple_threads, container, false);

       return v;

    }

}
