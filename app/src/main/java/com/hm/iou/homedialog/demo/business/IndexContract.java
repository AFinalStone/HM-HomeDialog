package com.hm.iou.homedialog.demo.business;


import com.hm.iou.base.mvp.BaseContract;

/**
 * Created by 2017年11月13日 18:10:25
 */
public interface IndexContract {

    interface View extends BaseContract.BaseView {

        /**
         * 开启首页弹窗动画
         */
        void startHomeDialogAnim();
    }

    interface Presenter extends BaseContract.BasePresenter {

        /**
         * 校验是否需要升级
         */
        void checkVersion();

        /**
         * 获取官方公告
         */
        void getCommunique();
    }
}
