package com.depromeet.threedays.mate.create.step1

import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.depromeet.threedays.core.BaseActivity
import com.depromeet.threedays.core.setOnSingleClickListener
import com.depromeet.threedays.core.util.dpToPx
import com.depromeet.threedays.mate.R
import com.depromeet.threedays.mate.create.step2.ChooseMateTypeActivity
import com.depromeet.threedays.mate.databinding.ActivityConnectHabitBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ConnectHabitActivity : BaseActivity<ActivityConnectHabitBinding>(R.layout.activity_connect_habit) {
    private val viewModel by viewModels<ConnectHabitViewModel>()
    lateinit var connectHabitAdatper: ConnectHabitAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initView()
        initEvent()
        setObserve()
    }

    private fun initView() {
        connectHabitAdatper = ConnectHabitAdapter(
            setHabitClickStatus = {
                viewModel.setHabitClickStatus(clickedHabit = it)
            },
        )
        binding.rvConnectHabit.apply {
            layoutManager = LinearLayoutManager(this@ConnectHabitActivity)
            adapter = connectHabitAdatper
            addItemDecoration(object : RecyclerView.ItemDecoration() {
                override fun getItemOffsets(
                    outRect: Rect,
                    view: View,
                    parent: RecyclerView,
                    state: RecyclerView.State
                ) {
                    outRect.bottom = 8.dpToPx(this@ConnectHabitActivity)
                }
            })
        }
    }

    private fun initEvent() {
        binding.ivOut.setOnSingleClickListener {
            finish()
        }
        binding.btnNext.setOnSingleClickListener {
            val intent = Intent(this, ChooseMateTypeActivity::class.java)
            intent.putExtra("clickedHabit", viewModel.uiState.value.clickedHabit)
            intent.flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
            startActivity(intent)
        }
    }

    private fun setObserve() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.habits.collect {
                        connectHabitAdatper.submitList(it)
                    }
                }
                launch {
                    viewModel.uiState.collect {
                        binding.ivIllustrator.setBackgroundResource(it.boxImageResId)
                    }
                }
            }
        }
    }
}
