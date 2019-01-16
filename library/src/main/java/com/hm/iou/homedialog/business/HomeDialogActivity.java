package com.hm.iou.homedialog.business;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Outline;
import android.os.Build;
import android.os.Bundle;
import android.os.Trace;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hm.iou.base.ActivityManager;
import com.hm.iou.base.BaseActivity;
import com.hm.iou.base.utils.RouterUtil;
import com.hm.iou.base.utils.TraceUtil;
import com.hm.iou.homedialog.R;
import com.hm.iou.homedialog.business.HomeDialogContract;
import com.hm.iou.homedialog.business.HomeDialogPresenter;
import com.hm.iou.homedialog.dict.DialogType;
import com.hm.iou.router.Router;
import com.hm.iou.tools.ImageLoader;
import com.hm.iou.tools.SystemUtil;
import com.hm.iou.uikit.dialog.HMAlertDialog;

public class HomeDialogActivity extends BaseActivity<HomeDialogPresenter> implements HomeDialogContract.View {

    public static final String EXTRA_KEY_ID = "dialog_id";
    public static final String EXTRA_KEY_DIALOG_TYPE = "dialog_type";
    public static final String EXTRA_KEY_DIALOG_TITLE = "dialog_title";
    public static final String EXTRA_KEY_DIALOG_CONTENT = "dialog_content";
    public static final String EXTRA_KEY_DIALOG_SUB_CONTENT = "dialog_sub_content";
    public static final String EXTRA_KEY_DIALOG_FILE_DOWN_URL = "dialog_file_down_url";
    public static final String EXTRA_KEY_DIALOG_AD_IMAGE_URL = "dialog_ad_image_url";
    public static final String EXTRA_KEY_DIALOG_AD_LINK_URL = "dialog_ad_link_url";
    public static final String EXTRA_KEY_DIALOG_COMMUNIQUE_NOTICE_ID = "notice_id";
    public static final String EXTRA_KEY_DIALOG_COMMUNIQUE_PUSH_TIME = "notice_push_time";

    private ViewStub mViewStubAdvertisement;

    private String mAutoId;
    private String mDialogType;    //对话框类型
    private String mDialogTitle;    //对话框标题
    private String mDialogContent;    //对话框内容
    private String mDialogSubContent;    //对话框二级内容
    private String mDialogFileDownUrl;    //APP下载地址
    private String mDialogAdImageUrl;              //广告图片
    private String mDialogAdLinkUrl;               //广告活动链接地址
    private String mNoticeNoticeId;      //官方公告ID
    private String mNoticePushTime;      //官方公告发布的时间

    @Override
    protected int getLayoutId() {
        return R.layout.homedialog_activity;
    }

    @Override
    protected HomeDialogPresenter initPresenter() {
        return new HomeDialogPresenter(this, this);
    }

    @Override
    protected void initEventAndData(Bundle bundle) {
        mViewStubAdvertisement = findViewById(R.id.viewStub_advertisement);
        mAutoId = getIntent().getStringExtra(EXTRA_KEY_ID);
        mDialogType = getIntent().getStringExtra(EXTRA_KEY_DIALOG_TYPE);
        mDialogTitle = getIntent().getStringExtra(EXTRA_KEY_DIALOG_TITLE);
        mDialogContent = getIntent().getStringExtra(EXTRA_KEY_DIALOG_CONTENT);
        mDialogSubContent = getIntent().getStringExtra(EXTRA_KEY_DIALOG_SUB_CONTENT);
        mDialogFileDownUrl = getIntent().getStringExtra(EXTRA_KEY_DIALOG_FILE_DOWN_URL);
        mDialogAdImageUrl = getIntent().getStringExtra(EXTRA_KEY_DIALOG_AD_IMAGE_URL);
        mDialogAdLinkUrl = getIntent().getStringExtra(EXTRA_KEY_DIALOG_AD_LINK_URL);
        mNoticeNoticeId = getIntent().getStringExtra(EXTRA_KEY_DIALOG_COMMUNIQUE_NOTICE_ID);
        mNoticePushTime = getIntent().getStringExtra(EXTRA_KEY_DIALOG_COMMUNIQUE_PUSH_TIME);
        if (bundle != null) {
            mAutoId = bundle.getString(EXTRA_KEY_ID);
            mDialogType = bundle.getString(EXTRA_KEY_DIALOG_TYPE);
            mDialogTitle = bundle.getString(EXTRA_KEY_DIALOG_TITLE);
            mDialogContent = bundle.getString(EXTRA_KEY_DIALOG_CONTENT);
            mDialogSubContent = bundle.getString(EXTRA_KEY_DIALOG_SUB_CONTENT);
            mDialogFileDownUrl = bundle.getString(EXTRA_KEY_DIALOG_FILE_DOWN_URL);
            mDialogAdImageUrl = bundle.getString(EXTRA_KEY_DIALOG_AD_IMAGE_URL);
            mDialogAdLinkUrl = bundle.getString(EXTRA_KEY_DIALOG_AD_LINK_URL);
            mNoticeNoticeId = bundle.getString(EXTRA_KEY_DIALOG_COMMUNIQUE_NOTICE_ID);
            mNoticePushTime = bundle.getString(EXTRA_KEY_DIALOG_COMMUNIQUE_PUSH_TIME);
        }
        mPresenter.init(mDialogType);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(EXTRA_KEY_ID, mAutoId);
        outState.putString(EXTRA_KEY_DIALOG_TYPE, mDialogType);
        outState.putString(EXTRA_KEY_DIALOG_TITLE, mDialogTitle);
        outState.putString(EXTRA_KEY_DIALOG_CONTENT, mDialogContent);
        outState.putString(EXTRA_KEY_DIALOG_SUB_CONTENT, mDialogSubContent);
        outState.putString(EXTRA_KEY_DIALOG_FILE_DOWN_URL, mDialogFileDownUrl);
        outState.putString(EXTRA_KEY_DIALOG_AD_IMAGE_URL, mDialogAdImageUrl);
        outState.putString(EXTRA_KEY_DIALOG_AD_LINK_URL, mDialogAdLinkUrl);
        outState.putString(EXTRA_KEY_DIALOG_COMMUNIQUE_NOTICE_ID, mNoticeNoticeId);
        outState.putString(EXTRA_KEY_DIALOG_COMMUNIQUE_PUSH_TIME, mNoticePushTime);
    }

