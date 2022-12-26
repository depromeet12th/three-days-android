package com.depromeet.threedays.signup

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.depromeet.threedays.core.BaseActivity
import com.depromeet.threedays.signup.databinding.ActivitySignupBinding
import com.depromeet.threedays.signup.extension.loginWithKakaoOrThrow
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class SignupActivity: BaseActivity<ActivitySignupBinding>(R.layout.activity_signup) {
    private val viewModel by viewModels<SignupViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        startKakaoLogin()
    }

    private fun startKakaoLogin() {
        val context = this
        binding.containerSignup.setOnClickListener {
            lifecycleScope.launch {
                kotlin.runCatching {
                    UserApiClient.loginWithKakaoOrThrow(context)
                }.onSuccess { OAuthToken ->
                    UserApiClient.instance.me { user, _ ->
                        if (user != null) {
                        }
                    }
                }.onFailure { throwable ->
                    if (throwable is ClientError && throwable.reason == ClientErrorCause.Cancelled) {
                        Timber.d("사용자가 명시적으로 카카오 로그인 취소")
                    } else {
                        Timber.d("로그인 실패 : ${throwable.message}")
                    }
                }
            }
        }
    }
}
