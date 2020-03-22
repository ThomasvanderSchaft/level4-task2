package com.example.rockpaperscissors

import androidx.room.TypeConverter
import java.util.*

class Converter {
    companion object {
        @TypeConverter
        @JvmStatic
        fun fromTimestamp(value: Long?): Date? = if (null == value) null else Date(value)
        @TypeConverter
        @JvmStatic
        fun dateToTimestamp(date: Date?): Long? = date?.time
    }
}