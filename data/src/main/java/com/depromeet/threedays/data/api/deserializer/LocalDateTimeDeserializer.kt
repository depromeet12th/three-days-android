package com.depromeet.threedays.data.api.deserializer

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class LocalDateTimeDeserializer: JsonDeserializer<LocalDateTime?> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): LocalDateTime {
        return LocalDateTime.parse(
            json?.asString,
            DateTimeFormatter.ofPattern( when(json?.asString?.length) {
                21 -> "yyyy-MM-dd HH:mm:ss.S"
                22 -> "yyyy-MM-dd HH:mm:ss.SS"
                23-> "yyyy-MM-dd HH:mm:ss.SSS"
                24-> "yyyy-MM-dd HH:mm:ss.SSSS"
                25-> "yyyy-MM-dd HH:mm:ss.SSSSS"
                26-> "yyyy-MM-dd HH:mm:ss.SSSSSS"
                27-> "yyyy-MM-dd HH:mm:ss.SSSSSSS"
                else -> "yyyy-MM-dd HH:mm:ss"
            })
        )
    }
}
