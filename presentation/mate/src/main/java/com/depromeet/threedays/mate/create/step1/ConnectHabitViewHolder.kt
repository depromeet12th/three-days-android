package com.depromeet.threedays.mate.create.step1

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.depromeet.threedays.core.setOnSingleClickListener
import com.depromeet.threedays.mate.R
import com.depromeet.threedays.mate.create.step1.model.HabitUI
import com.depromeet.threedays.mate.databinding.ItemConnectHabitBinding

class ConnectHabitViewHolder(
    private val binding: ItemConnectHabitBinding,
    private val setHabitClickStatus: (Long) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {

    fun onBind(habitUI: HabitUI) {
        binding.habitUI = habitUI
        binding.clHabit.setOnSingleClickListener {
            setHabitClickStatus(habitUI.habitId)

            if(habitUI.clicked) {
                binding.clHabit.setBackgroundResource(R.drawable.bg_rect_gray_200_border_gray_400_r10)
            }
        }
    }

    companion object {
        fun create(
            parent: ViewGroup,
            attachToParent: Boolean,
            setHabitClickStatus: (Long) -> Unit,
        ) = ConnectHabitViewHolder(
            binding = ItemConnectHabitBinding.inflate(
                LayoutInflater.from(parent.context), parent, attachToParent
            ),
            setHabitClickStatus = setHabitClickStatus,
        )
    }
}
