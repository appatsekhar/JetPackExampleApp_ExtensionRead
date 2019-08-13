package com.toeii.extensionreadjetpack

import android.app.Application
import com.qmuiteam.qmui.arch.QMUISwipeBackActivityManager
import com.qmuiteam.qmui.util.QMUIStatusBarHelper

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        QMUISwipeBackActivityManager.init(this)

    }

}