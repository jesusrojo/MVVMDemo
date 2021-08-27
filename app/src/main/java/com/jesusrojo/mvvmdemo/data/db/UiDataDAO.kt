package com.jesusrojo.mvvmdemo.data.db

import androidx.annotation.WorkerThread
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jesusrojo.mvvmdemo.data.model.UiData

@Dao
interface UiDataDAO {

    @WorkerThread
    @Query("SELECT * FROM uidata_table")
    fun fetchAll(): List<UiData> //// List<EntyData>

    @WorkerThread
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(enties: List<UiData>) //// List<EntyData>

    @WorkerThread
    @Query("DELETE FROM uidata_table")
    suspend fun deleteAll()
}