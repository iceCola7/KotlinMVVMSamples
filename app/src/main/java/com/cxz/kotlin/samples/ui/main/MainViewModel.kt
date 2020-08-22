package com.cxz.kotlin.samples.ui.main

import androidx.lifecycle.MutableLiveData
import com.cxz.kotlin.mvvm.base.BaseViewModel
import com.cxz.kotlin.samples.ext.executeResponse
import com.cxz.kotlin.samples.model.bean.Banner
import com.cxz.kotlin.samples.model.bean.BaseResponse
import com.cxz.kotlin.samples.model.bean.CollectionArticle
import com.cxz.kotlin.samples.model.bean.CollectionResponseBody
import com.cxz.kotlin.samples.model.repository.MainRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * @author chenxz
 * @date 2019/11/3
 * @desc
 */
class MainViewModel : BaseViewModel() {

    val mLoginData: MutableLiveData<BaseResponse<Any>> = MutableLiveData()

    val mLogoutData: MutableLiveData<BaseResponse<Any>> = MutableLiveData()

    val mBannerList: MutableLiveData<List<Banner>> = MutableLiveData()

    val mCollectResponseBody: MutableLiveData<CollectionResponseBody<CollectionArticle>> =
        MutableLiveData()

    val errorMsg: MutableLiveData<String> = MutableLiveData()

    private val repository by lazy {
        MainRepository()
    }

    fun login(username: String, password: String) {
        launch {
            val response = withContext(Dispatchers.IO) { repository.login(username, password) }
            executeResponse(
                response,
                { mLoginData.value = response },
                { errorMsg.value = response.errorMsg }
            )
        }
    }

    fun logout() {
        launch {
            val response = withContext(Dispatchers.IO) { repository.logout() }
            executeResponse(
                response,
                { mLogoutData.value = response },
                { errorMsg.value = response.errorMsg }
            )
        }
    }

    fun getBannerList() {
        launch {
            val response = withContext(Dispatchers.IO) { repository.getBanner() }
            executeResponse(
                response,
                { mBannerList.value = response.data },
                { errorMsg.value = response.errorMsg }
            )
        }
    }

    fun getCollectList(page: Int) {
        launch {
            val response = withContext(Dispatchers.IO) { repository.getCollectList(page) }
            executeResponse(
                response,
                { mCollectResponseBody.value = response.data },
                { errorMsg.value = response.errorMsg }
            )
        }
    }
}