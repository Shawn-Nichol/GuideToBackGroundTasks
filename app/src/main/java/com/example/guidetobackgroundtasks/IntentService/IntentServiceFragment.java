package com.example.guidetobackgroundtasks.IntentService;


import android.content.Intent;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.guidetobackgroundtasks.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class IntentServiceFragment extends Fragment {

    View v;
    public IntentServiceFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_intent_service,container, false);

        EditText et = v.findViewById(R.id.frag_intent_service_et);

        Button btnStart = v.findViewById(R.id.frag_intent_service_btn_start);
        btnStart.setOnClickListener(view -> {
            String input = et.getText().toString();

            Intent serviceIntent = new Intent(getActivity(), MyIntentService.class);
            serviceIntent.putExtra("inputExtra", input);

            ContextCompat.startForegroundService(getActivity(), serviceIntent);
        });


        return v;
    }

}
