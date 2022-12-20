package com.depromeet.threedays.history

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.depromeet.threedays.core.BaseFragment
import com.depromeet.threedays.core.setOnSingleClickListener
import com.depromeet.threedays.create.create.HabitCreateActivity
import com.depromeet.threedays.history.databinding.FragmentHistoryBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.time.ZoneId
import java.time.ZonedDateTime

@AndroidEntryPoint
class HistoryFragment: BaseFragment<FragmentHistoryBinding, HistoryViewModel>(R.layout.fragment_history) {
    override val viewModel by viewModels<HistoryViewModel>()
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        initEvent()
        setObserve()
    }

    private fun initView() {
        val now = ZonedDateTime.now(ZoneId.systemDefault())
        binding.tvThisMonth.text = getString(R.string.this_month_history_title, now.monthValue)
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
    }

    private fun setObserve() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.habits.collect {
                    binding.groupNoHabit.isVisible = it.isEmpty()
                    binding.ncvHasHabit.isVisible = it.isNotEmpty()
                }
            }
        }
    }
}
