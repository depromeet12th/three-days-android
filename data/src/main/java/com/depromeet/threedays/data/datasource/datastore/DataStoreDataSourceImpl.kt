package com.depromeet.threedays.data.datasource.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class DataStoreDataSourceImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : DataStoreDataSource {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = DATASTORE_NAME)

    override suspend fun readDataStore(key: String): String? {
        val dataStoreKey = stringPreferencesKey(key)
        val preferences = context.dataStore.data.first()
        return preferences[dataStoreKey]
    }

    override suspend fun writeDataStore(key: String, value: String) {
        val dataStoreKey = stringPreferencesKey(key)
        context.dataStore.edit {
            it[dataStoreKey] = value
        }
    }

    override suspend fun removeDataStore(key: String) {
        val dataStoreKey = stringPreferencesKey(key)
        context.dataStore.edit {
            it.remove(dataStoreKey)
        }
    }

    override suspend fun resetDataStore() {
        context.dataStore.edit {
            it.clear()
        }
    }

    companion object {
        const val DATASTORE_NAME = "THREE_DAYS"
    }
}
