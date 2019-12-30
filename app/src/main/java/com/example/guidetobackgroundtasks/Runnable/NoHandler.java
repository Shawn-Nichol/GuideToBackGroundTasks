package com.example.guidetobackgroundtasks.Runnable;

import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.guidetobackgroundtasks.R;

public class NoHandler implements Runnable {

    private static final String TAG = "MyLogHandler";
    int mSeconds;
    View v;
    Button btn;

    public NoHandler(View v, int mSeconds) {
        this.mSeconds = mSeconds;
        this.v = v;
    }

    @Override
    public void run() {
        btn = v.findViewById(R.id.frag_runnable_btn_no_handler);

        for(int i = 0; i < mSeconds; i++) {

            if(i == 5) {
                btn.setText("50%");
            }
            Log.d(TAG, "run: " + i);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}