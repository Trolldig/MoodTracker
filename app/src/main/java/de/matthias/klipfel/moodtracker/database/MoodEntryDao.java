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

    /**
     * Updating Affect
     * By date
     */
    @Query("UPDATE mood_entry_table SET pos_aff = :posAff, neg_aff = :negAff WHERE id = :id")
    void update(int posAff, int negAff, int id);


    @Query("DELETE FROm mood_entry_table")
    void deleteAll();

    @Query("SELECT * from mood_entry_table ORDER BY day ASC")
    LiveData<List<MoodEntry>> getAllMoodEntries();
}
