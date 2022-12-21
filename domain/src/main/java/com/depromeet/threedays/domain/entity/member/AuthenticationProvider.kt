package com.depromeet.threedays.domain.entity.member

/**
 * 인증 제공자
 */
enum class AuthenticationProvider(
    private val description: String,
) {
    GOOGLE("구글 로그인"),
    KAKAO("카카오 로그인"),
    UNKNOWN("알 수 없음"),
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
