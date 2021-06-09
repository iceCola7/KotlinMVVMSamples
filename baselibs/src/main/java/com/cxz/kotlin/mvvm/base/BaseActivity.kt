package com.cxz.kotlin.mvvm.base

import android.os.Bundle
import android.view.MenuItem
import android.view.MotionEvent
import android.view.WindowManager
import androidx.annotation.ColorInt
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import com.cxz.kotlin.mvvm.ext.showToast
import com.cxz.kotlin.mvvm.utils.KeyBoardUtil
import com.cxz.kotlin.mvvm.utils.StatusBarUtil

/**
 * @author chenxz
 * @date 2018/11/19
 * @desc BaseActivity
 */
abstract class BaseActivity : AppCompatActivity(), IView {

    /**
     * 布局文件id
     */
    @LayoutRes
    protected abstract fun attachLayoutRes(): Int

    /**
     * 初始化数据
     */
    open fun initData() {}

    /**
     * 初始化 View
     */
    abstract fun initView()

    /**
     * 设置状态栏的背景颜色
     */
    fun setStatusBarColor(@ColorInt color: Int) {
        StatusBarUtil.setColor(this, color, 0)
    }

    /**
     * 设置状态栏图标的颜色
     *
     * @param dark true: 黑色  false: 白色
     */
    fun setStatusBarIcon(dark: Boolean) {
        if (dark) {
            StatusBarUtil.setLightMode(this)
        } else {
            StatusBarUtil.setDarkMode(this)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        super.onCreate(savedInstanceState)
        // requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT // 强制竖屏
        setContentView(attachLayoutRes())
        initView()
        initData()
    }

    override fun showMsg(msg: String) {
        showToast(msg)
    }

    override fun showDefaultMsg(msg: String) {
        showToast(msg)
    }

    override fun showError(errorMsg: String) {
        showToast(errorMsg)
    }

    override fun showLoading() {
    }

    override fun hideLoading() {
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (ev?.action == MotionEvent.ACTION_UP) {
            val v = currentFocus
            // 如果不是落在EditText区域，则需要关闭输入法
            if (KeyBoardUtil.isHideKeyboard(v, ev)) {
                KeyBoardUtil.hideKeyBoard(this, v)
            }
        }
        return super.dispatchTouchEvent(ev)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}
