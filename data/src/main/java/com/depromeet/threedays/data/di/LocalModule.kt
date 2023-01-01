package com.depromeet.threedays.data.di

import android.content.Context
import com.depromeet.threedays.data.datasource.ThreeDaysSharedPreference
import com.depromeet.threedays.data.datasource.ThreeDaysSharedPreferenceImpl
import com.depromeet.threedays.data.datasource.datastore.DataStoreDataSource
import com.depromeet.threedays.data.datasource.datastore.DataStoreDataSourceImpl
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal class LocalModule {

    @Provides
    fun bindDataStoreDataSource(
        dataSource: DataStoreDataSourceImpl,
    ): DataStoreDataSource = dataSource

    @Provides
    fun bindSharedPreferenceProvide(
        @ApplicationContext context: Context,
        gson: Gson
    ): ThreeDaysSharedPreference {
        return ThreeDaysSharedPreferenceImpl(context, gson)
    }
}
