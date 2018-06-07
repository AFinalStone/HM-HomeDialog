package com.hm.iou.homedialog.api;

import com.hm.iou.network.HttpReqManager;
import com.hm.iou.sharedata.model.BaseResponse;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
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
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io());
    }

    /**
     * 关闭广告弹窗
     *
     * @return
     */
    public static Flowable<BaseResponse<Integer>> closeAdvertisement() {
        return getService().closeAdvertisement()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

}
