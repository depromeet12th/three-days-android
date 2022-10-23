package com.depromeet.threedays.home.home

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import com.depromeet.threedays.domain.entity.Goal
import com.depromeet.threedays.home.databinding.DialogCompleteGoalBinding

class CompleteGoalDialog(
    context: Context,
    val goal: Goal,
    val onCompleteClick: (Goal) -> Unit
) : Dialog(context) {
    private lateinit var binding: DialogCompleteGoalBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogCompleteGoalBinding.inflate(LayoutInflater.from(context))
        setContentView(binding.root)

        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setCanceledOnTouchOutside(true)
        setCancelable(true)

        binding.btnGreat.setOnClickListener {
            onCompleteClick(goal)
            dismiss()
        }
    }
}
