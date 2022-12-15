package com.depromeet.threedays.data.api

import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {
    private var accessToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzM4NCJ9.eyJtZW1iZXJJZCI6MSwiaWF0IjoxNjcwNTc4MzAzLCJleHAiOjE2NzI3MjU3ODZ9._eQlJYPHHaruePkVFvA6vD8zy2qj9dNLjtI0rCCQ5jLBia5Xiz5QouSRdtawHkUF"

    private fun authorization(token: String): String = "Bearer $token"

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder().addHeader("Authorization", authorization(accessToken)).build()

        return chain.proceed(request)
    }
}
