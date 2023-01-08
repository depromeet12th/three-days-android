package com.depromeet.threedays

import android.app.Application
import com.depromeet.threedays.data.datasource.property.BuildConfigFieldDataSource
import com.depromeet.threedays.data.datasource.property.BuildConfigFieldKey
import com.kakao.sdk.common.KakaoSdk
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
class ThreeDaysApplication : Application() {
    @Inject
    lateinit var buildConfigFieldDataSource: BuildConfigFieldDataSource

    override fun onCreate() {
        super.onCreate()

        initTimber()
        initKakaoSdk()
    }

    private fun initTimber() {
        Timber.plant(Timber.DebugTree())
    }

    private fun initKakaoSdk() {
        KakaoSdk.init(
            context = this,
            appKey = buildConfigFieldDataSource.get(BuildConfigFieldKey.KAKAO_APP_KEY),
        )
    }
}
