package com.depromeet.threedays.domain.repository

interface DeviceUniqueIdRepository {
    suspend fun save(deviceUniqueId: String): String
    suspend fun findOne(): String?
}
