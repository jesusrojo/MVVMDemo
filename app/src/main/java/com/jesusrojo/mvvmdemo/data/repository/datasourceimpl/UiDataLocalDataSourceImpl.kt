package com.jesusrojo.mvvmdemo.data.repository.datasourceimpl

import com.jesusrojo.mvvmdemo.data.db.UiDataDAO
import com.jesusrojo.mvvmdemo.data.model.UiData
import com.jesusrojo.mvvmdemo.data.repository.datasource.UiDataLocalDataSource
import com.jesusrojo.mvvmdemo.util.DebugHelp
import javax.inject.Inject


class UiDataLocalDataSourceImpl @Inject constructor (
    private val myDao: UiDataDAO
) : UiDataLocalDataSource {

    override suspend fun fetchAllInDB(): List<UiData> {
        DebugHelp.l("fetchAllInDB")
        return myDao.fetchAll()
    }

    override suspend fun saveAllToDB(enties: List<UiData>) {
        DebugHelp.l("saveAllToDB")
        myDao.insertAll(enties)
    }

    override suspend fun deleteAllInDB() {
        DebugHelp.l("deleteAllInDB")
        myDao.deleteAll()
    }
}