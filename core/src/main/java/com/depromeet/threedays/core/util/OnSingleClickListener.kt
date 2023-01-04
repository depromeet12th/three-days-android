package com.depromeet.threedays.core.util

import android.os.SystemClock
import android.view.View
import androidx.databinding.BindingAdapter
import com.depromeet.threedays.core.R
import com.depromeet.threedays.core.analytics.AnalyticsUtil
import com.depromeet.threedays.core.analytics.MixPanelEvent

class OnSingleClickListener(
    private val interval: Int,
    private val onSingleClick: (View) -> Unit
) : View.OnClickListener {

    private var lastClickedTime: Long = 0L

    override fun onClick(v: View) {
        val elapsedRealTime = SystemClock.elapsedRealtime()
        if ((elapsedRealTime - lastClickedTime) < interval) {
            return
        }
        lastClickedTime = elapsedRealTime
        onSingleClick(v)
    }
}

class OnSingleClickWithEventListener(
    private val interval: Int,
    private val event: MixPanelEvent,
    private val onSingleClick: (View) -> Unit,
) : View.OnClickListener {

    private var lastClickedTime: Long = 0L

    override fun onClick(v: View) {
        val elapsedRealTime = SystemClock.elapsedRealtime()
        if ((elapsedRealTime - lastClickedTime) < interval) {
            return
        }
        lastClickedTime = elapsedRealTime
        AnalyticsUtil.event(event.eventName, event.properties)
        onSingleClick(v)
    }
}

fun View.setOnSingleClickListener(
    interval: Int = 200,
    onClick: (View) -> Unit = { }
) {
    setOnClickListener(OnSingleClickListener(interval, onClick))
}

fun View.setOnSingleClickWithEventListener(
    interval: Int = 200,
    event: MixPanelEvent,
    onClick: (View) -> Unit = { },
) {
    setOnClickListener(OnSingleClickWithEventListener(interval, event, onClick))
}

@BindingAdapter("android:onClick", "throttleMillis", requireAll = false)
fun setOnSingleClickListener(
    view: View,
    listener: View.OnClickListener?,
    throttleMillis: Long? = 200
) {
    view.setOnClickListener(listener?.let {
        View.OnClickListener {
            val lastClickedAt = (view.getTag(R.id.last_click_at) as Long?) ?: 0L
            if (System.currentTimeMillis() > lastClickedAt + (throttleMillis ?: 200L)) {
                listener.onClick(view)
                view.setTag(R.id.last_click_at, System.currentTimeMillis())
            }
        }
    })
}
