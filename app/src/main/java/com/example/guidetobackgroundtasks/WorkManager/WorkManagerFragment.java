package com.example.guidetobackgroundtasks.WorkManager;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.work.Constraints;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.guidetobackgroundtasks.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class WorkManagerFragment extends Fragment {
    private static final String TAG = "MyLogWorkManagerFragment";

    View v;
    OneTimeWorkRequest workRequestOne;

    ProgressBar pb;

    public WorkManagerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_work_manager, container, false);
        Button btnOnce = v.findViewById(R.id.frag_work_manager_btn_once);


        pb = v.findViewById(R.id.frag_work_manager_progressbar);
        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.UNMETERED)
                .build();

        workRequestOne = new OneTimeWorkRequest.Builder(MyWorker.class)
                .setConstraints(constraints)
                .addTag("MyWorker")
                .build();


        btnOnce.setOnClickListener(view -> {
            Log.d(TAG, "onCreateView: btnStart");


            pb.setVisibility(View.VISIBLE);

            // Once the work request is defined you can now schedule it with the WorkManager using enqueue().
            WorkManager.getInstance(getActivity()).enqueue(workRequestOne);
        });

        Button btnRepeat = v.findViewById(R.id.frag_work_manager_btn_repeat);
        btnRepeat.setOnClickListener(view -> {

        });

        workStates();
        return v;
    }

    private void workStates() {
        WorkManager.getInstance(getActivity()).getWorkInfoByIdLiveData(workRequestOne.getId())
                .observe(getActivity(), new Observer<WorkInfo>() {
                    @Override
                    public void onChanged(WorkInfo workInfo) {
                    if (workInfo.getState() == WorkInfo.State.SUCCEEDED) {
                        Log.d(TAG, "onChanged: work finished");
                        pb.setVisibility(View.INVISIBLE);
                        }
                    }
                });

    }

}
