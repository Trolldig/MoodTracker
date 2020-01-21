package de.matthias.klipfel.moodtracker.database;

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
    @ColumnInfo(name = "day")
    private int mDay;
    @ColumnInfo(name = "month")
    private int mMonth;
    @ColumnInfo(name = "year")
    private int mYear;

    public MoodEntry(@NonNull int pA, @NonNull int nA, @NonNull int day, @NonNull int month,
                     @NonNull int year){
        this.mPA = pA;
        this.mNA = nA;
        this.mDay = day;
        this.mMonth = month;
        this.mYear = year;
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

    public int getDay() {
        return this.mDay;
    }

    public int getMonth() {
        return this.mMonth;
    }

    public int getYear() {
        return this.mYear;
    }

    public void setmPA(int mPA) {
        this.mPA = mPA;
    }

    public void setmNA(int mNA) {
        this.mNA = mNA;
    }

    public void setmDay(int mDay) {
        this.mDay = mDay;
    }

    public void setmMonth(int mMonth) {
        this.mMonth = mMonth;
    }

    public void setmYear(int mYear) {
        this.mYear = mYear;
    }
}
