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
                    showMateOrDefaultView(it.hasMate)
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
}
