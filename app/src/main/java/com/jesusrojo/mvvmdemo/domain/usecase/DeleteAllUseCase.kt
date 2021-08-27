package com.jesusrojo.mvvmdemo.domain.usecase

import com.jesusrojo.mvvmdemo.domain.repository.UiDataRepository
import javax.inject.Inject


class DeleteAllUseCase @Inject constructor (
    private val repository: UiDataRepository
) {
    suspend fun execute() = repository.deleteAll()
}