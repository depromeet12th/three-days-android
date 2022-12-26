package com.depromeet.threedays

import android.app.Application
import com.kakao.sdk.common.KakaoSdk
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ThreeDaysApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        initKakaoSdk()
    }

    private fun initKakaoSdk() {
        KakaoSdk.init(this, "f0c0458b5837b0f245c73b5a22908319")
    }
}
