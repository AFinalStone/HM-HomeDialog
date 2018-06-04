package com.hm.iou.homedialog.api;

import com.hm.iou.network.HttpReqManager;

import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

/**
 * @author : syl
 * @Date : 2018/5/29 10:32
 * @E-Mail : shiyaolei@dafy.com
 */
public class HomeDialogApi {

    private static HomeDialogService getService() {
        return HttpReqManager.getInstance().getService(HomeDialogService.class);
    }

    /**
     * 下载文件
     *
     * @param fileUrl
     * @return
     */
    public static Flowable<ResponseBody> downloadFile(String fileUrl) {
        return getService().downloadFile(fileUrl)
                .subscribeOn(Schedulers.io())//subscribeOn和ObserOn必须在io线程，如果在主线程会出错
                .unsubscribeOn(Schedulers.io());
    }

}
