package com.toeii.extensionreadjetpack.common.db

import androidx.room.*

/**
 * @author Toeii
 * @create 2019/12/3
 * @Describe
 */
@Dao
interface BrowseRecordDao {

    @Insert
    fun insert(browseRecord : BrowseRecordBean)

    @Insert
    fun insertAll(browseRecords : List<BrowseRecordBean>)

    @Delete
    fun delete(browseRecord : BrowseRecordBean)

    @Delete
    fun deleteAll()

    @Delete
    fun deleteById(point_id : Int)

    @Update
    fun updateBrowseRecord(browseRecord : BrowseRecordBean)

    @Query("SELECT * FROM browseRecord")
    fun getAll(): List<BrowseRecordBean>

    @Query("SELECT * FROM browseRecord WHERE point_id IN (:point_id)")
    fun getAllById(point_id: Int): List<BrowseRecordBean>

}