package com.hm.iou.homedialog.demo;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.hm.iou.homedialog.business.view.AdvertisementActivity;
import com.hm.iou.logger.Logger;
import com.hm.iou.network.HttpReqManager;
import com.hm.iou.network.HttpRequestConfig;
import com.hm.iou.tools.SystemUtil;

public class MainActivity extends AppCompatActivity {

//    private List<AdInfo> mListAdData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initNet();
        setContentView(R.layout.activity_main);
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_downFile:
                SystemUtil.openWebBrowser(this, "http://h5.54jietiao.com/update/android/app-release_v1.0.1.apk");
                break;
            case R.id.btn_showAdvertisement:
                startActivity(new Intent(this, AdvertisementActivity.class));
                overridePendingTransition(R.anim.uikit_activity_open_from_top, 0);
                break;
            default:
        }
    }

    private void initNet() {
        Logger.init(this, true);
        HttpRequestConfig config = new HttpRequestConfig.Builder(this)
                .setDebug(true)
                .setAppChannel("yyb")
                .setAppVersion("1.0.2")
                .setDeviceId("123abc123")
                .setBaseUrl("http://192.168.1.254")
                .build();
        HttpReqManager.init(config);
    }


}
