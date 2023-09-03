package com.depromeet.threedays.data.api.exception

import com.depromeet.threedays.data.entity.base.ErrorResponse
import com.depromeet.threedays.domain.exception.ThreeDaysException
import okhttp3.Request
import okio.Timeout
import retrofit2.*
import timber.log.Timber
import java.io.IOException

class ResultCall<T>(private val call: Call<T>, private val retrofit: Retrofit) : Call<Result<T>> {
    override fun enqueue(callback: Callback<Result<T>>) {
        call.enqueue(object : Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {

                if (response.isSuccessful) {

                    if(response.body() == null) {
                        callback.onResponse(
                            this@ResultCall,
                            Response.success(Result.failure(ThreeDaysException(ThreeDaysException.NO_BODY_RESPONSE, HttpException(response))))
                        )
                    }
                    else {
                        callback.onResponse(
                            this@ResultCall,
                            Response.success(response.code(), Result.success(response.body()!!))
                        )
                    }

                } else {

                    if(response.errorBody() == null) {
                        callback.onResponse( this@ResultCall,
                            Response.success(Result.failure(ThreeDaysException(ThreeDaysException.NO_ERROR_BODY_RESPONSE, HttpException(response))))
                        )
                    }
                    else {
                        val errorBody = retrofit.responseBodyConverter<ErrorResponse>(
                            ErrorResponse::class.java,
                            ErrorResponse::class.java.annotations
                        ).convert(response.errorBody()!!)

                        val message: String = errorBody?.message ?: ThreeDaysException.NO_ERROR_MESSAGE_RESPONSE

                        callback.onResponse(this@ResultCall,
                            Response.success(Result.failure(ThreeDaysException(message, HttpException(response))))
                        )

                        Timber.tag("ResultCall - onResponse").e("${ThreeDaysException(message, HttpException(response))}")
                    }

                }
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                val message = when (t) {
                    is IOException -> ThreeDaysException.INTERNET_CONNECTION_WAS_LOST
                    is HttpException -> ThreeDaysException.UNKNOWN_EXCEPTION
                    else -> t.localizedMessage
                }
                callback.onResponse(
                    this@ResultCall,
                    Response.success(Result.failure(ThreeDaysException(message, t)))
                )

                Timber.tag("ResultCall - onFailure").e("${ThreeDaysException(message, t)}")
            }
        })
    }

    override fun isExecuted(): Boolean {
        return call.isExecuted
    }

    override fun execute(): Response<Result<T>> {
        return Response.success(Result.success(call.execute().body()!!))
    }

    override fun cancel() {
        call.cancel()
    }

    override fun isCanceled(): Boolean {
        return call.isCanceled
    }

    override fun clone(): Call<Result<T>> {
        return ResultCall(call.clone(), retrofit)
    }

    override fun request(): Request {
        return call.request()
    }

    override fun timeout(): Timeout {
        return call.timeout()
    }
}
