package com.depromeet.threedays.home.home

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import com.depromeet.threedays.domain.entity.habit.Habit
import com.depromeet.threedays.home.databinding.DialogDeleteHabitBinding

class DeleteHabitDialog(
    context: Context,
    val habit: Habit,
    val onDeleteConfirmClick: (Habit) -> Unit
) : Dialog(context) {
    private lateinit var binding: DialogDeleteHabitBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogDeleteHabitBinding.inflate(LayoutInflater.from(context))
        setContentView(binding.root)

        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setCanceledOnTouchOutside(true)
        setCancelable(true)

        binding.btnCancel.setOnClickListener {
            dismiss()
        }

        binding.btnConfirm.setOnClickListener {
            onDeleteConfirmClick(habit)
            dismiss()
        }
    }
}

