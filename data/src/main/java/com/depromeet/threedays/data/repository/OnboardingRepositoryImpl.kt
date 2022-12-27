package com.depromeet.threedays.data.repository

import com.depromeet.threedays.data.datasource.datastore.DataStoreDataSource
import com.depromeet.threedays.domain.repository.OnboardingRepository
import javax.inject.Inject

class OnboardingRepositoryImpl @Inject constructor(
    private val dataStoreDataSource: DataStoreDataSource,
) : OnboardingRepository {

    override suspend fun readOnboardnig(key: String): String? =
        dataStoreDataSource.readDataStore(key)

    override suspend fun writeOnboardnig(key: String, value: String) =
        dataStoreDataSource.writeDataStore(key = key, value = value)
}
