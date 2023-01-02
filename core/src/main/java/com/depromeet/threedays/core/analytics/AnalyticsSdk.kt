package com.depromeet.threedays.core.analytics

import android.content.Context

interface AnalyticsSdk {
    fun init(context: Context)
    fun event(name:String, properties: Map<String, Any>)
}
