package de.matthias.klipfel.moodtracker.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {MoodEntry.class}, version = 2, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class MoodEntryRoomDatabase extends RoomDatabase {

    public abstract MoodEntryDao moodEntryDao();
    private static MoodEntryRoomDatabase INSTANCE;

    static MoodEntryRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (MoodEntryRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            MoodEntryRoomDatabase.class, "mood_entry_database")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
