package com.cxz.kotlin.mvvm.utils

import android.content.Context
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Build
import android.os.Looper
import android.os.Process
import com.cxz.mvvm.baselibs.BuildConfig
import com.orhanobut.logger.Logger
import java.io.PrintWriter
import java.io.StringWriter
import java.io.Writer
import java.lang.reflect.Field
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap

/**
 * @author chenxz
 * @date 2021/8/4
 * @desc
 */
class CrashHandler private constructor() : Thread.UncaughtExceptionHandler {

    companion object {
        const val TAG = "CrashHandler"

        val instance = CrashHandler()
    }

    // 系统默认的UncaughtException处理类
    private var mDefaultHandler: Thread.UncaughtExceptionHandler? = null

    // 程序的Context对象
    private var mContext: Context? = null

    // 用来存储设备信息和异常信息
    private val infoMap: MutableMap<String, String> = HashMap()

    // 用于格式化日期,作为日志文件名的一部分
    private val formatter: DateFormat = SimpleDateFormat("yyyy-MM-dd-HH-mm-ss", Locale.CHINA)

    var crashTip = "应用开小差了，稍后重启下，亲！"

    fun init(context: Context) {
        mContext = context
        // 获取系统默认的UncaughtException处理器
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler()
        // 设置该CrashHandler为程序的默认处理器
        Thread.setDefaultUncaughtExceptionHandler(this)
    }

    /**
     * 当UncaughtException发生时会转入该函数来处理
     *
     * @param thread 线程
     * @param throwable 异常
     */
    override fun uncaughtException(thread: Thread, throwable: Throwable) {
        if (!handleException(throwable) && mDefaultHandler != null) {
            // 如果用户没有处理则让系统默认的异常处理器来处理
            mDefaultHandler?.uncaughtException(thread, throwable)
        } else {
            try {
                Thread.sleep(3000)
            } catch (e: InterruptedException) {
                Logger.e("error : ", e)
                e.printStackTrace()
            }
            // 退出程序
            // 退出JVM(java虚拟机),释放所占内存资源,0表示正常退出(非0的都为异常退出)
            System.exit(0)
            // 从操作系统中结束掉当前程序的进程
            Process.killProcess(Process.myPid())
        }
    }

    /**
     * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成.
     *
     * @param throwable 异常
     * @return true: 如果处理了该异常信息;否则返回false.
     */
    private fun handleException(throwable: Throwable?): Boolean {
        if (throwable == null) {
            return false
        }
        // 使用Toast来显示异常信息
        object : Thread() {
            override fun run() {
                Looper.prepare()
                throwable.printStackTrace()
                // StringUtils.showMsgAsCenter(mContext, crashTip)
                Looper.loop()
            }
        }.start()
        // 收集设备参数信息
        collectDeviceInfo(mContext!!)
        // 保存日志文件
        saveCrashInfo2File(throwable)
        return true
    }

    /**
     * 收集设备参数信息
     *
     * @param context 上下文
     */
    fun collectDeviceInfo(context: Context) {
        try {
            val pm: PackageManager = context.packageManager
            val pi: PackageInfo =
                pm.getPackageInfo(context.packageName, PackageManager.GET_ACTIVITIES)
            val versionName =
                if (pi.versionName == null) "null" else pi.versionName
            val versionCode = pi.versionCode.toString()
            infoMap["versionName"] = versionName
            infoMap["versionCode"] = versionCode
        } catch (e: PackageManager.NameNotFoundException) {
            Logger.e("an error occured when collect package info", e)
        }
        val fields: Array<Field> = Build::class.java.declaredFields
        for (field in fields) {
            try {
                field.isAccessible = true
                infoMap[field.name] = field.get(null).toString()
                Logger.d(field.name + " : " + field.get(null))
            } catch (e: Exception) {
                Logger.e("an error occur when collect crash info", e)
            }
        }
    }

    /**
     * 保存错误信息到文件中
     *
     * @param ex 异常
     * @return 返回文件名称, 便于将文件传送到服务器
     */
    private fun saveCrashInfo2File(ex: Throwable): String? {
        val sb = StringBuffer()
        for ((key, value) in infoMap) {
            sb.append("$key=$value\n")
        }
        val writer: Writer = StringWriter()
        val printWriter = PrintWriter(writer)
        ex.printStackTrace(printWriter)
        var cause = ex.cause
        while (cause != null) {
            cause.printStackTrace(printWriter)
            cause = cause.cause
        }
        printWriter.close()
        val result: String = writer.toString()
        sb.append(result)
        // 这个 crashInfo 就是我们收集到的所有信息，可以做一个异常上报的接口用来提交用户的crash信息
        val crashInfo = sb.toString()
        Logger.e(crashInfo)
        if (BuildConfig.DEBUG) {
            return null
        }
        return crashInfo
    }
}

