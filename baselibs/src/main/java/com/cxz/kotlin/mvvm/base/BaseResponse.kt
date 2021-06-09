package com.cxz.kotlin.mvvm.base

/**
 * @author admin
 * @date 2019/7/8
 * @desc
 */
data class BaseResponse<out T>(
    val errorCode: Int,
    val errorMsg: String,
    val data: T
)