package com.toeii.extensionreadjetpack

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.qmuiteam.qmui.arch.QMUISwipeBackActivityManager
import com.qmuiteam.qmui.util.QMUIStatusBarHelper
import com.toeii.extensionreadjetpack.config.ERAppConfig
import org.jetbrains.anko.AnkoLogger

class ERApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = applicationContext
        QMUISwipeBackActivityManager.init(this)
        logger = AnkoLogger(ERAppConfig.LOGGER_TAG)
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var instance: Context
        lateinit var logger: AnkoLogger
    }

}