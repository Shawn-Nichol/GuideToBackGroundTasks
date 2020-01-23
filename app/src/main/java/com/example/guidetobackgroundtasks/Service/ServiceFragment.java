package com.example.guidetobackgroundtasks.Service;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.guidetobackgroundtasks.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class ServiceFragment extends Fragment {

    private static final String TAG = "MyLogServiceFragment";

    // UI components
    private ProgressBar mProgressBar;
    private TextView mProgressBarTV;
    private Button mBtnBound;

    private BoundService mService;
    private BoundServiceViewModel mViewModel;

    View v;

    public ServiceFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_service, container, false);

        getActivity().setTitle("Service");
        loadService();
        loadBackgroundService();
        loadForeGroundService();
        loadBondService();

        return v;
    }


    private void loadService() {
        Button btn = v.findViewById(R.id.frag_service_btn_service);
        btn.setOnClickListener(view -> {
            if (btn.getText().toString().equals("Service Started")) {
                Log.d(TAG, "loadService: Service Stopped");
                getActivity().stopService(new Intent(getActivity(), MyService.class));
                btn.setText("Service Stopped");

            } else {
                Log.d(TAG, "loadService: Service Started");
                getActivity().startService(new Intent(getActivity(), MyService.class));
                btn.setText("Service Started");
            }
        });
    }

    private void loadBackgroundService() {
        Button btn = v.findViewById(R.id.frag_service_btn_background_service);
        btn.setOnClickListener(view -> {

            Intent intent = new Intent(getActivity(), MyBackgroundService.class);
            getActivity().startService(intent);
        });

        Button btnCancel = v.findViewById(R.id.frag_service_btn_cancel_background);
        btnCancel.setOnClickListener(view -> {
            getActivity().stopService(new Intent(getActivity(), MyBackgroundService.class));
        });
    }

    private void loadForeGroundService() {

        Button btnStart = v.findViewById(R.id.frag_service_btn_foreground);
        btnStart.setOnClickListener(view -> {

            Intent serviceIntent = new Intent(getActivity(), MyForegroundService.class);

            ContextCompat.startForegroundService(getActivity(), serviceIntent);
        });

    }

    private void loadBondService() {
        mProgressBar = v.findViewById(R.id.frag_service_progressbar_bound);
        mProgressBarTV = v.findViewById(R.id.frag_service_tv_progress_bar);
        mBtnBound = v.findViewById(R.id.frag_service_btn_bound);

        mViewModel = ViewModelProviders.of(this).get(BoundServiceViewModel.class);
        setObservers();


        mBtnBound.setOnClickListener(view -> {
            if (mService.getProgress() == mService.getMaxValue()) {
                mService.resetTask();
                mBtnBound.setText("Start");
            } else if (mService.getIsPaused()) {
                mService.unPausePretendLongRunningTask();
                mViewModel.setIsProgressBarUpdating(true);
            } else {
                mService.pausePretendLongRunningTask();
                mViewModel.setIsProgressBarUpdating(false);
            }
        });

    }

    private void setObservers() {
        mViewModel.getBinder().observe(this, new Observer<BoundService.MyBinder>() {
            @Override
            public void onChanged(BoundService.MyBinder myBinder) {
                if (myBinder == null) {
                    Log.d(TAG, "onChanged: unbound from service");
                } else {
                    Log.d(TAG, "onChanged: onChanged: bound service.");
                    mService = myBinder.getService();
                }
            }
        });

        mViewModel.getIsProgressBarUpdating().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                final Handler handler = new Handler();
                final Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        if (mViewModel.getIsProgressBarUpdating().getValue()) {
                            if (mViewModel.getBinder().getValue() != null) {
                                if (mService.getProgress() == mService.getMaxValue()) {
                                    mViewModel.setIsProgressBarUpdating(false);
                                }

                                mProgressBar.setProgress(mService.getProgress());
                                mProgressBar.setMax(mService.getMaxValue());
                                String progress = String.valueOf(100 * mService.getProgress() / mService.getMaxValue()) + "%";
                                mProgressBarTV.setText(progress);
                            }
                            handler.postDelayed(this, 100);
                        } else {
                            handler.removeCallbacks(this);
                        }
                    }
                };

                if (aBoolean) {
                    mBtnBound.setText("Pause");
                    handler.postDelayed(runnable, 100);
                } else if (mService.getProgress() == mService.getMaxValue()) {
                    mBtnBound.setText("Restart");
                } else {
                    mBtnBound.setText("Start");
                }
            }

        });
    }

    @Override
    public void onResume() {
        super.onResume();
        Intent serviceIntent = new Intent(getActivity(), BoundService.class);
        getActivity().startService(serviceIntent);

        Intent serviceBindIntent = new Intent(getActivity(), BoundService.class);
        getActivity().bindService(serviceBindIntent, mViewModel.getServiceConnection(), Context.BIND_AUTO_CREATE);


    }

    @Override
    public void onStop() {
        super.onStop();
        if (mViewModel.getBinder() != null) {
            getActivity().unbindService(mViewModel.getServiceConnection());
        }
    }


}
