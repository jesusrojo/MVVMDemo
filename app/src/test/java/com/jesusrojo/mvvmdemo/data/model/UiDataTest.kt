package com.jesusrojo.mvvmdemo.data.model

import com.google.common.truth.Truth.assertThat
import com.jesusrojo.mvvmdemo.data.repository.fake.FakeRepository
import org.junit.Test


class UiDataTest {

    private val uiDataFake = FakeRepository.getFakeUiData("Testing")
    private val sut = uiDataFake

    @Test
    fun testAllVariables() {
        assertThat(uiDataFake.id).isEqualTo(0) // for room
        assertThat(uiDataFake.dataId).isEqualTo(sut.dataId) //from api
        assertThat(uiDataFake.name).isEqualTo(sut.name)
        assertThat(uiDataFake.avatarUrl).isEqualTo(sut.avatarUrl)
        assertThat(uiDataFake.title).isEqualTo(sut.title)
        assertThat(uiDataFake.description).isEqualTo(sut.description)
        assertThat(uiDataFake.fullDescription).isEqualTo(sut.fullDescription)
        assertThat(uiDataFake.forksCount).isEqualTo(sut.forksCount)
        assertThat(uiDataFake.startsCount).isEqualTo(sut.startsCount)
    }
    @Test
    fun testAllVariables2() {
        val dataId = 1111
        assertThat(dataId).isEqualTo(sut.dataId)

        assertThat("nameTesting").isEqualTo(sut.name)
        assertThat("avatarUrlTesting").isEqualTo(sut.avatarUrl)
        assertThat("titleTesting").isEqualTo(sut.title)
        assertThat("descriptionTesting").isEqualTo(sut.description)
        assertThat("fullDescriptionTesting").isEqualTo(sut.fullDescription)

        val forksCount = 2222
        assertThat(forksCount).isEqualTo(sut.forksCount)

        val startsCount = 3333
        assertThat(startsCount).isEqualTo(sut.startsCount)

    }
}