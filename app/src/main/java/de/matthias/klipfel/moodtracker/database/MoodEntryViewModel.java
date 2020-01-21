package de.matthias.klipfel.moodtracker.database;

import android.app.Application;

import java.util.List;

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
}
