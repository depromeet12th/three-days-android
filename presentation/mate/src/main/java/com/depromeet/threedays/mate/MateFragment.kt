package com.depromeet.threedays.mate

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import com.depromeet.threedays.core.BaseFragment
import com.depromeet.threedays.core.analytics.AnalyticsUtil
import com.depromeet.threedays.core.analytics.ButtonType
import com.depromeet.threedays.core.analytics.MixPanelEvent
import com.depromeet.threedays.core.analytics.Screen
import com.depromeet.threedays.core.analytics.ThreeDaysEvent
import com.depromeet.threedays.core.extensions.Empty
import com.depromeet.threedays.core.extensions.gone
import com.depromeet.threedays.core.extensions.visible
import com.depromeet.threedays.core.util.DialogInfo
import com.depromeet.threedays.core.util.OneButtonDialogInfo
import com.depromeet.threedays.core.util.ThreeDaysDialogFragment
import com.depromeet.threedays.core.util.ThreeDaysNoButtonDialogFragment
import com.depromeet.threedays.core.util.ThreeDaysOneButtonDialogFragment
import com.depromeet.threedays.core.util.ThreeDaysToast
import com.depromeet.threedays.core.util.setOnSingleClickListener
import com.depromeet.threedays.domain.entity.Color
import com.depromeet.threedays.domain.entity.habit.SingleHabit
import com.depromeet.threedays.domain.entity.mate.MateType
import com.depromeet.threedays.domain.exception.ThreeDaysException
import com.depromeet.threedays.domain.util.GetStringFromDateTime
import com.depromeet.threedays.mate.MateImageResourceResolver.Companion.levelToResourceFunction
import com.depromeet.threedays.mate.create.step1.ConnectHabitActivity
import com.depromeet.threedays.mate.create.step1.model.MateUI
import com.depromeet.threedays.mate.databinding.FragmentMateBinding
import com.depromeet.threedays.mate.onboarding.OnBoardingBottomSheet
import com.depromeet.threedays.mate.share.ShareMateActivity
import com.depromeet.threedays.navigator.ArchivedHabitNavigator
import com.depromeet.threedays.navigator.ConnectHabitNavigator
import com.depromeet.threedays.navigator.HabitCreateNavigator
import com.google.android.material.bottomsheet.BottomSheetBehavior
import dagger.hilt.android.AndroidEntryPoint
import io.sentry.Sentry
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject
import com.depromeet.threedays.core_design_system.R as core_design

@AndroidEntryPoint
class MateFragment : BaseFragment<FragmentMateBinding, MateViewModel>(R.layout.fragment_mate) {
    override val viewModel by viewModels<MateViewModel>()
    lateinit var clapAdapter: ClapAdapter
    lateinit var behavior: BottomSheetBehavior<ConstraintLayout>

    @Inject
    lateinit var connectHabitNavigator: ConnectHabitNavigator

    @Inject
    lateinit var archivedHabitNavigator: ArchivedHabitNavigator

    @Inject
    lateinit var habitCreateNavigator: HabitCreateNavigator

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
            AnalyticsUtil.event(
                name = ThreeDaysEvent.MateDefaultViewed.toString(),
                properties = mapOf(
                    MixPanelEvent.ScreenName to Screen.MateDefault.toString(),
                    MixPanelEvent.ButtonType to ButtonType.NewMate.toString(),
                )
            )

