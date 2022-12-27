package com.depromeet.threedays.mate.create.step3

import android.os.Bundle
import android.text.InputFilter
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.depromeet.threedays.core.BaseActivity
import com.depromeet.threedays.core.setOnSingleClickListener
import com.depromeet.threedays.mate.R
import com.depromeet.threedays.mate.create.step1.model.HabitUI
import com.depromeet.threedays.mate.databinding.ActivitySetMateNicknameBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.regex.Pattern

@AndroidEntryPoint
class SetMateNicknameActivity : BaseActivity<ActivitySetMateNicknameBinding>(R.layout.activity_set_mate_nickname) {
    private val viewModel by viewModels<SetMateNicknameViewMoodel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(intent.hasExtra("clickedHabit")) {
            val clickedHabit = intent.getParcelableExtra<HabitUI>("clickedHabit")
            val mateType = intent.getStringExtra("mateType")
            viewModel.setClickHabit(clickedHabit!!, mateType!!)
        }

        initEditText()
        initEvent()
        setUiStateObserver()
    }

    private fun initEditText() {
        val filterAlphaNumSpace = InputFilter { source, _, _, _, _, _ ->
            val ps = Pattern.compile("^[ㄱ-ㅣ가-힣a-zA-Z0-9]+$")
            if (!ps.matcher(source).matches()) {
                ""
            } else source
        }

        binding.etNickname.filters = arrayOf(filterAlphaNumSpace)
    }

    private fun initEvent() {
        binding.etNickname.doAfterTextChanged {
            viewModel.handleInputText(it.toString())
        }
        binding.ivOut.setOnSingleClickListener {
            finish()
        }
    }

    private fun setUiStateObserver() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    setGuideTextVisible(isGuideVisible = it.isGuideVisible)
                    setButtonView(
                        buttonBackgroundRes = it.buttonBackgroundRes,
                        buttonTextColor = it.buttonTextColor,
                    )
                    setAvailableInputLength(it.inputTextLength)
                    binding.ivIllustrator.setBackgroundResource(it.boxImageResId)
                }
            }
        }
    }

    private fun setGuideTextVisible(isGuideVisible: Boolean) {
        binding.tvGuide.isVisible = isGuideVisible
    }

    private fun setButtonView(buttonBackgroundRes: Int, buttonTextColor: Int) {
        binding.btnNext.setBackgroundResource(buttonBackgroundRes)
        binding.btnNext.setTextColor(getColor(buttonTextColor))
    }

    private fun setAvailableInputLength(inputTextLength: String) {
        binding.tvCountNicknameLength.text = inputTextLength
    }
}
