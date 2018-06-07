package com.hm.iou.homedialog.api;

import com.hm.iou.sharedata.model.BaseResponse;

import io.reactivex.Flowable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
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

    @GET("/api/iou/user/v1/closeAlertShow")
    Flowable<BaseResponse<Integer>> closeAdvertisement();

}
