package com.jesusrojo.mvvmdemo.data.repository.datasourceimpl

import com.google.common.truth.Truth.assertThat
import com.jesusrojo.mvvmdemo.data.api.RawDataApiService
import com.jesusrojo.mvvmdemo.data.model.RawData
import com.jesusrojo.mvvmdemo.data.model.RawDataResponse
import com.jesusrojo.mvvmdemo.data.repository.fake.FakeRepository
import com.jesusrojo.mvvmdemo.data.repository.fake.FakeUtil

import com.jesusrojo.mvvmdemo.utilunittests.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test

import org.mockito.Mockito.times
import retrofit2.Response

class UiDataRemoteDataSourceImplTest {

    private val page = 1
    private val query = "Kotlin"
    private val service: RawDataApiService = mock()
    private val sut = UiDataRemoteDataSourceImpl(service)

    @Test
    fun fetchUiData_callService() = runBlockingTest { //coroutinesTestRule.testDispatcher.runBlockingTest {
        val response = sut.fetchAllApi(page, query)
        verify(service, times(1))
            .fetchRawDatas(page, query)
    }

    @Test
    fun fetchUiData_callService_returnNull() = runBlockingTest {
        val response = sut.fetchAllApi(page, query)
        verify(service, times(1))
            .fetchRawDatas(page, query)
        assertThat(response).isEqualTo(null)
    }

    @Test
    fun fetchUiData_withListEmptyMocked_isReturnedByService() = runBlockingTest {

        val items: List<RawData> = emptyList()
        val body = RawDataResponse(false, items, 10)
        val response: Response<RawDataResponse> = Response.success(body)
        whenever(service.fetchRawDatas(page, query)).thenReturn(response)

        val responseResult = sut.fetchAllApi(page, query)

        val bodyResult = responseResult.body()

        assertThat(bodyResult).isEqualTo(body)
        assertThat(bodyResult?.rawDatas).isEqualTo(items)
        assertThat(bodyResult?.rawDatas?.size).isEqualTo(0)
    }

    @Test
    fun fetchUiData_withListFakeMocked_isReturnedByService() = runBlockingTest {

        val items: List<RawData> = FakeUtil.getFakeListRawDataOneTwo()
        val body = RawDataResponse(false, items, 10)
        val response: Response<RawDataResponse> = Response.success(body)
        whenever(service.fetchRawDatas(page, query)).thenReturn(response)

        val responseResult = sut.fetchAllApi(page,query)

        val bodyResult = responseResult.body()

        assertThat(bodyResult).isEqualTo(body)
        assertThat(bodyResult?.rawDatas).isEqualTo(items)
        assertThat(bodyResult?.rawDatas?.size).isEqualTo(items.size)
        assertThat(bodyResult?.rawDatas!![0].name).isEqualTo(items[0].name)
        assertThat(bodyResult.rawDatas!![0].name).isEqualTo("name1")
    }
}