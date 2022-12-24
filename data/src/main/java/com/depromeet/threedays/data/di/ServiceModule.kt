package com.depromeet.threedays.data.di

import com.depromeet.threedays.data.api.AchievementService
import com.depromeet.threedays.data.api.HabitService
import com.depromeet.threedays.data.api.MemberService
import com.depromeet.threedays.data.api.MateService
import com.depromeet.threedays.data.api.NotificationHistoryService
import com.depromeet.threedays.data.api.deserializer.LocalDateDeserializer
import com.depromeet.threedays.data.api.deserializer.LocalDateTimeDeserializer
import com.depromeet.threedays.data.api.deserializer.LocalTimeDeserializer
import com.depromeet.threedays.data.api.deserializer.MateTypeDeserializer
import com.depromeet.threedays.data.api.interceptor.AuthInterceptor
import com.depromeet.threedays.data.api.serializer.LocalDateSerializer
import com.depromeet.threedays.data.api.serializer.LocalDateTimeSerializer
import com.depromeet.threedays.data.api.serializer.LocalTimeSerializer
import com.depromeet.threedays.domain.entity.mate.MateType
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    @Provides
    @Singleton
    fun providesHabitService(
        retrofit: Retrofit,
    ): HabitService = retrofit.create()

    @Provides
    @Singleton
    fun providesMateService(
        retrofit: Retrofit
    ): MateService = retrofit.create()

    @Provides
    @Singleton
    fun providesNotificationHistoryService(
        retrofit: Retrofit,
    ): NotificationHistoryService = retrofit.create()

    @Provides
    @Singleton
    fun providesAchievementService(
        retrofit: Retrofit,
    ): AchievementService = retrofit.create()

    @Provides
    @Singleton
    fun providesMemberService(
        retrofit: Retrofit,
    ): MemberService = retrofit.create()


    @Provides
    @Singleton
    fun providesHttpClient(): OkHttpClient {
        val client = OkHttpClient.Builder()
            .readTimeout(10, TimeUnit.SECONDS)
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .addInterceptor(getLoggingInterceptor())
            .addInterceptor(AuthInterceptor())

        return client.build()
    }

    @Provides
    @Singleton
    fun providesConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    @Singleton
    @Provides
    fun providesRetrofit(
        okHttpClient: OkHttpClient
    ): Retrofit {
        val gson: Gson = GsonBuilder()
            .registerTypeAdapter(LocalDateTime::class.java, LocalDateTimeDeserializer())
            .registerTypeAdapter(LocalDate::class.java, LocalDateDeserializer())
            .registerTypeAdapter(LocalTime::class.java, LocalTimeDeserializer())
            .registerTypeAdapter(LocalDateTime::class.java, LocalDateTimeSerializer())
            .registerTypeAdapter(LocalDate::class.java, LocalDateSerializer())
            .registerTypeAdapter(LocalTime::class.java, LocalTimeSerializer())
            .registerTypeAdapter(MateType::class.java, MateTypeDeserializer())
            .create()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .client(providesHttpClient())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    private fun getLoggingInterceptor() =
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
}

const val BASE_URL = "https://api.jjaksim.com"
