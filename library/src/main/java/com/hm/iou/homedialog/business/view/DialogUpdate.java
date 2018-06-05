package com.hm.iou.homedialog.business.view;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hm.iou.homedialog.R;


/**
 * Created by syl on 2017/11/28.
 * <p>
 */

public class DialogUpdate extends Dialog {

    private DialogUpdate(@NonNull Context context) {
        super(context);
    }

    private DialogUpdate(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
    }

    private DialogUpdate(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }


    public static class Builder {

        private Context mContext;
        private String mTitleText;
        private String mContentText;
        private String mSubContentText;
        private String mNegativeButtonText;
        private String mPositiveButtonText;
        private OnClickListener mNegativeButtonClickListener;
        private OnClickListener mPositiveButtonClickListener;
        private boolean mFlagCancelable = true;

        public Builder(Context context) {
            this.mContext = context;
        }

        /**
         * 标题
         *
         * @param titleText
         * @return
         */
        public Builder setTitle(String titleText) {
            this.mTitleText = titleText;
            return this;
        }

        /**
         * 内容
         *
         * @param contentText
         * @return
         */
        public Builder setContent(String contentText) {
            this.mContentText = contentText;
            return this;
        }

        /**
         * 二级内容
         *
         * @param subContentText
         * @return
         */
        public Builder setSubContent(String subContentText) {
            this.mSubContentText = subContentText;
            return this;
        }

        /**
         * @param negativeButtonText
         * @param negativeButtonClickListener
         * @return
         */
        public Builder setNegativeButton(String negativeButtonText, OnClickListener negativeButtonClickListener) {
            this.mNegativeButtonText = negativeButtonText;
            this.mNegativeButtonClickListener = negativeButtonClickListener;
            return this;
        }


        /**
         * @param positiveButtonText
         * @param positiveButtonClickListener
         * @return
         */
        public Builder setPositiveButton(String positiveButtonText, OnClickListener positiveButtonClickListener) {
            this.mPositiveButtonText = positiveButtonText;
            this.mPositiveButtonClickListener = positiveButtonClickListener;
            return this;
        }


        public Builder setCancelable(boolean mFlagCancelable) {
            this.mFlagCancelable = mFlagCancelable;
            return this;
        }


        /**
         * 创建dialog
         *
         * @return
         */
        public DialogUpdate create() {

            final DialogUpdate dialogUpdate = new DialogUpdate(mContext, R.style.UikitAlertDialogStyle);
            // 调整dialog背景大小
            Window dialogWindow = dialogUpdate.getWindow();
            dialogWindow.setGravity(Gravity.CENTER);
            // 获取Dialog布局
            final View contentView = LayoutInflater.from(mContext).inflate(R.layout.homedialog_dialog_update, null);
            TextView tvTitle = contentView.findViewById(R.id.tv_title);
            TextView tvContent = contentView.findViewById(R.id.tv_content);
            TextView tvSubContent = contentView.findViewById(R.id.tv_subContent);

            if (!TextUtils.isEmpty(mTitleText)) {
                tvTitle.setText(mTitleText);
            }

            if (!TextUtils.isEmpty(mContentText)) {
                tvContent.setText(mContentText);
            }
            if (!TextUtils.isEmpty(mSubContentText)) {
                tvSubContent.setVisibility(View.VISIBLE);
                tvSubContent.setText(mSubContentText);
            }

            if (mNegativeButtonClickListener != null) {
                Button btnNegative = contentView.findViewById(R.id.btn_negative);
                btnNegative.setVisibility(View.VISIBLE);
                btnNegative.setText(mNegativeButtonText);
                btnNegative.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mNegativeButtonClickListener != null) {
                            mNegativeButtonClickListener.onClick();
                            dialogUpdate.dismiss();
                        }
                    }
                });
            }
            if (mPositiveButtonClickListener != null) {
                Button btnPositive = contentView.findViewById(R.id.btn_positive);
                btnPositive.setVisibility(View.VISIBLE);
                btnPositive.setText(mPositiveButtonText);
                btnPositive.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mPositiveButtonClickListener != null) {
                            mPositiveButtonClickListener.onClick();
                            dialogUpdate.dismiss();
                        }
                    }
                });
            }
            dialogUpdate.setCancelable(mFlagCancelable);
            dialogUpdate.setContentView(contentView);
            // 调整dialog背景大小
            WindowManager windowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
            Display display = windowManager.getDefaultDisplay();
            int width = (int) (display.getWidth() * 0.8);
            contentView.setLayoutParams(new FrameLayout.LayoutParams(width, LinearLayout.LayoutParams.WRAP_CONTENT));
            return dialogUpdate;
        }

    }

    public interface OnClickListener {
        void onClick();
    }
}
