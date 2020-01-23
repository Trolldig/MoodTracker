package de.matthias.klipfel.moodtracker.database;

import android.app.Application;

import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ExecutionException;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class MoodEntryViewModel extends AndroidViewModel {

    private MoodEntryRepository mRepository;

    private LiveData<List<MoodEntry>> mAllMoodEntries;

    public MoodEntryViewModel (Application application) {
        super(application);
        mRepository = new MoodEntryRepository(application);
        mAllMoodEntries = mRepository.getAllMoodEntries();
    }

    LiveData<List<MoodEntry>> getmAllMoodEntries() { return mAllMoodEntries; }

    public void insert (MoodEntry moodEntry) { mRepository.insertMoodEntry(moodEntry);}

    public List<MoodEntry> getEntriesMonth (Calendar from, Calendar to){
        try {
            return mRepository.getAllEntriesMonth(from, to);
        } catch (ExecutionException e) {
            System.out.println("I caught: " + e);
            return null;
        } catch (InterruptedException e) {
            System.out.println("I caught: " + e);
            return null;
        }
    }

    public void updateMoodEntry (int pA, int nA, Calendar date) {
        try {
            mRepository.updateMoodEntry(pA, nA, date);
        } catch (ExecutionException e) {
            System.out.println("I caught: " + e);
        } catch (InterruptedException e) {
            System.out.println("I caught: " + e);
        }
    }

    public MoodEntry checkForEntry (Calendar date) {
        try {
            return mRepository.checkForEntry(date);
        } catch (ExecutionException e) {
            System.out.println("I caught: " + e);
            return null;
        } catch (InterruptedException e) {
            System.out.println("I caught: " + e);
            return null;
        }
    }

    public LiveData<List<MoodEntry>> getAll(){
        return mRepository.getAllMoodEntries();
    }
}
