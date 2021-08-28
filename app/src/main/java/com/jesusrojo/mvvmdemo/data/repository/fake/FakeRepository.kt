@file:Suppress("unused")

package com.jesusrojo.mvvmdemo.data.repository.fake

import com.jesusrojo.mvvmdemo.data.model.OwnerRawData
import com.jesusrojo.mvvmdemo.data.model.RawData
import com.jesusrojo.mvvmdemo.data.model.RawDataResponse
import com.jesusrojo.mvvmdemo.data.model.UiData
import com.jesusrojo.mvvmdemo.domain.repository.UiDataRepository
import com.jesusrojo.mvvmdemo.util.Resource
import retrofit2.Response

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


    //FAKE DATA AND DATAS FOR TEST
    companion object {

        fun getFakeUiData(flag: String): UiData {
            val dataId = 1111
            val name = "name$flag"
            val avatarUrl = "avatarUrl$flag"
            val title = "title$flag"
            val description = "description$flag"
            val fullDescription = "fullDescription$flag"
            val forksCount = 2222
            val startsCount = 3333

            return UiData(dataId, name, avatarUrl, title, description, fullDescription, forksCount, startsCount)
        }

        fun getFakeRawData(flag: String): RawData {

            val uiData = getFakeUiData(flag)

            val id = uiData.dataId
            val name = uiData.name
            val description = uiData.description
            val forksCount =uiData.forksCount
            val stargazersCount = uiData.startsCount

            val ownerName = "title$flag"
            val avatarUrl = "avatarUrl$flag"
            val owner = OwnerRawData(ownerName, avatarUrl)

            return RawData(id, name, description, forksCount, stargazersCount, owner)
        }

        fun getFakeListEmpty(): List<UiData> = emptyList()


        fun getFakeListItemsOneTwo(): List<UiData> {
            return listOf(getFakeUiData("1"), getFakeUiData("2"))
        }


        fun getFakeListRawItemsThreeFour(): List<UiData> {
            val datas = mutableListOf<UiData>()
            datas.add(getFakeUiData("3"))
            datas.add(getFakeUiData("4"))
            return datas
        }

        fun getFakeListRawDataOneTwo(): List<RawData> {
            return listOf(getFakeRawData("1"), getFakeRawData("2"))
        }

        fun getFakeResponseSuccessEmptyList(): Response<RawDataResponse> {
            val items: List<RawData> = emptyList()
            val body = RawDataResponse(false, items, 10)
            return Response.success(body)
        }

        fun getFakeResponseSuccessList(): Response<RawDataResponse> {
            val items: List<RawData> = getFakeListRawDataOneTwo()
            val body = RawDataResponse(false, items, 10)
            return Response.success(body)
        }
    }
}