package com.cxz.kotlin.mvvm.ext

import android.content.Context
import android.content.res.Resources
import android.view.View
import android.widget.Checkable
import androidx.fragment.app.Fragment
import com.cxz.kotlin.mvvm.config.AppConfig
import com.cxz.kotlin.mvvm.utils.NLog
import com.cxz.kotlin.mvvm.widget.CustomToast

/**
 * @author chenxz
 * @date 2018/11/20
 * @desc
 */

fun Any.loge(content: String?) {
    val tag = this.javaClass.simpleName ?: AppConfig.TAG
    NLog.e(tag, content ?: "")
}

fun dp2px(dpValue: Float): Int {
    return (0.5f + dpValue * Resources.getSystem().displayMetrics.density).toInt()
}

fun Fragment.showToast(content: String) {
    CustomToast(this.activity?.applicationContext, content).show()
}

fun Context.showToast(content: String) {
    CustomToast(this, content).show()
}

// 扩展点击事件属性(重复点击时长)
var <T : View> T.lastClickTime: Long
    set(value) = setTag(1766613352, value)
    get() = getTag(1766613352) as? Long ?: 0

// 重复点击事件绑定
inline fun <T : View> T.setSingleClickListener(time: Long = 1000, crossinline block: (T) -> Unit) {
    setOnClickListener {
        val currentTimeMillis = System.currentTimeMillis()
        if (currentTimeMillis - lastClickTime > time || this is Checkable) {
            lastClickTime = currentTimeMillis
            block(this)
        }
    }
}
