package de.matthias.klipfel.moodtracker;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface MoodEntryDao {

    @Insert
    void insert(MoodEntry moodEntry);

    @Query("DELETE FROm mood_entry_table")
    void deleteAll();

    @Query("SELECT * from mood_entry_table ORDER BY date ASC")
    LiveData<List<MoodEntry>> getAllMoodEntries();
}
