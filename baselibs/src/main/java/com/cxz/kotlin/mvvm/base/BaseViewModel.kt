package com.cxz.kotlin.mvvm.base

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cxz.kotlin.mvvm.ext.showToast
import com.cxz.kotlin.mvvm.http.exception.HttpException
import com.orhanobut.logger.Logger
import kotlinx.coroutines.*

/**
 * @author chenxz
 * @date 2019/11/1
 * @desc BaseViewModel
 */
open class BaseViewModel : ViewModel(), LifecycleObserver {

    val showLoading = MutableLiveData<Boolean>()

    fun launchOnUI(
        block: suspend CoroutineScope.() -> Unit,
        onException: ((Throwable) -> Unit)? = null
    ) {
        val handler = CoroutineExceptionHandler { _, throwable ->
            Logger.e(throwable, throwable.message ?: "launchOnUI")
            showLoading.postValue(false)
            when (throwable) {
                is HttpException -> {
                    showToast(throwable.errorMsg)
                }
            }
            onException?.invoke(throwable)
        }
        viewModelScope.launch(Dispatchers.Main + handler) {
            block()
        }
    }
}
