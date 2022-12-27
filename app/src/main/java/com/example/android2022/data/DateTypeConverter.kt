package com.example.android2022.data

import androidx.room.TypeConverter
import java.util.*

class DateTypeConverter {

        @TypeConverter
        fun fromTimestamp(value: Long?): Date? {
            return value?.let { Date(it) }
        }

        @TypeConverter
        fun dateToTimestamp(date: Date?): Long? {
            return date?.time?.toLong()
        }
    }

