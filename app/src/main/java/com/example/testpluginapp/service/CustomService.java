package com.example.testpluginapp.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.example.testpluginapp.utils.LogUtils;

public class CustomService extends Service {
    private static final String TAG = "CustomService";

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtils.d(TAG, "CustomService onCreate");
        stopSelf();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtils.d(TAG, "CustomService onDestroy");
    }
}
