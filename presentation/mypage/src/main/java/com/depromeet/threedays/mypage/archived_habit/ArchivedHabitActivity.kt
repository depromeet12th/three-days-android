package com.depromeet.threedays.mypage.archived_habit

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.depromeet.threedays.core.BaseActivity
import com.depromeet.threedays.mypage.R
import com.depromeet.threedays.mypage.databinding.ActivityArchivedHabitBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ArchivedHabitActivity :
    BaseActivity<ActivityArchivedHabitBinding>(R.layout.activity_archived_habit) {
    private val viewModel by viewModels<ArchivedHabitViewModel>()
    private lateinit var archivedHabitAdapter: ArchivedHabitAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        archivedHabitAdapter = ArchivedHabitAdapter()
        initView()

        viewModel.fetchArchivedHabits()
        setObserve()
    }

    private fun setObserve() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(androidx.lifecycle.Lifecycle.State.STARTED) {
                viewModel.apply {
                    archivedHabits.collect {
                        archivedHabitAdapter.submitList(it)
//                        binding.containerNotificationEmpty.visibility = if (it.isEmpty()) View.VISIBLE else View.GONE
                    }
                }
            }
        }
    }

    private fun initView() {
        binding.rvArchivedHabit.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = archivedHabitAdapter

            val dividerItemDecoration = DividerItemDecoration(context, LinearLayoutManager.VERTICAL)
            addItemDecoration(dividerItemDecoration)
        }

        // 최초진입시
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
}
