package com.hm.iou.homedialog.business;

import com.hm.iou.base.mvp.BaseContract;

/**
 * @author : syl
 * @Date : 2018/5/29 10:29
 * @E-Mail : shiyaolei@dafy.com
 */
public class AdvertisementContract {

    /**
     * 通过邮箱找回密码
     **/
    public interface View extends BaseContract.BaseView {

        /**
         * 显示官方私信
         *
         * @param title
         * @param context
         * @param subContext
         */
        void showOfficialMsgDialog(String title, String context, String subContext);

        /**
         * 显示重要升级,强制更新
         *
         * @param title
         * @param context
         * @param subContext
         */
        void showMustUpdateDialog(String title, String context, String subContext);

        /**
         * 发现新版本，提示用户进行更新
         *
         * @param title
         * @param context
         * @param subContext
         */
        void showUpdateDialog(String title, String context, String subContext);

        /**
         * 展示广告
         *
         * @param imageUrl         图片地址
         * @param advertisementUrl 活动地址链接
         */
        void showAdvertisementDialog(String imageUrl, String advertisementUrl);

        /**
         * 显示下载进度
         *
         * @param currentProgress 当前进度
         * @param maxProgress     最大进度
         */
        void showProgressDialog(long currentProgress, long maxProgress);
    }

    public interface Present extends BaseContract.BasePresenter {

        /**
         * 获取所有类型的弹窗
         */
        void getAllTypeDialog();

        /**
         * 进行软件更新下载
         */
        void toUpdateApp();
    }
}
