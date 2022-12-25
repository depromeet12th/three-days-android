package com.depromeet.threedays.domain.usecase.datastore

import com.depromeet.threedays.domain.repository.DataStoreRepository
import javax.inject.Inject

class ResetDataStoreUseCase @Inject constructor(val repository: DataStoreRepository) {
    suspend fun execute() = repository.resetDataStore()
}
