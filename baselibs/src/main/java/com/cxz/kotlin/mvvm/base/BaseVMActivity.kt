package com.cxz.kotlin.mvvm.base

import android.os.Bundle
import androidx.lifecycle.ViewModelProviders

/**
 * @author chenxz
 * @date 2019/11/1
 * @desc BaseVMActivity
 */
abstract class BaseVMActivity<VM : BaseViewModel> : BaseActivity() {

    lateinit var mViewModel: VM

    abstract fun attachVMClass(): Class<VM>?

    open fun startObserver() {}

    override fun onCreate(savedInstanceState: Bundle?) {
        initVM()
        super.onCreate(savedInstanceState)
        startObserver()
    }

    private fun initVM() {
        if (attachVMClass() == null) {
            throw RuntimeException("ViewModel must not be null.")
        }
        attachVMClass()?.let {
            mViewModel = ViewModelProviders.of(this).get(it)
            lifecycle.addObserver(mViewModel)
        }
    }

    override fun onDestroy() {
        lifecycle.removeObserver(mViewModel)
        super.onDestroy()
    }
}