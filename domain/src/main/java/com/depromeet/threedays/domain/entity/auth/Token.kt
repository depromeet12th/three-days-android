package com.depromeet.threedays.domain.entity.auth

data class Token(
    val accessToken: String,
    val refreshToken: String
)
