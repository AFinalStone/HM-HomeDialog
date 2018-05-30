package com.hm.iou.homedialog.demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.hm.iou.base.BaseActivity;
import com.hm.iou.base.mvp.MvpActivityPresenter;
import com.hm.iou.homedialog.business.view.HomeDialogActivity;
import com.hm.iou.logger.Logger;
import com.hm.iou.network.HttpReqManager;
import com.hm.iou.network.HttpRequestConfig;
import com.hm.iou.tools.SystemUtil;

public class MainActivity<T extends MvpActivityPresenter> extends BaseActivity<T> {


    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected T initPresenter() {
        return null;
    }

    @Override
    protected void initEventAndData(Bundle bundle) {
        initNet();
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_downFile:
                SystemUtil.openWebBrowser(this, "http://h5.54jietiao.com/update/android/app-release_v1.0.1.apk");
                break;
            case R.id.btn_showAdvertisement:
                startActivity(new Intent(this, HomeDialogActivity.class));
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
