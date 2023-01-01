package com.depromeet.threedays.domain.entity

data class DataState<out T>(
    val status: Status,
    var data: @UnsafeVariance T?,
    val message: String?,
){
    companion object{
        fun <T> loading(): DataState<T> {
            return DataState(Status.LOADING, null, null)
        }

        fun <T> success(data: T?): DataState<T> {
            return DataState(Status.SUCCESS, data, null)
        }

        fun <T> error(msg: String): DataState<T> {
            return DataState(Status.ERROR, null, msg)
        }

        fun <T> fail(msg: String): DataState<T> {
            return DataState(Status.FAIL, null, msg)
        }
    }
}
