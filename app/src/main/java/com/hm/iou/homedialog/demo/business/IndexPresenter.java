package com.hm.iou.homedialog.demo.business;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.hm.iou.base.comm.ClipBoardBean;
import com.hm.iou.base.mvp.MvpActivityPresenter;
import com.hm.iou.base.utils.CommSubscriber;
import com.hm.iou.base.utils.RxUtil;
import com.hm.iou.homedialog.business.HomeAddFriendActivity;
import com.hm.iou.homedialog.demo.api.MainApi;
import com.hm.iou.homedialog.demo.bean.CommuniqueResBean;
import com.hm.iou.homedialog.demo.bean.HomeDialogResBean;
import com.hm.iou.router.Router;
import com.hm.iou.sharedata.model.BaseResponse;
import com.trello.rxlifecycle2.android.ActivityEvent;

/**
 * @author syl
 * @time 2018/5/19 下午4:54
 */
public class IndexPresenter extends MvpActivityPresenter<IndexContract.View> implements IndexContract.Presenter {

    public IndexPresenter(@NonNull Context context, @NonNull IndexContract.View view) {
        super(context, view);
    }

    private void getAdvertisement() {
        MainApi.getAdvertisement()
                .compose(getProvider().<BaseResponse<HomeDialogResBean>>bindUntilEvent(ActivityEvent.DESTROY))
                .map(RxUtil.<HomeDialogResBean>handleResponse())
                .subscribeWith(new CommSubscriber<HomeDialogResBean>(mView) {
                    @Override
                    public void handleResult(HomeDialogResBean dialogBean) {

                        if (dialogBean != null) {
                            Router.getInstance().buildWithUrl("hmiou://m.54jietiao.com/homedialog")
                                    .withString("dialog_type", dialogBean.getType() + "")
                                    .withString("dialog_title", dialogBean.getTitile() + "")
                                    .withString("dialog_content", dialogBean.getContent() + "")
                                    .withString("dialog_sub_content", dialogBean.getSubContent() + "")
                                    .withString("dialog_file_down_url", dialogBean.getAdUrl() + "")
                                    .withString("dialog_ad_image_url", dialogBean.getAdUrl() + "")
                                    .withString("dialog_ad_link_url", dialogBean.getAdLinkUrl() + "")
                                    .navigation(mContext);
                        }
                    }

                    @Override
                    public void handleException(Throwable throwable, String code, String msg) {
                    }

                    @Override
                    public boolean isShowBusinessError() {
                        return false;
                    }

                    @Override
                    public boolean isShowCommError() {
                        return false;
                    }
                });
    }

    @Override
    public void checkVersion() {
        MainApi.checkVersion()
                .compose(getProvider().<BaseResponse<HomeDialogResBean>>bindUntilEvent(ActivityEvent.DESTROY))
                .map(RxUtil.<HomeDialogResBean>handleResponse())
                .subscribeWith(new CommSubscriber<HomeDialogResBean>(mView) {
                    @Override
                    public void handleResult(HomeDialogResBean dialogBean) {
                        if (dialogBean == null || TextUtils.isEmpty(dialogBean.getType())) {
                            getAdvertisement();
                        } else {
                            Router.getInstance().buildWithUrl("hmiou://m.54jietiao.com/homedialog")
                                    .withString("dialog_type", dialogBean.getType() + "")
                                    .withString("dialog_title", dialogBean.getTitile() + "")
                                    .withString("dialog_content", dialogBean.getContent() + "")
                                    .withString("dialog_sub_content", dialogBean.getSubContent() + "")
                                    .withString("dialog_file_down_url", dialogBean.getAdUrl() + "")
                                    .withString("dialog_ad_image_url", dialogBean.getAdUrl() + "")
                                    .withString("dialog_ad_link_url", dialogBean.getAdLinkUrl() + "")
                                    .navigation(mContext);
                        }
                    }

                    @Override
                    public void handleException(Throwable throwable, String code, String msg) {
                    }

                    @Override
                    public boolean isShowBusinessError() {
                        return false;
                    }

                    @Override
                    public boolean isShowCommError() {
                        return false;
                    }
                });
    }

    @Override
    public void getCommunique() {
        MainApi.getCommunique()
                .compose(getProvider().<BaseResponse<CommuniqueResBean>>bindUntilEvent(ActivityEvent.DESTROY))
                .map(RxUtil.<CommuniqueResBean>handleResponse())
                .subscribeWith(new CommSubscriber<CommuniqueResBean>(mView) {
                    @Override
                    public void handleResult(CommuniqueResBean communiqueResBean) {
                        if (communiqueResBean != null) {
                            Router.getInstance().buildWithUrl("hmiou://m.54jietiao.com/homedialog")
                                    .withString("dialog_type", "100")
                                    .withString("dialog_content", communiqueResBean.getContent())
                                    .withString("notice_id", communiqueResBean.getNoticeId())
                                    .withString("notice_push_time", communiqueResBean.getPublishTime())
                                    .navigation(mContext);
                        }
                    }

                    @Override
                    public void handleException(Throwable throwable, String s, String s1) {

                    }
                });
    }

    @Override
    public void toAddFriendDialog() {
        ClipBoardBean clipBoardBean = new ClipBoardBean();
        clipBoardBean.setShearCode("04");
        clipBoardBean.setShearUrl("hmiou://m.54jietiao.com/message/firend_detail?userId=1099");
        ClipBoardBean.ExtInfo extInfo = new ClipBoardBean.ExtInfo();
        extInfo.setSex(1);
        extInfo.setAvatarUrl("https://iou-test.oss-cn-shanghai.aliyuncs.com/img/18/2019/01/201901291631333994.jpg?1548750688000");
        extInfo.setShowId("1000117");
        extInfo.setNickName("小管家");
        Intent intent = new Intent(mContext, HomeAddFriendActivity.class);
        intent.putExtra(HomeAddFriendActivity.EXTRA_KEY_CLIPBOARD_INFO, clipBoardBean);
        intent.putExtra(HomeAddFriendActivity.EXTRA_KEY_EXT_INFO, (Parcelable) extInfo);
        mContext.startActivity(intent);
    }
}
