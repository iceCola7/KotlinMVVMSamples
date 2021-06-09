package com.cxz.kotlin.mvvm.ext

import com.cxz.kotlin.mvvm.config.AppConfig
import com.cxz.kotlin.mvvm.widget.CustomToast

/**
 * @author chenxz
 * @date 2021/6/9
 * @desc toast相关拓展
 */

fun showToast(content: String = "") {
    CustomToast(AppConfig.getApplication(), content).show()
}
