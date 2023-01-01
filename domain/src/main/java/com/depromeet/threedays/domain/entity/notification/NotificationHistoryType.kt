package com.depromeet.threedays.domain.entity.notification

enum class NotificationHistoryType(
    private val description: String,
){
    GLOBAL("모든 회원이 수신하는 알림"),
    HABIT("습관 관련 알림"),
    NOTICE("공지사항 관련 알림"),
    UNKNOWN("알 수 없는 값"),
    ;

    companion object {
        /**
         * 대소문자 구분하지 않고 이름으로 비교해서 같은 값을 찾는다.
         * 일치하는 값이 없으면 UNKNOWN
         */
        fun from(value: String) = values()
            .filter { it != UNKNOWN }
            .firstOrNull {
                it.name.equals(
                    other = value,
                    ignoreCase = true,
                )
            } ?: UNKNOWN
    }
}
