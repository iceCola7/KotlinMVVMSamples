package com.cxz.kotlin.samples.ui.main

import androidx.lifecycle.MutableLiveData
import com.cxz.kotlin.mvvm.base.BaseViewModel
import com.cxz.kotlin.samples.ext.executeResponse
import com.cxz.kotlin.samples.model.bean.Banner
import com.cxz.kotlin.samples.model.repository.MainRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * @author chenxz
 * @date 2019/11/3
 * @desc
 */

class MainViewModel : BaseViewModel() {

    val mBannerList: MutableLiveData<List<Banner>> = MutableLiveData()

    val errorMsg: MutableLiveData<String> = MutableLiveData()

    private val repository by lazy {
        MainRepository()
    }

    fun getBannerList() {
        launch {
            val response = withContext(Dispatchers.IO) { repository.getBanner() }
            executeResponse(
                response,
                { mBannerList.value = response.data },
                { errorMsg.value = response.errorMsg })
        }
    }

}