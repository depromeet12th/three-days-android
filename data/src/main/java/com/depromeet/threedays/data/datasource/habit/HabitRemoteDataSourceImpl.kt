package com.depromeet.threedays.data.datasource.habit

import com.depromeet.threedays.data.api.HabitService
import com.depromeet.threedays.data.entity.HabitEntity
import com.depromeet.threedays.data.entity.request.PostHabitRequest
import com.depromeet.threedays.domain.entity.mate.Mate
import java.security.SecureRandom
import java.time.DayOfWeek
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class HabitRemoteDataSourceImpl @Inject constructor(
    private val habitService: HabitService
) : HabitRemoteDataSource {
    override suspend fun postHabit(request: PostHabitRequest) {
        return habitService.postHabit(request)
    }

    override suspend fun getHabits(): List<HabitEntity> {
        return habitService.getHabits().data ?: emptyList()
    }

    override suspend fun getArchivedHabits(): List<HabitEntity> {
        return listOf(
            HabitEntity(
                1,
                memberId = 1,
                title = "물 마시기",
                imojiPath = "",
                dayOfWeeks = listOf(
                    "MONDAY","TUESDAY","WEDNESDAY"
                ),
                reward = SecureRandom().nextInt(10),
                color = "pink",
                mate = Mate(
                    mateId = 1,
                    level = 2,
                    characterType = "",
                    createAt = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME),
                ),
                todayHabitAchievementId = null,
                sequence = 1,
                createAt = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME),
            ),
            HabitEntity(
                2,
                memberId = 1,
                title = "영어 공부 1시간 하기",
                imojiPath = "",
                dayOfWeeks = DayOfWeek.values().map { it.name }.toList(),
                reward = SecureRandom().nextInt(10),
                color = "blue",
                mate = null,
                todayHabitAchievementId = null,
                sequence = 1,
                createAt = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME),
            ),
            HabitEntity(
                3,
                memberId = 1,
                title = "숨 쉬기",
                imojiPath = "",
                dayOfWeeks = DayOfWeek.values().map { it.name }.toList(),
                reward = SecureRandom().nextInt(10),
                color = "green",
                mate = null,
                todayHabitAchievementId = null,
                sequence = 1,
                createAt = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME),
            ),
            HabitEntity(
                4,
                memberId = 1,
                title = "일기쓰기",
                imojiPath = "",
                dayOfWeeks = DayOfWeek.values().map { it.name }.toList(),
                reward = SecureRandom().nextInt(10),
                color = "pink",
                mate = null,
                todayHabitAchievementId = null,
                sequence = 1,
                createAt = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME),
            ),
            HabitEntity(
                5,
                memberId = 1,
                title = "공부하기",
                imojiPath = "",
                dayOfWeeks = DayOfWeek.values().map { it.name }.toList(),
                reward = SecureRandom().nextInt(10),
                color = "blue",
                mate = null,
                todayHabitAchievementId = null,
                sequence = 1,
                createAt = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME),
            ),
            HabitEntity(
                6,
                memberId = 1,
                title = "청소하기",
                imojiPath = "",
                dayOfWeeks = DayOfWeek.values().map { it.name }.toList(),
                reward = SecureRandom().nextInt(10),
                color = "green",
                mate = null,
                todayHabitAchievementId = null,
                sequence = 1,
                createAt = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME),
            ),
        )
//        return habitService.getArchivedHabits()
    }

//    override fun getHabit(habitId: Long): HabitEntity {
//
//    }

    override suspend fun updateHabit(habitId: Long) {

    }

    override suspend fun deleteHabit(habitId: Long) {
        return habitService.deleteHabit(habitId.toInt())
    }
}
