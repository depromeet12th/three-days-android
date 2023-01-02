package com.depromeet.threedays.core.analytics

import android.content.Context
import com.mixpanel.android.mpmetrics.MixpanelAPI
import org.json.JSONObject
import timber.log.Timber

class MixpanelAnalyticsSdk : AnalyticsSdk {
    private lateinit var mixpanelAPI: MixpanelAPI

    override fun init(context: Context) {
        mixpanelAPI = MixpanelAPI.getInstance(context, "823f0e71338dd687bbe4d1b2f34e1272", true)
    }

    override fun event(name: String, properties: Map<String, Any>) {
        Timber.i("eventName : $name , properties : ${JSONObject(properties)}")
        mixpanelAPI.track(name, JSONObject(properties))
    }
}
