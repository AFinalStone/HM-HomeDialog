package com.hm.iou.homedialog.business.view;

import android.os.Bundle;
import android.os.SystemClock;

import com.hm.iou.base.ActivityManager;
import com.hm.iou.base.BaseActivity;
import com.hm.iou.homedialog.R;
import com.hm.iou.homedialog.business.HomeDialogContract;
import com.hm.iou.homedialog.business.HomeDialogPresenter;
import com.hm.iou.router.Router;

public class HomeDialogActivity extends BaseActivity<HomeDialogPresenter> implements HomeDialogContract.View {

    public static final String EXTRA_KEY_DIALOG_TYPE = "dialog_type";
    public static final String EXTRA_KEY_DIALOG_TITLE = "dialog_title";
    public static final String EXTRA_KEY_DIALOG_CONTENT = "dialog_content";
    public static final String EXTRA_KEY_DIALOG_SUB_CONTENT = "dialog_sub_content";
    public static final String EXTRA_KEY_DIALOG_FILE_DOWN_URL = "dialog_file_down_url";
    public static final String EXTRA_KEY_DIALOG_AD_IMAGE_URL = "dialog_ad_image_url";
    public static final String EXTRA_KEY_DIALOG_AD_LINK_URL = "dialog_ad_link_url";

    //对话框类型
    private String mDialogType;
    //对话框标题
    private String mDialogTitle;
    //对话框内容
    private String mDialogContent;
    //对话框二级内容
    private String mDialogSubContent;
    //APP下载地址
    private String mDialogFileDownUrl;
    //广告图片
    private String mDialogAdImageUrl;
    //广告活动链接地址
    private String mDialogAdLinkUrl;

    @Override
    protected int getLayoutId() {
        return R.layout.homedialog_activity_advertisement;
    }

    @Override
    protected HomeDialogPresenter initPresenter() {
        return new HomeDialogPresenter(this, this);
    }

    @Override
    protected void initEventAndData(Bundle bundle) {
        mDialogType = getIntent().getStringExtra(EXTRA_KEY_DIALOG_TYPE);
        mDialogTitle = getIntent().getStringExtra(EXTRA_KEY_DIALOG_TITLE);
        mDialogContent = getIntent().getStringExtra(EXTRA_KEY_DIALOG_CONTENT);
        mDialogSubContent = getIntent().getStringExtra(EXTRA_KEY_DIALOG_SUB_CONTENT);
        mDialogFileDownUrl = getIntent().getStringExtra(EXTRA_KEY_DIALOG_FILE_DOWN_URL);
        mDialogAdImageUrl = getIntent().getStringExtra(EXTRA_KEY_DIALOG_AD_IMAGE_URL);
        mDialogAdLinkUrl = getIntent().getStringExtra(EXTRA_KEY_DIALOG_AD_LINK_URL);
        if (bundle != null) {
            mDialogType = bundle.getString(EXTRA_KEY_DIALOG_TYPE);
            mDialogTitle = bundle.getString(EXTRA_KEY_DIALOG_TITLE);
            mDialogContent = bundle.getString(EXTRA_KEY_DIALOG_CONTENT);
            mDialogSubContent = bundle.getString(EXTRA_KEY_DIALOG_SUB_CONTENT);
            mDialogFileDownUrl = bundle.getString(EXTRA_KEY_DIALOG_FILE_DOWN_URL);
            mDialogAdImageUrl = bundle.getString(EXTRA_KEY_DIALOG_AD_IMAGE_URL);
            mDialogAdLinkUrl = bundle.getString(EXTRA_KEY_DIALOG_AD_LINK_URL);
        }
        mPresenter.init(mDialogType);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(EXTRA_KEY_DIALOG_TYPE, mDialogType);
        outState.putString(EXTRA_KEY_DIALOG_TITLE, mDialogTitle);
        outState.putString(EXTRA_KEY_DIALOG_CONTENT, mDialogContent);
        outState.putString(EXTRA_KEY_DIALOG_SUB_CONTENT, mDialogSubContent);
        outState.putString(EXTRA_KEY_DIALOG_FILE_DOWN_URL, mDialogFileDownUrl);
        outState.putString(EXTRA_KEY_DIALOG_AD_IMAGE_URL, mDialogAdImageUrl);
        outState.putString(EXTRA_KEY_DIALOG_AD_LINK_URL, mDialogAdLinkUrl);
    }

    private void toUpdateApp() {
        mPresenter.toUpdateApp(mDialogFileDownUrl, "");
    }

    @Override
    public void showOfficialMsgDialog() {
        new DialogUpdate.Builder(mContext)
                .setTitle(mDialogTitle)
                .setColorBg(getResources().getColor(R.color.homedialog_color_office_msg_bg))
                .setContent(mDialogContent)
                .setSubContent(mDialogSubContent)
                .setCancelable(false)
                .setNegativeButton(getString(R.string.homedialog_quit_account), new DialogUpdate.OnClickListener() {
                    @Override
                    public void onClick() {
                        ActivityManager.getInstance().exitAllActivities();
                    }
                })
                .create()
                .show();
    }

    @Override
    public void showMustUpdateDialog() {
        new DialogUpdate.Builder(mContext)
                .setTitle(mDialogTitle)
                .setColorBg(getResources().getColor(R.color.homedialog_color_must_update_bg))
                .setContent(mDialogContent)
                .setSubContent(mDialogSubContent)
                .setCancelable(false)
                .setNegativeButton(getString(R.string.homedialog_update), new DialogUpdate.OnClickListener() {
                    @Override
                    public void onClick() {
                        toUpdateApp();
                    }
                })
                .create()
                .show();
    }

    @Override
    public void showUpdateDialog() {
        new DialogUpdate.Builder(mContext)
                .setTitle(mDialogTitle)
                .setColorBg(getResources().getColor(R.color.homedialog_color_update_bg))
                .setContent(mDialogContent)
                .setSubContent(mDialogSubContent)
                .setCancelable(false)
                .setNegativeButton(getString(R.string.homedialog_remainder_next), new DialogUpdate.OnClickListener() {
                    @Override
                    public void onClick() {
                        finish();
                        overridePendingTransition(0, R.anim.uikit_activity_to_top);
                    }
                })
                .setPositiveButton(getString(R.string.homedialog_update), new DialogUpdate.OnClickListener() {
                    @Override
                    public void onClick() {
                        toUpdateApp();
                    }
                })
                .create()
                .show();
    }

    @Override
    public void showAdvertisementDialog() {
        new DialogAdvertisement.Builder(mContext)
                .setAdImageUrl(mDialogAdImageUrl)
                .setCancelable(false)
                .setOnClickListener(new DialogAdvertisement.OnClickListener() {
                    @Override
                    public void onAdvertisementClick() {
                        Router.getInstance().buildWithUrl("hmiou://m.54jietiao.com/webview/index")
                                .withString("url", mDialogAdLinkUrl)
                                .navigation(mContext);
                        finish();
                    }

                    @Override
                    public void onCloseClick() {
                        finish();
                        overridePendingTransition(0, R.anim.uikit_activity_to_top);
                    }
                })
                .create()
                .show();
    }

    @Override
    public void showProgressDialog(final long currentProgress, final long maxProgress) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

            }
        });
    }
}
