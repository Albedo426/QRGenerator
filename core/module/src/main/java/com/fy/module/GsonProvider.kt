package com.fy.module

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import java.lang.reflect.Type
import java.util.Date

object GsonProvider {
	private val gson: Gson = GsonBuilder()
		.registerTypeAdapter(Date::class.java, DateAdapter())
		.create()

	fun get(): Gson {
		return gson
	}

	private val prettyGson: Gson = GsonBuilder()
		.setPrettyPrinting()
		.create()

	fun getPrettyGson(): Gson {
		return prettyGson
	}
}

private class DateAdapter: JsonSerializer<Date>, JsonDeserializer<Date> {
	override fun serialize(src: Date?, typeOfSrc: Type?, context: JsonSerializationContext?): JsonElement {
		return if (src == null) {
			JsonPrimitive(0)
		} else {
			JsonPrimitive(src.time)
		}
	}

	override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): Date? {
		if (json != null && json.isJsonPrimitive && (json as JsonPrimitive).isNumber) {
			return Date(json.asLong)
		}

		return null
	}
}
