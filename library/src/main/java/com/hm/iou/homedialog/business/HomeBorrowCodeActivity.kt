package com.hm.iou.homedialog.business

import android.graphics.Outline
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.ViewOutlineProvider
import com.hm.iou.base.BaseActivity
import com.hm.iou.base.comm.ClipBoardBean
import com.hm.iou.base.mvp.BaseContract
import com.hm.iou.base.mvp.MvpActivityPresenter
import com.hm.iou.base.utils.RouterUtil
import com.hm.iou.homedialog.R
import com.hm.iou.tools.ImageLoader
import com.hm.iou.tools.kt.clickWithDuration
import com.hm.iou.tools.kt.dp2px
import com.hm.iou.tools.kt.extraDelegate
import kotlinx.android.synthetic.main.homedialog_activity_borrow_code.*

/**
 * Created by hjy on 2019/9/16
 *
 * 首页借款码剪切板弹窗
 */
class HomeBorrowCodeActivity : BaseActivity<MvpActivityPresenter<BaseContract.BaseView>>() {

    companion object {
        const val EXTRA_KEY_CLIPBOARD_INFO: String = "clipboard"
        const val EXTRA_KEY_BORROW_CODE_INFO: String = "borrowcode"
    }

    private var mClipBoardInfo: ClipBoardBean? by extraDelegate(EXTRA_KEY_CLIPBOARD_INFO, null)
    private var mBorrowCodeInfo: ClipBoardBean.BorrowCodeInfo? by extraDelegate(EXTRA_KEY_BORROW_CODE_INFO, null)

    override fun initPresenter(): MvpActivityPresenter<BaseContract.BaseView>? = null
    override fun getLayoutId(): Int = R.layout.homedialog_activity_borrow_code

    override fun initEventAndData(data: Bundle?) {
        data?.apply {
            mClipBoardInfo = getParcelable(EXTRA_KEY_CLIPBOARD_INFO)
            mBorrowCodeInfo = getParcelable(EXTRA_KEY_BORROW_CODE_INFO)
        }

        initViews()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putParcelable(EXTRA_KEY_CLIPBOARD_INFO, mClipBoardInfo)
        outState?.putParcelable(EXTRA_KEY_BORROW_CODE_INFO, mBorrowCodeInfo)
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(0, 0)
    }

    private fun setBgRadius(view: View, radius: Float) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            view.outlineProvider = object : ViewOutlineProvider() {
                override fun getOutline(view: View?, outline: Outline?) {
                    view?.let {
                        outline?.setRoundRect(0, 0, view.width, view.height, radius)
                    }
                }
            }
            view.clipToOutline = true
        }
    }

    private fun initViews() {
        setBgRadius(rl_friend_content, dp2px(4).toFloat())
        iv_friend_close.clickWithDuration {
            finish()
        }

        if (mClipBoardInfo == null || mBorrowCodeInfo == null) {
            finish()
            return
        }

        ImageLoader.getInstance(this).displayImage(mBorrowCodeInfo?.avatarUrl, iv_dialog_avatar,
                R.mipmap.uikit_icon_header_unknow, R.mipmap.uikit_icon_header_unknow)
        tv_dialog_title.text = mBorrowCodeInfo?.title
        tv_dialog_title.setTextColor(0xFF1A6B20.toInt())
        tv_dialog_nickname.text = "金主昵称：${mBorrowCodeInfo?.nickName}"
        tv_dialog_amount.text = "闲置资金：${mBorrowCodeInfo?.amount}"
        tv_dialog_days.text = "借款周期：${mBorrowCodeInfo?.deadline}"
        tv_dialog_interest.text = "利息合计：${mBorrowCodeInfo?.interest}"
        tv_dialog_overdue_interest.text = "逾期利息：${mBorrowCodeInfo?.overdueInterestDesc}"
        btn_dialog_submit.text = "共享${mBorrowCodeInfo?.amount?.replace("¥", "")?.replace("￥", "")}元"
        btn_dialog_submit.setBackgroundResource(R.drawable.homedialog_btn_borrow_code)
        btn_dialog_submit.clickWithDuration {
            RouterUtil.clickMenuLink(this, mClipBoardInfo?.shearUrl)
            finish()
        }
    }

}