            if (viewModel.uiState.value.hasHabit) {
                val intent = connectHabitNavigator.intent(requireContext())
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
            } else {
                ThreeDaysDialogFragment.newInstance(
                    DialogInfo.EMPTY.copy(
                        onPositiveAction = {
                            val intent = habitCreateNavigator.intent(requireContext())
                            startActivity(intent)
                        },
                        title = getString(R.string.no_habit_dialog_title),
                        description = getString(R.string.no_habit_dialog_description),
                        confirmText = getString(R.string.no_habit_dialog_confirm_text),
                        buttonTopMargin = 30f,
                    )
                ).show(parentFragmentManager, ThreeDaysDialogFragment.TAG)
            }
        }
        binding.ivShare.setOnSingleClickListener {
            AnalyticsUtil.event(
                name = ThreeDaysEvent.MateShareClicked.toString(),
                properties = mapOf(
                    MixPanelEvent.ScreenName to Screen.MateHome.toString(),
                    MixPanelEvent.ButtonType to ButtonType.Share.toString(),
                )
            )

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
                data = DialogInfo.EMPTY.copy(
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
        binding.btnSaveMate.setOnSingleClickListener {
            AnalyticsUtil.event(
                name = ThreeDaysEvent.MateSaveClicked.toString(),
                properties = mapOf(
                    MixPanelEvent.ScreenName to Screen.MateCompleted.toString(),
                    MixPanelEvent.ButtonType to ButtonType.MateSave.toString(),
                )
            )

            ThreeDaysDialogFragment.newInstance(
                data = DialogInfo.EMPTY.copy(
                    onPositiveAction = { startActivity(archivedHabitNavigator.intent(requireContext())) },
                    iconResId = com.depromeet.threedays.core_design_system.R.drawable.ic_star_check,
                    title = getString(R.string.max_level_dialog_title),
                    description = getString(
                        R.string.max_level_dialog_description,
                        viewModel.uiState.value.mate?.level ?: 0
                    ),
                    confirmText = getString(R.string.going_to_see),
                    buttonTopMargin = 30f
                )
            ).show(parentFragmentManager, ThreeDaysDialogFragment.TAG)
        }
        behavior = BottomSheetBehavior.from(binding.clBottomSheet)
        behavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onSlide(bottomSheet: View, slideOffset: Float) {

            }

            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_COLLAPSED -> {
                        binding.ivArrow.setImageResource(core_design.drawable.ic_arrow_up)
                        binding.viewBg.gone()
                    }

                    BottomSheetBehavior.STATE_DRAGGING -> {
                        if (!binding.viewBg.isVisible) {
                            binding.viewBg.visible()
                        }
                    }

                    BottomSheetBehavior.STATE_EXPANDED -> {
                        AnalyticsUtil.event(
                            name = ThreeDaysEvent.MateClapOpenClicked.toString(),
                            properties = mapOf(
                                MixPanelEvent.ScreenName to Screen.MateDefault.toString(),
                                MixPanelEvent.ButtonType to ButtonType.MateClapOpen.toString(),
                            )
                        )
                        binding.ivArrow.setImageResource(core_design.drawable.ic_arrow_down)
                    }

                    BottomSheetBehavior.STATE_HIDDEN -> {

                    }

                    BottomSheetBehavior.STATE_SETTLING -> {

                    }
                }
            }
        })
        binding.ivArrow.setOnSingleClickListener {
            if (behavior.state == BottomSheetBehavior.STATE_COLLAPSED) {
                binding.viewBg.visible()
                behavior.state = BottomSheetBehavior.STATE_EXPANDED
            } else if (behavior.state == BottomSheetBehavior.STATE_EXPANDED) {
                binding.viewBg.gone()
                behavior.state = BottomSheetBehavior.STATE_COLLAPSED
            }
        }
    }

    private fun setObserve() {
        viewModel.error
            .onEach { error ->
                ThreeDaysToast().error(requireContext(), error.message ?: error.defaultMessage)
                if (error.message != ThreeDaysException.INTERNET_CONNECTION_WAS_LOST) {
                    Sentry.captureException(error)
                }
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.uiState.collect {
                        if (it.isMateInitialized && it.isHabitListInitialized) {
                            if ((it.hasMate && it.isHabitInitialized) || !it.hasMate) {
                                binding.progressMate.gone()
                                showMateOrDefaultView(
                                    hasMate = it.hasMate,
                                    backgroundResColor = it.backgroundResColor
                                )
                            }
                        } else {
                            binding.groupHasMate.gone()
                            binding.groupNoHabit.gone()
                            binding.groupSpeechBubble.gone()
                            binding.clBottomSheet.gone()

                            binding.progressMate.visible()
                        }

                        setMateInfo(mateUI = it.mate)
                        setHabitInfo(habit = it.habit)
                        clapAdapter.submitList(it.stamps)
                    }
                }

                launch {
                    viewModel.uiEffect.collect {
                        when (it) {
                            is UiEffect.ShowToastMessage -> showDeleteSuccessMessage(it.resId)
                            is UiEffect.ShowAchieveMaxLevel -> showAchieveMaxLevel(it.mateLevel)
                            UiEffect.ShowMateOnboarding -> showMateOnboarding()
                        }
                    }
                }
            }
        }
    }

    private fun showMateOrDefaultView(hasMate: Boolean, backgroundResColor: Int) {
        binding.groupHasMate.isVisible = hasMate
        binding.groupNoHabit.isVisible = hasMate.not()
        binding.clBottomSheet.isVisible = hasMate
        binding.clTopLayout.setBackgroundResource(backgroundResColor)

        val window = requireActivity().window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        if (hasMate) {
            window.statusBarColor =
                ContextCompat.getColor(requireActivity(), core_design.color.gray_background)
        } else {
            window.statusBarColor =
                ContextCompat.getColor(requireActivity(), core_design.color.white)
        }
    }

    private fun setMateInfo(mateUI: MateUI?) {
        mateUI?.let {
            binding.tvSpeechBubble.text = it.bubble
            binding.tvLevel.text = getString(R.string.level, it.level)
            binding.tvMateNickname.text = it.title
            binding.tvStartDate.text = getString(
                R.string.start_date_with_mate,
                it.createAt.toString().substring(0, 10).replace("-", ".")
            )
            binding.ivIllustration.setImageResource(it.resolveMateImageResource())

            val clapCount = it.reward ?: 0
            val maxLevel = it.levelUpSectioin?.last() ?: 22
            binding.tvClapCount.text = "${clapCount}개"
            binding.tvMaxLevel.text = "${maxLevel}개"

            val isMaxLevel = (mateUI.levelUpSectioin?.last() ?: 22) == mateUI.reward
            if (isMaxLevel) {
                AnalyticsUtil.event(
                    name = ThreeDaysEvent.MateCompletedViewed.toString(),
                    properties = mapOf(
                        MixPanelEvent.ScreenName to Screen.MateCompleted.toString(),
                    )
                )

                binding.tvNextLevelGuide.text = if (mateUI.characterType == MateType.CARROT) {
                    getString(R.string.max_level_carrot_mate_guide)
                } else {
                    getString(R.string.max_level_whip_mate_guide)
                }
            } else {
                binding.tvNextLevelGuide.text =
                    getString(R.string.next_level_guide, (maxLevel - clapCount))
            }

            binding.groupAchieveMaxLevel.isVisible = isMaxLevel
        }
    }

    private fun setHabitInfo(habit: SingleHabit?) {
        habit?.let {
            binding.tvHabitEmoji.text = habit.emoji.value
            binding.tvHabitTitle.text = habit.title
            binding.tvLevel.setBackgroundResource(
                when (habit.color) {
                    Color.GREEN -> core_design.drawable.bg_rect_green50_r10
                    Color.PINK -> core_design.drawable.bg_rect_pink50_r10
                    Color.BLUE -> core_design.drawable.bg_rect_blue50_r10
                }
            )
        }
    }

    private fun showMateOnboarding() {
        val modal = OnBoardingBottomSheet {
            viewModel.writeIsFirstVisitor()
            startActivity(Intent(requireActivity(), ConnectHabitActivity::class.java))
        }
        modal.setStyle(
            DialogFragment.STYLE_NORMAL,
            core_design.style.RoundCornerBottomSheetDialogTheme
        )
        modal.show(parentFragmentManager, OnBoardingBottomSheet.TAG)
    }

    private fun showDeleteSuccessMessage(resId: Int) {
        ThreeDaysToast().show(requireActivity(), getString(resId))
    }

    private fun showAchieveMaxLevel(
        mateLevel: Int,
    ) {
        AnalyticsUtil.event(
            name = ThreeDaysEvent.MateLevelupViewed.toString(),
            properties = mapOf(
                MixPanelEvent.ScreenName to Screen.MateLevelup.toString(),
            )
        )

        ThreeDaysNoButtonDialogFragment(
            resId = levelToResourceFunction(mateLevel),
            content = "최종 레벨 달성"
        ).show(
            parentFragmentManager, ThreeDaysNoButtonDialogFragment.TAG
        )
    }

    override fun onStop() {
        super.onStop()

        val window = requireActivity().window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor =
            ContextCompat.getColor(requireActivity(), core_design.color.gray_background)
    }
}
