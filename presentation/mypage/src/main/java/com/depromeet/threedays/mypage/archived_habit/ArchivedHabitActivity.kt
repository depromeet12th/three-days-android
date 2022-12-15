package com.depromeet.threedays.mypage.archived_habit

import android.graphics.Rect
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.depromeet.threedays.core.BaseActivity
import com.depromeet.threedays.core.extensions.dp
import com.depromeet.threedays.core.extensions.invisible
import com.depromeet.threedays.core.extensions.visible
import com.depromeet.threedays.core.util.ThreeDaysSnackBar
import com.depromeet.threedays.core.util.ThreeDaysToast
import com.depromeet.threedays.core.util.dpToPx
import com.depromeet.threedays.core_design_system.R as CoreDesignSystemResources
import com.depromeet.threedays.mypage.R
import com.depromeet.threedays.mypage.databinding.ActivityArchivedHabitBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ArchivedHabitActivity :
    BaseActivity<ActivityArchivedHabitBinding>(R.layout.activity_archived_habit) {
    private val viewModel by viewModels<ArchivedHabitViewModel>()
    private lateinit var archivedHabitAdapter: ArchivedHabitAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        archivedHabitAdapter = ArchivedHabitAdapter(viewModel)
        initView()

        viewModel.fetchArchivedHabits()
        setObserve()

        initEvent()
    }

    private fun setObserve() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(androidx.lifecycle.Lifecycle.State.STARTED) {
                viewModel.apply {
                    archivedHabits.collect { archivedHabits ->
                        // recycler view
                        archivedHabitAdapter.submitList(archivedHabits)

                        // 삭제하기 버튼
                        val selectedCount = archivedHabits.filter { it.selected }.size
                        if (selectedCount == 0) {
                            binding.btnDelete.text = getString(R.string.btn_delete)
                            binding.btnDelete.setBackgroundResource(CoreDesignSystemResources.drawable.bg_rect_gray200_r10)
                            binding.btnDelete.setTextColor(getColor(CoreDesignSystemResources.color.gray_450))
                        } else {
                            binding.btnDelete.text = "${selectedCount}개 삭제하기"
                            binding.btnDelete.setBackgroundResource(CoreDesignSystemResources.drawable.bg_rect_gray800_r15)
                            binding.btnDelete.setTextColor(getColor(CoreDesignSystemResources.color.white))
                        }
                    }
                }
            }
        }
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(androidx.lifecycle.Lifecycle.State.STARTED) {
                viewModel.apply {
                    editable.collect { editable ->
                        // editable 상태 따라서 편집/나가기 버튼이름 변경
                        binding.tvActionButton.text = getString(
                            if (editable) {
                                R.string.archived_habit_action_quit
                            } else {
                                R.string.archived_habit_action_edit
                            }
                        )
                        // editable 상태 따라서 삭제하기 버튼 보여주거나 가리기
                        if (editable) {
                            binding.btnDelete.visible()
                        } else {
                            binding.btnDelete.invisible()
                        }

                        // recyclerView Bottom Padding 변경 필요
                        if (editable) {
                            binding.rvArchivedHabit.setPadding(0, 14.dp, 0, 80.dp)
                        } else {
                            binding.rvArchivedHabit.setPadding(0, 14.dp, 0, 22.dp)
                        }
                    }
                }
            }
        }
    }

    /**
     * 화면 관련 초기화 작업
     */
    private fun initView() {
        binding.rvArchivedHabit.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = archivedHabitAdapter
            addItemDecoration(object : RecyclerView.ItemDecoration() {
                override fun getItemOffsets(
                    outRect: Rect,
                    view: View,
                    parent: RecyclerView,
                    state: RecyclerView.State
                ) {
                    outRect.bottom = 18.dpToPx(context)
                }
            })
        }

        // TODO: 최초진입인지 여부 조회
        val isFirst = true
        if (isFirst) {
            ArchivedHabitOnboardingSnackBar.show(
                view = binding.flArchivedHabitOnboarding,
                onAction = {
                    // TODO: 읽었다고 저장
                }
            )
        }
    }

    /**
     * 이벤트 관련 초기화 작업
     */
    private fun initEvent() {
        binding.tvActionButton.setOnClickListener {
            viewModel.toggleEditable()
        }
        binding.btnDelete.setOnClickListener {
            viewModel.deleteSelected()
            viewModel.toggleEditable()
            // FIXME: bottom navigation bar 없는 화면에선 토스트가 위에 떠있음.
            ThreeDaysToast().show(it.context, "습관이 완전히 삭제됐어요.")
        }
    }
}
