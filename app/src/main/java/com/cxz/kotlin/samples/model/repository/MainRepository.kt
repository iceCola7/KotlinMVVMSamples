package com.cxz.kotlin.samples.model.repository

import com.cxz.kotlin.samples.model.api.BaseRepository
import com.cxz.kotlin.samples.model.api.MainRetrofit
import com.cxz.kotlin.samples.model.bean.Banner
import com.cxz.kotlin.samples.model.bean.CollectionArticle
import com.cxz.kotlin.samples.model.bean.CollectionResponseBody

/**
 * @author admin
 * @date 2019/7/8
 * @desc
 */
class MainRepository : BaseRepository() {

    private val mainService by lazy {
        MainRetrofit.service
    }

    suspend fun login(username: String, password: String): Any? {
        return apiCall { mainService.login(username, password) }
    }

    suspend fun logout(): Any? {
        return apiCall { mainService.logout() }
    }

    suspend fun getBanner(): List<Banner>? {
        return apiCall { mainService.getBanner() }
    }

    suspend fun getCollectList(page: Int): CollectionResponseBody<CollectionArticle>? {
        return apiCall { mainService.getCollectList(page) }
    }
}
