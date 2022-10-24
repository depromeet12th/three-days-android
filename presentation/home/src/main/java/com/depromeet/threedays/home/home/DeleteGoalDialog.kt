package com.depromeet.threedays.home.home

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import com.depromeet.threedays.domain.entity.Goal
import com.depromeet.threedays.home.databinding.DialogDeleteGoalBinding

class DeleteGoalDialog(
    context: Context,
    val goal: Goal,
    val onDeleteConfirmClick: (Goal) -> Unit
) : Dialog(context) {
    private lateinit var binding: DialogDeleteGoalBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogDeleteGoalBinding.inflate(LayoutInflater.from(context))
        setContentView(binding.root)

        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setCanceledOnTouchOutside(true)
        setCancelable(true)

        binding.btnCancel.setOnClickListener {
            dismiss()
        }

        binding.btnConfirm.setOnClickListener {
            onDeleteConfirmClick(goal)
            dismiss()
        }
    }
}

