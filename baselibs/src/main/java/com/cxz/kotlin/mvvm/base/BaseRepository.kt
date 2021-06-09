package com.cxz.kotlin.mvvm.base

import com.cxz.kotlin.mvvm.http.constant.HttpErrorCode
import com.cxz.kotlin.mvvm.http.exception.HttpException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * @author admin
 * @date 2019/11/1
 * @desc
 */
open class BaseRepository {

    suspend fun <T> apiCall(call: suspend () -> BaseResponse<T>): T? {
        return withContext(Dispatchers.IO) {
            val response = call.invoke()
            executeResponse(response)
        }
    }

    fun <T> executeResponse(response: BaseResponse<T>): T? {
        when (response.errorCode) {
            HttpErrorCode.SUCCESS -> {
                return response.data
            }
            else -> {
                throw HttpException(response.errorCode, response.errorMsg)
            }
        }
    }
}
