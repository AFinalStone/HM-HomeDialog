package com.hm.iou.homedialog.business.view;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.hm.iou.homedialog.R;
import com.hm.iou.tools.ImageLoader;


/**
 * Created by syl on 2017/11/28.
 * <p>
 */

public class DialogAdvertisement extends Dialog {

    private DialogAdvertisement(@NonNull Context context) {
        super(context);
    }

    private DialogAdvertisement(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
    }

    private DialogAdvertisement(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }


    public static class Builder {

        private Context mContext;
        private String mAdImageUrl;
        private OnClickListener mOnClickListener;
        private boolean mFlagCancelable;

        public Builder(Context mContext) {
            this.mContext = mContext;
        }

        public Builder setAdImageUrl(String adImageUrl) {
            this.mAdImageUrl = adImageUrl;
            return this;
        }

        public Builder setOnClickListener(OnClickListener mOnClickListener) {
            this.mOnClickListener = mOnClickListener;
            return this;
        }


        public Builder setCancelable(boolean flagCancelable) {
            this.mFlagCancelable = flagCancelable;
            return this;
        }

        /**
         * 创建dialog
         *
         * @return
         */
        public DialogAdvertisement create() {

            final DialogAdvertisement dialogUpdate = new DialogAdvertisement(mContext, R.style.UikitAlertDialogStyle);
            // 调整dialog背景大小
            Window dialogWindow = dialogUpdate.getWindow();
            dialogWindow.setGravity(Gravity.CENTER);
            // 获取Dialog布局
            final View contentView = LayoutInflater.from(mContext).inflate(R.layout.homedialog_dialog_advertisement, null);
            //初始化控件
            ImageView ivAdvertisement = contentView.findViewById(R.id.iv_advertisement);
            ImageView ivClose = contentView.findViewById(R.id.iv_close);
            ImageLoader.getInstance(mContext).displayImage(mAdImageUrl, ivAdvertisement,
                    R.drawable.uikit_bg_pic_loading_place, R.drawable.uikit_bg_pic_loading_place);
            ivAdvertisement.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnClickListener != null) {
                        mOnClickListener.onAdvertisementClick();
                    }
                }
            });
            ivClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mOnClickListener != null) {
                        mOnClickListener.onCloseClick();
                    }
                }
            });
            dialogUpdate.setCancelable(mFlagCancelable);
            dialogUpdate.setContentView(contentView);
            // 调整dialog背景大小
            WindowManager windowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
            Display display = windowManager.getDefaultDisplay();
            int width = display.getWidth();
            int height = display.getHeight();
            contentView.setLayoutParams(new FrameLayout.LayoutParams(width, height));
            return dialogUpdate;
        }

    }

    public interface OnClickListener {
        /**
         * 广告被点击
         */
        void onAdvertisementClick();

        /**
         * 关闭按钮被点击
         */
        void onCloseClick();

    }
}
