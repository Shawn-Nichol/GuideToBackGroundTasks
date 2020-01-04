package com.example.guidetobackgroundtasks.AsyncTask;


import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.guidetobackgroundtasks.MainActivity;
import com.example.guidetobackgroundtasks.R;

import java.lang.ref.WeakReference;

/**
 * A simple {@link Fragment} subclass.
 */
public class AsyncTaskFragment extends Fragment {
    private static final String TAG = "MyLogAsyncTaskFragment";

    View v;
    Button btnStart;
    ProgressBar progressBar;

    public AsyncTaskFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_async_task, container, false);

        progressBar = v.findViewById(R.id.frag_async_progress_bar);

        btnStart = v.findViewById(R.id.frag_async_btn_start);
        btnStart.setOnClickListener(view -> {
            // Create subclass.
            MyAsyncTask task = new MyAsyncTask(this);
            // Execute AsyncTask
            task.execute(10);
        });


        return v;

    }

    /**
     * MyAsyncTask.
     *
     * Integer: Parameters.
     * Integer: Progress.
     * String: results.
     */
    private class MyAsyncTask extends AsyncTask<Integer, Integer, String> {

        private WeakReference<AsyncTaskFragment> activityWeakReference;

        MyAsyncTask(AsyncTaskFragment activity){
            activityWeakReference = new WeakReference<AsyncTaskFragment>(activity);
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            AsyncTaskFragment fragment = activityWeakReference.get();
            if(fragment == null || fragment.isRemoving()){
                return;
            }
            fragment.progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(Integer... integers) {
            for(int i = 0; i < integers[0]; i++) {
                publishProgress((i * 100) /integers[0]);

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            return "Finished";
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            AsyncTaskFragment fragment = activityWeakReference.get();
            if(fragment == null || fragment.isRemoving()){
                return;
            }
            fragment.progressBar.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            AsyncTaskFragment fragment = activityWeakReference.get();
            if(fragment == null || fragment.isRemoving()){
                return;
            }

            Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
            fragment.progressBar.setVisibility(View.INVISIBLE);
        }


    }

}
