package com.hm.iou.homedialog.business;

import com.hm.iou.base.mvp.BaseContract;

/**
 * @author : syl
 * @Date : 2018/5/29 10:29
 * @E-Mail : shiyaolei@dafy.com
 */
public class HomeDialogContract {

    /**
     * 通过邮箱找回密码
     **/
    public interface View extends BaseContract.BaseView {
        /**
         * 官方公告
         */
        void showNoticeDialog();

        /**
         * 显示官方私信
         */
        void showOfficialMsgDialog();

        /**
         * 显示重要升级,强制更新
         */
        void showMustUpdateDialog();

        /**
         * 发现新版本，提示用户进行更新
         */
        void showUpdateDialog();

        /**
         * 展示广告
         */
        void showAdvertisementDialog();

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
         * 初始化
         *
         * @param dialogJson 弹窗对象的json字符串
         */
        void init(String dialogJson);

        /**
         * 进行软件更新下载
         */
        void toUpdateApp(String fileUrl, String fileMD5);


        /**
         * 把官方公共移送到消息中心
         *
         * @param noticeId 公告id
         * @param pushDate 公告开始的时间
         * @param notice   公告内容
         */
        void insertNoticeToMsgCenter(String noticeId, String pushDate, String notice);


        /**
         * 关闭广告弹窗的时候调用
         */
        void closeAdvertisement();


    }
}
