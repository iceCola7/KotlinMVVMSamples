package com.cxz.kotlin.samples.model.api

import com.cxz.kotlin.samples.model.bean.Banner
import com.cxz.kotlin.samples.model.bean.BaseResponse
import retrofit2.http.GET

/**
 * @author chenxz
 * @date 2019/11/1
 * @desc
 */
interface MainApi {

    companion object {
        const val BASE_URL = "https://www.wanandroid.com"
    }

    @GET("/banner/json")
    suspend fun getBanner(): BaseResponse<List<Banner>>

}