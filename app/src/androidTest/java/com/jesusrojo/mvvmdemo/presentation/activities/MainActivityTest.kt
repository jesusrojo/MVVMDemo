package com.jesusrojo.mvvmdemo.presentation.activities


import com.jesusrojo.mvvmdemo.R
import com.jesusrojo.mvvmdemo.utilsandroidtests.BaseUITest
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertNotDisplayed
import org.junit.Test


class MainActivityTest: BaseUITest(){

    @Test
    fun displayScreenTitle() {
        assertDisplayed("First Fragment")
    }

    @Test
    fun displayLayoutInitial() {
        Thread.sleep(500)
        assertDisplayed(R.id.swipe_layout_container_items)
        assertDisplayed(R.id.recycler_view_items)
    }

    @Test
    fun hidesLoader() {
        assertNotDisplayed(R.id.progress_bar_items)
    }


    // IDLING RESOURCE
//    @Test
//    fun displaysLoaderWhileFetchingDatas() {
//        IdlingRegistry.getInstance().unregister(idlingResource)
////        Thread.sleep(500)
//        assertDisplayed(R.id.progress_bar_items)
//    }




//    @Before
//    fun setup() {
//        IdlingRegistry.getInstance().register(idlingResource)
//    }
//
//    @After
//    fun tearDown() {
//        tearDownChild()
//        IdlingRegistry.getInstance().unregister(idlingResource)
//    }
}