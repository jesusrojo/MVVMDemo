package com.jesusrojo.mvvmdemo.data.repository.datasource

import com.jesusrojo.mvvmdemo.data.model.UiData


interface UiDataLocalDataSource {

    suspend fun fetchAllInDB(): List<UiData>
    suspend fun saveAllToDB(datas: List<UiData>)
    suspend fun deleteAllInDB()
}