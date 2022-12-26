package com.depromeet.threedays.history

import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.depromeet.threedays.core.BaseFragment
import com.depromeet.threedays.core.setOnSingleClickListener
import com.depromeet.threedays.create.create.HabitCreateActivity
import com.depromeet.threedays.history.databinding.FragmentHistoryBinding
import com.depromeet.threedays.history.detail.DetailHistoryActivity
import com.depromeet.threedays.history.model.HabitUI
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HistoryFragment: BaseFragment<FragmentHistoryBinding, HistoryViewModel>(R.layout.fragment_history) {
    override val viewModel by viewModels<HistoryViewModel>()
    lateinit var habitAdapter: HabitAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        initEvent()
        setObserve()
    }

    private fun initView() {
        habitAdapter = HabitAdapter()
        binding.rvHabit.apply {
            layoutManager = LinearLayoutManager(requireActivity())
            adapter = habitAdapter
            addItemDecoration(object : RecyclerView.ItemDecoration(){
                override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
                    outRect.bottom = 20
                }
            })
        }
    }

    private fun initEvent() {
        binding.ivTooltip.setOnClickListener {
            binding.tvTooltip.apply {
                visibility = if (isVisible) {
                    View.GONE
                } else {
                    View.VISIBLE
                }
            }
        }
        binding.btnCreateHabit.setOnSingleClickListener {
            startActivity(Intent(requireActivity(), HabitCreateActivity::class.java))
        }
        binding.ivPrevMonth.setOnSingleClickListener {
            startActivity(Intent(requireActivity(), DetailHistoryActivity::class.java))
        }
        binding.ivNextMonth.setOnSingleClickListener {

        }
    }

    private fun fetchHabits(habits: List<HabitUI>) {
        habitAdapter.submitList(habits)
        binding.groupNoHabit.isVisible = habits.isEmpty()
        binding.ncvHasHabit.isVisible = habits.isNotEmpty()
    }

    private fun setObserve() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.uiState.collect {
                        fetchHabits(it.habits)
                        binding.tvThisMonth.text = getString(R.string.this_month_history_title, it.thisMonth)
                    }
                }
            }
        }
    }
}
