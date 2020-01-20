package de.matthias.klipfel.moodtracker.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "mood_entry_table")
public class MoodEntry {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "pos_aff")
    private int mPA;
    @ColumnInfo(name = "neg_aff")
    private int mNA;
    @ColumnInfo(name = "date")
    private String mDate;

    public MoodEntry(@NonNull int pA, @NonNull int nA, @NonNull String date){
        this.mPA = pA;
        this.mNA = nA;
        this.mDate = date;
    }

    public int getPA() {
        return this.mPA;
    }

    public int getNA() {
        return this.mNA;
    }

    public String getDate() {
        return this.mDate;
    }
}
