package com.depromeet.threedays.data.repository

import com.depromeet.threedays.data.datasource.datastore.DataStoreDataSource
import com.depromeet.threedays.domain.repository.NotificationPermissionRepository
import javax.inject.Inject

class NotificationPermissionRepositoryImpl @Inject constructor(
    private val dataStoreDataSource: DataStoreDataSource,
) : NotificationPermissionRepository {

    override suspend fun readNotificationPermission(key: String): String? =
        dataStoreDataSource.readDataStore(key)

    override suspend fun writeNotificationPermission(key: String, value: String) =
        dataStoreDataSource.writeDataStore(key = key, value = value)
}
