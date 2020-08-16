package com.example.testpluginapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Process;
import android.view.View;
import android.widget.Button;

import com.example.testpluginapp.BuildConfig;
import com.example.testpluginapp.R;
import com.example.testpluginapp.service.CustomService;
import com.example.testpluginapp.utils.LogUtils;
import com.qihoo360.replugin.RePlugin;

import java.util.concurrent.Executors;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = "PluginMainActivity";
    // 插件进程是否启动
    private static boolean isRunningP5 = false;
    private Button btnPictures;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnPictures = findViewById(R.id.btn_pictures);
        setTitle("插件首页");

        btnPictures.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        String process = getPackageName() + ":p5";
        LogUtils.d(TAG, "process = ", process);
        if (!isRunningP5 && !RePlugin.isPluginRunningInProcess(BuildConfig.APPLICATION_ID, process)) {
            isRunningP5 = true;
            LogUtils.d(TAG, "isPluginRunningInProcess = ", Process.myPid());
            Executors.newSingleThreadExecutor().execute(new Runnable() {
                @Override
                public void run() {
                    startService(new Intent(getApplicationContext(), CustomService.class));
                }
            });
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_pictures:
                startActivity(new Intent(this, PicturesActivity.class));
                break;
            default:
                break;
        }
    }
}
