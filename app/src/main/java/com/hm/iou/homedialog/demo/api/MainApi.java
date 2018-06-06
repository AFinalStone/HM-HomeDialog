package com.hm.iou.homedialog.demo.api;

import com.hm.iou.homedialog.demo.bean.CommuniqueResBean;
import com.hm.iou.network.HttpReqManager;
import com.hm.iou.sharedata.model.BaseResponse;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import com.hm.iou.homedialog.demo.bean.HomeDialogResBean;

/**
 * @author : syl
 * @Date : 2018/5/29 10:32
 * @E-Mail : shiyaolei@dafy.com
 */
public class MainApi {

    private static MainService getService() {
        return HttpReqManager.getInstance().getService(MainService.class);
    }

    /**
     * 获取首页所有需要弹出的对话框
     *
     * @return
     */
    public static Flowable<BaseResponse<HomeDialogResBean>> getAdvertisement() {
        return getService().getAdvertisement().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 检验是否需要更新
     *
     * @return
     */
    public static Flowable<BaseResponse<HomeDialogResBean>> checkVersion() {
        return getService().checkVersion().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 获取官方公告
     *
     * @return
     */
    public static Flowable<BaseResponse<CommuniqueResBean>> getCommunique() {
        return getService().getCommunique().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

}
