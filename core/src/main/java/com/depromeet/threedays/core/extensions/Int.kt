package com.depromeet.threedays.core.extensions

val Int?.toEmoji
    get() = String(Character.toChars(this ?: 0))
