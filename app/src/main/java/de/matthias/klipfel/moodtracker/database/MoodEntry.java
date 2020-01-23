package de.matthias.klipfel.moodtracker.database;

import java.util.Calendar;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "mood_entry_table")
public class MoodEntry {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "pos_aff")
    private int mPA;

    @ColumnInfo(name = "neg_aff")
    private int mNA;

    @ColumnInfo(name="date")
    private Calendar date;

    public MoodEntry(@NonNull int pA, @NonNull int nA, Calendar date){
        this.mPA = pA;
        this.mNA = nA;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public int getPA() {
        return this.mPA;
    }

    public int getNA() {
        return this.mNA;
    }


    /**
     * Sets the entry id. Is used by the Database.
     *
     * @param id the id
     * @deprecated because the id is auto-generated
     */
    @Deprecated
    @SuppressWarnings("DeprecatedIsStillUsed")
    public void setId(int id) {
        this.id = id;
    }

    public void setmPA(int mPA) {
        this.mPA = mPA;
    }

    public void setmNA(int mNA) {
        this.mNA = mNA;
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }
}
