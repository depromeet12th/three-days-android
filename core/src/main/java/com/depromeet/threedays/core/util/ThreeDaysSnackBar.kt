package com.depromeet.threedays.core.util

import android.view.LayoutInflater
import android.view.View
import androidx.core.content.ContextCompat
import com.depromeet.threedays.core.databinding.SnackbarThreeDaysBinding
import com.depromeet.threedays.core.setOnSingleClickListener
import com.google.android.material.snackbar.Snackbar

class ThreeDaysSnackBar {
    fun show(
        view: View,
        text: String,
        actionText: String,
        onAction: () -> Unit
    ) {
        val binding = SnackbarThreeDaysBinding.inflate(LayoutInflater.from(view.context), null,false)
        val snackbar = Snackbar.make(view, text, Snackbar.LENGTH_SHORT)
        val snackbarLayout = snackbar.view as Snackbar.SnackbarLayout

        with(snackbarLayout) {
            removeAllViews()
            setPadding(0, 0, 0, 0)
            setBackgroundColor(ContextCompat.getColor(context, android.R.color.transparent))
            addView(binding.root, 0)
        }

        binding.tvText.text = text
        binding.tvActionButton.text = actionText
        binding.tvActionButton.setOnSingleClickListener {
            onAction()
            snackbar.dismiss()
        }

        snackbar.show()
    }
}
