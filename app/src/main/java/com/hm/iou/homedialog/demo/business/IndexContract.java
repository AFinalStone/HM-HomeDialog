package com.hm.iou.homedialog.demo.business;


import com.hm.iou.base.mvp.BaseContract;

/**
 * Created by 2017年11月13日 18:10:25
 */
public interface IndexContract {

    interface View extends BaseContract.BaseView {
        /**
         * 开启弹窗广告
         */
        void startHomeDialogAnim();
    }

    interface Presenter extends BaseContract.BasePresenter {
        /**
         * 获取首页弹窗
         */
        void getHomeDialog();
    }
}
