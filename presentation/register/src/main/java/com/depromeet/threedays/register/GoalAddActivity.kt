package com.depromeet.threedays.register

import android.graphics.Rect
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.activity.viewModels
import androidx.core.widget.addTextChangedListener
import com.depromeet.threedays.core.BaseActivity
import com.depromeet.threedays.register.databinding.ActivityGoalAddBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GoalAddActivity : BaseActivity<ActivityGoalAddBinding>(R.layout.activity_goal_add) {
    private val viewModel by viewModels<GoalAddViewModel>()

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
}
