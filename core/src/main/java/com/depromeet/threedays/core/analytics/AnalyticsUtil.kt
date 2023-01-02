package com.depromeet.threedays.core.analytics

import android.content.Context

object AnalyticsUtil : AnalyticsSdk {
    private val analyticsSdkList: List<AnalyticsSdk> =
        listOf(
            MixpanelAnalyticsSdk()
        )

    override fun init(context: Context) {
        analyticsSdkList.forEach { it.init(context) }
    }

    override fun event(name: String, properties: Map<String, Any>) {
        analyticsSdkList.forEach { it.event(name = name, properties = properties) }
    }
}
