package com.depromeet.threedays.property

import com.depromeet.threedays.data.datasource.property.BuildConfigFieldDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class PropertyModule {

    @Singleton
    @Provides
    fun bindsLocalPropertiesDataSourceProvide(
        buildConfigFieldDataSourceImpl: BuildConfigFieldDataSourceImpl,
    ): BuildConfigFieldDataSource = buildConfigFieldDataSourceImpl
}
