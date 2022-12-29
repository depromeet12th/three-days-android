package com.depromeet.threedays.mate

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.depromeet.threedays.mate.databinding.ItemClapBinding
import com.depromeet.threedays.mate.model.StampUI

class ClapViewHolder(
    private val binding: ItemClapBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun onBind(stampUI: StampUI) {
        binding.viewBackground.setBackgroundResource(stampUI.backgroundResId)
    }
    companion object {
        fun create(
            parent: ViewGroup,
            attachToParent: Boolean,
        ) = ClapViewHolder(
            binding = ItemClapBinding.inflate(
                LayoutInflater.from(parent.context), parent, attachToParent
            ),
        )
    }
}
