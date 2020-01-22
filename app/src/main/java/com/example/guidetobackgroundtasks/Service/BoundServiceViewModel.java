package com.example.guidetobackgroundtasks.Service;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class BoundServiceViewModel extends ViewModel {

    private static final String TAG = "MyLogBoundServiceViewModel";

    private MutableLiveData<Boolean> mIsProgressBarUpdating = new MutableLiveData<>();
    private MutableLiveData<BoundService.MyBinder> mBinder = new MutableLiveData<>();

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder iBinder) {
            Log.d(TAG, "onServiceConnected: connected to service.");
            BoundService.MyBinder binder = (BoundService.MyBinder) iBinder;
            mBinder.postValue(binder);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG, "onServiceDisconnected: disconnect from service.");
            mBinder.postValue(null);
        }
    };

    public ServiceConnection getServiceConnection() {
        return serviceConnection;
    }

    public LiveData<BoundService.MyBinder> getBinder() {
        return mBinder;
    }

    public LiveData<Boolean> getIsProgressBarUpdating() {
        return mIsProgressBarUpdating;
    }

    public void setIsProgressBarUpdating(boolean isUpdating) {
        mIsProgressBarUpdating.postValue(isUpdating);
    }
}
