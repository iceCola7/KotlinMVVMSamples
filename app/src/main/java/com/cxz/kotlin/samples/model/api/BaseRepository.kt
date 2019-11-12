package com.cxz.kotlin.samples.model.api

import com.cxz.kotlin.samples.model.bean.BaseResponse

/**
 * @author admin
 * @date 2019/11/1
 * @desc
 */
open class BaseRepository {

    suspend fun <T : Any> apiCall(call: suspend () -> BaseResponse<T>): BaseResponse<T> {
        return call.invoke()
    }

}
