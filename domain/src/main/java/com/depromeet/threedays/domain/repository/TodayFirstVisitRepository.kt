package com.depromeet.threedays.domain.repository

interface TodayFirstVisitRepository {
    suspend fun readTodayFirstVisit(key: String): String?
    suspend fun writeTodayFirstVisit(key: String, value: String)
}
