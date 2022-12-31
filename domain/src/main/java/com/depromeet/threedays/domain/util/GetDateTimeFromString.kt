package com.depromeet.threedays.domain.util

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class GetDateTimeFromString @Inject constructor() {
    operator fun invoke(date: String): LocalDate? {
        return LocalDate.parse(getValidDateString(date), DateTimeFormatter.ISO_DATE)
    }

    private fun getValidDateString(date: String): String? {
        return try {
            if (date.length > 10) {
                return date.substring(0, 10)
            } else {
                return date
            }
        } catch (e: Exception) {
            return null
        }
    }
}
