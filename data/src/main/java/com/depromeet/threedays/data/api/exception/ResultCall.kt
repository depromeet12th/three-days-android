package com.depromeet.threedays.data.api.exception

import com.depromeet.threedays.data.entity.base.ApiResponse
import com.depromeet.threedays.domain.exception.ThreeDaysException
import com.depromeet.threedays.domain.key.NO_INTERNET_CONNECTION
import com.depromeet.threedays.domain.key.UNKNOWN_ERROR
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.Request
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import timber.log.Timber
import java.io.IOException
import java.lang.reflect.Type

class ResultCall<T>(private val call: Call<T>, private val gson: Gson) : Call<Result<T>> {

    override fun enqueue(callback: Callback<Result<T>>) {
        call.enqueue(object : Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
                val type: Type = object : TypeToken<ApiResponse<Any>>() {}.type
                val body = gson.fromJson<ApiResponse<Any>>(response.body().toString(), type)
                val code = body.code

                if (response.isSuccessful) {
                    callback.onResponse(
                        this@ResultCall,
                        Response.success(
                            response.code(),
                            Result.success(response.body()!!)
                        )
                    )
                } else {
                    Timber.tag("ResultCall - onResponse").e("code : $code / ${ThreeDaysException(code, HttpException(response))}")

                    callback.onResponse(
                        this@ResultCall,
                        Response.success(
                            Result.failure(
                                ThreeDaysException(code, HttpException(response))
                            )
                        )
                    )
                }


            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                val code = when (t) {
                    is IOException -> NO_INTERNET_CONNECTION
                    is HttpException -> UNKNOWN_ERROR
                    else -> t.localizedMessage
                }
                callback.onResponse(
                    this@ResultCall,
                    Response.success(Result.failure(ThreeDaysException(code, t)))
                )

                Timber.tag("ResultCall - onFailure").e("code : $code / ${ThreeDaysException(code, t)}")
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
        return ResultCall(call.clone(), gson)
    }

    override fun request(): Request {
        return call.request()
    }

    override fun timeout(): Timeout {
        return call.timeout()
    }
}
