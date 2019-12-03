package com.toeii.extensionreadjetpack.common.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class BrowseRecordBean(

    @PrimaryKey var uid: Int,
    @ColumnInfo(name = "point_id") val pointId: String,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "content") val content: String,
    @ColumnInfo(name = "url") val url: String,
    @ColumnInfo(name = "image") val image: String


)