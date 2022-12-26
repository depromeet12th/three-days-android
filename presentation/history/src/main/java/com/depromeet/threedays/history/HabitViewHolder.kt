package com.depromeet.threedays.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.depromeet.threedays.core.setOnSingleClickListener
import com.depromeet.threedays.history.databinding.ItemHabitRecordBinding
import com.depromeet.threedays.history.model.HabitUI

class HabitViewHolder(
    private val binding: ItemHabitRecordBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun onBind(habitUI: HabitUI) {
        binding.habitUI = habitUI
        binding.clHabit.setOnSingleClickListener {
            // TODO: 습관 보관함으로 이동하는 함수 호출 
        }
    }

    companion object {
        fun create(
            parent: ViewGroup,
            attachToParent: Boolean,
        ) = HabitViewHolder(
            binding = ItemHabitRecordBinding.inflate(
                LayoutInflater.from(parent.context), parent, attachToParent
            ),
        )
    }
}
