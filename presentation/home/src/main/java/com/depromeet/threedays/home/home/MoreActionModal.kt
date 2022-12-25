package com.depromeet.threedays.home.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.depromeet.threedays.core_design_system.R
import com.depromeet.threedays.home.databinding.ModalMoreActionBinding
import com.depromeet.threedays.home.home.model.HabitUI
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class MoreActionModal(
    val habitUI: HabitUI,
    val onEditClick: (Long) -> Unit,
    val onDeleteClick: (HabitUI) -> Unit
) : BottomSheetDialogFragment() {
    lateinit var binding: ModalMoreActionBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = ModalMoreActionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.clEdit.setOnClickListener {
            onEditClick(habitUI.habitId)
            dismiss()
        }

        binding.clDelete.setOnClickListener {
            onDeleteClick(habitUI)
            dismiss()
        }
    }

    companion object {
        const val TAG = "MoreActionModal"

        fun newInstance(
            habitUI: HabitUI,
            onEditClick: (Long) -> Unit,
            onDeleteClick: (HabitUI) -> Unit
        ): MoreActionModal {
            val modal = MoreActionModal(habitUI, onEditClick, onDeleteClick)
            modal.setStyle(DialogFragment.STYLE_NORMAL, R.style.RoundCornerBottomSheetDialogTheme)
            return modal
        }
    }
}
