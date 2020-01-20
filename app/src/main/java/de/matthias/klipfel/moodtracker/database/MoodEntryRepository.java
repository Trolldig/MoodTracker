package de.matthias.klipfel.moodtracker.database;

import android.app.Application;
import android.os.AsyncTask;

import java.util.List;

import androidx.lifecycle.LiveData;

public class MoodEntryRepository {

    private MoodEntryDao mMoodEntryDao;
    private LiveData<List<MoodEntry>> mAllMoodEntries;

    public MoodEntryRepository(Application application) {
        MoodEntryRoomDatabase db = MoodEntryRoomDatabase.getDatabase(application);
        mMoodEntryDao = db.moodEntryDao();
        mAllMoodEntries = mMoodEntryDao.getAllMoodEntries();
    }

    LiveData<List<MoodEntry>> getAllMoodEntries() {
        return mAllMoodEntries;
    }

    public void insertMoodEntry (MoodEntry moodEntry) {
        new insertAsyncTask(mMoodEntryDao).execute(moodEntry);
    }

    /**
     * {@link AsyncTask} for inserting operations.
     */
    private static class insertAsyncTask extends AsyncTask<MoodEntry, Void, Void> {

        private final MoodEntryDao moodEntryDao;

        insertAsyncTask(MoodEntryDao dao) {
            moodEntryDao = dao;
        }

        @Override
        protected Void doInBackground(MoodEntry... moodEntries) {
            moodEntryDao.insert(moodEntries[0]);
            return null;
        }
    }
}
