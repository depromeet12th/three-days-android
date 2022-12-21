package com.depromeet.threedays.domain.entity.notification

enum class NotificationHistoryStatus(
    private val description: String
) {
    SUCCESS("성공"),
    FAILURE("실패"),
    CHECKED("읽음"),
    UNKNOWN("정의되지 않은 값"),
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

    /**
     * 읽었는지 여부
     */
    fun hasRead() = when (this) {
        SUCCESS, FAILURE, UNKNOWN -> false
        CHECKED -> true
    }
}
