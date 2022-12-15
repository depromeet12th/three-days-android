package com.depromeet.threedays.mypage.archived_habit

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.depromeet.threedays.core.extensions.gone
import com.depromeet.threedays.core.extensions.invisible
import com.depromeet.threedays.core.extensions.visible
import com.depromeet.threedays.core_design_system.R as CoreDesignSystemResources
import com.depromeet.threedays.mypage.databinding.ItemHabitArchivedBinding

class ArchivedHabitViewHolder(
    private val binding: ItemHabitArchivedBinding,
    private val viewModel: ArchivedHabitViewModel,
) : RecyclerView.ViewHolder(binding.root) {
    /**
     * 데이터 연결해주는 작업
     */
    fun onBind(archivedHabitUI: ArchivedHabitUI) {
        binding.tvTitle.text = archivedHabitUI.title
        if (archivedHabitUI.hasMate()) {
            if (archivedHabitUI.mateOpened) {
                binding.clMateOpened.visible()
                binding.clMateClosed.gone()
            } else {
                binding.clMateOpened.gone()
                binding.clMateClosed.visible()
            }
            with(archivedHabitUI.mate!!) {
                binding.tvMateNickname.text = nickname
                binding.tvMateLevelDescription.text = getLevelDescription()
            }
        } else {
            binding.clMateOpened.gone()
            binding.clMateClosed.gone()
        }
        binding.ivMateProfileImageButtonExpand.imageAlpha = 204 // 256 * 0.8 = 204.8
        binding.ivHabitArchivedIconCheck.apply {
            // editable 일때만 체크 아이콘 보이기
            run {
                if (archivedHabitUI.editable) {
                    visible()
                } else {
                    invisible()
                }
            }
            // 선택 여부 따라 색 바꾸기
            setImageResource(
                if (archivedHabitUI.selected) {
                    CoreDesignSystemResources.drawable.ic_check_gray700_circle
                } else {
                    CoreDesignSystemResources.drawable.ic_check_gray400_circle
                }
            )
        }
        initEvent(archivedHabitUI)
    }

    private fun initEvent(archivedHabitUI: ArchivedHabitUI) {
        binding.clMateOpenedButtonClose.setOnClickListener {
            viewModel.closeMateUI(archivedHabitUI)
        }
        binding.clMateClosed.setOnClickListener {
            viewModel.openMateUI(archivedHabitUI)
        }
        binding.ivHabitArchivedIconCheck.setOnClickListener {
            viewModel.toggleSelected(archivedHabitUI.habitId)
        }
    }

    companion object {
        fun create(
            parent: ViewGroup,
            attachToParent: Boolean,
            viewModel: ArchivedHabitViewModel,
        ): ArchivedHabitViewHolder {
            return ArchivedHabitViewHolder(
                binding = ItemHabitArchivedBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    attachToParent
                ),
                viewModel = viewModel,
            )
        }
    }
}
