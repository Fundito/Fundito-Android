package com.fundito.fundito.data.enumerator

import com.google.gson.*
import java.lang.reflect.Type

/**
 * Created by mj on 30, December, 2019
 */
enum class FundStatus (val value : Int) {

    ONGOING(0),
    SUCCESS(1),
    FAIL(2),
    DISABLE(3)

    ;

    companion object {
        fun parseValue(value : Int) = values().firstOrNull { value == it.value } ?: DISABLE
    }
}

class FundStatusSerializer : JsonSerializer<FundStatus>,JsonDeserializer<FundStatus> {
    override fun serialize(src: FundStatus?, typeOfSrc: Type?, context: JsonSerializationContext?): JsonElement {
        return JsonPrimitive(src?.value ?: 0)
    }

    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): FundStatus {
        return FundStatus.parseValue(json?.asInt ?: 0)
    }
}