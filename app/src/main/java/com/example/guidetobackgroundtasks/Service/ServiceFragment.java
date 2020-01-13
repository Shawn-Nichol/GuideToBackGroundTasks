package com.example.guidetobackgroundtasks.Service;


import android.content.Intent;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
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
public class ServiceFragment extends Fragment {

    private static final String TAG = "MyLogServiceFragment";

    View v;
    public ServiceFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_service,container, false);

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
            if(btn.getText().toString().equals("Service Started")) {
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

    }



}
