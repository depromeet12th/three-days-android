package com.depromeet.threedays.data.repository

import com.depromeet.threedays.data.datasource.datastore.DataStoreDataSource
import com.depromeet.threedays.domain.repository.LocalDataRepository
import javax.inject.Inject

class LocalDataRepositoryImpl @Inject constructor(
    private val dataStoreDataSource: DataStoreDataSource,
) : LocalDataRepository {
    override suspend fun removeAll() {
        dataStoreDataSource.resetDataStore()
    }
}
