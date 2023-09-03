package com.depromeet.threedays.mate.create.step1

import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.depromeet.threedays.core.BaseActivity
import com.depromeet.threedays.core.analytics.*
import com.depromeet.threedays.core.util.ThreeDaysToast
import com.depromeet.threedays.core.util.dpToPx
import com.depromeet.threedays.core.util.setOnSingleClickListener
import com.depromeet.threedays.domain.exception.ThreeDaysException
import com.depromeet.threedays.mate.R
import com.depromeet.threedays.mate.create.step2.ChooseMateTypeActivity
import com.depromeet.threedays.mate.databinding.ActivityConnectHabitBinding
import dagger.hilt.android.AndroidEntryPoint
import io.sentry.Sentry
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ConnectHabitActivity :
    BaseActivity<ActivityConnectHabitBinding>(R.layout.activity_connect_habit) {
    private val viewModel by viewModels<ConnectHabitViewModel>()
    lateinit var connectHabitAdatper: ConnectHabitAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AnalyticsUtil.event(
            name = ThreeDaysEvent.MateMakingViewed.toString(),
            properties = mapOf(
                MixPanelEvent.ScreenName to "${Screen.MateMaking}1",
            )
        )
        initView()
        initEvent()
        setObserve()
    }

    private fun initView() {
        connectHabitAdatper = ConnectHabitAdapter(
            setHabitClickStatus = {
                viewModel.setHabitClickStatus(clickedHabit = it)
            },
        )
        binding.rvConnectHabit.apply {
            layoutManager = LinearLayoutManager(this@ConnectHabitActivity)
            adapter = connectHabitAdatper
            addItemDecoration(object : RecyclerView.ItemDecoration() {
                override fun getItemOffsets(
                    outRect: Rect,
                    view: View,
                    parent: RecyclerView,
                    state: RecyclerView.State
                ) {
                    outRect.bottom = 8.dpToPx(this@ConnectHabitActivity)
                }
            })
        }
    }

    private fun initEvent() {
        binding.ivOut.setOnSingleClickListener {
            finish()
        }
        binding.btnNext.setOnSingleClickListener {
            AnalyticsUtil.event(
                name = ThreeDaysEvent.ButtonClicked.toString(),
                properties = mapOf(
                    MixPanelEvent.ScreenName to "${Screen.MateMaking}1",
                    MixPanelEvent.ButtonType to ButtonType.Next.toString(),
                )
            )

            val intent = Intent(this, ChooseMateTypeActivity::class.java)
            intent.putExtra("clickedHabit", viewModel.uiState.value.clickedHabit)
            intent.flags = Intent.FLAG_ACTIVITY_NO_ANIMATION
            startActivity(intent)
        }
    }

    private fun setButtonView(
        buttonClickable: Boolean,
        buttonBackgroundRes: Int,
        buttonTextColor: Int
    ) {
        binding.btnNext.apply {
            isClickable = buttonClickable
            setBackgroundResource(buttonBackgroundRes)
            setTextColor(getColor(buttonTextColor))
        }
    }

    private fun setObserve() {
        viewModel.error
            .onEach { error ->
                ThreeDaysToast().error(this, error.message ?: error.defaultMessage)
                if (error.message != ThreeDaysException.INTERNET_CONNECTION_WAS_LOST) {
                    Sentry.captureException(error)
                }
            }
            .launchIn(lifecycleScope)


        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.habits.collect {
                        connectHabitAdatper.submitList(it)
                    }
                }
                launch {
                    viewModel.uiState.collect {
                        binding.ivIllustrator.setBackgroundResource(it.boxImageResId)
                        setButtonView(
                            buttonClickable = it.buttonClickable,
                            buttonBackgroundRes = it.buttonBackgroundRes,
                            buttonTextColor = it.buttonTextColor,
                        )
                    }
                }
            }
        }
    }
}
