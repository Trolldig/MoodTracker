package de.matthias.klipfel.moodtracker;

import android.app.Application;
import android.os.AsyncTask;

import java.util.List;

import androidx.lifecycle.LiveData;

public class MoodEntryRepository {

    private MoodEntryDao mMoodEntryDao;
    private LiveData<List<MoodEntry>> mAllMoodEntries;

    MoodEntryRepository(Application application) {
        MoodEntryRoomDatabase db = MoodEntryRoomDatabase.getDatabase(application);
        mMoodEntryDao = db.moodEntryDao();
        mAllMoodEntries = mMoodEntryDao.getAllMoodEntries();
    }

    LiveData<List<MoodEntry>> getAllMoodEntries() {
        return mAllMoodEntries;
    }

    public void insert (MoodEntry moodEntry) {
        new insertAsyncTask(mMoodEntryDao).execute(moodEntry);
    }

    private static class insertAsyncTask extends AsyncTask<MoodEntry, Void, Void> {

        private MoodEntryDao mAsyncTaskDao;

        insertAsyncTask(MoodEntryDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final MoodEntry... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }
}
