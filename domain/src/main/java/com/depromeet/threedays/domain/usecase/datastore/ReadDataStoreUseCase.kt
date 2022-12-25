package com.depromeet.threedays.domain.usecase.datastore

import com.depromeet.threedays.domain.repository.DataStoreRepository
import javax.inject.Inject

class ReadDataStoreUseCase @Inject constructor(val repository: DataStoreRepository) {
    suspend fun execute(key: String): String? = repository.readDataStore(key)
}
