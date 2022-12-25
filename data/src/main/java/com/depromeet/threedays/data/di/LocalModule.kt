package com.depromeet.threedays.data.di

import com.depromeet.threedays.data.datasource.datastore.DataStoreDataSource
import com.depromeet.threedays.data.datasource.datastore.DataStoreDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class LocalModule {

    @Binds
    @Singleton
    abstract fun bindDataStoreDataSource(
        dataSource: DataStoreDataSourceImpl,
    ): DataStoreDataSource
}
