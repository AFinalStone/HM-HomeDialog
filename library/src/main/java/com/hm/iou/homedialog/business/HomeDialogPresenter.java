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

    /**
     * 是否已经下载过更新文件了
     **/
    public boolean haveDownApp(String path, String md5AppCurrent) {
        File file = new File(path);
        if (!file.exists()) {
            return false;
        }
        String md5AppHaveDown = Md5Util.getMd5ByFile(file).toLowerCase();
        if (TextUtils.isEmpty(md5AppHaveDown)) {
            return false;
        }
        if (!md5AppHaveDown.equals(md5AppCurrent)) {
            return false;
        }
        return true;
    }

    /**
     * 安装APP
     *
     * @param file
     */
    public void install(File file) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        //判断是否是AndroidN以及更高的版本,版本在7.0以上是不能直接通过uri访问的
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            // 由于没有在Activity环境下启动Activity,设置下面的标签
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //参数1 上下文, 参数2 Provider主机地址 和配置文件中保持一致   参数3  共享的文件
            Uri contentUri = FileProvider.getUriForFile(mContext, mFileProvider, file);
            //添加这一句表示对目标应用临时授权该Uri所代表的文件
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        mContext.startActivity(intent);
        ActivityManager.getInstance().exitAllActivities();
    }

    /**
     * 保存File
     *
     * @param response
     * @param destFileDir
     * @param destFileName
     * @return
     * @throws IOException
     */
    public File saveFile(ResponseBody response, String destFileDir, String destFileName) throws IOException {
        InputStream is = null;
        byte[] buf = new byte[2048];
        FileOutputStream fos = null;

        try {
            is = response.byteStream();
            final long total = response.contentLength();
            long currentSize = 0L;
            File dir = new File(destFileDir);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            File file = new File(dir, destFileName);
            fos = new FileOutputStream(file);

            int len1;
            while ((len1 = is.read(buf)) != -1) {
                currentSize += (long) len1;
                fos.write(buf, 0, len1);
                fos.flush();
                mView.showProgressDialog(currentSize, total);
            }
            File var11 = file;
            return var11;
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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
//        mUpdateAppBean.setVersionCode(3);
//        mUpdateAppBean.setFileMD5("234243");
//        mUpdateAppBean.setDownloadUrl("http://h5.54jietiao.com/update/android/app-release_v1.0.1.apk");
//        //获取安装包下载地址
//        String fileUrl = mUpdateAppBean.getDownloadUrl();
//        final String MD5 = mUpdateAppBean.getFileMD5();
//        //获取安装包名称    并获取下载SD卡位置
//        final String apkName = SystemUtil.getCurrentAppVersionName(mContext) + ".apk";
//        final String apkFilesPath = FileUtil.getCacheDirPath(mContext);
//        final String apkPath = apkFilesPath + System.getProperty("file.separator") + apkName;
//        if (haveDownApp(apkPath, MD5)) {
//            install(new File(apkPath));
//            return;
//        }
//

//        HomeDialogApi.downloadFile(fileUrl)
//                .map(new Function<ResponseBody, InputStream>() {
//                    @Override
//                    public InputStream apply(ResponseBody responseBody) throws Exception {
//                        InputStream inputStream = responseBody.byteStream();
//                        return inputStream;
//                    }
//                })
//                .observeOn(Schedulers.computation())
//                .doOnNext(new Consumer<InputStream>() {
//                    @Override
//                    public void accept(InputStream inputStream) throws Exception {
//                        writeFile(inputStream, apkPath);
//                    }
//                })
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeWith(new CommSubscriber<InputStream>(mView) {
//                    @Override
//                    public void handleResult(InputStream inputStream) {
//                        Logger.d("被执行");
//                    }
//
//                    @Override
//                    public void handleException(Throwable throwable, String s, String s1) {
//                        Logger.d("被执行"+throwable.getMessage());
//                    }
//                });
//                .subscribeWith(new CommSubscriber<File>(mView) {
//                    @Override
//                    public void handleResult(File file) {
//                        String md5AppHaveDown = Md5Util.getMd5ByFile(file).toLowerCase();
//                        Logger.d("MD5" + md5AppHaveDown);
//                        if (MD5.equals(md5AppHaveDown)) {
//                            install(file);
//                        } else {
//                            mView.toastMessage(R.string.homedialog_installation_package_maybe_damage);
//                        }
//                    }
//
//                    @Override
//                    public void handleException(Throwable throwable, String s, String s1) {
//                        throwable.printStackTrace();
//                    }
//                });

    }

    @Override
    public void insertNoticeToMsgCenter(String noticeId, String pushDate, String notice) {
        MsgCenterDbHelper.addOrUpdateNoticeToCache(noticeId, pushDate, notice);
        EventBus.getDefault().post(new CommBizEvent(EXTRA_KEY_MOVE_NOTICE_TO_MSG_CENTER, "官方公告成功插入到消息中心"));
    }

    @Override
    public void closeAdvertisement() {
        HomeDialogApi.closeAdvertisement()
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


//    /**
//     * 将输入流写入文件
//     *
//     * @param inputString
//     * @param filePath
//     */
//    private void writeFile(InputStream inputString, String filePath) {
//
//        File file = new File(filePath);
//        if (file.exists()) {
//            file.delete();
//        }
//
//        FileOutputStream fos = null;
//        try {
//            fos = new FileOutputStream(file);
//
//            byte[] b = new byte[1024];
//
//            int len;
//            while ((len = inputString.read(b)) != -1) {
//                fos.write(b, 0, len);
//            }
//            inputString.close();
//            fos.close();
//
//        } catch (FileNotFoundException e) {
//            mView.toastMessage("FileNotFoundException");
//        } catch (IOException e) {
//            mView.toastMessage("IOException");
//        }
//
//    }
}
