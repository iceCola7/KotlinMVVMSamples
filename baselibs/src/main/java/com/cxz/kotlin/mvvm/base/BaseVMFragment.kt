package com.cxz.kotlin.mvvm.base

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProviders

/**
 * @author chenxz
 * @date 2019/11/1
 * @desc
 */
abstract class BaseVMFragment<VM : BaseViewModel> : BaseFragment() {

    lateinit var mViewModel: VM

    abstract fun attachVMClass(): Class<VM>?

    open fun startObserver() {}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initVM()
        startObserver()
        super.onViewCreated(view, savedInstanceState)
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