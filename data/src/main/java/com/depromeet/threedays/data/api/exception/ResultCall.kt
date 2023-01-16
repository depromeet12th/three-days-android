package com.depromeet.threedays.data.api.exception

import com.depromeet.threedays.data.entity.base.ApiResponse
import com.depromeet.threedays.domain.exception.ThreeDaysException
import com.google.gson.Gson
import okhttp3.Request
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import timber.log.Timber
import java.io.IOException

class ResultCall<T>(private val call: Call<T>, private val gson: Gson) : Call<Result<T>> {

    override fun enqueue(callback: Callback<Result<T>>) {
        call.enqueue(object : Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
                Timber.tag("ResultCall - onResponse").d("response : $response")
                Timber.tag("ResultCall - onResponse").d("response.body() : ${response.body()}")
                val jsonBody = gson.toJson(response.body())
                val body = gson.fromJson(
                    jsonBody,
                    ApiResponse::class.java
                )
                Timber.tag("ResultCall - onResponse").d("jsonBody : $jsonBody")
                Timber.tag("ResultCall - onResponse").d("body : $body")
                val message: String = body.message
                if (response.isSuccessful) {
                    Timber.tag("ResultCall - onResponse").d("response.isSuccessful")
                    callback.onResponse(
                        this@ResultCall,
                        Response.success(
                            response.code(),
                            Result.success(response.body()!!)
                        )
                    )
                } else {
                    Timber.tag("ResultCall - onResponse").e("error: ${ThreeDaysException(message, HttpException(response))}")

                    callback.onResponse(
                        this@ResultCall,
                        Response.success(
                            Result.failure(
                                ThreeDaysException(message, HttpException(response))
                            )
                        )
                    )
                }
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                val message = when (t) {
                    is IOException -> "인터넷 연결이 끊겼습니다."
                    is HttpException -> "알 수 없는 오류가 발생했어요."
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
        return ResultCall(call.clone(), gson)
    }

    override fun request(): Request {
        return call.request()
    }

    override fun timeout(): Timeout {
        return call.timeout()
    }
}
