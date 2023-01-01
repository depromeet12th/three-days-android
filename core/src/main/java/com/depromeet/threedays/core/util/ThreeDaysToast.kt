package com.depromeet.threedays.core.util

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.TextView
import android.widget.Toast
import com.depromeet.threedays.core.R

class ThreeDaysToast {
    fun show(
        context: Context,
        title: String,
        duration: Int = Toast.LENGTH_SHORT
    ) {
        val inflater = LayoutInflater.from(context)
        val toast = Toast(context)
        val view = inflater.inflate(R.layout.toast_three_days, null)
        val textView: TextView = view.findViewById(R.id.tv_toast)
        textView.text = title
        toast.setGravity(Gravity.BOTTOM or Gravity.FILL_HORIZONTAL, 0, 0)
        toast.view = view
        toast.duration = duration
        toast.show()
    }
}
