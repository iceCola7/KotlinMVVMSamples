package com.cxz.kotlin.mvvm.ext

import com.cxz.kotlin.mvvm.config.AppConfig
import com.cxz.kotlin.mvvm.utils.NLog

/**
 * @author chenxz
 * @date 2018/11/20
 * @desc
 */

fun Any.loge(content: String?) {
    val tag = this.javaClass.simpleName ?: AppConfig.TAG
    NLog.e(tag, content ?: "")
}

