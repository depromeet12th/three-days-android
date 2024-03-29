package com.depromeet.threedays.data.api.interceptor

import android.content.Context
import android.content.Intent
import androidx.core.content.edit
import com.depromeet.threedays.buildproperty.BuildProperty
import com.depromeet.threedays.buildproperty.BuildPropertyRepository
import com.depromeet.threedays.data.entity.auth.RenewalTokenResponse
import com.depromeet.threedays.domain.key.ACCESS_TOKEN_KEY
import com.depromeet.threedays.domain.key.REFRESH_TOKEN_KEY
import com.depromeet.threedays.domain.key.SHARED_PREFERENCE_KEY
import com.depromeet.threedays.navigator.SignupNavigator
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.*
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    @ApplicationContext private val context: Context,
    private val client: OkHttpClient,
    private val gson: Gson,
    private val signupNavigator: SignupNavigator,
    private val buildPropertyRepository: BuildPropertyRepository,
) : Interceptor {
    private val preference by lazy {
        context.getSharedPreferences(SHARED_PREFERENCE_KEY, Context.MODE_PRIVATE)
    }

    private var accessToken: String = ""
        get() {
            return preference.getString(ACCESS_TOKEN_KEY, null) ?: ""
        }
        private set(token) {
            preference.edit(true) {
                putString(ACCESS_TOKEN_KEY, token)
            }
            field = token
        }

    private var refreshToken: String = ""
        get() {
            return preference.getString(REFRESH_TOKEN_KEY, null) ?: ""
        }
        private set(token) {
            preference.edit(true) {
                putString(REFRESH_TOKEN_KEY, token)
            }
            field = token
        }

    private fun authorization(token: String): String = "Bearer $token"

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder().addHeader(AUTHORIZATION, authorization(accessToken)).build()
        val oldResponse = chain.proceed(request)
        when (oldResponse.code) {
            401 -> {
                for (i in 1..REPEAT_NUM) {
                    val requestBody: RequestBody = FormBody.Builder().build()
                    val renewalTokensRequest = chain.request().newBuilder()
                        .post(requestBody)
                        .url("${buildPropertyRepository.get(BuildProperty.BASE_URL)}/api/v1/members/tokens")
                        .addHeader(X_THREE_DAYS_REFRESH_TOKEN, refreshToken)
                        .addHeader("Content-Type", "application/json; charset=UTF-8")
                        .build()
                    val renewalTokensResponse = client.newCall(renewalTokensRequest).execute()

                    if (renewalTokensResponse.isSuccessful) {
                        val newTokensData = gson.fromJson(
                            renewalTokensResponse.body?.string(),
                            RenewalTokenResponse::class.java
                        )
                        accessToken = newTokensData.data.accessToken
                        refreshToken = newTokensData.data.refreshToken

                        oldResponse.close()
                        val newRequest = chain.request().newBuilder()
                            .addHeader(AUTHORIZATION, authorization(accessToken)).build()
                        return chain.proceed(newRequest)
                    }
                }

                // 리프레시 토큰 만료
                removeTokens()
                val intent = signupNavigator.intent(context)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                context.startActivity(intent)
            }
        }
        return oldResponse
    }

    private fun removeTokens() {
        preference.edit(true) { remove(ACCESS_TOKEN_KEY) }
        preference.edit(true) { remove(REFRESH_TOKEN_KEY) }
    }

    companion object {
        private const val REPEAT_NUM = 3
        private const val AUTHORIZATION = "Authorization"
        private const val X_THREE_DAYS_REFRESH_TOKEN = "X-THREE-DAYS-REFRESH-TOKEN"
    }
}
