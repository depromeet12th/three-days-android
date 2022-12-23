package com.depromeet.threedays.history.detail.view

import android.content.Context
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.view.View
import com.depromeet.threedays.core.extensions.getColorCompat
import com.depromeet.threedays.core.extensions.getDrawableCompat
import com.depromeet.threedays.domain.entity.Color
import com.depromeet.threedays.history.R
import com.depromeet.threedays.history.databinding.CalendarDayBinding
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.DayPosition
import com.kizitonwose.calendar.view.MonthDayBinder
import com.kizitonwose.calendar.view.ViewContainer
import java.time.DayOfWeek
import java.time.LocalDate
import com.depromeet.threedays.core_design_system.R as designR

class DayBind(private val executeDateWithStatusList: Map<LocalDate, Status> = emptyMap(), private val color: Color) :
    MonthDayBinder<DayContainer> {
    private val today = LocalDate.now()
    private val clipLevelHalf = 5000

    override fun bind(container: DayContainer, data: CalendarDay) {
        val context = container.view.context
        val textView = container.textView
        val roundBgView = container.roundBgView
        val continuousBgView = container.continuousBgView
        val leftSpaceView = container.leftSpaceView
        val rightSpaceView = container.rightSpaceView

        val rangeStartBackgroundCompat = when(color) {
            Color.GREEN -> context.getDrawableCompat(R.drawable.bg_range_start_green50)
            Color.BLUE -> context.getDrawableCompat(R.drawable.bg_range_start_blue50)
            Color.PINK -> context.getDrawableCompat(R.drawable.bg_range_start_pink50)
        }
        val rangeEndBackgroundCompat = when(color) {
            Color.GREEN -> context.getDrawableCompat(R.drawable.bg_range_end_green50)
            Color.BLUE -> context.getDrawableCompat(R.drawable.bg_range_end_blue50)
            Color.PINK -> context.getDrawableCompat(R.drawable.bg_range_end_pink50)
        }
        val rangeStartBackground = rangeStartBackgroundCompat.also { it.level = clipLevelHalf }
        val rangeEndBackground = rangeEndBackgroundCompat.also { it.level = clipLevelHalf }
        val rangeBetweenBackground = context.getDrawableCompat(R.drawable.bg_range_middle) as GradientDrawable
        val singleBackground = context.getDrawableCompat(R.drawable.bg_single_selection) as GradientDrawable
        val todayBackground = context.getDrawableCompat(R.drawable.bg_today)

        rangeBetweenBackground.setColor(context.getHabitColor(color))
        singleBackground.setColor(context.getHabitColor(color))

        container.textView.text = data.date.dayOfMonth.toString()
        roundBgView.visibility = View.INVISIBLE
        continuousBgView.visibility = View.INVISIBLE
        leftSpaceView.visibility = View.INVISIBLE
        rightSpaceView.visibility = View.INVISIBLE
        textView.setTextAppearance(designR.style.Typography_Calendar_10dp)

        val isExecuteDate = executeDateWithStatusList.any {
            val day = it.key
            day == data.date
        }

        if(data.position == DayPosition.MonthDate) {
            textView.setTextColor(context.getColorCompat(designR.color.gray_600))

            if (data.date == today) {
                textView.setTextAppearance(designR.style.Typography_CalendarDay)
                roundBgView.applyBackground(todayBackground)
            }

            if(isExecuteDate) {
                val key: LocalDate = data.date
                val status = executeDateWithStatusList[key]!!

                textView.setTextColor(context.getColorCompat(designR.color.white))

                when (status) {
                    Status.START -> {
                        rightSpaceView.applyEndBackground(context = context, isEnd = (data.date.dayOfWeek == DayOfWeek.SATURDAY))
                        continuousBgView.applyBackground(rangeStartBackground)
                        roundBgView.applyBackground(singleBackground)
                    }
                    Status.BETWEEN -> {
                        leftSpaceView.applyEndBackground(context = context, isEnd = (data.date.dayOfWeek == DayOfWeek.SUNDAY))
                        rightSpaceView.applyEndBackground(context = context, isEnd = (data.date.dayOfWeek == DayOfWeek.SATURDAY))
                        continuousBgView.applyBackground(rangeBetweenBackground)
                    }
                    Status.END -> {
                        leftSpaceView.applyEndBackground(context = context, isEnd = (data.date.dayOfWeek == DayOfWeek.SUNDAY))
                        continuousBgView.applyBackground(rangeEndBackground)
                        roundBgView.applyBackground(singleBackground)
                    }
                    Status.SINGLE -> {
                        roundBgView.applyBackground(singleBackground)
                    }
                }
            }
        } else {
            textView.setTextColor(context.getColorCompat(designR.color.white))
        }
    }

    private fun View.applyBackground(drawable: Drawable) {
        visibility = View.VISIBLE
        background = drawable
    }

    private fun View.applyEndBackground(context: Context, isEnd: Boolean) {
        visibility = View.VISIBLE
        val backGroundWithColor = if(isEnd) (context.getDrawableCompat(R.drawable.bg_rect_white)) as GradientDrawable else
            (context.getDrawableCompat(R.drawable.bg_rect_green50)) as GradientDrawable
        backGroundWithColor.setColor(context.getHabitColor(color))
        background = backGroundWithColor

    }

    override fun create(view: View): DayContainer = DayContainer(view)

    companion object {
        fun newInstance(executeDateList: Map<LocalDate, Status> = emptyMap(), color: Color): DayBind =
            DayBind(executeDateWithStatusList = executeDateList, color = color)
    }
}

class DayContainer(view: View) : ViewContainer(view) {
    private val binding = CalendarDayBinding.bind(view)
    val textView = binding.tvDay
    val roundBgView = binding.vRound
    val continuousBgView = binding.vBackground
    val leftSpaceView = binding.vLeftSpace
    val rightSpaceView = binding.vRightSpace
}

fun Context.getHabitColor(color: Color): Int {
    return when(color) {
        Color.GREEN -> this.getColorCompat(designR.color.green_50)
        Color.BLUE -> this.getColorCompat(designR.color.blue_50)
        Color.PINK -> this.getColorCompat(designR.color.pink_50)
    }
}

fun Context.getHabitLightColor(color: Color): Int {
    return when(color) {
        Color.GREEN -> this.getColorCompat(designR.color.green_10)
        Color.BLUE -> this.getColorCompat(designR.color.blue_10)
        Color.PINK -> this.getColorCompat(designR.color.pink_10)
    }
}

enum class Status {
    START,
    BETWEEN,
    END,
    SINGLE
}
