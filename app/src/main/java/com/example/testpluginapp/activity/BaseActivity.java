package com.example.testpluginapp.activity;

import android.os.Process;

import com.example.testpluginapp.utils.LogUtils;
import com.qihoo360.replugin.loader.a.PluginAppCompatActivity;

import java.io.File;

public class BaseActivity extends PluginAppCompatActivity {
    private final String TAG = "Plugin" + File.separator + getClass().getSimpleName();
    @Override
    protected void onResume() {
        super.onResume();
        LogUtils.d(TAG, " onResume pid = ", Process.myPid());
    }
}
