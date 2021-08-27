@file:Suppress("unused")

package com.jesusrojo.mvvmdemo.utilunittests

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import org.junit.Rule

open class BaseUnitTest {

    //https://medium.com/swlh/kotlin-coroutines-in-android-unit-test-28ff280fc0d5
    protected val testDispatcher = TestCoroutineDispatcher()

    //https://medium.com/androiddevelopers/easy-coroutines-in-android-viewmodelscope-25bffb605471
    @get:Rule
    var coroutinesTestRule = CoroutinesTestRule()

//    @get:Rule
//    var coroutinesTestRule = MainCoroutineScopeRule()


    // Executes each task synchronously using Architecture Components.
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    // Set the main coroutines dispatcher for unit testing.



    //BLUE
    // Set the main coroutines dispatcher for unit testing.
    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

//    // Executes each task synchronously using Architecture Components.
//    @get:Rule
//    var instantExecutorRule = InstantTaskExecutorRule()



}