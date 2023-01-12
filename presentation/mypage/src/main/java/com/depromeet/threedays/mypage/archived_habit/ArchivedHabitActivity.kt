package com.depromeet.threedays.mypage.archived_habit

import android.graphics.Rect
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.depromeet.threedays.core.BaseActivity
import com.depromeet.threedays.core.extensions.dp
import com.depromeet.threedays.core.extensions.invisible
import com.depromeet.threedays.core.extensions.visible
import com.depromeet.threedays.core.util.OneButtonDialogInfo
import com.depromeet.threedays.core.util.ThreeDaysOneButtonDialogFragment
import com.depromeet.threedays.core.util.ThreeDaysToast
import com.depromeet.threedays.core.util.dpToPx
import com.depromeet.threedays.mypage.R
import com.depromeet.threedays.mypage.databinding.ActivityArchivedHabitBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import com.depromeet.threedays.core_design_system.R as CoreDesignSystemResources

@AndroidEntryPoint
class ArchivedHabitActivity :
    BaseActivity<ActivityArchivedHabitBinding>(R.layout.activity_archived_habit) {
    private val viewModel by viewModels<ArchivedHabitViewModel>()
    private lateinit var archivedHabitAdapter: ArchivedHabitAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        archivedHabitAdapter = ArchivedHabitAdapter(
            closeMateUIFunction = { viewModel.closeMateUI(it) },
            openMateUIFunction = { viewModel.openMateUI(it) },
            toggleSelectedFunction = { viewModel.toggleSelected(it.habitId) },
            openMateDialogFunction = {
                ThreeDaysOneButtonDialogFragment.newInstance(
                    data = OneButtonDialogInfo(
                        resId = it.resolveMateImageResource(),
                        level = it.level,
                        title = it.nickname,
                        description = it.getLevelDescription(),
                    ),
                ).show(
                    supportFragmentManager,
                    ThreeDaysOneButtonDialogFragment.TAG,
                )
            }
        )

        initView()
        setObserve()
        initEvent()
    }

    private fun setObserve() {
        viewModel.error
            .onEach { errorMessage -> ThreeDaysToast().error(this, errorMessage) }
            .launchIn(lifecycleScope)

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(androidx.lifecycle.Lifecycle.State.STARTED) {
                launch {
                    viewModel.archivedHabits.collect { archivedHabits ->
                        // recycler view
                        archivedHabitAdapter.submitList(archivedHabits)

                        if (archivedHabits.isEmpty()) {
                            binding.rvArchivedHabit.invisible()
                            binding.groupArchivedHabitEmpty.visible()
                        } else {
                            binding.rvArchivedHabit.visible()
                            binding.groupArchivedHabitEmpty.invisible()
                        }

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
                launch {
                    viewModel.uiEffect.collect {
                        when(it) {
                            UiEffect.ShowSnackBar -> showSnackBar()
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
    }

    /**
     * 이벤트 관련 초기화 작업
     */
    private fun initEvent() {
        binding.ivBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
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

    /**
     * 보관된 습관 안내
     */
    private fun showSnackBar() {
        ArchivedHabitOnboardingSnackBar.show(
            view = binding.flArchivedHabitOnboarding,
        )
    }
}
