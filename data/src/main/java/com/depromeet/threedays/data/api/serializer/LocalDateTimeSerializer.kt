package com.depromeet.threedays.data.api.serializer

import com.google.gson.*
import java.lang.reflect.Type
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class LocalDateTimeSerializer : JsonSerializer<LocalDateTime> {
    override fun serialize(
        src: LocalDateTime?,
        typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonElement {
        return src?.let { JsonPrimitive(it.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)) }
            ?: JsonNull.INSTANCE
    }
}
