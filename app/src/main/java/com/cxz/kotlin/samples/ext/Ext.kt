package com.cxz.kotlin.samples.ext

import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.net.Uri
import com.cxz.kotlin.samples.constants.ResponseCode
import com.cxz.kotlin.samples.model.bean.BaseResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope

fun dp2px(dpValue: Float): Int {
    return (0.5f + dpValue * Resources.getSystem().displayMetrics.density).toInt()
}

suspend fun executeResponse(
    response: BaseResponse<Any>,
    successBlock: suspend CoroutineScope.() -> Unit,
    errorBlock: suspend CoroutineScope.() -> Unit
) {
    coroutineScope {
        if (response.errorCode == ResponseCode.SUCCESS) {
            successBlock()
        } else {
            errorBlock()
        }
    }
}

fun Context.openBrowser(url: String) {
    Intent(Intent.ACTION_VIEW, Uri.parse(url)).run { startActivity(this) }
}
