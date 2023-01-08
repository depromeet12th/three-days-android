package com.depromeet.threedays.core.util

import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import com.depromeet.threedays.core.databinding.ImageSnackbarThreeDaysBinding
import com.google.android.material.snackbar.Snackbar

class ThreeDaysImageSnackBar {
    fun show(
        view: View,
        title: String,
        content: String,
        actionText: String,
        onAction: () -> Unit
    ) {
        val binding = ImageSnackbarThreeDaysBinding.inflate(LayoutInflater.from(view.context), null, false)
        val snackbar = Snackbar.make(view, title, 5000)
        val snackbarLayout = snackbar.view as Snackbar.SnackbarLayout

        with(snackbarLayout) {
            removeAllViews()
            setPadding(0, 0, 0, 0)
            setBackgroundColor(ContextCompat.getColor(context, android.R.color.transparent))
            addView(binding.root, 0)

            val params = layoutParams as FrameLayout.LayoutParams
            params.gravity = Gravity.TOP
        }

        binding.tvTitle.text = title
        binding.tvContent.text = content
        binding.tvActionButton.text = actionText
        binding.tvActionButton.setOnSingleClickListener {
            onAction()
            snackbar.dismiss()
        }

        snackbar.show()
    }
}
