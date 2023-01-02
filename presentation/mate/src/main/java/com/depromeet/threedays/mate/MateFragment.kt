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
import androidx.recyclerview.widget.GridLayoutManager
import com.depromeet.threedays.core.BaseFragment
import com.depromeet.threedays.core.extensions.Empty
import com.depromeet.threedays.core.util.setOnSingleClickListener
import com.depromeet.threedays.core.util.*
import com.depromeet.threedays.domain.entity.Color
import com.depromeet.threedays.domain.entity.habit.SingleHabit
import com.depromeet.threedays.domain.util.GetStringFromDateTime
import com.depromeet.threedays.mate.create.step1.model.MateUI
import com.depromeet.threedays.mate.databinding.FragmentMateBinding
import com.depromeet.threedays.mate.onboarding.OnBoardingBottomSheet
import com.depromeet.threedays.mate.share.ShareMateActivity
import com.depromeet.threedays.navigator.ConnectHabitNavigator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject
import com.depromeet.threedays.core_design_system.R as core_design


@AndroidEntryPoint
class MateFragment: BaseFragment<FragmentMateBinding, MateViewModel>(R.layout.fragment_mate) {
    override val viewModel by viewModels<MateViewModel>()
    lateinit var clapAdapter: ClapAdapter

    @Inject
    lateinit var connectHabitNavigator: ConnectHabitNavigator

    override fun onResume() {
        super.onResume()

        viewModel.fetchMate()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        initEvent()
        setObserve()
    }

    private fun initView() {
        clapAdapter = ClapAdapter()
        binding.rvClap.apply {
            layoutManager = GridLayoutManager(requireActivity(), 6)
            adapter = clapAdapter
        }
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
            val intent = connectHabitNavigator.intent(requireContext())
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }
        binding.ivShare.setOnSingleClickListener {
            val intent = Intent(requireActivity(), ShareMateActivity::class.java)
            intent.putExtra("habitId", viewModel.uiState.value.mate?.habitId)
            startActivity(intent)
        }
        binding.ivIllustration.setOnSingleClickListener {
            // TODO: very bad quality
            ThreeDaysOneButtonDialogFragment.newInstance(
                data = OneButtonDialogInfo(
                    resId = core_design.drawable.bg_mate_level_1,
                    level = viewModel.uiState.value.mate?.level ?: 0,
                    title = viewModel.uiState.value.mate?.title ?: String.Empty,
                    description = GetStringFromDateTime().invoke(
                        viewModel.uiState.value.mate?.createAt?.toLocalDate() ?: LocalDate.now()
                    ) + "에 진화"
                )
            ).show(parentFragmentManager, ThreeDaysOneButtonDialogFragment.TAG)
        }
        binding.ivDelete.setOnSingleClickListener {
            // TODO: move to strings.xml and viewModel
            ThreeDaysDialogFragment.newInstance(
                data = DialogInfo.EMPTY.copy (
                    title = "정말 삭제하시겠어요?",
                    description = "지금까지의 짝꿍과 함께한 기록이\n전부 사라져요.",
                    cancelText = "아니요",
                    confirmText = "삭제할래요",
                    onPositiveAction = {
                        viewModel.deleteMate()
                    }
                )
            ).show(parentFragmentManager, ThreeDaysDialogFragment.TAG)
        }
    }

    private fun setObserve() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.uiState.collect {
                        showMateOrDefaultView(
                            hasMate = it.hasMate,
                            backgroundResColor = it.backgroundResColor
                        )
                        setMateInfo(mateUI = it.mate)
                        setHabitInfo(habit = it.habit)
                        clapAdapter.submitList(it.stamps)
                    }
                }

                launch {
                    viewModel.uiEffect.collect {
                        when(it) {
                            is UiEffect.ShowToastMessage -> showDeleteSuccessMessage(it.resId)
                        }
                    }
                }

                launch {
                    viewModel.isFirstVisitor.collect {
                        showMateOnboarding(isFirstVisitor = it)
                    }
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
            binding.ivIllustration.setImageResource(it.resolveMateImageResource())

            val clapCount = it.reward ?: 0
            val maxLevel = it.levelUpSectioin?.last() ?: 22
            binding.tvNextLevelGuide.text = getString(R.string.next_level_guide, (maxLevel - clapCount) )
            binding.tvClapCount.text = "${clapCount}개"
            binding.tvMaxLevel.text = "${maxLevel}개"
        }
    }

    private fun setHabitInfo(habit: SingleHabit?) {
        habit?.let {
            binding.tvHabitEmoji.text = habit.emoji.value
            binding.tvHabitTitle.text = habit.title
            binding.tvLevel.setBackgroundResource(
                when(habit.color) {
                    Color.GREEN -> core_design.drawable.bg_rect_green50_r10
                    Color.PINK -> core_design.drawable.bg_rect_pink50_r10
                    Color.BLUE -> core_design.drawable.bg_rect_blue50_r10
                }
            )
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

    private fun showDeleteSuccessMessage(resId: Int) {
        ThreeDaysToast().show(requireActivity(), getString(resId))
    }
}
