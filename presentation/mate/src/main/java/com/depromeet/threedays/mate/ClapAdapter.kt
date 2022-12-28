package com.depromeet.threedays.mate

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.depromeet.threedays.mate.model.StampUI

class ClapAdapter : ListAdapter<StampUI, ClapViewHolder>(DIFF_UTIL) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ClapViewHolder.create(parent = parent, attachToParent = false)

    override fun onBindViewHolder(holder: ClapViewHolder, position: Int) {
        holder.onBind(getItem(position), position)
    }

    override fun getItemCount(): Int = currentList.size

    override fun getItemId(position: Int): Long {
        return getItem(position).stampCount
    }

    companion object {
        private val DIFF_UTIL = object : DiffUtil.ItemCallback<StampUI>() {
            override fun areItemsTheSame(oldItem: StampUI, newItem: StampUI): Boolean {
                return oldItem.stampCount == newItem.stampCount
            }

            override fun areContentsTheSame(oldItem: StampUI, newItem: StampUI): Boolean {
                return oldItem == newItem
            }
        }
    }
}
