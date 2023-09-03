package com.depromeet.threedays.domain.exception

class ThreeDaysException(override var message: String?, throwable: Throwable) : Exception(message, throwable) {
    var defaultMessage = UNKNOWN_EXCEPTION
    companion object {
        const val UNKNOWN_EXCEPTION = "알 수 없는 오류가 발생했어요."
        const val INTERNET_CONNECTION_WAS_LOST = "인터넷 연결이 끊겼습니다."
        const val NO_BODY_RESPONSE = "응답이 비었습니다."
        const val NO_ERROR_BODY_RESPONSE = "에러 응답이 비었습니다."
        const val NO_ERROR_MESSAGE_RESPONSE = "에러 메세지가 비었습니다."
        const val LOGIN_FAIL = "로그인에 실패하였습니다. 잠시 후 다시 시도해주세요."
        const val LOGOUT_FAIL = "로그아웃에 실패했습니다. 잠시 후 다시 시도해주세요."
        const val MEMBERSHIP_WITHDRAWAL_FAIL = "회원탈퇴를 실패했습니다. 잠시 후 다시 시도해주세요."
    }
}