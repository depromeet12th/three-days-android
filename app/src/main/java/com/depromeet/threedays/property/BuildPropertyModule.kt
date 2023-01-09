package com.depromeet.threedays.property

import com.depromeet.threedays.buildproperty.BuildPropertyRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class BuildPropertyModule {

    @Singleton
    @Provides
    fun bindsBuildPropertyRepositoryProvide(
        buildPropertyRepositoryImpl: BuildPropertyRepositoryImpl,
    ): BuildPropertyRepository = buildPropertyRepositoryImpl
}
