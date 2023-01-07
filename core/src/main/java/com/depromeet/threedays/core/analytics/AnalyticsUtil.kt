package com.depromeet.threedays.core.analytics

import android.content.Context

object AnalyticsUtil : AnalyticsSdk {
    private const val MIXPANEL = "mixpanel"

    private val analyticsSdkList: Map<String, AnalyticsSdk> =
        mapOf(
            MIXPANEL to MixpanelAnalyticsSdk()
        )

    override fun init(context: Context) {
        analyticsSdkList.forEach { it.value.init(context) }
    }

    override fun isInitialized(): Boolean = analyticsSdkList.all { it.value.isInitialized() }


    override fun event(name: String, properties: Map<String, Any>) {
        analyticsSdkList.forEach { it.value.event(name = name, properties = properties) }
    }
}
