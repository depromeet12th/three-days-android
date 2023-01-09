package com.depromeet.threedays.property

import com.depromeet.threedays.BuildConfig
import com.depromeet.threedays.buildproperty.BuildProperty
import com.depromeet.threedays.buildproperty.BuildPropertyRepository
import timber.log.Timber
import javax.inject.Inject

class BuildPropertyRepositoryImpl @Inject constructor() : BuildPropertyRepository {
    override fun get(buildProperty: BuildProperty): String {
        try {
            return readProperties(buildProperty = buildProperty)
        } catch (e: Exception) {
            throw IllegalStateException(
                "Failed to read property from local.properties. key: $buildProperty",
                e
            )
        }
    }

    override fun getOrNull(buildProperty: BuildProperty): String? {
        return try {
            readProperties(buildProperty = buildProperty)
        } catch (e: Exception) {
            Timber.e(e, "Failed to read property from local.properties. key: $buildProperty")
            null
        }
    }

    private fun readProperties(buildProperty: BuildProperty): String {
        return BuildConfig::class.java.getDeclaredField(buildProperty.key).get(null) as String
    }
}
