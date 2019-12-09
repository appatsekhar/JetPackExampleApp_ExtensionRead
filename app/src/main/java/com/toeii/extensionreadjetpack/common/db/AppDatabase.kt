package com.toeii.extensionreadjetpack.common.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [BrowseRecordBean::class], version = 1,exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun browseRecordDao(): BrowseRecordDao

}