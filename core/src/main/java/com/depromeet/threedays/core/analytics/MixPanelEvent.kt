package com.depromeet.threedays.core.analytics

data class MixPanelEvent (
    val eventName: ThreeDaysEvent,
    val properties: Map<String, String>
) {
    companion object {
        const val ScreenName: String = "ScreenName"
        const val ButtonType: String = "ButtonType"
    }
}
