package com.hm.iou.homedialog.demo.api;


import com.hm.iou.homedialog.demo.bean.HomeDialogBean;
import com.hm.iou.sharedata.model.BaseResponse;

import io.reactivex.Flowable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * @author : syl
 * @Date : 2018/5/29 10:33
 * @E-Mail : shiyaolei@dafy.com
 */
public interface MainService {

    @POST("/api/iou/user/v1/selectAlertShowByType")
    Flowable<BaseResponse<HomeDialogBean>> getAllTypeDialog(@Body Object bean);

}
