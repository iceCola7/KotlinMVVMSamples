package com.cxz.kotlin.samples.app

import android.app.Application
import com.cxz.kotlin.mvvm.config.AppConfig

/**
 * @author chenxz
 * @date 2019/11/1
 * @desc
 */
class App : Application() {

    override fun onCreate() {
        super.onCreate()

        initApp()
    }

    private fun initApp() {
        AppConfig.init(this)
        AppConfig.openDebug()
    }

}
