package com.depromeet.threedays.domain.entity

import java.time.ZonedDateTime

data class Goal(
    val goalId: Long,
    val title: String,
    val startDate: ZonedDateTime? = null,
    val endDate: ZonedDateTime? = null,
    val startTime: ZonedDateTime? = null,
    val notificationTime: ZonedDateTime? = null,
    val notificationContent: String? = null,
    val status: ZonedDateTime? = null,
    val createDate: ZonedDateTime? = null,
    val sequence: Int,
    var lastAchievementDate: ZonedDateTime? = null,

    // 0,1,2 중에 오늘 몇번째 짝! 누를수 있는지
    var clapIndex: Int = 0,
    // 오늘꺼 눌렀는지 아닌지
    var clapChecked: Boolean = false
) {
    // index: 0,1,2 위젯 중에 몇번째 인덱스 위젯인지 (주먹, 보)
    fun isChecked(index: Int): Boolean {
        if (clapIndex > index) {
            return true
        }
        if (clapIndex == index) {
            return clapChecked
        }
        // clapIndex < index 는 아직 도래하지않은 짝! 이므로 무조건 false
        return false
    }

    // index: 0,1,2 위젯 중에 몇번째 인덱스 위젯인지 (외곽선)
    fun isFocused(index: Int): Boolean {
        return clapIndex == index
    }
}
