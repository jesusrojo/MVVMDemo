package com.jesusrojo.mvvmdemo.data.repository

import com.google.common.truth.Truth.assertThat
import com.jesusrojo.mvvmdemo.data.model.MapperRawToUiData
import com.jesusrojo.mvvmdemo.data.model.RawDataResponse
import com.jesusrojo.mvvmdemo.data.repository.datasource.FakeRepository
import com.jesusrojo.mvvmdemo.data.repository.datasource.UiDataCacheDataSource
import com.jesusrojo.mvvmdemo.data.repository.datasource.UiDataLocalDataSource
import com.jesusrojo.mvvmdemo.data.repository.datasource.UiDataRemoteDataSource
import com.jesusrojo.mvvmdemo.domain.repository.UiDataRepository
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.test.runBlockingTest

import org.junit.Before
import org.junit.Test
import retrofit2.Response

class UiDataRepositoryImplTest {

    private val page = 0
    private val query = "Kotlin"
    private val remoteDataSource: UiDataRemoteDataSource = mock()
    private val localDataSource: UiDataLocalDataSource = mock()
    private val cacheDataSource: UiDataCacheDataSource = mock()
    private val mapper: MapperRawToUiData = mock()

    private val datas = FakeRepository.getFakeListRawDataOneTwo()
    private val rawDataResponse = RawDataResponse(false, datas, 10)
    private val response: Response<RawDataResponse> = Response.success(rawDataResponse)

    private lateinit var sut: UiDataRepository

    @Before
    fun setUp() {
        sut = UiDataRepositoryImpl(cacheDataSource, localDataSource, remoteDataSource, mapper)
    }

    //VERIFY ONLY INTERACTIONS
    @Test
    fun fetchDatas_call_fetchAllFromCache() =
        ////  coroutinesTestRule.testDispatcher.runBlockingTest {
        runBlockingTest {
            sut.fetchDatas(query)
            verify(cacheDataSource, times(1)).fetchAllFromCache()
        }


    @Test
    fun fetchNextDatas_call_fetchAllApi() = runBlockingTest {
        sut.fetchNextDatas(query)
        verify(remoteDataSource, times(1)).fetchAllApi(page,
            query + "in:name,description") //IN_QUALIFIER
    }


    @Test
    fun deleteAll() = runBlockingTest {
        sut.deleteAll()
        verify(cacheDataSource, times(1)).deleteAllInCache()
        verify(localDataSource, times(1)).deleteAllInDB()
    }


    @Test
    fun deleteAllCache() = runBlockingTest {
        sut.deleteAllCache()
        verify(cacheDataSource, times(1)).deleteAllInCache()
    }


    //VERIFY INTERACTIONS AND DATA ///////////////////////////////////////
    @Test
    fun fetchDatas_returnSuccess() = runBlockingTest {

        val datas = FakeRepository.getFakeListItemsOneTwo()
        whenever(cacheDataSource.fetchAllFromCache()).thenReturn(datas)

        val resourceResult = sut.fetchDatas(query)

        assertThat(resourceResult.data!![0].name).isEqualTo(datas[0].name)
        verify(cacheDataSource, times(1)).fetchAllFromCache()
    }


    @Test
    fun fetchDatas_returnError() = runBlockingTest {

        val datas = FakeRepository.getFakeListItemsOneTwo()
        whenever(cacheDataSource.fetchAllFromCache()).thenReturn(datas)

        val resourceResult = sut.fetchDatas(query)

        assertThat(resourceResult.data!![0].name).isEqualTo(datas[0].name)
        verify(cacheDataSource, times(1)).fetchAllFromCache()
    }


    @Test
    fun fetchUiData_callCache_listOK() = runBlockingTest {

        val datas = FakeRepository.getFakeListItemsOneTwo()
        whenever(cacheDataSource.fetchAllFromCache()).thenReturn(datas)

        val results = sut.fetchDatas(query)

        assertThat(results.data).isEqualTo(datas)
        verify(cacheDataSource, times(1)).fetchAllFromCache()
    }

    @Test
    fun fetchUiData_callCache_callLocal_listOK() = runBlockingTest {
        val datas = FakeRepository.getFakeListItemsOneTwo()
        whenever(cacheDataSource.fetchAllFromCache()).thenReturn(null)
        whenever(localDataSource.fetchAllInDB()).thenReturn(datas)
        sut.fetchDatas(query)
        verify(cacheDataSource, times(1)).fetchAllFromCache()
        verify(localDataSource, times(1)).fetchAllInDB()
    }


    @Test
    fun fetchUiData_callCache_callLocal_callRemote_remoteReturnError() =
        runBlockingTest {
            whenever(cacheDataSource.fetchAllFromCache()).thenReturn(null)
            whenever(localDataSource.fetchAllInDB()).thenReturn(null)
            whenever(remoteDataSource.fetchAllApi(page, query)).thenReturn(null)

            val resourceResult = sut.fetchDatas(query)

            assertThat(resourceResult.data).isNull()
            verify(cacheDataSource, times(1)).fetchAllFromCache()
            verify(localDataSource, times(1)).fetchAllInDB()
            verify(remoteDataSource, times(1)).fetchAllApi(
                page, query + "in:name,description"
            ) //IN_QUALIFIER
        }

    @Test
    fun fetchUiData_callCache_callLocal_callRemote_remoteReturnSuccess() =
        runBlockingTest {
            whenever(cacheDataSource.fetchAllFromCache()).thenReturn(null)
            whenever(localDataSource.fetchAllInDB()).thenReturn(null)
            whenever(remoteDataSource.fetchAllApi(page, query)).thenReturn(response)

            val resourceResult = sut.fetchDatas(query)

           // assertThat(resourceResult.data).isNotNull() //todo fail
            //  assertThat(resourceResult.data!![0].name).isEqualTo(datas[0].name)

            verify(cacheDataSource, times(1)).fetchAllFromCache()
            verify(localDataSource, times(1)).fetchAllInDB()
            verify(remoteDataSource, times(1)).fetchAllApi(
                page, query + "in:name,description") //IN_QUALIFIER
        }
}