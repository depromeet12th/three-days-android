package com.depromeet.threedays.create.create

import android.graphics.Rect
import android.os.Bundle
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.activity.viewModels
import androidx.core.widget.addTextChangedListener
import com.depromeet.threedays.core.BaseActivity
import com.depromeet.threedays.core.extensions.visibleOrGone
import com.depromeet.threedays.core.setOnSingleClickListener
import com.depromeet.threedays.core.util.Emoji
import com.depromeet.threedays.create.R
import com.depromeet.threedays.create.databinding.ActivityHabitCreateBinding
import com.depromeet.threedays.create.emoji.EmojiBottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HabitCreateActivity : BaseActivity<ActivityHabitCreateBinding>(R.layout.activity_habit_create) {
    private val viewModel by viewModels<HabitCreateViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel
        initView()
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
            finish()
        }

        binding.etHabitTitle.addTextChangedListener {
            binding.tvHabitTitleCounter.text = String.format("%d/15", it?.length ?: 0)
        }

        binding.etNotificationContent.addTextChangedListener {
            binding.tvNotificationContentCounter.text = String.format("%d/25", it?.length ?: 0)
        }

        binding.swNotification.setOnCheckedChangeListener { _, isChecked ->
            binding.containerNotification.visibleOrGone(isChecked)
        }

        binding.tvEmoji.text = Emoji().getEmojiString(Emoji.Word.FIRE)
        binding.tvEmoji.setOnSingleClickListener {
            EmojiBottomSheetDialogFragment().show(supportFragmentManager, "emojiBottomSheetDialogFragment")
        }
    }

    /*
    private fun observe() {
        viewModel.action.onEach { action ->
            when (action) {
                is SaveClick -> {
                    setResult(RESULT_CREATE)
                    finish()
                }
            }
        }.launchIn(lifecycleScope)
    }


    private fun showTimePicker(zonedDateTime: ZonedDateTime, isRunTime: Boolean) {
        val timeSetListener = TimePickerDialog.OnTimeSetListener { _, hour, min ->
            if (isRunTime) {
                viewModel.setStartTime(hour, min)
                binding.tvRunTime.text = getTimeFormat(hour, min)
                if(viewModel.goal.value.notificationTime == null) {
                    viewModel.setNotificationTime(hour, min)
                    binding.tvNotification.text = getTimeFormat(hour, min)
                }
            } else {
                viewModel.setNotificationTime(hour, min)
                binding.tvNotification.text = getTimeFormat(hour, min)
            }
        }
        val dialog = TimePickerDialog(
            this,
            android.R.style.Theme_Holo_Light_Dialog_NoActionBar,
            timeSetListener,
            zonedDateTime.hour,
            zonedDateTime.minute,
            false
        )
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.show()
    }
    */
}
