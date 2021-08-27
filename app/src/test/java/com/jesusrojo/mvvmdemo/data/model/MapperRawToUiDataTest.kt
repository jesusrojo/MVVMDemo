package com.jesusrojo.mvvmdemo.data.model

import com.google.common.truth.Truth.assertThat
import com.jesusrojo.mvvmdemo.data.repository.datasource.FakeRepository
import com.jesusrojo.mvvmdemo.utilunittests.BaseUnitTest

import org.junit.Test


class MapperRawToUiDataTest: BaseUnitTest() {

    private val sut = MapperRawToUiData()

    private val rawData = FakeRepository.getFakeRawData("MapperTest")
    private val uiData = sut(listOf(rawData))
    private val userIndexZero = uiData[0]

    @Test
    fun keepSameVariables() {
        assertThat(rawData.id).isEqualTo(userIndexZero.dataId)
        assertThat(rawData.name).isEqualTo(userIndexZero.name)
        assertThat(rawData.owner?.avatarUrl).isEqualTo(userIndexZero.avatarUrl)
        assertThat(rawData.owner?.ownerName).isEqualTo(userIndexZero.title)
        assertThat(rawData.description).isEqualTo(userIndexZero.description)

        assertThat("ID: 1111\n" +
                "NAME: nameMapperTest\n" +
                "DESCRIPTION: descriptionMapperTest\n" +
                "FORKS: 2222\n" +
                "STARS: 3333\n" +
                "AUTHOR NAME: titleMapperTest").isEqualTo(userIndexZero.fullDescription)
        assertThat(userIndexZero.toString()).isEqualTo("*** FULL DESCRIPTION ***\n"
                +userIndexZero.fullDescription)

        assertThat(rawData.forksCount).isEqualTo(userIndexZero.forksCount)
        assertThat(rawData.stargazersCount).isEqualTo(userIndexZero.startsCount)
    }
    @Test
    fun keepSameVariables2() {
        val dataId = 1111
        assertThat(dataId).isEqualTo(userIndexZero.dataId)
        assertThat("nameMapperTest").isEqualTo(userIndexZero.name)
        assertThat("avatarUrlMapperTest").isEqualTo(userIndexZero.avatarUrl)
        assertThat("titleMapperTest").isEqualTo(userIndexZero.title)
        assertThat("descriptionMapperTest").isEqualTo(userIndexZero.description)
        //assertThat("fullDescriptionMapperTest").isEqualTo(userIndexZero.fullDescription)

        assertThat("ID: 1111\n" +
                "NAME: nameMapperTest\n" +
                "DESCRIPTION: descriptionMapperTest\n" +
                "FORKS: 2222\n" +
                "STARS: 3333\n" +
                "AUTHOR NAME: titleMapperTest").isEqualTo(userIndexZero.fullDescription)

        assertThat(userIndexZero.toString()).isEqualTo("*** FULL DESCRIPTION ***\n"
                +userIndexZero.fullDescription)


        val forksCount = 2222
        assertThat(forksCount).isEqualTo(userIndexZero.forksCount)

        val startsCount = 3333
        assertThat(startsCount).isEqualTo(userIndexZero.startsCount)
    }
}