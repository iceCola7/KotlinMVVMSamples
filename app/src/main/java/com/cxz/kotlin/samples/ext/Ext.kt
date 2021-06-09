package com.cxz.kotlin.samples.ext

import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.net.Uri

fun dp2px(dpValue: Float): Int {
    return (0.5f + dpValue * Resources.getSystem().displayMetrics.density).toInt()
}

fun Context.openBrowser(url: String) {
    Intent(Intent.ACTION_VIEW, Uri.parse(url)).run { startActivity(this) }
}
