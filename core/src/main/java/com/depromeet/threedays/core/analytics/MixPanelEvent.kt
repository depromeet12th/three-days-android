package com.depromeet.threedays.core.analytics

import android.content.Context

data class MixPanelEvent (
    val eventName: String,
    val properties: Map<String, String>
) {
    companion object {
        const val ScreenName: String = "ScreenName"
        const val ButtonType: String = "ButtonType"
    }
}

fun getViewedEventName(context: Context): String {
    return getScreenName(context) + viewSuffix
}

fun getScreenName(context: Context): String {
    val className = context.javaClass.simpleName
    val suffixLength =
        if(className.contains("Fragment")) fragmentLength
        else if(className.contains("Activity")) activityLength
        else 0

    return className.substring(0, className.length - suffixLength)
}

const val viewSuffix = "Viewed"
const val activityLength = "Activity".length
const val fragmentLength = "Fragment".length
