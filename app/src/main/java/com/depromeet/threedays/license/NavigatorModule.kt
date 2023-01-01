package com.depromeet.threedays.license

import com.depromeet.threedays.navigator.LicenseNavigator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NavigatorModule {

    @Singleton
    @Provides
    fun bindLicenseNavigatorProvide(): LicenseNavigator {
        return LicenseNavigatorImpl()
    }
}
