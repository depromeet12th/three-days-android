package com.depromeet.threedays.domain.entity.emoji

/**
 * 이모지 1개를 나타내는 클래스 (값객체)
 */
data class Emoji(
    /**
     * unicode 값
     */
    val value: String,
) {
    companion object {
        fun from(unicode: String) = Emoji(unicode)

        fun from(numberCode: Int) = Emoji(
            EmojiUtil.getEmojiString(numberCode)
        )

        fun from(word: EmojiUtil.Word) = Emoji(
            EmojiUtil.getEmojiString(word)
        )
    }
}
