package com.cxz.kotlin.mvvm.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.core.content.FileProvider
import java.io.File

/**
 * @author chenxz
 * @date 2020/3/16
 * @desc
 */
object ApkUtil {

    /**
     * 安装Apk
     */
    fun installApk(context: Context, apk: File) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.action = Intent.ACTION_VIEW
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        intent.addCategory(Intent.CATEGORY_DEFAULT)
        val uri: Uri
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            uri = FileProvider.getUriForFile(context, context.packageName, apk)
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        } else {
            uri = Uri.fromFile(apk)
        }
        intent.setDataAndType(uri, "application/vnd.android.package-archive")
        context.startActivity(intent)
    }

    /**
     * 获取当前app的升级版本号
     *
     * @param context 上下文
     */
    fun getVersionCode(context: Context): Long {
        return try {
            val packageManager = context.packageManager
            val packageInfo =
                packageManager.getPackageInfo(context.packageName, 0)
            packageInfo.longVersionCode
        } catch (e: Exception) {
            e.printStackTrace()
            1
        }
    }

    /**
     * 获取当前app的版本号
     *
     * @param context 上下文
     */
    fun getVersionName(context: Context): String? {
        return try {
            val packageManager = context.packageManager
            val packageInfo =
                packageManager.getPackageInfo(context.packageName, 0)
            packageInfo.versionName
        } catch (e: Exception) {
            e.printStackTrace()
            "1.0.0"
        }
    }

}