package com.depromeet.threedays.domain.util

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class GetStringFromDateTime @Inject constructor() {
    private val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    operator fun invoke(date: LocalDate): String {
        return date.format(formatter)
    }
}
