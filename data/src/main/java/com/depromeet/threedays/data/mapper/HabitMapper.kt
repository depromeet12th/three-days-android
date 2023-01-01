package com.depromeet.threedays.data.mapper

import com.depromeet.threedays.data.entity.habit.HabitEntity
import com.depromeet.threedays.data.entity.habit.PostHabitRequest
import com.depromeet.threedays.data.entity.habit.SingleHabitEntity
import com.depromeet.threedays.domain.entity.Color
import com.depromeet.threedays.domain.entity.emoji.Emoji
import com.depromeet.threedays.domain.entity.habit.CreateHabit
import com.depromeet.threedays.domain.entity.habit.Habit
import com.depromeet.threedays.domain.entity.habit.SingleHabit

fun HabitEntity.toHabit(): Habit {
    return Habit(
        id = id,
        memberId = memberId,
        title = title,
        imojiPath = imojiPath,
        dayOfWeeks = dayOfWeeks,
        reward = reward,
        color = getColorFromString(color),
        mate = mate?.toMate(),
        totalAchievementCount = totalAchievementCount,
        todayHabitAchievementId = todayHabitAchievementId,
        sequence = sequence,
        createAt = createAt,
    )
}

fun SingleHabitEntity.toSingleHabit(): SingleHabit {
    return SingleHabit(
        id = id,
        memberId = memberId,
        title = title,
        emoji = Emoji(imojiPath),
        dayOfWeeks = dayOfWeeks,
        reward = reward,
        color = getColorFromString(color),
        mate = mate,
        todayHabitAchievementId = todayHabitAchievementId,
        sequence = sequence,
        createAt = createAt,
        notification = notification,
        status = status,
        totalAchievementCount = totalAchievementCount
    )
}

private fun getColorFromString(color: String): Color {
    return when (color) {
        "green" -> Color.GREEN
        "blue" -> Color.BLUE
        "pink" -> Color.PINK
        else -> Color.GREEN
    }
}

private fun getStringFromColor(color: Color): String {
    return when (color) {
        Color.GREEN -> "green"
        Color.BLUE -> "blue"
        Color.PINK -> "pink"
    }
}

fun CreateHabit.toPostHabitRequest(): PostHabitRequest {
    val notificationNullChecked: PostHabitRequest.Notification? =
        if(this.notification == null) {
            null
        } else {
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
