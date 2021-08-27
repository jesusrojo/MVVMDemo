package com.jesusrojo.mvvmdemo.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jesusrojo.mvvmdemo.data.model.UiData

@Database(entities = [UiData::class], version = 1, exportSchema = false)
abstract class UiDataDatabase: RoomDatabase(){
    abstract fun getMyDAO(): UiDataDAO
}