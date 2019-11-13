package com.cxz.kotlin.samples.model.api

import com.cxz.kotlin.samples.model.bean.Banner
import com.cxz.kotlin.samples.model.bean.BaseResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

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

    @POST("user/login")
    @FormUrlEncoded
    suspend fun login(@Field("username") username: String, @Field("password") password: String)
            : BaseResponse<Any>

    @GET("user/logout/json")
    suspend fun logout(): BaseResponse<Any>



}