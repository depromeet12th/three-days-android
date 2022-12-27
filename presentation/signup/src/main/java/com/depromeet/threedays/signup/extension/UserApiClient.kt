package com.depromeet.threedays.signup.extension

import android.content.Context
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

suspend fun UserApiClient.Companion.loginWithKakaoOrThrow(context: Context): OAuthToken {
    return if (instance.isKakaoTalkLoginAvailable(context)) {
        try {
            UserApiClient.loginWithKakaoTalk(context)
        } catch (error: Throwable) {
            if (error is ClientError && error.reason == ClientErrorCause.Cancelled) throw error

            UserApiClient.loginWithKakaoAccount(context)
        }
    } else {
        UserApiClient.loginWithKakaoAccount(context)
    }
}

suspend fun UserApiClient.Companion.loginWithKakaoTalk(context: Context): OAuthToken {
    return suspendCancellableCoroutine { continuation ->
        instance.loginWithKakaoTalk(context) { token, error ->
            when {
                error != null -> {
                    continuation.resumeWithException(error)
                }
                token != null -> {
                    continuation.resume(token)
                }
                else -> {
                    continuation.resumeWithException(RuntimeException("kakao access token 받기 실패"))
                }
            }
        }
    }
}

suspend fun UserApiClient.Companion.loginWithKakaoAccount(context: Context): OAuthToken {
    return suspendCancellableCoroutine { continuation ->
        instance.loginWithKakaoAccount(context) { token, error ->
            when {
                error != null -> {
                    continuation.resumeWithException(error)
                }
                token != null -> {
                    continuation.resume(token)
                }
                else -> {
                    continuation.resumeWithException(RuntimeException("kakao access token 받기 실패"))
                }
            }
        }
    }
}
