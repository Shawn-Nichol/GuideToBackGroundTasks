package com.example.guidetobackgroundtasks.JobScheduler;


import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.guidetobackgroundtasks.R;

import static android.content.Context.JOB_SCHEDULER_SERVICE;

/**
 * A simple {@link Fragment} subclass.
 */
public class JobSchedulerFragment extends Fragment {

    private static final String TAG = "MyLogJobSchedulerFragment";
    View v;

    public JobSchedulerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_job_scheduler, container, false);

        Button btnScheduler = v.findViewById(R.id.frag_job_scheduler_btn_start);
        btnScheduler.setOnClickListener(view -> {

            ComponentName componentName = new ComponentName(getActivity(), MyJobService.class);
            JobInfo info = new JobInfo.Builder(123, componentName)
//                    .setRequiresCharging(true)
                   .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)
                    .setPersisted(true)
                    .setPeriodic(15 * 60 * 1000)
                    .build();

            JobScheduler scheduler = (JobScheduler) getActivity().getSystemService(JOB_SCHEDULER_SERVICE);

            int resultCode = scheduler.schedule(info);
            if(resultCode == JobScheduler.RESULT_SUCCESS) {
                Log.d(TAG, "onCreateView: success");
            } else {
                Log.d(TAG, "onCreateView: failed");
            }
        });

        Button btnStop = v.findViewById(R.id.frag_job_scheduler_btn_stop);
        btnStop.setOnClickListener(view -> {
            JobScheduler scheduler = (JobScheduler) getActivity().getSystemService(JOB_SCHEDULER_SERVICE);
            scheduler.cancel(123);
            Log.d(TAG, "onCreateView: Job Canceled");
        });

        return v;
    }

}
