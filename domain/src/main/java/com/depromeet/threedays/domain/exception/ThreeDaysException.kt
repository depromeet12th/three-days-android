package com.depromeet.threedays.domain.exception

import com.depromeet.threedays.domain.key.*

class ThreeDaysException(val code: String?, throwable: Throwable) : Exception(throwable) {
    override val message: String? = when(code) {
        HABIT_CONSTRAINTS_INSUFFICIENT_DAY_OF_WEEKS -> "실천 요일을 3개 이상 선택해 주세요."
        HABIT_ACHIEVEMENT_CONSTRAINTS_DATE_IS_IN_THE_PAST -> "이전의 기록을 변경할 수 없어요."
        HABIT_ACHIEVEMENT_CONSTRAINTS_INVALID_ACHIEVEMENT_DATE -> "습관 취소는 오늘만 할 수 있어요"
        HABIT_CONSTRAINTS_INSUFFICIENT_NOTIFICATION -> "Push 알림을 마저 설정해 주세요."
        MEMBER_CONSTRAINTS_INVALID_MEMBER -> "가입된 기록이 없어요."
        MEMBER_NOT_FOUND -> "가입된 기록이 없어요."
        SOCIAL_LOGIN_ERROR -> "로그인에 실패했어요."
        MATE_CONSTRAINTS_ALREADY_EXIST_MATE -> "이미 함께하고 있는 짝꿍이 있어요."
        METHOD_ARGUMENT_NOT_VALID_EXCEPTION_BIND_EXCEPTION, BAD_REQUEST, UNKNOWN_ERROR -> "알 수 없는 오류가 발생했어요."
        NO_INTERNET_CONNECTION -> "인터넷 연결이 끊겼습니다."
        else -> null
    }
}
