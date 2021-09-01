@file:Suppress("unused")

package com.jesusrojo.mvvmdemo.data.repository.fake

import com.jesusrojo.mvvmdemo.data.model.UiData
import com.jesusrojo.mvvmdemo.data.repository.fake.FakeUtil.Companion.getFakeListItemsOneTwo
import com.jesusrojo.mvvmdemo.data.repository.fake.FakeUtil.Companion.getFakeListRawItemsThreeFour
import com.jesusrojo.mvvmdemo.domain.repository.UiDataRepository
import com.jesusrojo.mvvmdemo.util.Resource

class FakeRepository: UiDataRepository {

    private var datas = mutableListOf<UiData>()

    init {
        datas = getFakeListItemsOneTwo().toMutableList()
    }

    override suspend fun fetchDatas(query: String): Resource<List<UiData>> =
        Resource.Success(getFakeListItemsOneTwo())


    override suspend fun fetchNextDatas(query: String): Resource<List<UiData>> =
       Resource.Success(getFakeListRawItemsThreeFour())

    override suspend fun refreshDatas(query: String): Resource<List<UiData>> =
        Resource.Success(getFakeListItemsOneTwo())

    override suspend fun deleteAll() {} // nothing
    override suspend fun deleteAllCache() {} // nothing
}