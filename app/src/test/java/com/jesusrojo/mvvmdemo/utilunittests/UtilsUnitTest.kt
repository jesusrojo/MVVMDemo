package com.jesusrojo.mvvmdemo.utilunittests

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import io.mockk.*

import org.mockito.Mockito


//https://www.arthlimchiu.com/2019/07/03/how-to-unit-test-live-data-and-view-model.html
inline fun <reified T> mock(): T = Mockito.mock(T::class.java)


//https://stackoverflow.com/questions/63339306/viewmodel-unit-testing-multiple-view-states-with-livedata-coroutines-and-mockk
inline fun <reified T > LiveData<T>.captureValues(): List<T?> {
    val mockObserver = mockk<Observer<T>>()
    val list = mutableListOf<T?>()
    every { mockObserver.onChanged(captureNullable(list))} just runs
    this.observeForever(mockObserver)
    return list
}

//@Test
//fun fetchDatas_callUseCase() =
//
//        // GIVEN  //Arrange
//
//
//        // WHEN //Act
//
//
//        // THEN //Assert
//
//
//    }

