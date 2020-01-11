package com.example.guidetobackgroundtasks.WorkManager;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.work.ArrayCreatingInputMerger;
import androidx.work.Constraints;
import androidx.work.ExistingWorkPolicy;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
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
import java.util.concurrent.TimeUnit;

/**
 * A simple {@link Fragment} subclass.
 */
public class WorkManagerFragment extends Fragment {
    private static final String TAG = "MyLogWorkManagerFragment";

    View v;

    Button btnChain;
    Button btnAtOnce;
    Button btnCombine;

    Button btnCancelChain;
    Button btnCancelAtOnce;
    Button btnCancelWorkRequest;
    Button btnRecurringSingle;
    Button btnUnique;
    Button btnWorkerOne;
    Button btnWorkerTwo;
    Button btnWorkerThree;
    Button btnCancelOne;
    Button btnCancelTwo;
    Button btnCancelThree;
    Button btnCancelAll;


    ProgressBar pb;
    Constraints constraints;

    private int counter = 0;

    public WorkManagerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_work_manager, container, false);

        pb = v.findViewById(R.id.frag_work_manager_progressbar);

        loadConstraints();


        loadChainWork();
        loadAtOnce();
        loadCombine();
        loadRecurring();
        loadUnique();
        loadWorkerOne();
        loadWorkerTwo();
        loadWorkerThree();
        loadCancelAll();



        return v;
    }



    private void loadConstraints() {
        constraints = new Constraints.Builder()
                .build();
    }

    private void loadChainWork() {

        btnChain = v.findViewById(R.id.frag_work_manager_btn_chain_work);
        btnChain.setOnClickListener(view -> {
            Log.d(TAG, "loadChainWork: btnPressed");
            pb.setVisibility(View.VISIBLE);
            counter += 1;

            OneTimeWorkRequest workRequestOne = new OneTimeWorkRequest.Builder(MyWorkerOne.class)
                    .setConstraints(constraints)
                    .addTag("Worker One")
                    .addTag("Chain")
                    .build();

            OneTimeWorkRequest workRequestTwo = new OneTimeWorkRequest.Builder(MyWorkerTwo.class)
                    .addTag("Worker Two")
                    .addTag("Chain")
                    .build();

            OneTimeWorkRequest workRequestThree = new OneTimeWorkRequest.Builder(MyWorkerThree.class)
                    .addTag("Worker Three")
                    .addTag("Chain")
                    .build();

            WorkContinuation continuation = WorkManager.getInstance(getActivity()).beginWith(workRequestOne);

            continuation.then(workRequestTwo)
                    .then(workRequestThree)
                    .enqueue();

            WorkManager.getInstance(getActivity()).getWorkInfoByIdLiveData(workRequestThree.getId())
                    .observe(getActivity(), workInfo -> {
                        if(workInfo.getState() == WorkInfo.State.SUCCEEDED) {
                            Log.d(TAG, "loadChainWork: workRequest completed");
                            displayLoading();
                        }
                    });

        });

        btnCancelChain = v.findViewById(R.id.frag_work_manager_btn_cancel_chain_work);
        btnCancelChain.setOnClickListener(view -> {
            Log.d(TAG, "loadChainWork: btnCancel");
            WorkManager.getInstance(getActivity()).cancelAllWorkByTag("Chain");
            displayLoading();
        });

    }

    private void loadAtOnce() {
        btnAtOnce = v.findViewById(R.id.frag_work_manager_btn_chain_at_once);
        btnAtOnce.setOnClickListener(view -> {
            Log.d(TAG, "loadAtOnce: btnPressed");
            pb.setVisibility(View.VISIBLE);
            counter += 1;

            OneTimeWorkRequest workRequestOne = new OneTimeWorkRequest.Builder(MyWorkerOne.class)
                    .setConstraints(constraints)
                    .addTag("loadAtOnce")
                    .build();

            OneTimeWorkRequest workRequestTwo = new OneTimeWorkRequest.Builder(MyWorkerTwo.class)
                    .setConstraints(constraints)
                    .addTag("loadAtOnce")
                    .build();

            OneTimeWorkRequest workRequestThree = new OneTimeWorkRequest.Builder(MyWorkerThree.class)
                    .setConstraints(constraints)
                    .addTag("loadAtOnce")
                    .build();

            WorkManager.getInstance(getActivity())
                    // Runs in parallel
                    .beginWith(Arrays.asList(workRequestOne, workRequestTwo, workRequestThree))
                    .enqueue();

            WorkManager.getInstance(getActivity()).getWorkInfoByIdLiveData(workRequestThree.getId())
                    .observe(getActivity(), workInfo -> {
                        if(workInfo.getState() == WorkInfo.State.SUCCEEDED) {
                            Log.d(TAG, "loadAtOnce: workRequest finished");
                            displayLoading();
                        }
                    });
        });

        btnCancelAtOnce = v.findViewById(R.id.frag_work_manager_btn_cancel_work_at_once);
        btnCancelAtOnce.setOnClickListener(view -> {
            WorkManager.getInstance(getActivity()).cancelAllWorkByTag("loadAtOnce");
            displayLoading();
        });
    }

    private void loadCombine() {
        btnCombine = v.findViewById(R.id.frag_work_manager_btn_chain_combine);
        btnCombine.setOnClickListener(view -> {
            Log.d(TAG, "loadCombine: btnPressed");
            pb.setVisibility(View.VISIBLE);
            counter +=1;

            OneTimeWorkRequest workRequestOne = new OneTimeWorkRequest.Builder(MyWorkerOne.class)
                    .setConstraints(constraints)
                    .addTag("Combine")
                    .build();

            OneTimeWorkRequest workRequestTwo = new OneTimeWorkRequest.Builder(MyWorkerTwo.class)
                    .setConstraints(constraints)
                    .addTag("Combine")
                    .build();

            OneTimeWorkRequest workRequestThree = new OneTimeWorkRequest.Builder(MyWorkerThree.class)
                    .setConstraints(constraints)
                    .addTag("Combine")
                    .build();

            WorkManager.getInstance(getActivity())
                    // Runs in parallel
                    .beginWith(Arrays.asList(workRequestOne, workRequestTwo))
                    .then(workRequestThree)
                    .enqueue();
            WorkManager.getInstance(getActivity()).getWorkInfoByIdLiveData(workRequestThree.getId())
                    .observe(getActivity(), workInfo -> {
                        if(workInfo.getState() == WorkInfo.State.SUCCEEDED) {
                            Log.d(TAG, "loadCombine: workRequest finished");
                            displayLoading();
                        }
                    });
        });
    }

    private void loadRecurring() {
        btnRecurringSingle = v.findViewById(R.id.frag_work_manager_btn_recurring_single);
        btnRecurringSingle.setOnClickListener(view ->{
            PeriodicWorkRequest workRequest = new PeriodicWorkRequest.Builder(MyPeriodicWork.class, 3, TimeUnit.MINUTES)
                    .setConstraints(constraints)
                    .addTag("Worker Periodic")
                    .build();

            WorkManager.getInstance(getActivity()).enqueue(workRequest);
        });

    }

    private void loadUnique() {

        btnUnique = v.findViewById(R.id.frag_work_manager_btn_unique_work);
        btnUnique.setOnClickListener(view -> {

            OneTimeWorkRequest workRequest = new OneTimeWorkRequest.Builder(MyWorkerOne.class)
                    .setConstraints(constraints)
                    .addTag("Worker Unique")
                    .build();

            WorkManager.getInstance(getActivity())
                    .enqueueUniqueWork("myUnique work", ExistingWorkPolicy.REPLACE, workRequest);
        });
    }

    private void loadWorkerOne() {
        btnWorkerOne = v.findViewById(R.id.frag_work_manager_btn_worker_one);
        btnWorkerOne.setOnClickListener(view -> {
            Log.d(TAG, "loadWorkerOne: BtnPressed");
            pb.setVisibility(View.VISIBLE);
            counter += 1;

            OneTimeWorkRequest workRequest = new OneTimeWorkRequest.Builder(MyWorkerOne.class)
                    .setConstraints(constraints)
                    .addTag("Worker A")
                    .addTag("ALL")
                    .build();

            WorkManager.getInstance(getActivity()).enqueue(workRequest);

            WorkManager.getInstance(getActivity()).getWorkInfoByIdLiveData(workRequest.getId())
                    .observe(getActivity(), workInfo -> {
                        if(workInfo.getState() == WorkInfo.State.SUCCEEDED) {
                            Log.d(TAG, "loadWorkerOne: workerOne succeeded");
                            displayLoading();
                        }
                    });
        });

        btnCancelOne = v.findViewById(R.id.frag_work_manager_btn_cancel_one);
        btnCancelOne.setOnClickListener(view -> {
            Log.d(TAG, "loadWorkerOne: btnCancel");
            WorkManager.getInstance(getActivity()).cancelAllWorkByTag("Worker A");
            displayLoading();
        });
    }

    private void loadWorkerTwo() {
        btnWorkerTwo = v.findViewById(R.id.frag_work_manager_btn_worker_two);
        btnWorkerTwo.setOnClickListener(view -> {
            Log.d(TAG, "loadWorkerTwo: BtnPressed");
            pb.setVisibility(View.VISIBLE);
            counter += 1;

            OneTimeWorkRequest workRequest = new OneTimeWorkRequest.Builder(MyWorkerTwo.class)
                    .setConstraints(constraints)
                    .addTag("Worker B")
                    .addTag("ALL")
                    .build();


            WorkManager.getInstance(getActivity()).enqueue(workRequest);

            WorkManager.getInstance(getActivity()).getWorkInfoByIdLiveData(workRequest.getId())
                    .observe(getActivity(), workInfo -> {
                        if(workInfo.getState() == WorkInfo.State.SUCCEEDED) {
                            Log.d(TAG, "loadWorkerTwo: work succeeded");
                            displayLoading();
                        }
                    });
        });

        btnCancelTwo = v.findViewById(R.id.frag_work_manager_btn_cancel_two);
        btnCancelTwo.setOnClickListener(view -> {
            Log.d(TAG, "loadWorkerTwo: BtnCancel");
            WorkManager.getInstance(getActivity()).cancelAllWorkByTag("Worker B");
            displayLoading();
        });

    }

    private void loadWorkerThree() {
        btnWorkerThree = v.findViewById(R.id.frag_work_manager_btn_worker_three);
        btnWorkerThree.setOnClickListener(view -> {
            Log.d(TAG, "loadWorkerThree: BtnPressed");
            pb.setVisibility(View.VISIBLE);
            counter += 1;

            OneTimeWorkRequest workRequest = new OneTimeWorkRequest.Builder(MyWorkerThree.class)
                    .setConstraints(constraints)
                    .addTag("Worker C")
                    .addTag("ALL")
                    .build();

            WorkManager.getInstance(getActivity()).enqueue(workRequest);

            WorkManager.getInstance(getActivity()).getWorkInfoByIdLiveData(workRequest.getId())
                    .observe(getActivity(), workInfo -> {
                        if(workInfo.getState() == WorkInfo.State.SUCCEEDED) {
                            Log.d(TAG, "loadWorkerThree: workerThree succeeded");
                            displayLoading();
                        }
                    });
        });

        btnCancelThree = v.findViewById(R.id.frag_work_manager_btn_cancel_three);
        btnCancelThree.setOnClickListener(view -> {
            Log.d(TAG, "loadWorkerThree: btnCanceled");
            WorkManager.getInstance(getActivity()).cancelAllWorkByTag("Worker C");
            displayLoading();
        });
    }

    private void loadCancelAll() {
        btnCancelAll = v.findViewById(R.id.frag_work_manager_btn_cancel_all);
        btnCancelAll.setOnClickListener(view -> {
            Log.d(TAG, "loadCancelAll: btnPressed");
            WorkManager.getInstance(getActivity()).cancelAllWorkByTag("ALL");
            pb.setVisibility(View.INVISIBLE);
            counter = 0;
        });
    }

    private void displayLoading() {
        counter -= 1;
        Log.d(TAG, "displayLoading: counter = " + counter);
        if(counter == 0) {
            pb.setVisibility(View.INVISIBLE);
        }
    }
}
