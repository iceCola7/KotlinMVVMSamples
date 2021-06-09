package com.cxz.kotlin.samples.app

import android.app.Application
import com.cxz.kotlin.mvvm.config.AppConfig
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.DiskLogAdapter
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy

/**
 * @author chenxz
 * @date 2019/11/1
 * @desc
 */
class App : Application() {

    override fun onCreate() {
        super.onCreate()

        initApp()

        initLogger()
    }

    private fun initApp() {
        AppConfig.init(this)
        AppConfig.openDebug()
    }

    /**
     * 初始化log日志打印
     */
    private fun initLogger() {
        val formatStrategy = PrettyFormatStrategy.newBuilder()
            .tag("mvvm")
            .methodCount(1)
            .build()
        Logger.addLogAdapter(AndroidLogAdapter(formatStrategy))
        Logger.addLogAdapter(DiskLogAdapter())
    }

}
