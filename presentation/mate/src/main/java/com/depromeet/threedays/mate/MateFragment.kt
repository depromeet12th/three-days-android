package com.depromeet.threedays.mate

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.depromeet.threedays.core.BaseFragment
import com.depromeet.threedays.core.setOnSingleClickListener
import com.depromeet.threedays.mate.create.step1.model.MateUI
import com.depromeet.threedays.mate.databinding.FragmentMateBinding
import com.depromeet.threedays.navigator.ConnectHabitNavigator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MateFragment: BaseFragment<FragmentMateBinding, MateViewModel>(R.layout.fragment_mate) {
    override val viewModel by viewModels<MateViewModel>()
    
    @Inject
    lateinit var connectHabitNavigator: ConnectHabitNavigator

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initEvent()
        setObserve()
    }

    private fun initEvent() {
        binding.ivTooltip.setOnSingleClickListener {
            binding.clTooltip.apply {
                visibility = if (isVisible) {
                    View.GONE
                } else {
                    View.VISIBLE
                }
            }
        }
        binding.btnCreateMate.setOnSingleClickListener {
            startActivity(connectHabitNavigator.intent(requireContext()))
        }
    }

    private fun setObserve() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    showMateOrDefaultView(hasMate = it.hasMate)
                    setMateInfo(mateUI = it.mate) // TODO: api가 수정되면 습관과 연결된 짝꿍으로 변경할 것
                }
            }
        }
    }

    private fun showMateOrDefaultView(hasMate: Boolean) {
        binding.groupHasMate.isVisible = hasMate
        binding.groupNoHabit.isVisible = hasMate.not()
        binding.groupSpeechBubble.isVisible = hasMate
        binding.clBottomSheet.isVisible = hasMate
    }

    private fun setMateInfo(mateUI: List<MateUI?>) { // TODO: api가 수정되면 습관과 연결된 짝꿍으로 변경할 것
        if(mateUI.isNotEmpty()) {
            mateUI[0]?.let {
                binding.tvSpeechBubble.text = it.bubble
                binding.tvLevel.text = getString(R.string.level, it.level)
                binding.tvMateNickname.text = it.title
                binding.tvStartDate.text = getString(R.string.start_date_with_mate, it.createAt.toString().substring(0, 10).replace("-", ".")) // TODO: api가 수정되면 습관과 연결된 짝꿍으로 변경할 것
            }
        }
    }
}
