package com.jesusrojo.mvvmdemo.data.db

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import com.jesusrojo.mvvmdemo.data.repository.fake.FakeRepository
import com.jesusrojo.mvvmdemo.utilsandroidtests.BaseUITest
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class UiDataDaoTest: BaseUITest() {

    private lateinit var dao: UiDataDAO
    private lateinit var database: UiDataDatabase

    @Before
    fun setUp() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            UiDataDatabase::class.java
        ).build()

        dao = database.getMyDAO()
    }

    @After fun tearDown() = database.close()

    @Test
    fun saveTest() = runBlocking {

        val enties = FakeRepository.getFakeListItemsOneTwo()

        dao.insertAll(enties)

        val resultEnties = dao.fetchAll()
        assertThat(resultEnties).isEqualTo(enties)
        assertThat(resultEnties.size).isEqualTo(2)
    }

    @Test
    fun deleteTest() = runBlocking {

        val enties = FakeRepository.getFakeListItemsOneTwo()

        dao.insertAll(enties)

        dao.deleteAll()

        val resultEnties = dao.fetchAll()
        assertThat(resultEnties).isEmpty()
    }
}