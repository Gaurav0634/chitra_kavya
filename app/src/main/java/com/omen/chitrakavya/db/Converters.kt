package com.omen.chitrakavya.db

import androidx.room.TypeConverter

object Converters {
    @TypeConverter
    @JvmStatic
    fun fromStringList(value: List<String>?): String? {
        return value?.joinToString(separator = ",")
    }

    @TypeConverter
    @JvmStatic
    fun toStringList(value: String?): List<String>? {
        return value?.split(",")?.map { it.trim() }
    }
}
