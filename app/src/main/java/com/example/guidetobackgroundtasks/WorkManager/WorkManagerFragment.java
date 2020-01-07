package com.example.guidetobackgroundtasks.WorkManager;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.work.ArrayCreatingInputMerger;
import androidx.work.Constraints;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkContinuation;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.example.guidetobackgroundtasks.R;

import java.util.Arrays;

/**
 * A simple {@link Fragment} subclass.
 */
public class WorkManagerFragment extends Fragment {
    private static final String TAG = "MyLogWorkManagerFragment";

    View v;
    OneTimeWorkRequest workRequestSingle;
    OneTimeWorkRequest workRequestOne;
    OneTimeWorkRequest workRequestTwo;
    OneTimeWorkRequest workRequestThree;

    Button btnOnce;
    Button btnChain;
    Button btnAtOnce;
    Button btnCombine;

    ProgressBar pb;
    Constraints constraints;

    public WorkManagerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_work_manager, container, false);

        pb = v.findViewById(R.id.frag_work_manager_progressbar);

        loadConstraints();
        loadWorkRequest();

        loadSingleWorkRequest();

        loadChainWork();
        loadAtOnce();
        loadCombine();

        loadWorkStates();

        return v;
    }

    private void loadWorkStates() {
        WorkManager.getInstance(getActivity()).getWorkInfoByIdLiveData(workRequestSingle.getId())
                .observe(getActivity(), workInfo -> {
                if (workInfo.getState() == WorkInfo.State.SUCCEEDED) {
                    Log.d(TAG, "onChanged: work finished");
                    pb.setVisibility(View.INVISIBLE);
                    }
                });

        WorkManager.getInstance(getActivity()).getWorkInfoByIdLiveData(workRequestThree.getId())
                .observe(getActivity(), workInfo -> {
                    if(workInfo.getState() == WorkInfo.State.SUCCEEDED) {
                        pb.setVisibility(View.INVISIBLE);
                    }
                });

    }

    private void loadConstraints() {
        constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.UNMETERED)
                .build();
    }

    private void loadWorkRequest() {

        workRequestSingle = new OneTimeWorkRequest.Builder(MyWorker.class)
                .setConstraints(constraints)
                .addTag("MyWorker")
                .build();

        workRequestOne = new OneTimeWorkRequest.Builder(MyWorkerOne.class)
                .setConstraints(constraints)
                .addTag("Worker One")
                .build();

        workRequestTwo = new OneTimeWorkRequest.Builder(MyWorkerTwo.class)
                .setConstraints(constraints)
                .addTag("Worker Two")
                .build();

        workRequestThree = new OneTimeWorkRequest.Builder(MyWorkerThree.class)
                .setConstraints(constraints)
                .addTag("Worker Three")
                .setInputMerger(ArrayCreatingInputMerger.class)
                .build();
    }

    private void loadSingleWorkRequest(){
        btnOnce = v.findViewById(R.id.frag_work_manager_btn_single);
        btnOnce.setOnClickListener(view -> {
            Log.d(TAG, "onCreateView: btnStart");


            pb.setVisibility(View.VISIBLE);

            // Once the work request is defined you can now schedule it with the WorkManager using enqueue().
            WorkManager.getInstance(getActivity()).enqueue(workRequestSingle);
        });
    }

    private void loadChainWork() {

        btnChain = v.findViewById(R.id.frag_work_manager_btn_chain_work);
        btnChain.setOnClickListener(view -> {
            Log.d(TAG, "loadChainWork: btnPressed");
            pb.setVisibility(View.VISIBLE);
            WorkContinuation continuation = WorkManager.getInstance(getActivity()).beginWith(workRequestOne);

            continuation.then(workRequestTwo)
                    .then(workRequestThree)
                    .enqueue();
        });

    }

    private void loadAtOnce() {
        btnAtOnce = v.findViewById(R.id.frag_work_manager_btn_chain_at_once);
        btnAtOnce.setOnClickListener(view -> {
            Log.d(TAG, "loadAtOnce: btnPressed");
            pb.setVisibility(View.VISIBLE);

            WorkManager.getInstance(getActivity())
                    // Runs in parallel
                    .beginWith(Arrays.asList(workRequestOne, workRequestTwo, workRequestThree))
                    .enqueue();
        });
    }

    private void loadCombine() {
        btnCombine = v.findViewById(R.id.frag_work_manager_btn_chain_combine);
        btnCombine.setOnClickListener(view -> {
            pb.setVisibility(View.VISIBLE);
            Log.d(TAG, "loadCombine: btnPressed");
            WorkManager.getInstance(getActivity())
                    // Runs in parallel
                    .beginWith(Arrays.asList(workRequestOne, workRequestTwo))
                    .then(workRequestThree)
                    .enqueue();
        });
    }



}
