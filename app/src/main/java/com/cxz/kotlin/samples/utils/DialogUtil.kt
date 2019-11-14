package com.cxz.kotlin.samples.utils

import android.content.Context
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog

/**
 * @author admin
 * @date 2018/11/22
 * @desc
 */
object DialogUtil {

    /**
     * 获取提示 Dialog
     *
     * @param context
     * @param message
     * @return
     */
    fun getTipDialog(context: Context, message: String): QMUITipDialog {
        return QMUITipDialog.Builder(context)
            .setTipWord(message)
            .create()
    }

    /**
     * 获取加载中的 Dialog
     *
     * @param context
     * @param message
     * @return
     */
    fun getWaitDialog(context: Context, message: String): QMUITipDialog {
        return QMUITipDialog.Builder(context)
            .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
            .setTipWord(message)
            .create()
    }

}