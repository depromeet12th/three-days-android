package com.depromeet.threedays.data.repository

import com.depromeet.threedays.data.datasource.datastore.DataStoreDataSource
import com.depromeet.threedays.domain.repository.DeviceUniqueIdRepository
import javax.inject.Inject

class DeviceUniqueIdRepositoryImpl @Inject constructor(
    private val dataStoreDataSource: DataStoreDataSource,
) : DeviceUniqueIdRepository {
    override suspend fun save(deviceUniqueId: String): String {
        dataStoreDataSource.writeDataStore(
            key = DEVICE_UNIQUE_ID_KEY,
            value = deviceUniqueId,
        )
        return deviceUniqueId
    }

    override suspend fun findOne(): String? {
        return dataStoreDataSource.readDataStore(
            key = DEVICE_UNIQUE_ID_KEY,
        )
    }

    companion object {
        const val DEVICE_UNIQUE_ID_KEY = "THREE_DAYS:DEVICE_UNIQUE_ID"
    }
}
