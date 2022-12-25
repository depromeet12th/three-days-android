package com.depromeet.threedays.domain.repository

interface DataStoreRepository {
    suspend fun readDataStore(key: String): String?
    suspend fun writeDataStore(key: String, value: String)
    suspend fun resetDataStore()
}
