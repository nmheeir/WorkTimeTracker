package com.example.worktimetracker.data.remote.adapters

import com.example.worktimetracker.data.remote.enums.EmployeeType
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import java.lang.reflect.Type

class EmployeeTypeAdapter : JsonSerializer<EmployeeType>, JsonDeserializer<EmployeeType> {
    override fun serialize(
        src: EmployeeType,
        typeOfSrc: Type,
        context: JsonSerializationContext?,
    ): JsonElement? {
        return JsonPrimitive(src.name)
    }


    override fun deserialize(
        json: JsonElement,
        typeOfT: Type,
        context: JsonDeserializationContext?,
    ): EmployeeType? {
        return EmployeeType.valueOf(json.asString)
    }
}