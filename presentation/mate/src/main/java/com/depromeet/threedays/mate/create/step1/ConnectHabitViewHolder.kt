package com.depromeet.threedays.mate.create.step1

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.depromeet.threedays.mate.create.step1.model.HabitUI
import com.depromeet.threedays.mate.databinding.ItemConnectHabitBinding

class ConnectHabitViewHolder(private val binding: ItemConnectHabitBinding) : RecyclerView.ViewHolder(binding.root) {
    fun onBind(habitUI: HabitUI) {
        binding.habitUI = habitUI
    }

    companion object {
        fun create(parent: ViewGroup, attachToParent: Boolean): ConnectHabitViewHolder {
            return ConnectHabitViewHolder(
                ItemConnectHabitBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    attachToParent
                )
            )
        }
    }
}
