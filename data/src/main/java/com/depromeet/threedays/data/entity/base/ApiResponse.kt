package com.depromeet.threedays.data.entity.base

import com.depromeet.threedays.domain.exception.ThreeDaysException
import timber.log.Timber

data class ApiResponse<T>(
    val data: T?,
    val message: String,
    val code: String
)

fun <T>Result<ApiResponse<T>>.getResult(): Result<T> {
    this.onSuccess { response ->
        if(response.data is Unit) {
            Timber.d("response.data is Unit, response.data : ${response.data}")
            return Result.success(response.data)
        }
        if(response.data is List<*>) {
            Timber.d("response.data is List<*>, response.data : ${response.data}")
            return Result.success(emptyList<T>() as T)
        }

        val data = response.data ?: run {
            return Result.failure(ThreeDaysException("데이터가 비어있습니다.", IllegalStateException()))
        }

        return Result.success(data)
    }.onFailure { throwable ->
        return Result.failure(throwable)
    }
    return Result.failure(IllegalStateException("알 수 없는 오류가 발생했습니다."))
}
