package com.toeii.extensionreadjetpack.common.db

import androidx.room.*

/**
 * @author Toeii
 * @create 2019/12/3
 * @Describe
 */
@Dao
interface BrowseRecordDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(browseRecord : BrowseRecordBean)

    @Insert
    fun insertAll(browseRecords : List<BrowseRecordBean>)

    @Delete
    fun delete(browseRecord : BrowseRecordBean)

    @Query("DELETE FROM browserecordbean")
    fun deleteAll()

    @Query("DELETE FROM browserecordbean WHERE point_id IN (:point_id)")
    fun deleteById(point_id : Int)

    @Update
    fun updateBrowseRecord(browseRecord : BrowseRecordBean)

    @Query("SELECT * FROM browserecordbean order by uid desc")
    fun getAll(): List<BrowseRecordBean>

    @Query("SELECT * FROM browserecordbean WHERE point_id IN (:point_id) order by uid desc")
    fun getAllById(point_id: Int): List<BrowseRecordBean>

}