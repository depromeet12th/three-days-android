package com.depromeet.threedays

import android.app.Application
import com.depromeet.threedays.core.analytics.AnalyticsUtil
import com.kakao.sdk.common.KakaoSdk
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class ThreeDaysApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        initTimber()
        initKakaoSdk()
        initAnalytics()
    }

    private fun initTimber() {
        Timber.plant(Timber.DebugTree())
    }

    private fun initKakaoSdk() {
        KakaoSdk.init(this, "f0c0458b5837b0f245c73b5a22908319")
    }

    private fun initAnalytics() {
        AnalyticsUtil.init(this)
    }
}
