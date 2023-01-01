package com.depromeet.threedays.domain.repository

interface MaxLevelMateRepository {
    suspend fun readMaxLevelMate(key: String): String?
    suspend fun writeMaxLevelMate(key: String, value: String)
}
