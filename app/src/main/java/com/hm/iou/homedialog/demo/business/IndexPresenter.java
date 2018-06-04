package com.hm.iou.homedialog.demo.business;

import android.content.Context;
import android.support.annotation.NonNull;

import com.hm.iou.base.mvp.MvpActivityPresenter;
import com.hm.iou.base.utils.CommSubscriber;
import com.hm.iou.base.utils.RxUtil;
import com.hm.iou.homedialog.demo.NavigationHelper;
import com.hm.iou.homedialog.demo.api.MainApi;
import com.hm.iou.homedialog.demo.bean.HomeDialogBean;
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

    @Override
    public void getHomeDialog() {
        MainApi.getAllDialogType()
                .compose(getProvider().<BaseResponse<HomeDialogBean>>bindUntilEvent(ActivityEvent.DESTROY))
                .map(RxUtil.<HomeDialogBean>handleResponse())
                .subscribeWith(new CommSubscriber<HomeDialogBean>(mView) {
                    @Override
                    public void handleResult(HomeDialogBean dialogBean) {

                        if (dialogBean != null) {
                            NavigationHelper.toHomeDialog(mContext, dialogBean);
                            mView.startHomeDialogAnim();
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
}
