package com.depromeet.threedays.register.update

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.graphics.Rect
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.activity.viewModels
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import com.depromeet.threedays.core.BaseActivity
import com.depromeet.threedays.register.R
import com.depromeet.threedays.register.update.GoalUpdateViewModel.Action.*
import com.depromeet.threedays.register.databinding.ActivityGoalUpdateBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.time.ZonedDateTime

@AndroidEntryPoint
class GoalUpdateActivity : BaseActivity<ActivityGoalUpdateBinding>(R.layout.activity_goal_update) {
    private val viewModel by viewModels<GoalUpdateViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewModel = viewModel
        initView()
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
            finish()
        }

        binding.etGoalName.addTextChangedListener {
            binding.tvGoalCounter.text = String.format("%d/15", it?.length ?: 0)
        }

        binding.etNotificationContent.addTextChangedListener {
            binding.tvNotificationCounter.text = String.format("%d/20", it?.length ?: 0)
        }

        binding.swGoalPeriod.setOnCheckedChangeListener { _, isChecked ->
            binding.clPeriod.visibility = if (isChecked) View.VISIBLE else View.GONE
        }

        binding.swRunTime.setOnCheckedChangeListener { _, isChecked ->
            binding.tvRunTime.visibility = if (isChecked) View.VISIBLE else View.GONE
        }
    }

    private fun observe() {
        viewModel.action.onEach { action ->
            when(action){
                is StartCalendarClick -> showDatePicker(action.currentDate, true)
                is EndCalendarClick -> showDatePicker(action.currentDate, false)
                is RunTimeClick -> showTimePicker(action.currentTime)
            }
        }.launchIn(lifecycleScope)
    }

    private fun showDatePicker(zonedDateTime: ZonedDateTime, isStart: Boolean) {
        val dateSetListener = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            if (isStart) {
                viewModel.setStartDate(year, month + 1, dayOfMonth)
            } else {
                viewModel.setEndDate(year, month + 1, dayOfMonth)
            }
        }

        DatePickerDialog(
            this,
            dateSetListener,
            zonedDateTime.year,
            zonedDateTime.monthValue - 1,
            zonedDateTime.dayOfMonth
        ).show()
    }

    private fun showTimePicker(zonedDateTime: ZonedDateTime) {
        val timeSetListener = TimePickerDialog.OnTimeSetListener { _, hour, min ->
            viewModel.setStartTimeWithNotificationTime(hour, min)
            binding.tvRunTime.text =
                when (hour) {
                    12 -> String.format(
                        getString(R.string.three_days_time_afternoon_format),
                        hour,
                        min
                    )
                    in 13..23 -> String.format(
                        getString(R.string.three_days_time_afternoon_format),
                        hour - 12,
                        min
                    )
                    24 -> String.format(getString(R.string.three_days_time_morning_format), 0, min)
                    else -> String.format(
                        getString(R.string.three_days_time_morning_format),
                        hour,
                        min
                    )
                }.also { binding.tvNotification.text = it }
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
}
