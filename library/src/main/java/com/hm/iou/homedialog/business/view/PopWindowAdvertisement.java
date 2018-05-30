package com.hm.iou.homedialog.business.view;


import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.hm.iou.homedialog.R;
import com.hm.iou.tools.ImageLoader;


/**
 * 广告
 * SHI
 * 2016-12-26 18:32:01
 */
public class PopWindowAdvertisement extends PopupWindow {

    private PopWindowAdvertisement(Context context) {
        super(context);
    }

    private PopWindowAdvertisement(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private PopWindowAdvertisement(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private PopWindowAdvertisement(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private PopWindowAdvertisement() {
    }

    private PopWindowAdvertisement(View contentView) {
        super(contentView);
    }

    private PopWindowAdvertisement(int width, int height) {
        super(width, height);
    }

    private PopWindowAdvertisement(View contentView, int width, int height) {
        super(contentView, width, height);
    }

    private PopWindowAdvertisement(View contentView, int width, int height, boolean focusable) {
        super(contentView, width, height, focusable);
    }

    public static class Builder {
        private Context mContext;
        private String mImageUrl;
        private OnClickListener mOnClickListener;

        public Builder(Context context) {
            this.mContext = context;
        }

        public Builder setImageUrl(String imageUrl) {
            this.mImageUrl = imageUrl;
            return this;
        }

        public Builder setOnClickListener(OnClickListener onClickListener) {
            this.mOnClickListener = onClickListener;
            return this;
        }

        public void show() {


            View contentView = LayoutInflater.from(mContext).inflate(R.layout.homedialog_pop_window, null);
            PopWindowAdvertisement mPopWindow = new PopWindowAdvertisement(contentView,
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);
            mPopWindow.setContentView(contentView);

            //初始化控件
            View viewTop = contentView.findViewById(R.id.view_top);
            ImageView ivAdvertisement = contentView.findViewById(R.id.iv_advertisement);
            ImageView ivClose = contentView.findViewById(R.id.iv_close);
            ImageLoader.getInstance(mContext).displayImage(mImageUrl, ivAdvertisement);
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
            //获取设备的高度，动态设置广告的高度和宽度
            int displayHeight = mContext.getResources().getDisplayMetrics().heightPixels;
            RelativeLayout.LayoutParams lpViewTop = (RelativeLayout.LayoutParams) viewTop.getLayoutParams();
            lpViewTop.height = displayHeight / 4;
            viewTop.setLayoutParams(lpViewTop);

            RelativeLayout.LayoutParams lpIvAdvertisement = (RelativeLayout.LayoutParams) ivAdvertisement.getLayoutParams();
            lpIvAdvertisement.height = displayHeight / 2;
            lpIvAdvertisement.width = lpIvAdvertisement.height * 227 / 343;
            ivAdvertisement.setLayoutParams(lpIvAdvertisement);
            // 设置SelectPicPopupWindow的View
            mPopWindow.setContentView(contentView);
            // 设置SelectPicPopupWindow弹出窗体可点击
            mPopWindow.setFocusable(false);
            // 实例化一个ColorDrawable颜色为半透明
            ColorDrawable dw = new ColorDrawable(0x80000000);
            // 设置SelectPicPopupWindow弹出窗体的背景
            mPopWindow.setBackgroundDrawable(dw);
            //显示PopupWindow
            View rootView = LayoutInflater.from(mContext).inflate(R.layout.homedialog_activity_advertisement, null);
            mPopWindow.showAsDropDown(rootView, Gravity.CENTER, 0, 0);
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
