package com.depromeet.threedays.data.entity.auth

import com.depromeet.threedays.domain.entity.auth.Token

data class RenewalTokenResponse (
    val data: Token,
    val message: String,
    val code: String
)
