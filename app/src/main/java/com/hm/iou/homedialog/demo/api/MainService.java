package com.hm.iou.homedialog.demo.api;


import io.reactivex.Flowable;
import retrofit2.http.GET;

import com.hm.iou.homedialog.demo.bean.CommuniqueResBean;
import com.hm.iou.homedialog.demo.bean.HomeDialogResBean;
import com.hm.iou.sharedata.model.BaseResponse;

/**
 * @author : syl
 * @Date : 2018/5/29 10:33
 * @E-Mail : shiyaolei@dafy.com
 */
public interface MainService {

    @GET("/api/iou/user/v1/selectAlertShowByType")
    Flowable<BaseResponse<HomeDialogResBean>> getAdvertisement();

    @GET("/api/iou/user/v1/checkVersion")
    Flowable<BaseResponse<HomeDialogResBean>> checkVersion();

    @GET("/api/iou/notice/v1/selectNotice")
    Flowable<BaseResponse<CommuniqueResBean>> getCommunique();

}
