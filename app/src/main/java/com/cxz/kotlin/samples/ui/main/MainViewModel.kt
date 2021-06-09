package com.cxz.kotlin.samples.ui.main

import androidx.lifecycle.MutableLiveData
import com.cxz.kotlin.mvvm.base.BaseViewModel
import com.cxz.kotlin.samples.model.bean.Banner
import com.cxz.kotlin.samples.model.bean.CollectionArticle
import com.cxz.kotlin.samples.model.bean.CollectionResponseBody
import com.cxz.kotlin.samples.model.repository.MainRepository

/**
 * @author chenxz
 * @date 2019/11/3
 * @desc
 */
class MainViewModel : BaseViewModel() {

    val mLoginData: MutableLiveData<Any> = MutableLiveData()

    val mLogoutData: MutableLiveData<Any> = MutableLiveData()

    val mBannerList: MutableLiveData<List<Banner>> = MutableLiveData()

    val mCollectResponseBody: MutableLiveData<CollectionResponseBody<CollectionArticle>> = MutableLiveData()

    val errorMsg: MutableLiveData<String> = MutableLiveData()

    private val repository by lazy {
        MainRepository()
    }

    fun login(username: String, password: String) {
        scopeLaunch({
            showLoading.postValue(true)
            mLoginData.value = repository.login(username, password)
            showLoading.postValue(false)
        })
    }

    fun logout() {
        scopeLaunch({
            showLoading.postValue(true)
            mLogoutData.value = repository.logout()
            showLoading.postValue(false)
        })
    }

    fun getBannerList() {
        scopeLaunch({
            showLoading.postValue(true)
            mBannerList.value = repository.getBanner()
            showLoading.postValue(false)
        })
    }

    fun getCollectList(page: Int) {
        scopeLaunch({
            showLoading.postValue(true)
            mCollectResponseBody.value = repository.getCollectList(page)
            showLoading.postValue(false)
        })
    }
}