package com.toeii.extensionreadjetpack.common.db

import androidx.room.Database
import androidx.room.RoomDatabase

/**
 * @author Toeii
 * @create 2019/12/3
 * @Describe
 */
@Database(entities = [BrowseRecordBean::class], version = 1,exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun browseRecordDao(): BrowseRecordDao

}