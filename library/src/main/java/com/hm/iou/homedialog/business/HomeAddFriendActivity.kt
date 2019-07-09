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
import kotlinx.android.synthetic.main.homedialog_add_friend.*

/**
 * 首页剪切板添加好友弹窗
 */
class HomeAddFriendActivity : BaseActivity<MvpActivityPresenter<BaseContract.BaseView>>() {

    companion object {
        const val EXTRA_KEY_CLIPBOARD_INFO: String = "clipboard"
        const val EXTRA_KEY_EXT_INFO: String = "extinfo"
    }

    override fun initPresenter(): MvpActivityPresenter<BaseContract.BaseView>? = null

    override fun getLayoutId(): Int = R.layout.homedialog_add_friend

    private var mClipBoardInfo: ClipBoardBean? = null
    private var mExtInfo: ClipBoardBean.ExtInfo? = null

    override fun initEventAndData(bundle: Bundle?) {
        mClipBoardInfo = intent.getParcelableExtra(EXTRA_KEY_CLIPBOARD_INFO)
        mExtInfo = intent.getParcelableExtra(EXTRA_KEY_EXT_INFO)
        bundle?.apply {
            mClipBoardInfo = getParcelable(EXTRA_KEY_CLIPBOARD_INFO)
            mExtInfo = getParcelable(EXTRA_KEY_EXT_INFO)
        }

        setBgRadius()
        initViews()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putParcelable(EXTRA_KEY_CLIPBOARD_INFO, mClipBoardInfo)
        outState?.putParcelable(EXTRA_KEY_EXT_INFO, mExtInfo)
    }

    override fun finish() {
        overridePendingTransition(0, 0)
        super.finish()
    }

    private fun setBgRadius() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val radius = resources.displayMetrics.density * 4
            rl_friend_content.outlineProvider = object : ViewOutlineProvider() {
                override fun getOutline(view: View?, outline: Outline?) {
                    view?.let {
                        outline?.setRoundRect(0, 0, view.width, view.height, radius)
                    }
                }
            }
            rl_friend_content.clipToOutline = true
        }
    }

    private fun initViews() {
        if (mClipBoardInfo == null || mExtInfo == null) {
            finish()
            return
        }


        mExtInfo?.apply {
            avatarUrl?.let {
                ImageLoader.getInstance(this@HomeAddFriendActivity).displayImage(it, iv_friend_avatar,
                        R.drawable.uikit_bg_pic_loading_place, R.mipmap.uikit_icon_header_unknow)
            }
            when(sex) {
                0 -> iv_friend_sex.setImageResource(R.mipmap.uikit_ic_gender_woman)
                1 -> iv_friend_sex.setImageResource(R.mipmap.uikit_ic_gender_man)
                else -> iv_friend_sex.visibility = View.GONE
            }
            tv_friend_nickname.text = nickName
            tv_friend_showid.text = "ID：$showId"
        }

        iv_friend_close.setOnClickListener {
            finish()
        }

        btn_friend_submit.setOnClickListener {
            mClipBoardInfo?.let {
                val jumpUrl: String? = it.shearUrl
                jumpUrl?.let {
                    RouterUtil.clickMenuLink(this@HomeAddFriendActivity, jumpUrl)
                }
            }
            finish()
        }
    }

}