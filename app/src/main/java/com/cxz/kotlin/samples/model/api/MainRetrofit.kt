package com.cxz.kotlin.samples.model.api

import com.cxz.kotlin.mvvm.http.RetrofitFactory

/**
 * @author chenxz
 * @date 2019/11/1
 * @desc
 */
object MainRetrofit : RetrofitFactory<MainApi>() {

    override fun baseUrl(): String = MainApi.BASE_URL

    override fun attachService(): Class<MainApi> = MainApi::class.java

}