package ec.com.pmyb.jetnote.util

import androidx.room.TypeConverter
import java.util.*

class DateConverter {
    @TypeConverter
    fun timeStampFromDate(date: Date): Long {
        return date.time
    }

    @TypeConverter
    fun dateFromStampFrom(timestamp: Long): Date {
        return Date(timestamp)
    }
}