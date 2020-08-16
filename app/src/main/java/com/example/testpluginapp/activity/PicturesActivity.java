package com.example.testpluginapp.activity;

import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.example.aidllibrary.IHostInterface;
import com.example.aidllibrary.Params;
import com.example.aidllibrary.V2.HostCallback;
import com.example.aidllibrary.V2.IHostInterfaceV2;
import com.example.testpluginapp.R;
import com.example.testpluginapp.utils.FastJsonUtils;
import com.example.testpluginapp.utils.LogUtils;
import com.qihoo360.replugin.RePlugin;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class PicturesActivity extends BaseActivity {
    private static final String TAG = "PicturesActivity";
    private LinearLayout llPictures;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pictures);
        llPictures = findViewById(R.id.ll_pictures);
        initPictures();
        setTitle("Glide使用");

        getHostBinder();
        getHostV2Binder();
    }

    private void initPictures() {
        //Glide.with(this).load(url).into(ivBelle);
        InputStream inputStream = null;
        try {
            inputStream = getAssets().open("pictures_url.json");
            byte[] buffers = new byte[4096];
            int len = -1;
            StringBuilder sb = new StringBuilder();
            while ((len = inputStream.read(buffers)) != -1) {
                String str = new String(buffers, 0, len);
                sb.append(str);
            }
            String data = sb.toString();
            List<String> pictures = FastJsonUtils.parseArray(data, String.class);
            for (String picture : pictures) {
                ImageView imageView = new ImageView(this);
                LinearLayout.LayoutParams params =
                        new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT);
                params.bottomMargin = 10;
                imageView.setLayoutParams(params);
                llPictures.addView(imageView);
                Glide.with(this).load(picture).into(imageView);
            }
        } catch (IOException e) {
            LogUtils.w(TAG, "IOException");
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                LogUtils.w(TAG, "close IOException");
            }
        }
    }

    private void getHostBinder() {
        // 获取宿主的AIDL binder
        IBinder binder = RePlugin.getGlobalBinder("IHost");
        if (binder != null) {
            IHostInterface hostInterface = IHostInterface.Stub.asInterface(binder);
            Params params = new Params();
            params.put("age", 18);
            try {
                hostInterface.call(params);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        } else {
            LogUtils.w(TAG, "binder is null");
        }
    }

    private void getHostV2Binder() {
        // 获取宿主的AIDL binder
        IBinder binder = RePlugin.getGlobalBinder("IHostV2");
        if (binder != null) {
            IHostInterfaceV2 hostInterface = IHostInterfaceV2.Stub.asInterface(binder);
            try {
                hostInterface.setHostCallback(new HostCallback.Stub() {
                    @Override
                    public void onLive(Params params) throws RemoteException {
                        LogUtils.d(TAG, " HostCallback params = ", params.toString());
                    }
                });
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        } else {
            LogUtils.w(TAG, "IHostV2 binder is null");
        }
    }
}
