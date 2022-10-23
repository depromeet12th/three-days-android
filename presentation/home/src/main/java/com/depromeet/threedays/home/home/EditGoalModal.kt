package com.depromeet.threedays.home.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.depromeet.threedays.domain.entity.Goal
import com.depromeet.threedays.home.databinding.ModalEditGoalBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class EditGoalModal(
    val goal: Goal,
    val onEditClick: (Goal) -> Unit,
    val onDeleteClick: (Goal) -> Unit
) : BottomSheetDialogFragment() {
    lateinit var binding: ModalEditGoalBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = ModalEditGoalBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.llEdit.setOnClickListener {
            onEditClick(goal)
            dismiss()
        }

        binding.llDelete.setOnClickListener {
            onDeleteClick(goal)
            dismiss()
        }
    }

    companion object {
        const val TAG = "ModalExample3"
    }
}
