package com.depromeet.threedays.data.datasource

import android.content.Context
import android.content.SharedPreferences
import android.os.Parcelable
import androidx.annotation.AnyThread
import androidx.core.content.edit
import com.depromeet.threedays.domain.key.SHARED_PREFERENCE_KEY
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class ThreeDaysSharedPreferenceImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val gson: Gson
) : ThreeDaysSharedPreference {
    private val preference: SharedPreferences =
        context.getSharedPreferences(SHARED_PREFERENCE_KEY, Context.MODE_PRIVATE)

    override fun putString(key: String, value: String) {
        preference.edit(true) { putString(key, value) }
    }

    override fun putLong(key: String, value: Long) {
        preference.edit(true) { putLong(key, value) }
    }

    override fun putInt(key: String, value: Int) {
        preference.edit(true) { putInt(key, value) }
    }

    override fun putParcelable(key: String, value: Parcelable) {
        preference.edit(true) { putString(key, gson.toJson(value)) }
    }

    override fun getString(key: String): String {
        return preference.getString(key, "").toString()
    }

    override fun remove(key: String) {
        preference.edit(true) { remove(key) }
    }
}

interface ThreeDaysSharedPreference {
    fun putString(key: String, value: String)
    fun putLong(key: String, value: Long)
    fun putInt(key: String, value: Int)
    fun putParcelable(key: String, value: Parcelable)
    fun getString(key: String): String
    fun remove(key: String)
}

class SerializablePreference<T>(
    private val preferences: SharedPreferences,
    private val gson: Gson,
    private val name: String,
    private val defaultValue: T,
    private val clazz: Class<T>
) : ReadWriteProperty<Any, T> {

    @AnyThread
    override fun getValue(thisRef: Any, property: KProperty<*>): T {
        val json = preferences.getString(name, null)
        return json?.let {
            gson.fromJson(it, clazz)
        } ?: defaultValue
    }

    @AnyThread
    override fun setValue(thisRef: Any, property: KProperty<*>, value: T) {
        val json = gson.toJson(value, clazz)
        preferences.edit {
            putString(name, json)
        }
    }
}
