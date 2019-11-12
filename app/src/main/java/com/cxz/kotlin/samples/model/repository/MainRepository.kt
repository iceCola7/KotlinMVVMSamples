package com.cxz.kotlin.samples.model.repository

import com.cxz.kotlin.samples.model.api.BaseRepository
import com.cxz.kotlin.samples.model.api.MainRetrofit
import com.cxz.kotlin.samples.model.bean.Banner
import com.cxz.kotlin.samples.model.bean.BaseResponse

/**
 * @author admin
 * @date 2019/7/8
 * @desc
 */

class MainRepository : BaseRepository() {

    suspend fun getBanner(): BaseResponse<List<Banner>> {
        return apiCall { MainRetrofit.service.getBanner() }
    }

}
