package com.depromeet.threedays.data.mapper

import com.depromeet.threedays.data.entity.HabitEntity
import com.depromeet.threedays.data.entity.request.PostHabitRequest
import com.depromeet.threedays.domain.entity.Color
import com.depromeet.threedays.domain.entity.habit.CreateHabit
import com.depromeet.threedays.domain.entity.habit.Habit

fun HabitEntity.toHabit(): Habit {
    return Habit(
        habitId = habitId,
        memberId = memberId,
        title = title,
        imojiPath = imojiPath,
        dayOfWeeks = dayOfWeeks,
        reward = reward,
        color = getColorFromString(color),
        mate = mate,
        todayHabitAchievementId = todayHabitAchievementId,
        sequence = sequence,
        createAt = createAt,
    )
}

private fun getColorFromString(color: String): Color {
    return when(color) {
        "green" -> Color.GREEN
        "blue" -> Color.BLUE
        "pink" -> Color.PINK
        else -> Color.GREEN
    }
}

private fun getStringFromColor(color: Color): String {
    return when(color) {
        Color.GREEN -> "green"
        Color.BLUE -> "blue"
        Color.PINK -> "pink"
    }
}

fun CreateHabit.toPostHabitRequest(): PostHabitRequest {
    val notificationNullChecked: PostHabitRequest.Notification? = if(this.notification == null) {null} else {
        PostHabitRequest.Notification(
            notificationTime = this.notification!!.notificationTime,
            contents = this.notification!!.contents
        )
    }
    return PostHabitRequest(
        color = getStringFromColor(color),
        dayOfWeeks = dayOfWeeks,
        imojiPath = emoji.value,
        notification = notificationNullChecked,
        title = title
    )
}
