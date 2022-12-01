package com.depromeet.threedays.core.util

class Emoji{
    val objects = mapOf(
        "trash" to 0x1F5D1,
        "clock" to 0x23F0,
    )

    fun getEmojiString(code: Int?): String {
        if (code != null) {
            return String(Character.toChars(code))
        } else {
            throw Exception()
        }
    }

    fun getEmojiString(word: String): String {
        return getEmojiString(objects[word])
    }
}
