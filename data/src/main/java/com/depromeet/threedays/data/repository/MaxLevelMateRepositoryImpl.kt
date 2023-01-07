package com.depromeet.threedays.data.repository

import com.depromeet.threedays.data.datasource.datastore.DataStoreDataSource
import com.depromeet.threedays.domain.repository.MaxLevelMateRepository
import javax.inject.Inject

class MaxLevelMateRepositoryImpl @Inject constructor(
    private val dataStoreDataSource: DataStoreDataSource,
) : MaxLevelMateRepository {

    override suspend fun readMaxLevelMate(key: String): String? {
        return dataStoreDataSource.readDataStore(key)
    }

    override suspend fun writeMaxLevelMate(key: String, value: String) {
        return dataStoreDataSource.writeDataStore(key = key, value = value)
    }
}
