package com.depromeet.threedays.data.api.serializer

import com.google.gson.*
import java.lang.reflect.Type
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class LocalTimeSerializer : JsonSerializer<LocalTime> {
    override fun serialize(
        src: LocalTime?,
        typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonElement {
        return src?.let { JsonPrimitive(it.format(DateTimeFormatter.ISO_LOCAL_TIME)) }
            ?: JsonNull.INSTANCE
    }
}
