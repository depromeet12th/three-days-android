package com.depromeet.threedays.data.repository

import com.depromeet.threedays.data.datasource.datastore.DataStoreDataSource
import com.depromeet.threedays.domain.repository.TodayFirstVisitRepository
import javax.inject.Inject

class TodayFirstVisitRepositoryImpl @Inject constructor(
    private val dataStoreDataSource: DataStoreDataSource,
) : TodayFirstVisitRepository {

    override suspend fun readTodayFirstVisit(key: String): String? =
        dataStoreDataSource.readDataStore(key)

    override suspend fun writeTodayFirstVisit(key: String, value: String) =
        dataStoreDataSource.writeDataStore(key = key, value = value)
}
