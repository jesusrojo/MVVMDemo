package com.jesusrojo.mvvmdemo.domain.usecase

import com.google.common.truth.Truth
import com.jesusrojo.mvvmdemo.data.repository.fake.FakeRepository
import com.jesusrojo.mvvmdemo.domain.repository.UiDataRepository
import com.jesusrojo.mvvmdemo.util.Resource
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.test.runBlockingTest

import org.junit.Test

class FetchNextDatasUseCaseTest {

    private val repository: UiDataRepository = mock()
    private val sut = FetchNextDatasUseCase(repository)

    private val page = 1
    private val query = "Kotlin"
    @Test
    fun execute_callRepository() = runBlockingTest {

        sut.execute(query)

        verify(repository, times(1)).fetchNextDatas(query)
    }

    @Test
    fun execute_callRepository_listOK() = runBlockingTest {

        val datas = FakeRepository.getFakeListItemsOneTwo()
        whenever(repository.fetchNextDatas(query)).thenReturn(Resource.Success(datas))

        val results = sut.execute(query)

        Truth.assertThat(results.data).isEqualTo(datas)
        Truth.assertThat(results.data?.size).isEqualTo(datas.size)
        Truth.assertThat(results.data?.size).isEqualTo(2)
    }
}