package com.augustbyrne.tas.ui.notes

import androidx.room.TypeConverter
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/**
 * Type Converters
 */
class Converters {
    @TypeConverter
    fun toLocalDateTime(s: String?): LocalDateTime? = s?.let { LocalDateTime.parse(s) }

    @TypeConverter
    fun fromLocalDateTime(l: LocalDateTime?): String? =
        l?.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
}

/**
 * Volatile Enum Classes
 */

enum class TimerState {
    Stopped, Paused, Running
}

/**
 * Stored Enum Classes
 */

enum class DarkMode(val mode: Int) {
    System(0),
    On(1),
    Off(2);
    companion object {
        fun getMode(modeValue: Int): DarkMode {
            return values().first { value ->
                value.mode == modeValue
            }
        }
    }
}

enum class SortType(val type: Int) {
    Creation(0),
    LastEdited(1),
    Order(2),
    Default(3);
    companion object {
        fun getType(typeValue: Int): SortType {
            return values().first { value ->
                value.type == typeValue
            }
        }
    }
}