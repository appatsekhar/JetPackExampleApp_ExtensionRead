package com.toeii.extensionreadjetpack

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import androidx.room.Room
import com.qmuiteam.qmui.arch.QMUISwipeBackActivityManager
import com.toeii.extensionreadjetpack.common.db.AppDatabase
import com.toeii.extensionreadjetpack.config.ERAppConfig
import org.jetbrains.anko.AnkoLogger

class ERApplication : Application() {

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var instance: Context
        lateinit var logger: AnkoLogger
        lateinit var db: AppDatabase
    }

    override fun onCreate() {
        super.onCreate()
        instance = applicationContext

        QMUISwipeBackActivityManager.init(this)

        logger = AnkoLogger(ERAppConfig.LOGGER_TAG)

        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, ERAppConfig.DB_TAG
        ).build()

    }


}