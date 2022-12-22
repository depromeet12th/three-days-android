package com.depromeet.threedays.history.detail.view

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.View
import com.depromeet.threedays.core.extensions.getColorCompat
import com.depromeet.threedays.core.extensions.getDrawableCompat
import com.depromeet.threedays.history.R
import com.depromeet.threedays.history.databinding.CalendarDayBinding
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.DayPosition
import com.kizitonwose.calendar.view.MonthDayBinder
import com.kizitonwose.calendar.view.ViewContainer
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.time.temporal.WeekFields
import java.util.*
import com.depromeet.threedays.core_design_system.R as designR

class DayBind(private val executeDateWithStatusList: Map<String, Status> = emptyMap()) :
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

        val rangeStartBackground = context.getDrawableCompat(R.drawable.bg_range_start).also { it.level = clipLevelHalf }
        val rangeEndBackground = context.getDrawableCompat(R.drawable.bg_range_end).also { it.level = clipLevelHalf }
        val rangeBetweenBackground = context.getDrawableCompat(R.drawable.bg_range_middle)
        val singleBackground = context.getDrawableCompat(R.drawable.bg_single_selection)
        val todayBackground = context.getDrawableCompat(R.drawable.bg_today)


        container.textView.text = data.date.dayOfMonth.toString()
        roundBgView.visibility = View.INVISIBLE
        continuousBgView.visibility = View.INVISIBLE
        leftSpaceView.visibility = View.INVISIBLE
        rightSpaceView.visibility = View.INVISIBLE
        textView.setTextAppearance(designR.style.Typography_Calendar_10dp)

        val isExecuteDate = executeDateWithStatusList.any {
            val day = LocalDate.parse(it.key)
            day == data.date
        }

        if(data.position == DayPosition.MonthDate) {
            textView.setTextColor(context.getColorCompat(designR.color.gray_600))

            if (data.date == today) {
                textView.setTextAppearance(designR.style.Typography_CalendarDay)
                roundBgView.applyBackground(todayBackground)
            }

            if(isExecuteDate) {
                val key: String = data.date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
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
        background = if(isEnd) context.getDrawableCompat(R.drawable.bg_rect_white) else
            context.getDrawableCompat(R.drawable.bg_rect_green50)
    }

    override fun create(view: View): DayContainer = DayContainer(view)


    companion object {
        fun newInstance(executeDateList: Map<String, Status> = emptyMap()): DayBind =
            DayBind(executeDateList)
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

internal val currentMonth = YearMonth.now()
internal val firstMonth = currentMonth.minusMonths(100)
internal val lastMonth = currentMonth.plusMonths(0)
internal val firstDayOfWeek = WeekFields.of(Locale.KOREAN).firstDayOfWeek

enum class Status {
    START,
    BETWEEN,
    END,
    SINGLE
}
