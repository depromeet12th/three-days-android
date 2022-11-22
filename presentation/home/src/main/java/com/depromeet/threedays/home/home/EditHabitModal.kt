package com.depromeet.threedays.home.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.depromeet.threedays.domain.entity.habit.Habit
import com.depromeet.threedays.home.databinding.ModalEditHabitBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class EditHabitModal(
    val habit: Habit,
    val onEditClick: (Habit) -> Unit,
    val onDeleteClick: (Habit) -> Unit
) : BottomSheetDialogFragment() {
    lateinit var binding: ModalEditHabitBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = ModalEditHabitBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.llEdit.setOnClickListener {
            onEditClick(habit)
            dismiss()
        }

        binding.llDelete.setOnClickListener {
            onDeleteClick(habit)
            dismiss()
        }
    }

    companion object {
        const val TAG = "ModalExample3"
    }
}
