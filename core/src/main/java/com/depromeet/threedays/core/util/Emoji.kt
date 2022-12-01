package com.depromeet.threedays.core.util

class Emoji{
    val entire = mapOf(
        Word.FIRE to 0x1F525,
        Word.TRASH to 0x1F5D1,
        Word.CLOCK to 0x23F0,
    )

    val objects = mapOf(
        Word.TRASH to 0x1F5D1,
        Word.CLOCK to 0x23F0,
    )

    fun getEmojiString(code: Int?): String {
        if (code != null) {
            return String(Character.toChars(code))
        } else {
            throw Exception()
        }
    }

    fun getEmojiString(word: Word): String {
        return getEmojiString(entire[word])
    }

    enum class Word {
        FIRE,
        TRASH,
        CLOCK
    }
}
