package com.depromeet.threedays.home.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.depromeet.threedays.core.setOnSingleClickListener
import com.depromeet.threedays.home.databinding.ItemHabitFooterBinding
import kotlin.reflect.KFunction0

class HabitFooterViewHolder(private val binding: ItemHabitFooterBinding) : RecyclerView.ViewHolder(binding.root) {

    fun onBind(
        onCreateHabitClick: KFunction0<Unit>,
    ) {
        binding.tvCreateHabit.setOnSingleClickListener {
            onCreateHabitClick()
        }
    }

    companion object {
        fun create(parent: ViewGroup, attachToParent: Boolean): HabitFooterViewHolder {
            return HabitFooterViewHolder(
                ItemHabitFooterBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    attachToParent
                )
            )
        }
    }
}
