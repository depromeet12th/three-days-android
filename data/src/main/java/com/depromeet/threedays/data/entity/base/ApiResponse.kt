package com.depromeet.threedays.data.entity.base

data class ApiResponse<T>(
    val data: T?,
    val message: String,
    val code: String
)
