package com.depromeet.threedays.core.extensions

val String.Companion.Empty
    get() = ""


fun String.removeActivity(): String {
    return this.substring(0, this.length - "Activity".length)
}

fun String.removeFragment(): String {
    return this.substring(0, this.length - "Fragment".length)
}
