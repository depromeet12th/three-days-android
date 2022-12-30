package com.depromeet.threedays.data.di

import android.content.Context
import com.depromeet.threedays.data.api.*
import com.depromeet.threedays.data.api.deserializer.LocalDateDeserializer
import com.depromeet.threedays.data.api.deserializer.LocalDateTimeDeserializer
import com.depromeet.threedays.data.api.deserializer.LocalTimeDeserializer
import com.depromeet.threedays.data.api.deserializer.MateTypeDeserializer
import com.depromeet.threedays.data.api.interceptor.AuthInterceptor
import com.depromeet.threedays.data.api.serializer.LocalDateSerializer
import com.depromeet.threedays.data.api.serializer.LocalDateTimeSerializer
import com.depromeet.threedays.data.api.serializer.LocalTimeSerializer
import com.depromeet.threedays.domain.entity.mate.MateType
import com.depromeet.threedays.navigator.SignupNavigator
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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
    fun providesAuthService(
        retrofit: Retrofit,
    ): AuthService = retrofit.create()

    @Provides
    @Singleton
    fun providesRecordService(
        retrofit: Retrofit,
    ): RecordService = retrofit.create()

    @Provides
    @Singleton
    fun providesHttpClient(
        @ApplicationContext context: Context,
        gson: Gson,
        signupNavigator: SignupNavigator
    ): OkHttpClient {
        val client = OkHttpClient.Builder()
            .readTimeout(10, TimeUnit.SECONDS)
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
        val clientWithAuthInterceptor = client
            .addInterceptor(AuthInterceptor(context, client.build(), gson, signupNavigator))
            .addInterceptor(getLoggingInterceptor())
        return clientWithAuthInterceptor.build()
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
        val gsonWithAdapter: Gson = GsonBuilder()
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
            .addConverterFactory(GsonConverterFactory.create(gsonWithAdapter))
            .build()
    }

    private fun getLoggingInterceptor() =
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
}

const val BASE_URL = "https://api.jjaksim.com"
