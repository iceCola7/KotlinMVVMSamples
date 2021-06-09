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

    private val mException: MutableLiveData<Exception> = MutableLiveData()

    val showLoading = MutableLiveData<Boolean>()

    fun scopeLaunch(
        block: suspend CoroutineScope.() -> Unit,
        onException: ((Throwable) -> Unit)? = null
    ) {
        val handler = CoroutineExceptionHandler { _, throwable ->
            Logger.e(throwable, throwable.message ?: "scopeLaunch")
            showLoading.postValue(false)
            when (throwable) {
                is HttpException -> {
                    showToast(throwable.errorMsg)
                }
            }
            onException?.invoke(throwable)
        }
        viewModelScope.launch(handler) {
            block()
        }
    }

    private fun launchOnUI(block: suspend CoroutineScope.() -> Unit) {
        MainScope().launch { block() }
        // viewModelScope.launch { block() }  /// ???
    }

    suspend fun <T> launchIO(block: suspend CoroutineScope.() -> T) {
        withContext(Dispatchers.IO) {
            block
        }
    }

    fun launch(tryBlock: suspend CoroutineScope.() -> Unit) {
        launchOnUI {
            tryCatch(tryBlock, {}, {}, true)
        }
    }

    fun launchOnUITryCatch(
        tryBlock: suspend CoroutineScope.() -> Unit,
        catchBlock: suspend CoroutineScope.(Throwable) -> Unit,
        finallyBlock: suspend CoroutineScope.() -> Unit,
        handleCancellationExceptionManually: Boolean
    ) {
        launchOnUI {
            tryCatch(tryBlock, catchBlock, finallyBlock, handleCancellationExceptionManually)
        }
    }

    fun launchOnUITryCatch(
        tryBlock: suspend CoroutineScope.() -> Unit,
        handleCancellationExceptionManually: Boolean = false
    ) {
        launchOnUI {
            tryCatch(tryBlock, {}, {}, handleCancellationExceptionManually)
        }
    }

    private suspend fun tryCatch(
        tryBlock: suspend CoroutineScope.() -> Unit,
        catchBlock: suspend CoroutineScope.(Throwable) -> Unit,
        finallyBlock: suspend CoroutineScope.() -> Unit,
        handleCancellationExceptionManually: Boolean = false
    ) {
        coroutineScope {
            try {
                tryBlock()
            } catch (e: Exception) {
                if (e !is CancellationException || handleCancellationExceptionManually) {
                    mException.value = e
                    catchBlock(e)
                } else {
                    throw e
                }
            } finally {
                finallyBlock()
            }
        }
    }
}
