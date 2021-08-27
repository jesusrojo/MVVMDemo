package com.jesusrojo.mvvmdemo.domain.usecase

import com.jesusrojo.mvvmdemo.domain.repository.UiDataRepository
import javax.inject.Inject


class DeleteAllCacheUseCase @Inject constructor (
    private val repository: UiDataRepository
) {
    suspend fun execute() = repository.deleteAllCache()
}