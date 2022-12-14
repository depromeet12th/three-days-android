package com.depromeet.threedays.mypage.archived_habit

import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import com.depromeet.threedays.core.util.setOnSingleClickListener
import com.depromeet.threedays.mypage.R
import com.depromeet.threedays.mypage.databinding.SnackbarArchivedHabitOnboardingBinding
import com.google.android.material.snackbar.Snackbar

class ArchivedHabitOnboardingSnackBar {
    companion object {
        fun show(
            view: View,
        ) {
            val binding = SnackbarArchivedHabitOnboardingBinding.inflate(LayoutInflater.from(view.context), null,false)
            val snackbar = Snackbar.make(
                view,
                view.context.getString(R.string.archived_habit_onboarding_text),
                Snackbar.LENGTH_INDEFINITE
            )
            val snackBarLayout = snackbar.view as Snackbar.SnackbarLayout

            with(snackBarLayout) {
                removeAllViews()
                setPadding(0, 200, 0, 0)
                setBackgroundColor(ContextCompat.getColor(context, android.R.color.transparent))
                addView(binding.root, 0)

                val params = layoutParams as FrameLayout.LayoutParams
                params.gravity = Gravity.TOP
            }

            binding.ivClose.setOnSingleClickListener {
                snackbar.dismiss()
            }

            snackbar.show()
        }
    }
}
