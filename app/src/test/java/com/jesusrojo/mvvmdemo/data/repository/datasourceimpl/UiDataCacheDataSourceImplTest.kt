package com.jesusrojo.mvvmdemo.data.repository.datasourceimpl

import com.google.common.truth.Truth.assertThat
import com.jesusrojo.mvvmdemo.data.model.UiData
import com.jesusrojo.mvvmdemo.data.repository.fake.FakeRepository
import kotlinx.coroutines.test.runBlockingTest


import org.junit.Test


class UiDataCacheDataSourceImplTest {

    private val sut = UiDataCacheDataSourceImpl()

    @Test
    fun saveAllToCacheOK() = runBlockingTest { //coroutinesTestRule.testDispatcher.runBlockingTest {

            val datas = emptyList<UiData>()
            sut.saveAllToCache(datas)

            val results = sut.fetchAllFromCache()

            assertThat(results).isEqualTo(datas)
        }

    @Test
    fun fetchAllFromCacheOK() = runBlockingTest {

            val datas = FakeRepository.getFakeListItemsOneTwo()
            sut.saveAllToCache(datas)

            val results = sut.fetchAllFromCache()

            assertThat(results).isEqualTo(datas)
        }

    @Test
    fun deleteAllInCacheOK() = runBlockingTest {

            val datas = FakeRepository.getFakeListItemsOneTwo()
            sut.saveAllToCache(datas)

            var results = sut.fetchAllFromCache()
            assertThat(results).isEqualTo(datas)

            sut.deleteAllInCache()

            results = sut.fetchAllFromCache()
            assertThat(results).isEqualTo(emptyList<UiData>())
        }
}