package com.hm.iou.homedialog.demo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;

import com.hm.iou.base.BaseActivity;
import com.hm.iou.base.comm.ClipBoardBean;
import com.hm.iou.homedialog.business.HomeBorrowApplyActivity;
import com.hm.iou.homedialog.business.HomeBorrowCodeActivity;
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
            case R.id.btn_borrow_code: {
                ClipBoardBean clipBoardBean = new ClipBoardBean();
                clipBoardBean.setShearCode("07");
                clipBoardBean.setShearUrl("hmiou://m.54jietiao.com/ioucode/clipboard_detail?code=BR190911101311000297");
                ClipBoardBean.BorrowCodeInfo borrowCodeInfo = new ClipBoardBean.BorrowCodeInfo();
                borrowCodeInfo.title = "我有“闲置”资金";
                borrowCodeInfo.amount = "¥56800";
                borrowCodeInfo.nickName = "陈小毛";
                borrowCodeInfo.deadline = "60天";
                borrowCodeInfo.interest = "¥10";
                borrowCodeInfo.overdueInterestDesc = "未还金额的万分之6*逾期天数";
                Intent intent = new Intent(this, HomeBorrowCodeActivity.class);
                intent.putExtra(HomeBorrowCodeActivity.EXTRA_KEY_CLIPBOARD_INFO, clipBoardBean);
                intent.putExtra(HomeBorrowCodeActivity.EXTRA_KEY_BORROW_CODE_INFO, (Parcelable) borrowCodeInfo);
                startActivity(intent);
                break;
            }
            case R.id.btn_borrow_apply: {
                ClipBoardBean clipBoardBean = new ClipBoardBean();
                clipBoardBean.setShearCode("07");
                clipBoardBean.setShearUrl("hmiou://m.54jietiao.com/ioucode/clipboard_detail?code=BR190911101311000297");
                ClipBoardBean.BorrowCodeInfo borrowCodeInfo = new ClipBoardBean.BorrowCodeInfo();
                borrowCodeInfo.title = "我想“求借”资金";
                borrowCodeInfo.amount = "¥56800";
                borrowCodeInfo.nickName = "陈小毛";
                borrowCodeInfo.deadline = "2019.10.10-2019.12.22";
                borrowCodeInfo.interest = "¥100";
                borrowCodeInfo.overdueInterestDesc = "未还金额的万分之6";
                Intent intent = new Intent(this, HomeBorrowApplyActivity.class);
                intent.putExtra(HomeBorrowApplyActivity.EXTRA_KEY_CLIPBOARD_INFO, clipBoardBean);
                intent.putExtra(HomeBorrowApplyActivity.EXTRA_KEY_BORROW_CODE_INFO, (Parcelable) borrowCodeInfo);
                startActivity(intent);
                break;
            }
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
