package com.depromeet.threedays.data.datasource.habit

import com.depromeet.threedays.data.api.HabitService
import com.depromeet.threedays.data.entity.HabitEntity
import java.security.SecureRandom
import java.time.DayOfWeek
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class HabitRemoteDataSourceImpl @Inject constructor(
    private val habitService: HabitService
) : HabitRemoteDataSource {
    override suspend fun postHabit(habitEntity: HabitEntity) {

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
                mate = null,
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
        )
//        return habitService.getArchivedHabits()
    }

//    override fun getHabit(habitId: Long): HabitEntity {
//
//    }

    override suspend fun updateHabit(habitId: Long) {

    }

    override suspend fun deleteHabit(habitId: Long) {

    }
}
