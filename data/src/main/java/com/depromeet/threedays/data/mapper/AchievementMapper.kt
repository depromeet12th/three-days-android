package com.depromeet.threedays.data.mapper

import com.depromeet.threedays.data.entity.achievement.AchievementEntity
import com.depromeet.threedays.domain.entity.achievement.Achievement

fun AchievementEntity.toAchievement(): Achievement {
    return Achievement(
        id = id,
        nextAchievementDate = nextAchievementDate,
        habitId = habitId,
        achievementDate = achievementDate,
        sequence = sequence,
    )
}
