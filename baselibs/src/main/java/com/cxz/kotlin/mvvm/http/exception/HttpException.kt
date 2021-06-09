package com.cxz.kotlin.mvvm.http.exception

/**
 * 业务异常
 */
class HttpException(
    var errorCode: Int,
    var errorMsg: String,
    var throwable: Throwable? = null
) : Exception() {

}