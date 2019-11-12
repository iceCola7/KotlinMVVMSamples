package com.cxz.kotlin.mvvm.ext

import android.content.Context
import android.content.res.Resources
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
