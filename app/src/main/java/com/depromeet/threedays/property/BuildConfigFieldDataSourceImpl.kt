package com.depromeet.threedays.property

import com.depromeet.threedays.BuildConfig
import com.depromeet.threedays.data.datasource.property.BuildConfigFieldDataSource
import com.depromeet.threedays.data.datasource.property.BuildConfigFieldKey
import timber.log.Timber
import javax.inject.Inject

class BuildConfigFieldDataSourceImpl @Inject constructor(): BuildConfigFieldDataSource {
    override fun get(buildConfigFieldKey: BuildConfigFieldKey): String {
        try {
            return readProperties(buildConfigFieldKey = buildConfigFieldKey)
        } catch (e: Exception) {
            throw IllegalStateException("Failed to read property from local.properties. key: $buildConfigFieldKey", e)
        }
    }

    override fun getOrNull(buildConfigFieldKey: BuildConfigFieldKey): String? {
        return try {
            readProperties(buildConfigFieldKey = buildConfigFieldKey)
        } catch (e: Exception) {
            Timber.e(e, "Failed to read property from local.properties. key: $buildConfigFieldKey")
            null
        }
    }

    private fun readProperties(buildConfigFieldKey: BuildConfigFieldKey): String {
        return BuildConfig::class.java.getDeclaredField(buildConfigFieldKey.key).get(null) as String
    }
}
