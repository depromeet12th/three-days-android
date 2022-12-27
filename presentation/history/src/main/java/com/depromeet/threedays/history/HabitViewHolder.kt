package com.depromeet.threedays.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.depromeet.threedays.core.setOnSingleClickListener
import com.depromeet.threedays.history.databinding.ItemHabitRecordBinding
import com.depromeet.threedays.history.model.HabitUI

class HabitViewHolder(
    private val binding: ItemHabitRecordBinding,
    private val onHabitClick: (Long) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {

    fun onBind(habitUI: HabitUI) {
        binding.habitUI = habitUI
        binding.clHabit.setOnSingleClickListener {
            onHabitClick(habitUI.habitId)
        }
    }

    companion object {
        fun create(
            parent: ViewGroup,
            attachToParent: Boolean,
            onHabitClick: (Long) -> Unit,
        ) = HabitViewHolder(
            binding = ItemHabitRecordBinding.inflate(
                LayoutInflater.from(parent.context), parent, attachToParent
            ),
            onHabitClick = onHabitClick
        )
    }
}
