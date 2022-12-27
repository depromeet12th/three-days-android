package com.depromeet.threedays.mate

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.depromeet.threedays.core.BaseFragment
import com.depromeet.threedays.core.setOnSingleClickListener
import com.depromeet.threedays.mate.create.step1.model.MateUI
import com.depromeet.threedays.mate.databinding.FragmentMateBinding
import com.depromeet.threedays.mate.onboarding.OnBoardingBottomSheet
import com.depromeet.threedays.mate.share.ShareMateActivity
import com.depromeet.threedays.navigator.ConnectHabitNavigator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.depromeet.threedays.core_design_system.R as core_design

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
        binding.ivShare.setOnSingleClickListener {
            val intent = Intent(requireActivity(), ShareMateActivity::class.java)
            intent.putExtra("habitId", viewModel.uiState.value.mate?.habitId)
            startActivity(intent)
        }
    }

    private fun setObserve() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    showMateOrDefaultView(
                        hasMate = it.hasMate,
                        backgroundResColor = it.backgroundResColor
                    )
                    setMateInfo(mateUI = it.mate)
                    showMateOnboarding(it.isFirstVisitor)
                }
            }
        }
    }

    private fun showMateOrDefaultView(hasMate: Boolean, backgroundResColor: Int) {
        binding.groupHasMate.isVisible = hasMate
        binding.groupNoHabit.isVisible = hasMate.not()
        binding.groupSpeechBubble.isVisible = hasMate
        binding.clBottomSheet.isVisible = hasMate
        binding.clTopLayout.setBackgroundResource(backgroundResColor)
    }

    private fun setMateInfo(mateUI: MateUI?) {
        mateUI?.let {
            binding.tvSpeechBubble.text = it.bubble
            binding.tvLevel.text = getString(R.string.level, it.level)
            binding.tvMateNickname.text = it.title
            binding.tvStartDate.text = getString(R.string.start_date_with_mate, it.createAt.toString().substring(0, 10).replace("-", "."))
        }
    }

    private fun showMateOnboarding(isFirstVisitor: Boolean) {
        if(isFirstVisitor) {
            val modal = OnBoardingBottomSheet {
                viewModel.writeIsFirstVisitor()
            }
            modal.setStyle(DialogFragment.STYLE_NORMAL, core_design.style.RoundCornerBottomSheetDialogTheme)
            modal.show(parentFragmentManager, OnBoardingBottomSheet.TAG)
        }
    }
}
