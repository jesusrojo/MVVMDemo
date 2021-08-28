package com.jesusrojo.mvvmdemo.data.repository.datasourceimpl

import com.google.common.truth.Truth.assertThat
import com.jesusrojo.mvvmdemo.data.db.UiDataDAO
import com.jesusrojo.mvvmdemo.data.model.UiData
import com.jesusrojo.mvvmdemo.data.repository.fake.FakeRepository
import com.jesusrojo.mvvmdemo.utilunittests.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import org.mockito.Mockito.times


class UiDataLocalDataSourceImplTest {

    private val myDAO: UiDataDAO = mock()
    private val sut = UiDataLocalDataSourceImpl(myDAO)
    private val emptyList = emptyList<UiData>()

    @Test
    fun fetchAllInDB_callDAO() = runBlockingTest {

        sut.fetchAllInDB()

        verify(myDAO, times(1)).fetchAll()
    }

    @Test
    fun ssaveAllToDB_callDAOInsertAll() = runBlockingTest {

        sut.saveAllToDB(emptyList)

        verify(myDAO, times(1)).insertAll(emptyList)
    }


    @Test
    fun fetchAllInDB_getEmptyList_And_callDAO() = runBlockingTest {
        val results = sut.fetchAllInDB()

        assertThat(results).isEqualTo(emptyList<UiData>())

        verify(myDAO, times(1)).fetchAll()
    }



    @Test
    fun fetchAllInDB_returnFakeListMocked() = runBlockingTest {

        val datas = FakeRepository.getFakeListItemsOneTwo()
        whenever(myDAO.fetchAll()).thenReturn(datas)

        val results = sut.fetchAllInDB()

        assertThat(results).isEqualTo(datas)
    }
}