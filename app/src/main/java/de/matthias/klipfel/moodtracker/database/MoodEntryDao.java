package de.matthias.klipfel.moodtracker.database;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import de.matthias.klipfel.moodtracker.database.MoodEntry;

@Dao
public interface MoodEntryDao {

    @Insert
    void insert(MoodEntry moodEntry);

    @Query("SELECT * FROM mood_entry_table WHERE month = 1 ORDER BY day ASC")
    List<MoodEntry> getAllEntriesMonth();

    @Query("DELETE FROm mood_entry_table")
    void deleteAll();

    @Query("SELECT * from mood_entry_table ORDER BY day ASC")
    LiveData<List<MoodEntry>> getAllMoodEntries();
}
