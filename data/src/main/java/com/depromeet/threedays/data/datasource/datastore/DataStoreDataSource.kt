package com.depromeet.threedays.data.datasource.datastore

interface DataStoreDataSource {
    suspend fun readDataStore(key: String): String?
    suspend fun writeDataStore(key: String, value: String)
    suspend fun removeDataStore(key: String)
    suspend fun resetDataStore()
}
