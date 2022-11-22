package com.depromeet.threedays.home.home

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import com.depromeet.threedays.domain.entity.habit.Habit
import com.depromeet.threedays.home.databinding.DialogCompleteHabitBinding

class CompleteHabitDialog(
    context: Context,
    val habit: Habit
) : Dialog(context) {
    private lateinit var binding: DialogCompleteHabitBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogCompleteHabitBinding.inflate(LayoutInflater.from(context))
        setContentView(binding.root)

        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setCanceledOnTouchOutside(true)
        setCancelable(true)

        binding.btnGreat.setOnClickListener {
            dismiss()
        }
    }
}
