package de.matthias.klipfel.moodtracker.database;

import java.util.Calendar;
import java.util.Date;
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

    @Query("SELECT * FROM mood_entry_table WHERE date BETWEEN :from AND :to")
    List<MoodEntry> getAllEntriesMonth(Calendar from, Calendar to);

    /**
     * Updating Affect
     * By date
     */
    @Query("UPDATE mood_entry_table SET pos_aff = :posAff, neg_aff = :negAff WHERE date=:date")
    void update(int posAff, int negAff, Calendar date);

    @Query(" SELECT * from mood_entry_table  WHERE date=:date")
    MoodEntry checkForEntry(Calendar date);


    @Query("DELETE FROM mood_entry_table")
    void deleteAll();

    @Query("SELECT * from mood_entry_table")
    LiveData<List<MoodEntry>> getAllMoodEntries();
}
