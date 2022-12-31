package com.depromeet.threedays.mate.create.step1

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.depromeet.threedays.core.setOnSingleClickListener
import com.depromeet.threedays.mate.create.step1.model.HabitUI
import com.depromeet.threedays.mate.databinding.ItemConnectHabitBinding

class ConnectHabitViewHolder(
    private val binding: ItemConnectHabitBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun onBind(
        habitUI: HabitUI,
        selectCheck: Boolean,
        onRadioButtonClick: (Boolean) -> Unit,
        setHabitClickStatus: (HabitUI) -> Unit,
    ) {
        binding.habitUI = habitUI
        binding.rbHabit.apply {
            isChecked = selectCheck
            setOnSingleClickListener {
                setHabitClickStatus(habitUI)
                onRadioButtonClick(isChecked)
            }
        }
    }

    companion object {
        fun create(
            parent: ViewGroup,
            attachToParent: Boolean,
        ) = ConnectHabitViewHolder(
            binding = ItemConnectHabitBinding.inflate(
                LayoutInflater.from(parent.context), parent, attachToParent
            )
        )
    }
}
