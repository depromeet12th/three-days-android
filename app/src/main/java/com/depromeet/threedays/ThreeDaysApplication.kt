package com.depromeet.threedays

import android.app.Application
import com.depromeet.threedays.buildproperty.BuildProperty
import com.depromeet.threedays.buildproperty.BuildPropertyRepository
import com.depromeet.threedays.core.analytics.AnalyticsUtil
import com.depromeet.threedays.timber.ReleaseTree
import com.kakao.sdk.common.KakaoSdk
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
class ThreeDaysApplication : Application() {
    @Inject
    lateinit var buildPropertyRepository: BuildPropertyRepository

    override fun onCreate() {
        super.onCreate()

        initTimber()
        initKakaoSdk()
        initAnalytics()
    }

    private fun initTimber() {
        if(BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        else {
            Timber.plant(ReleaseTree())
        }
    }

    private fun initKakaoSdk() {
        KakaoSdk.init(
            context = this,
            appKey = buildPropertyRepository.get(BuildProperty.KAKAO_APP_KEY),
        )
    }

    private fun initAnalytics() {
        AnalyticsUtil.init(this)
    }
}
