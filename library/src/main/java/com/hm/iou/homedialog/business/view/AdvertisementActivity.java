package com.hm.iou.homedialog.business.view;

import android.Manifest;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;

import com.hm.iou.base.ActivityManager;
import com.hm.iou.base.BaseActivity;
import com.hm.iou.base.utils.PermissionUtil;
import com.hm.iou.homedialog.R;
import com.hm.iou.homedialog.business.AdvertisementContract;
import com.hm.iou.homedialog.business.AdvertisementPresenter;
import com.hm.iou.router.Router;
import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.functions.Consumer;

public class AdvertisementActivity extends BaseActivity<AdvertisementPresenter> implements AdvertisementContract.View {

    private ProgressDialog mProgressDialog;

    @Override
    protected int getLayoutId() {
        return R.layout.homedialog_activity_advertisement;
    }

    @Override
    protected AdvertisementPresenter initPresenter() {
        return new AdvertisementPresenter(this, this);
    }

    @Override
    protected void initEventAndData(Bundle bundle) {
        findViewById(R.id.frameLayout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
//        mPresenter.getAllTypeDialog();
        toUpdateApp();
    }


    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, R.anim.uikit_activity_to_top);
    }


    private void toUpdateApp() {
        RxPermissions rxPermissions = new RxPermissions(this);
        rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                if (aBoolean) {
                    mPresenter.toUpdateApp();
                } else {
                    PermissionUtil.showStoragePermissionDialog(mContext, new PermissionUtil.OnPermissionDialogClick() {
                        @Override
                        public void onPositiveBtnClick() {

                        }

                        @Override
                        public void onNegativeBtnClick() {
                            toastMessage(R.string.homedialog_installation_permission_refuse);
                            finish();
                        }
                    });
                }
            }
        });
    }

    @Override
    public void showOfficialMsgDialog(String title, String context, String subContext) {
        new DialogUpdate.Builder(mContext)
                .setTitle(title)
                .setColorBg(getResources().getColor(R.color.homedialog_color_office_msg_bg))
                .setContent(context)
                .setSubContent(subContext)
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
    public void showMustUpdateDialog(String title, String context, String subContext) {
        new DialogUpdate.Builder(mContext)
                .setTitle(title)
                .setColorBg(getResources().getColor(R.color.homedialog_color_must_update_bg))
                .setContent(context)
                .setSubContent(subContext)
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
    public void showUpdateDialog(String title, String context, String subContext) {
        new DialogUpdate.Builder(mContext)
                .setTitle(title)
                .setColorBg(getResources().getColor(R.color.homedialog_color_update_bg))
                .setContent(context)
                .setSubContent(subContext)
                .setCancelable(false)
                .setNegativeButton(getString(R.string.homedialog_remainder_next), new DialogUpdate.OnClickListener() {
                    @Override
                    public void onClick() {
                        finish();
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
    public void showAdvertisementDialog(String imageUrl, final String advertisementUrl) {
        new PopWindowAdvertisement
                .Builder(mContext)
                .setImageUrl(imageUrl)
                .setOnClickListener(new PopWindowAdvertisement.OnClickListener() {
                    @Override
                    public void onAdvertisementClick() {
                        Router.getInstance().buildWithUrl("hmiou://m.54jietiao.com/webview/index?title=*&url=*&showtitle=*&showdivider=*&showtitlebar=*")
                                .withString("url", advertisementUrl)
                                .navigation(mContext);
                    }

                    @Override
                    public void onCloseClick() {
                        finish();
                    }

                }).show();
    }

    @Override
    public void showProgressDialog(final long currentProgress, final long maxProgress) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mProgressDialog == null) {
                    mProgressDialog = new ProgressDialog(mContext);
                    mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                    mProgressDialog.setMessage("下载中...");
                    mProgressDialog.setIndeterminate(false);
                    mProgressDialog.setCancelable(false);
                    mProgressDialog.show();
                    return;
                }
                if (currentProgress != maxProgress) {
                    mProgressDialog.setProgress((int) (currentProgress * 100 / maxProgress));
                    return;
                }
                mProgressDialog.dismiss();
            }
        });
    }
}
