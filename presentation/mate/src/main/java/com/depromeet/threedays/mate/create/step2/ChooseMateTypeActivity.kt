package com.depromeet.threedays.mate.create.step2

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.depromeet.threedays.core.BaseActivity
import com.depromeet.threedays.core.setOnSingleClickListener
import com.depromeet.threedays.mate.R
import com.depromeet.threedays.mate.create.step3.SetMateNicknameActivity
import com.depromeet.threedays.mate.databinding.ActivityChooseMateTypeBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ChooseMateTypeActivity : BaseActivity<ActivityChooseMateTypeBinding>(R.layout.activity_choose_mate_type) {
    private val viewModel by viewModels<ChooseMateTypeViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initEvent()
        setUiStateObserver()
    }

    private fun initEvent() {
        binding.clWhippingMate.setOnSingleClickListener {
            viewModel.setMateType(MateType.WhippingMate)
        }
        binding.clCarrotMate.setOnSingleClickListener {
            viewModel.setMateType(MateType.CarrotMate)
        }
        binding.ivOut.setOnSingleClickListener {
            finish()
        }
        binding.btnNext.setOnSingleClickListener {
            val intent = Intent(this, SetMateNicknameActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setMateSelected(
        whippingMateBackgroundRes: Int,
        carrotMateBackgroundRes: Int,
        whippingMateRes: Int,
        carrotMateRes: Int
    ) {
        binding.clWhippingMate.setBackgroundResource(whippingMateBackgroundRes)
        binding.clCarrotMate.setBackgroundResource(carrotMateBackgroundRes)
        binding.ivWhippingMateIllustrator.setBackgroundResource(whippingMateRes)
        binding.ivCarrotMateIllustrator.setBackgroundResource(carrotMateRes)
    }

    private fun setUiStateObserver() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    setMateSelected(
                        whippingMateBackgroundRes = it.whippingMateBackgroundRes,
                        carrotMateBackgroundRes = it.carrotMateBackgroundRes,
                        whippingMateRes = it.whippingMateRes,
                        carrotMateRes = it.carrotMateRes,
                    )
                }
            }
        }
    }
}
