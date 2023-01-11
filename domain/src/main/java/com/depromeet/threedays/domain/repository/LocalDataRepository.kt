package com.depromeet.threedays.domain.repository

interface LocalDataRepository {
    suspend fun removeAll()
}
