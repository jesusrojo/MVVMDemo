@file:Suppress("unused")

package com.jesusrojo.mvvmdemo.utilunittests

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import org.junit.Rule
import org.junit.runner.RunWith
import org.junit.runners.JUnit4


open class BaseUnitTest {

    //https://medium.com/swlh/kotlin-coroutines-in-android-unit-test-28ff280fc0d5
    protected val testCoroutineDispatcher = TestCoroutineDispatcher()

    //https://medium.com/androiddevelopers/easy-coroutines-in-android-viewmodelscope-25bffb605471
    @get:Rule
    var coroutinesTestRule = CoroutinesTestRule()

//    @get:Rule
//    var coroutinesTestRule = MainCoroutineScopeRule()

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    //BLUE
    // Set the main coroutines dispatcher for unit testing.
    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()
}