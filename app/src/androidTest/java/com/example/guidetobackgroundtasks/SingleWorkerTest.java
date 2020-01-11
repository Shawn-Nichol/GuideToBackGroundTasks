package com.example.guidetobackgroundtasks;

import android.content.Context;

import androidx.test.runner.AndroidJUnit4;
import androidx.work.Data;

import com.example.guidetobackgroundtasks.WorkManager.SingleWorker;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.Result;
import org.junit.runner.RunWith;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@RunWith(AndroidJUnit4.class)
public class SingleWorkerTest  {
    private Context mContext;
    private Executor mExecutor;

//    @Before
//    public void setUp() {
//        mContext = ApplicationProvider.getApplicationcontext();
//        mExecutor = Executors.newSingleThreadExecutor();
//    }
//
//    @Test
//    public void testSingleWorker() {
//        Data inputData = new Data.Builder()
//                .putLong("SLEEP_DURATION", 5_000L)
//                .build();
//
//        SingleWorker worker = (SingleWorker) TestWorkerBuilder.from(mContext,
//                SingleWorker.class,
//                mExecutor)
//                .setInputData(inputData)
//                .build();
//
//        Result result = worker.doWork();
//        assertThat(result, is(Result.success()));
//    }
}
