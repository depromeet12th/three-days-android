package com.depromeet.threedays.buildproperty

/**
 * LocalProperties 에 저장된 값을 buildConfigField 를 통해 접근할 때 사용하는 key 모음.
 */
enum class BuildProperty(
    private val description: String,
) {
    BASE_URL("api 서버 주소"),
    KAKAO_APP_KEY("Kakao native app key"),
    MIXPANEL_PROJECT_TOKEN("Mixpanel project token")
    ;

    /**
     * build.gradle 의 buildConfigField 에 정의한 변수 이름
     */
    val key: String = this.name

    override fun toString(): String {
        return "BuildProperty(description='$description', key='$key')"
    }
}
