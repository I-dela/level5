package com.example.notepad.Database.Helpers

import androidx.room.TypeConverter
import java.util.*

/**
 * Room cannot store object references in the database. For this reason TypeConverters exist.
 * They convert an object type into a type that can be stored in the database (String, Int, Long etc.).
 * We have defined one method for creating a timestamp from a Date object and one for creating a Date object from
 * the timestamp. The timestamp is stored in the database and when the object is retrieved from the database
 * the typeconverter will convert it back to a Date object.
 */

class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?) : Date?{
        return value?.let{Date(it)}
    }

    @TypeConverter
    fun dateToTimestamp(date:Date?) : Long?{
        return date?.time?.toLong()
    }
}