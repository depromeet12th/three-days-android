package com.depromeet.threedays.core.util

class Emoji{
    val entire = mapOf(
        Word.FIRE to 0x1F525,
        Word.TRASH to 0x1F5D1,
        Word.CLOCK to 0x23F0,
        Word.SMILE to 0x1F600,
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
        CLOCK,
        SMILE
    }

    enum class Category {
        FACE,
        FOOD,
        ANIMAL,
        ACTIVITY,
        OBJECT
    }

    fun getEmojiList(category: Category): List<Int> {
        return when(category) {
            Category.FACE -> faceEmojiList
            Category.FOOD -> foodEmojiList
            Category.ANIMAL -> animalEmojiList
            Category.ACTIVITY -> activityEmojiList
            Category.OBJECT -> objectEmojiList
        }
    }

    private val faceEmojiList = listOf(
        0x1F600,
        0x1F606,
        0x1F602,
        0x263A,
        0x1F607,
        0x1F642,
        0x1F609,
        0x1F60C,
        0x1F60D,
        0x1F61C,
        0x1F60E,
        0x1F973,
        0x1F61E,
        0x2639,
        0x1F97A,
        0x1F621,
        0x1F633,
        0x1F914,
        0x1F92D,
        0x1F644,
        0x1F62A,
        0x1F916,
        0x1F924,
        0x1F610,
        0x1F974,
        0x1F608,
        0x1F4A9,
        0x1F47B,
        0x2620,
        0x1F47D
    )

    private val animalEmojiList = listOf(
        0x1F430,
        0x1F43A,
        0x1F437,
        0x1F427,
        0x1F43C,
        0x1F419,
        0x1F42D,
        0x1F435,
        0x1F981,
        0x1F428,
        0x1F434,
        0x1F439,
        0x1F424,
        0x1F438,
        0x1F98A,
        0x1F42C,
        0x1F436,
        0x1F431,
        0x1F42E,
        0x1F414,
        0x1F417,
        0x1F43B,
        0x1F41C,
        0x1F984,
        0x1F42F,
        0x1F332,
        0x1F340,
        0x1F338,
        0x1F337,
        0x2618,
        0x1F331,
        0x1F339,
        0x1F333,
        0x26A1,
        0x1F319,
        0x2600,
        0x2601,
        0x2B50,
        0x1F525,
        0x1F4A5,
        0x1F30F,
    )

    private val foodEmojiList = listOf(
        0x1F356,
        0x1F34B,
        0x1F355,
        0x2615,
        0x1F35F,
        0x1F35B,
        0x1F966,
        0x1F34E,
        0x1F9CB,
        0x1F364,
        0x1F347,
        0x1F95E,
        0x1F360,
        0x1F950,
        0x1F34C,
        0x1F32D,
        0x1F96A,
        0x1F351,
        0x1F375,
        0x1F953,
        0x1F34F,
        0x1F354,
        0x1F377,
        0x1F349,
        0x1F9C7,
        0x1F345,
        0x1F34A,
        0x1F32E,
        0x1F363,
        0x1F959,
    )

    private val activityEmojiList = listOf(
        0x1F3BE,
        0x26BD,
        0x1F94E,
        0x1F3C9,
        0x1F3B3,
        0x1F3C0,
        0x26BE,
        0x1F3C8,
        0x1F3D0,
        0x26F3,
        0x1F94A,
        0x1F3F8,
        0x1F3BD,
        0x1FA71,
        0x1F94B,
        0x26F1,
        0x1F3C6,
        0x1F3AE,
        0x1F3AC,
        0x1F3AF,
        0x1F3D3,
        0x1F3B2,
        0x1F3B1,
        0x265F,
        0x1F3A3,
        0x1F3B9,
        0x1F3A8,
        0x1F941,
        0x1F3BB,
        0x1F3B8,
        0x1F9F6,
        0x1F3A4,
        0x1F9E9,
        0x1F9D7,
        0x1F6B4,
        0x1F9D8,
        0x1F697,
        0x1F680,
        0x2708,
        0x1F6B2,
        0x26F5,
        0x1F6F8,
        0x1F6A8,
        0x1F3D6,
        0x1F3DD,
        0x1FA90,
        0x1F3E0,
        0x2764,
        0x1F90D,
        0x1F49A,
        0x1F499,
        0x1F90E,
        0x1F49B,
        0x1F49C,
        0x1F9E1,
        0x1F5A4,
        0x2763,
        0x1F496,
        0x1F6AB,
        0x2668,
        0x1F4AF,
        0x1F4A,
        0x2B55,
        0x274C,
        0x26A0,
    )

    private val objectEmojiList = listOf(
        0x1F6CD,
        0x1F389,
        0x1F52B,
        0x1F4B8,
        0x1F50D,
        0x1F48A,
        0x1F916,
        0x1F4D9,
        0x1F4DD,
        0x1F380,
        0x1F45F,
        0x1F6BF,
        0x1FAA5,
        0x270F,
        0x23F0,
        0x1F4CC,
        0x1F399,
        0x1F459,
        0x1F4A7,
        0x1F4A3,
        0x1F48E,
        0x1F9F8,
        0x1F381,
        0x1F4BB,
        0x1F9E8,
        0x1F4D7,
        0x1F6AC,
        0x1F4D5,
        0x1F4D8,
        0x1F4A1,
    )
}
