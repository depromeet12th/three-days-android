package com.depromeet.threedays.signup

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.depromeet.threedays.core.BaseActivity
import com.depromeet.threedays.core.analytics.*
import com.depromeet.threedays.core.util.ThreeDaysToast
import com.depromeet.threedays.core.util.setOnSingleClickListener
import com.depromeet.threedays.navigator.HomeNavigator
import com.depromeet.threedays.signup.SignupViewModel.Action
import com.depromeet.threedays.signup.complete.SignupCompleteActivity
import com.depromeet.threedays.signup.databinding.ActivitySignupBinding
import com.depromeet.threedays.signup.extension.loginWithKakaoOrThrow
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class SignupActivity: BaseActivity<ActivitySignupBinding>(R.layout.activity_signup) {
    private val viewModel by viewModels<SignupViewModel>()

    @Inject
    lateinit var homeNavigator: HomeNavigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AnalyticsUtil.event(
            name = getViewedEventName(this),
            properties = mapOf(
                MixPanelEvent.ScreenName to getScreenName(this)
            )
        )

        startKakaoLogin()
        observe()
    }

    private fun startKakaoLogin() {
        binding.containerSignup.setOnSingleClickListener {
            AnalyticsUtil.event(
                name = ThreeDaysEvent.ButtonClicked.toString(),
                properties = mapOf(
                    MixPanelEvent.ScreenName to getScreenName(this),
                    MixPanelEvent.ButtonType to ButtonType.Next.toString()
                )
            )

            lifecycleScope.launch {
                kotlin.runCatching {
                    UserApiClient.loginWithKakaoOrThrow(this@SignupActivity)
                }.onSuccess { oAuthToken ->
                    UserApiClient.instance.me { user, _ ->
                        if (user != null) {
                            viewModel.createMember(socialToken = oAuthToken.accessToken)
                        }
                    }
                }.onFailure { throwable ->
                    if (throwable is ClientError && throwable.reason == ClientErrorCause.Cancelled) {
                        Timber.d("사용자가 명시적으로 카카오 로그인 취소")
                    } else {
                        ThreeDaysToast().error(this@SignupActivity, "로그인에 실패했어요.")
                        Timber.e(throwable, "로그인 실패 : ${throwable.message}")
                    }
                }
            }
        }
    }

    private fun observe() {
        viewModel.action.onEach { action ->
            val intent = when(action) {
                Action.AlreadySignedUp -> {
                    homeNavigator.intent(this)
                }
                Action.FirstSignup -> {
                    Intent(this@SignupActivity, SignupCompleteActivity::class.java)
                }
            }
            startActivity(intent)
            finish()
        }.launchIn(lifecycleScope)
    }
}
