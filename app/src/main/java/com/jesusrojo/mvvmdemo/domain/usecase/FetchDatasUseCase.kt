package com.jesusrojo.mvvmdemo.domain.usecase

import com.jesusrojo.mvvmdemo.data.model.UiData
import com.jesusrojo.mvvmdemo.domain.repository.UiDataRepository
import com.jesusrojo.mvvmdemo.util.Resource

import javax.inject.Inject

class FetchDatasUseCase @Inject constructor(
    private val repository: UiDataRepository
) {
    suspend fun execute(page: Int, query: String): Resource<List<UiData>> =
        repository.fetchDatas(page, query)
}