    @Override
    public void onBackPressed() {
    }


    @Override
    public void showNoticeDialog() {
        Dialog dialog = new HMAlertDialog.Builder(this)
                .setTitle(R.string.homedialog_communique_title)
                .setMessage(mDialogContent)
                .setPositiveButton(R.string.homedialog_minimize)
                .setNegativeButton(R.string.homedialog_remove_msg_to_msg_center)
                .setOnClickListener(new HMAlertDialog.OnClickListener() {
                    @Override
                    public void onPosClick() {
                        finish();
                    }

                    @Override
                    public void onNegClick() {
                        mPresenter.insertNoticeToMsgCenter(mNoticeNoticeId, mNoticePushTime, mDialogContent);
                        finish();
                    }
                })
                .setCancelable(false)
                .setCanceledOnTouchOutside(false)
                .create();
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                finish();
            }
        });
        dialog.show();
    }

    @Override
    public void showOfficialMsgDialog() {
        Dialog dialog = new HMAlertDialog.Builder(this)
                .setTitle(mDialogTitle)
                .setMessage(mDialogContent)
                .setPositiveButton(R.string.homedialog_quit_account)
                .setOnClickListener(new HMAlertDialog.OnClickListener() {
                    @Override
                    public void onPosClick() {
                        ActivityManager.getInstance().exitAllActivities();
                    }

                    @Override
                    public void onNegClick() {
                    }
                })
                .setCancelable(false)
                .setCanceledOnTouchOutside(false)
                .setDismessedOnClickBtn(false)
                .create();
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                finish();
            }
        });
        dialog.show();
    }

    @Override
    public void showMustUpdateDialog() {
        //目前写死更新弹窗文案
        mDialogTitle = "新版本上线";
        mDialogContent = String.format("当前版本%s，为了给您带来更好的体验，强烈建议大家更新至最新版本，感谢配合～", SystemUtil.getCurrentAppVersionName(this));
        mDialogSubContent = "客服微信号：jietiaoguanjia2018";
        Dialog dialog = new HMAlertDialog.Builder(this)
                .setTitle(mDialogTitle)
                .setMessage(mDialogContent)
                .setPositiveButton(R.string.homedialog_update)
                .setOnClickListener(new HMAlertDialog.OnClickListener() {
                    @Override
                    public void onPosClick() {
                        TraceUtil.onEvent(mContext, "err_force_update_click");
                        mPresenter.toUpdateApp(mDialogFileDownUrl, "");
                    }

                    @Override
                    public void onNegClick() {

                    }
                })
                .setCancelable(false)
                .setCanceledOnTouchOutside(false)
                .setDismessedOnClickBtn(false)
                .create();
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                finish();
            }
        });
        dialog.show();
    }

    @Override
    public void showUpdateDialog() {
        mDialogTitle = "新版本上线";
        mDialogContent = String.format("当前版本%s，为了给您带来更好的体验，强烈建议大家更新至最新版本，感谢配合～", SystemUtil.getCurrentAppVersionName(this));
        mDialogSubContent = "客服微信号：jietiaoguanjia2018";

        Dialog dialog = new HMAlertDialog.Builder(this)
                .setTitle(mDialogTitle)
                .setMessage(mDialogContent)
                .setPositiveButton("立即更新")
                .setNegativeButton("以后再说")
                .setOnClickListener(new HMAlertDialog.OnClickListener() {
                    @Override
                    public void onPosClick() {
                        TraceUtil.onEvent(mContext, "err_remind_update_click");
                        mPresenter.toUpdateApp(mDialogFileDownUrl, "");
                    }

                    @Override
                    public void onNegClick() {
                        TraceUtil.onEvent(mContext, "err_update_next_click");
                    }
                })
                .setCancelable(false)
                .setCanceledOnTouchOutside(false)
                .create();
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                finish();
            }
        });
        dialog.show();
    }

    @Override
    public void showAdvertisementDialog() {
        View inflatedView = mViewStubAdvertisement.inflate();
        //初始化控件
        ImageView iVAdvertisement = inflatedView.findViewById(R.id.iv_advertisement);
        ImageView iVClose = inflatedView.findViewById(R.id.iv_close);
        ImageLoader.getInstance(mContext).displayImage(mDialogAdImageUrl, iVAdvertisement);
        iVAdvertisement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RouterUtil.clickMenuLink(HomeDialogActivity.this, mDialogAdLinkUrl);
                TraceUtil.onEvent(mContext, "err_alert_ad_click");
                mPresenter.confirmAdvertisement(mAutoId);
            }
        });
        iVClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TraceUtil.onEvent(mContext, "err_alert_ad_close_click");
                mPresenter.closeAdvertisement(mAutoId);
            }
        });
    }


    @Override
    public void showProgressDialog(final long currentProgress, final long maxProgress) {

    }

}
