package com.jesusrojo.mvvmdemo.data.repository.datasourceimpl

import com.jesusrojo.mvvmdemo.data.model.UiData
import com.jesusrojo.mvvmdemo.data.repository.datasource.UiDataCacheDataSource
import com.jesusrojo.mvvmdemo.util.DebugHelp
import javax.inject.Inject


class UiDataCacheDataSourceImpl @Inject constructor() : UiDataCacheDataSource {

    private var datas = ArrayList<UiData>()

    override suspend fun fetchAllFromCache(): List<UiData> {
        DebugHelp.l("fetchAllFromCache")
        return datas
    }

    override suspend fun saveAllToCache(datas: List<UiData>) {
        DebugHelp.l("saveAllToCache")
        this.datas.clear()
        this.datas = ArrayList(datas)
    }

    override suspend fun deleteAllInCache() {
        DebugHelp.l("deleteAllInCache")
        datas.clear()
    }
}