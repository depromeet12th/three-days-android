package com.depromeet.threedays.core.analytics

data class MixPanelEvent (
    val eventName: String,
    val properties: Map<String, String>
) {
    companion object {
        const val ScreenName: String = "ScreenName"
        const val ButtonType: String = "ButtonType"
    }
}

fun getEventName(className: String): String {
    return getScreenName(className) + viewSuffix
}

fun getScreenName(className: String): String {
    val suffixLength =
        if(className.contains("Fragment")) fragmentLength
        else if(className.contains("Activity")) activityLength
        else 0

    return className.substring(0, className.length - suffixLength)
}

const val viewSuffix = "Viewed"
const val activityLength = "Activity".length
const val fragmentLength = "Fragment".length
