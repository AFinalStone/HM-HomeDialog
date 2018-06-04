package com.hm.iou.homedialog.demo.api;

import com.hm.iou.homedialog.demo.bean.HomeDialogBean;
import com.hm.iou.network.HttpReqManager;
import com.hm.iou.sharedata.model.BaseResponse;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

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
     * 获取所有需要弹出的对话框类型
     *
     * @return
     */
    public static Flowable<BaseResponse<HomeDialogBean>> getAllDialogType() {
        return getService().getAllTypeDialog(new Object()).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

}
