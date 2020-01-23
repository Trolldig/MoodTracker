package de.matthias.klipfel.moodtracker.database;

import java.util.Calendar;
import java.util.Date;

import androidx.room.TypeConverter;

public class Converters {
    @TypeConverter
    public static Calendar fromTimestamp(Long value) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(value);
        return calendar;
    }

    @TypeConverter
    public static Long calendarToTimestamp(Calendar calendar) {
        return calendar == null ? null : calendar.getTimeInMillis();
    }
}
