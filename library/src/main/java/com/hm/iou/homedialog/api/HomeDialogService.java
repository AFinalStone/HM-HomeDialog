package com.hm.iou.homedialog.api;

import com.hm.iou.sharedata.model.BaseResponse;

import io.reactivex.Flowable;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * @author : syl
 * @Date : 2018/5/29 10:33
 * @E-Mail : shiyaolei@dafy.com
 */
public interface HomeDialogService {

    @Streaming
    @GET
    Flowable<ResponseBody> downloadFile(@Url String url);

    @POST("/api/iou/user/v1/closeAlertShow")
    Flowable<BaseResponse<Integer>> closeAlertShow(@Body IdReqBean reqBean);

    @POST("/api/iou/user/v1/confirmAlertShow")
    Flowable<BaseResponse<Integer>> confirmAlertShow(@Body IdReqBean reqBean);
}
