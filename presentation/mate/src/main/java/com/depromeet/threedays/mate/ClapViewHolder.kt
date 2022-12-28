package com.depromeet.threedays.mate

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.depromeet.threedays.core.extensions.gone
import com.depromeet.threedays.core.extensions.visible
import com.depromeet.threedays.mate.databinding.ItemClapBinding
import com.depromeet.threedays.mate.model.StampType
import com.depromeet.threedays.mate.model.StampUI

class ClapViewHolder(
    private val binding: ItemClapBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun onBind(stampUI: StampUI, position: Int) {
//        binding.viewBackground.setBackgroundResource(stampUI.backgroundResId)
       // binding.viewBackground2.setBackgroundResource(stampUI.backgroundResId)

        if (stampUI.stampType == StampType.Locked) {
            binding.viewBackground.gone()
            binding.groupLockedClap.visible()
            binding.tvLevel.text = "Lv.${position + 1}"
        }
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
