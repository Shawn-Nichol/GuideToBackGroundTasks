package com.example.guidetobackgroundtasks.Runnable;


import android.os.Bundle;

import androidx.fragment.app.Fragment;


import android.os.Handler;
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

    // Widgets
    View v;

    Button btnRunnableOne;
    Button btnRunnableTwo;
    Button btnRunnableThree;
    Button btnStop1;
    Button btnStop2;
    Button btnStop3;
    Button btnStopAll;
    Button btnNoHandler;

    RunnableRunnableOne runnableRunnableOne;
    RunnableRunnableTwo runnableRunnableTwo;
    RunnableRunnableThree runnableRunnableThree;


    private Handler myHandler = new Handler();


    public RunnableFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_runnable, container, false);


        btnRunnableOne = v.findViewById(R.id.frag_runnable_btn_runnable_one);
        btnRunnableOne.setOnClickListener(view -> {

            runnableRunnableOne = new RunnableRunnableOne(10, btnRunnableOne);
            new Thread(runnableRunnableOne).start();
            runnableRunnableOne.setStop(false);
        });

        btnStop1 = v.findViewById(R.id.frag_runnable_btn_handler1_stop);
        btnStop1.setOnClickListener(view -> {
            runnableRunnableOne.setStop(true);
        });



        btnRunnableTwo = v.findViewById(R.id.frag_runnable_btn_runnable_two);
        btnRunnableTwo.setOnClickListener(view -> {
            runnableRunnableTwo = new RunnableRunnableTwo(10, btnRunnableTwo);
            new Thread(runnableRunnableTwo).start();
            runnableRunnableTwo.setStop(false);
        });

        btnStop2 = v.findViewById(R.id.frag_runnable_btn_handler2_stop);
        btnStop2.setOnClickListener( view -> {
            runnableRunnableTwo.setStop(true

            );
        });

        btnRunnableThree = v.findViewById(R.id.frag_runnable_btn_handler3);
        btnRunnableThree.setOnClickListener(view -> {

            runnableRunnableThree = new RunnableRunnableThree(getActivity(), 10, btnRunnableThree);
            new Thread(runnableRunnableThree).start();
            runnableRunnableThree.setStop(false);

        });

        btnStop3 = v.findViewById(R.id.frag_runnable_btn_handler3_stop);
        btnStop3.setOnClickListener(view -> {
            runnableRunnableThree.setStop(true);
        });

        btnStopAll = v.findViewById(R.id.frag_runnable_btn_stop_all);
        btnStopAll.setOnClickListener(view -> {
            runnableRunnableOne.setStop(true);
            runnableRunnableTwo.setStop(true);
            runnableRunnableThree.setStop(true);

        });

        btnNoHandler = v.findViewById(R.id.frag_runnable_btn_no_handler);
        btnNoHandler.setOnClickListener(view -> {
            NoHandler noHandler = new NoHandler(v, 10);
            new Thread(noHandler).start();
        });



        return v;
    }


}
