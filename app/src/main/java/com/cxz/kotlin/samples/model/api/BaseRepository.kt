package com.cxz.kotlin.samples.model.api

import com.cxz.kotlin.mvvm.http.exception.HttpException
import com.cxz.kotlin.samples.constants.ResponseCode
import com.cxz.kotlin.samples.model.bean.BaseResponse
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
        return when (response.errorCode) {
            ResponseCode.SUCCESS -> {
                return response.data
            }
            else -> {
                throw HttpException(response.errorCode, response.errorMsg)
            }
        }
    }
}
