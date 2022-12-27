package com.depromeet.threedays.domain.repository

interface OnboardingRepository {
    suspend fun readOnboardnig(key: String): String?
    suspend fun writeOnboardnig(key: String, value: String)
}
