package com.depromeet.threedays.domain.usecase.datastore

import com.depromeet.threedays.domain.repository.DataStoreRepository
import javax.inject.Inject

class WriteDataStoreUseCase @Inject constructor(private val repository: DataStoreRepository) {
    suspend fun execute(key: String, value: String) = repository.writeDataStore(key, value)
}
