package com.depromeet.threedays.mate.create.step1

import android.os.Bundle
import androidx.activity.viewModels
import com.depromeet.threedays.core.BaseActivity
import com.depromeet.threedays.mate.R
import com.depromeet.threedays.mate.databinding.ActivityConnectHabitBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ConnectHabitActivity : BaseActivity<ActivityConnectHabitBinding>(R.layout.activity_connect_habit) {
    private val viewModel by viewModels<ConnectHabitViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    private fun setObserve() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.habits.collect {
                    connectHabitAdatper.submitList(it)
                }
            }
        }
    }
}

