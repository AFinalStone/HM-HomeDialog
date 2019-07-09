package com.hm.iou.homedialog.demo;

import android.os.Bundle;
import android.view.View;

import com.hm.iou.base.BaseActivity;
import com.hm.iou.homedialog.demo.business.IndexContract;
import com.hm.iou.homedialog.demo.business.IndexPresenter;
import com.hm.iou.logger.Logger;
import com.hm.iou.network.HttpReqManager;
import com.hm.iou.network.HttpRequestConfig;
import com.hm.iou.router.Router;
import com.hm.iou.tools.SystemUtil;
import com.orm.SugarContext;

public class MainActivity extends BaseActivity<IndexPresenter> implements IndexContract.View {


    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected IndexPresenter initPresenter() {
        return new IndexPresenter(this, this);
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
                mPresenter.checkVersion();
                break;
            case R.id.btn_getCommunique:
                mPresenter.getCommunique();
                break;
            case R.id.btn_clipboard:
                mPresenter.toAddFriendDialog();
                break;
            default:
        }
    }

    private void initNet() {
        Router.init(this);
        Logger.init(this, true);
        SugarContext.init(this);
        HttpRequestConfig config = new HttpRequestConfig.Builder(this)
                .setDebug(true)
                .setAppChannel("yyb")
                .setAppVersion("1.0.4")
                .setDeviceId("123abc123")
                .setBaseUrl("http://192.168.1.217")
                .build();
        HttpReqManager.init(config);
    }

}
