package com.hm.iou.homedialog.demo;

import android.content.Context;

import com.hm.iou.homedialog.demo.bean.HomeDialogBean;
import com.hm.iou.router.Router;

/**
 * @author syl
 * @time 2018/5/19 下午2:59
 */
public class NavigationHelper {

    /**
     * 跳转到首页弹窗
     *
     * @param context
     */
    public static void toHomeDialog(Context context, HomeDialogBean dialogBean) {
        Router.getInstance().buildWithUrl("hmiou://m.54jietiao.com/homedialog")
                .withString("dialog_type", dialogBean.getType() + "")
                .withString("dialog_title", dialogBean.getTitile() + "")
                .withString("dialog_content", dialogBean.getContent() + "")
                .withString("dialog_sub_content", dialogBean.getSubContent() + "")
                .withString("dialog_file_down_url", dialogBean.getAdUrl() + "")
                .withString("dialog_ad_image_url", dialogBean.getAdUrl() + "")
                .withString("dialog_ad_link_url", dialogBean.getAdLinkUrl() + "")
                .navigation(context);
    }

}
