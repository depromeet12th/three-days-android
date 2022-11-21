package com.depromeet.threedays.data.datasource.habit

import com.depromeet.threedays.data.api.HabitService
import com.depromeet.threedays.data.entity.HabitEntity
import javax.inject.Inject

class HabitRemoteDataSourceImpl @Inject constructor(
    private val habitService: HabitService
) : HabitRemoteDataSource {
    override suspend fun postHabit(habitEntity: HabitEntity) {

    }

    override suspend fun getHabits(): List<HabitEntity> {
        return habitService.getHabits()
    }

//    override fun getHabit(habitId: Long): HabitEntity {
//
//    }

    override suspend fun updateHabit(habitId: Long) {

    }

    override suspend fun deleteHabit(habitId: Long) {

    }
}
