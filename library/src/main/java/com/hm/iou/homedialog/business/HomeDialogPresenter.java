package com.hm.iou.homedialog.business;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;

import com.hm.iou.base.ActivityManager;
import com.hm.iou.base.mvp.MvpActivityPresenter;
import com.hm.iou.base.utils.CommSubscriber;
import com.hm.iou.base.utils.RxUtil;
import com.hm.iou.database.MsgCenterDbHelper;
import com.hm.iou.homedialog.api.HomeDialogApi;
import com.hm.iou.homedialog.dict.DialogType;
import com.hm.iou.sharedata.event.CommBizEvent;
import com.hm.iou.sharedata.model.BaseResponse;
import com.hm.iou.tools.Md5Util;
import com.hm.iou.tools.SystemUtil;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import io.reactivex.functions.Consumer;
import okhttp3.ResponseBody;

/**
 * @author syl
 * @time 2018/5/19 下午4:54
 */
public class HomeDialogPresenter extends MvpActivityPresenter<HomeDialogContract.View> implements HomeDialogContract.Present {

    private String mFileProvider;
    public static final String EXTRA_KEY_MOVE_NOTICE_TO_MSG_CENTER = "HomeDialog_moveNoticeToMsgCenter";

    public HomeDialogPresenter(@NonNull Context context, @NonNull HomeDialogContract.View view) {
        super(context, view);
        mFileProvider = SystemUtil.getCurrentAppPackageName(mContext);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void init(String dialogType) {
        if (DialogType.Notice.getValue().equals(dialogType)) {
            mView.showNoticeDialog();
            return;
        }
        if (DialogType.OfficeMsg.getValue().equals(dialogType)) {
            mView.showOfficialMsgDialog();
            return;
        }
        if (DialogType.MustUpdate.getValue().equals(dialogType)) {
            mView.showMustUpdateDialog();
            return;
        }
        if (DialogType.Update.getValue().equals(dialogType)) {
            mView.showUpdateDialog();
            return;
        }
        if (DialogType.AdvertisementMoney.getValue().equals(dialogType)) {
            mView.showAdvertisementDialog();
            return;
        }
        if (DialogType.AdvertisementOther.getValue().equals(dialogType)) {
            mView.showAdvertisementDialog();
            return;
        }
        mView.closeCurrPage();
    }

    @Override
    public void toUpdateApp(String fileUrl, String fileMD5) {
        SystemUtil.openWebBrowser(mContext, fileUrl);
    }

    @Override
    public void insertNoticeToMsgCenter(String noticeId, String pushDate, String notice) {
        MsgCenterDbHelper.addOrUpdateNoticeToCache(noticeId, pushDate, notice);
        EventBus.getDefault().post(new CommBizEvent(EXTRA_KEY_MOVE_NOTICE_TO_MSG_CENTER, "官方公告成功插入到消息中心"));
    }

    @Override
    public void closeAdvertisement(String autoId) {
        long id = 0;
        try {
            id = Long.valueOf(autoId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        HomeDialogApi.closeAlertShow(id)
                .subscribe(new Consumer<BaseResponse<Integer>>() {
                    @Override
                    public void accept(BaseResponse<Integer> integerBaseResponse) throws Exception {

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });
        mView.closeCurrPage();
    }

    @Override
    public void confirmAdvertisement(String autoId) {
        long id = 0;
        try {
            id = Long.valueOf(autoId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        HomeDialogApi.confirmAlertShow(id)
                .subscribe(new Consumer<BaseResponse<Integer>>() {
                    @Override
                    public void accept(BaseResponse<Integer> integerBaseResponse) throws Exception {

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });
        mView.closeCurrPage();
    }
}