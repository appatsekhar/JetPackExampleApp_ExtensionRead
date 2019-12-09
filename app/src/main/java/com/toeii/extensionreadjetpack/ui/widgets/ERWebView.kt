package com.toeii.extensionreadjetpack.ui.widgets

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.webkit.WebSettings
import com.qmuiteam.qmui.util.QMUIDisplayHelper
import com.qmuiteam.qmui.util.QMUIPackageHelper
import com.qmuiteam.qmui.widget.webview.QMUIWebView
import com.toeii.extensionreadjetpack.BuildConfig

@SuppressLint("SetJavaScriptEnabled")
class ERWebView(context: Context?) : QMUIWebView(context){

    init{
        settings.javaScriptEnabled = true
        settings.setSupportZoom(true)
        settings.builtInZoomControls = true
        settings.defaultTextEncodingName = "GBK"
        settings.useWideViewPort = true
        settings.loadWithOverviewMode = true
        settings.domStorageEnabled = true
        settings.cacheMode = WebSettings.LOAD_NO_CACHE
        settings.textZoom = 100

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            settings.mixedContentMode = WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE
        }

        val screen =
            QMUIDisplayHelper.getScreenWidth(context).toString() + "x" + QMUIDisplayHelper.getScreenHeight(context)
        val userAgent = ("ExtensionRead/" + QMUIPackageHelper.getAppVersion(context)
                + " (Android; " + Build.VERSION.SDK_INT
                + "; Screen/" + screen + "; Scale/" + QMUIDisplayHelper.getDensity(context) + ")")
        val agent = settings.userAgentString
        if (agent == null || !agent.contains(userAgent)){
            settings.userAgentString = "$agent $userAgent"
        }

        // 开启调试
        if (BuildConfig.DEBUG && Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            setWebContentsDebuggingEnabled(true)
        }
    }

}