package com.depromeet.threedays.mate.share

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.depromeet.threedays.core.BaseActivity
import com.depromeet.threedays.core.setOnSingleClickListener
import com.depromeet.threedays.mate.R
import com.depromeet.threedays.mate.databinding.ActivityShareMateBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ShareMateActivity : BaseActivity<ActivityShareMateBinding>(R.layout.activity_share_mate) {
    private val viewModel by viewModels<ShareMateViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val habitId = intent.getLongExtra("habitId", -1)
        viewModel.fetchMate(habitId)
        initEvent()
        setObserve()
    }

    private fun initEvent() {
        binding.ivClose.setOnSingleClickListener {
            finish()
        }
        binding.tvImageSave.setOnSingleClickListener {

        }
    }

    private fun setObserve() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    if(it.singleHabit != null) {
                        binding.singleHabit = it.singleHabit
                    }
                    setScreenShotBackgroundColor(it.backgroundResId)
                }
            }
        }
    }

    private fun setScreenShotBackgroundColor(resId: Int) {
        binding.clScreenShotArea.setBackgroundResource(resId)
    }
}
