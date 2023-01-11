package com.depromeet.threedays.core.analytics

import android.content.Context
import com.depromeet.threedays.buildproperty.BuildProperty
import com.mixpanel.android.mpmetrics.MixpanelAPI
import org.json.JSONObject
import timber.log.Timber

class MixpanelAnalyticsSdk : AnalyticsSdk {
    private lateinit var mixpanelAPI: MixpanelAPI
    private var initialized = false

    override fun init(context: Context) {
        val projectToken = resolveToken(context)
        mixpanelAPI = MixpanelAPI.getInstance(context, projectToken, true)
        initialized = true
    }

    // FIXME: DI 받아서 접근하도록 변경
    private fun resolveToken(context: Context): String {
        return try {
            context.classLoader
                .loadClass("com.depromeet.threedays.BuildConfig")
                .getDeclaredField(BuildProperty.MIXPANEL_PROJECT_TOKEN.name)
                .get(null) as String
        } catch (e: Exception) {
            Timber.e(e, "Failed to resolve mixpanel project token")
            throw e
        }
    }

    override fun isInitialized(): Boolean = initialized

    override fun event(name: String, properties: Map<String, Any>) {
        Timber.i("eventName : $name , properties : ${JSONObject(properties)}")
        mixpanelAPI.track(name, JSONObject(properties))
    }
}
