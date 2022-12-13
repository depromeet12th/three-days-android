package com.depromeet.threedays.mypage.archived_habit

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.depromeet.threedays.mypage.databinding.ItemHabitArchivedBinding

class ArchivedHabitViewHolder(
    private val binding: ItemHabitArchivedBinding,
) : RecyclerView.ViewHolder(binding.root) {
    /**
     * 데이터 연결해주는 작업
     */
    fun onBind(archivedHabitUI: ArchivedHabitUI) {
        binding.tvTitle.text = archivedHabitUI.title
    }

    companion object {
        fun create(parent: ViewGroup, attachToParent: Boolean): ArchivedHabitViewHolder {
            return ArchivedHabitViewHolder(
                ItemHabitArchivedBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    attachToParent
                )
            )
        }
    }
}
