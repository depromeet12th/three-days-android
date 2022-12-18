package com.depromeet.threedays.create.create

import android.graphics.Rect
import android.os.Bundle
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.CompoundButton
import android.widget.EditText
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import com.depromeet.threedays.core.BaseActivity
import com.depromeet.threedays.core.extensions.formatHourMinute
import com.depromeet.threedays.core.extensions.visibleOrGone
import com.depromeet.threedays.core.setOnSingleClickListener
import com.depromeet.threedays.core.util.DialogInfo
import com.depromeet.threedays.core.util.RangeTimePickerDialogFragment
import com.depromeet.threedays.core.util.ThreeDaysDialogFragment
import com.depromeet.threedays.create.R
import com.depromeet.threedays.create.create.HabitCreateViewModel.Action
import com.depromeet.threedays.create.databinding.ActivityHabitCreateBinding
import com.depromeet.threedays.create.emoji.EmojiBottomSheetDialogFragment
import com.depromeet.threedays.domain.entity.Color
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.time.DayOfWeek
import java.time.LocalTime

@AndroidEntryPoint
class HabitCreateActivity :
    BaseActivity<ActivityHabitCreateBinding>(R.layout.activity_habit_create) {
    private val viewModel by viewModels<HabitCreateViewModel>()

    private val dayOfWeekCheckBoxIdMap = mapOf(
        R.id.cb_monday to DayOfWeek.MONDAY,
        R.id.cb_tuesday to DayOfWeek.TUESDAY,
        R.id.cb_wednesday to DayOfWeek.WEDNESDAY,
        R.id.cb_thursday to DayOfWeek.THURSDAY,
        R.id.cb_friday to DayOfWeek.FRIDAY,
        R.id.cb_saturday to DayOfWeek.SATURDAY,
        R.id.cb_sunday to DayOfWeek.SUNDAY
    )

    private val checkListener by lazy {
        CompoundButton.OnCheckedChangeListener { view, isChecked ->
            val dayOfWeek = requireNotNull(dayOfWeekCheckBoxIdMap[view.id]) {
                "요일을 선택하지 않았습니다"
            }
            if (isChecked) {
                viewModel.addSavingDayOfWeek(dayOfWeek)
            } else {
                viewModel.deleteSavingDayOfWeek(dayOfWeek)
            }
        }
    }

    private val callback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            setBackBtnClickEvent()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.onBackPressedDispatcher.addCallback(this, callback)

        binding.viewModel = viewModel

        initView()
        viewModel.setSaveHabitEnable()
        viewModel.setInformationEntered()
        observe()
    }

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            val v = currentFocus
            if (v is EditText) {
                val outRect = Rect()
                v.getGlobalVisibleRect(outRect)
                if (!outRect.contains(event.rawX.toInt(), event.rawY.toInt())) {
                    v.clearFocus()
                    val imm: InputMethodManager =
                        getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0)
                }
            }
        }
        return super.dispatchTouchEvent(event)
    }

    private fun initView() {
        binding.ivClose.setOnClickListener {
            setBackBtnClickEvent()
        }

        binding.etHabitTitle.addTextChangedListener {
            binding.tvHabitTitleCounter.text = String.format("%d/15", it?.length ?: 0)
        }

        binding.etNotificationContent.addTextChangedListener {
            viewModel.setNotificationContent(it.toString())
            binding.tvNotificationContentCounter.text = String.format("%d/25", it?.length ?: 0)
        }

        binding.swNotification.setOnCheckedChangeListener { _, isChecked ->
            viewModel.setNotificationInfoActive(isChecked)
            binding.containerNotification.visibleOrGone(isChecked)
        }

        binding.tvEmoji.setOnSingleClickListener {
            EmojiBottomSheetDialogFragment.newInstance { emoji ->
                viewModel.setEmoji(emoji)
            }.show(supportFragmentManager, EmojiBottomSheetDialogFragment.TAG)
        }

        binding.groupColor.setOnCheckedChangeListener { _ , id ->
            viewModel.setColor(
                when(id) {
                    R.id.rb_green -> Color.GREEN
                    R.id.rb_blue -> Color.BLUE
                    R.id.rb_pink -> Color.PINK
                    else -> Color.GREEN
                }
            )

        }

        binding.cbMonday.setOnCheckedChangeListener(checkListener)
        binding.cbTuesday.setOnCheckedChangeListener(checkListener)
        binding.cbWednesday.setOnCheckedChangeListener(checkListener)
        binding.cbThursday.setOnCheckedChangeListener(checkListener)
        binding.cbFriday.setOnCheckedChangeListener(checkListener)
        binding.cbSaturday.setOnCheckedChangeListener(checkListener)
        binding.cbSunday.setOnCheckedChangeListener(checkListener)
    }

    private fun observe() {
       viewModel.isSaveHabitEnable
           .onEach {
               binding.tvHabitCreate.isEnabled = it
           }.launchIn(lifecycleScope)

        viewModel.action
            .onEach { action ->
                when(action) {
                    is Action.SaveClick -> {
                        finish()
                    }
                    is Action.NotificationTimeClick -> {
                        showTimePicker(action.currentTime)
                    }
                }
            }.launchIn(lifecycleScope)
    }

    private fun showTimePicker(currentTime: LocalTime) {
        RangeTimePickerDialogFragment.newInstance(
            time = currentTime,
            onConfirmClickListener = { time ->
                viewModel.setNotificationTime(time)
                binding.tvNotificationTime.text = time.formatHourMinute()
            }
        ).show(supportFragmentManager, RangeTimePickerDialogFragment.TAG)
    }

    fun setBackBtnClickEvent() {
        if(viewModel.isInformationEntered.value) {
            ThreeDaysDialogFragment.newInstance(
                data = DialogInfo.EMPTY.copy(
                    onPositiveAction = { finish() },
                    title = getString(com.depromeet.threedays.core.R.string.title_cancel_habit_create),
                    description = getString(com.depromeet.threedays.core.R.string.description_cancel_habit_create),
                    cancelText = getString(com.depromeet.threedays.core.R.string.reply_no),
                    confirmText = getString(com.depromeet.threedays.core.R.string.reply_go_out)
                )
            ).show(supportFragmentManager, ThreeDaysDialogFragment.TAG)
        }
        else {
            finish()
        }
    }
}
