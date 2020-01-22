package de.matthias.klipfel.moodtracker.database;

import android.app.Application;
import android.os.AsyncTask;

import java.util.List;
import java.util.concurrent.ExecutionException;

import androidx.lifecycle.LiveData;

public class MoodEntryRepository {

    private MoodEntryDao mMoodEntryDao;
    private LiveData<List<MoodEntry>> mAllMoodEntries;

    public MoodEntryRepository(Application application) {
        MoodEntryRoomDatabase db = MoodEntryRoomDatabase.getDatabase(application);
        mMoodEntryDao = db.moodEntryDao();
        mAllMoodEntries = mMoodEntryDao.getAllMoodEntries();
    }

    public LiveData<List<MoodEntry>> getAllMoodEntries() {
        return mAllMoodEntries;
    }

    public void insertMoodEntry (MoodEntry moodEntry) {
        new insertAsyncTask(mMoodEntryDao).execute(moodEntry);
    }

    public void updateMoodEntry (int pA, int nA, int day, int month, int year) throws ExecutionException, InterruptedException {
        new updateAsyncTask(mMoodEntryDao).execute(pA, nA, day, month, year);
    }

    /**
     * Returns Entries of a given month
     *
     * @param selectedMonth
     * @return MoodEntries of the given month
     */
    public List<MoodEntry> getAllEntriesMonth(int selectedMonth) throws ExecutionException, InterruptedException {
        return new getAsyncTask(mMoodEntryDao).execute(selectedMonth).get();
    }

    /**
     * Returns an entry if one already exists on this date
     *
     * @param day
     * @param month
     * @param year
     * @return Entry of the given date
     */
    public MoodEntry checkForEntry(int day, int month, int year) throws ExecutionException, InterruptedException {
        return new checkForEntryAsyncTask(mMoodEntryDao).execute(day,month,year).get();
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

    private static class updateAsyncTask extends AsyncTask<Integer, Void, Void>{

        private final MoodEntryDao moodEntryDao;

        updateAsyncTask(MoodEntryDao moodEntryDao) {
            this.moodEntryDao = moodEntryDao;
        }

        @Override
        protected Void doInBackground(Integer... integers) {
            moodEntryDao.update(integers[0], integers[1], integers[2], integers[3], integers[4]);
            return null;
        }
    }

    private static class getAsyncTask extends AsyncTask<Integer, Void, List<MoodEntry>> {

        private final MoodEntryDao moodEntryDao;

        getAsyncTask(MoodEntryDao dao) {
            this.moodEntryDao = dao;
        }

        @Override
        protected List<MoodEntry> doInBackground(Integer... integers) {
            return moodEntryDao.getAllEntriesMonth(integers[0]);
        }
    }

    private static class checkForEntryAsyncTask extends AsyncTask<Integer, Void, MoodEntry> {

        private final MoodEntryDao moodEntryDao;

        checkForEntryAsyncTask(MoodEntryDao dao) {
            this.moodEntryDao = dao;
        }

        @Override
        protected MoodEntry doInBackground(Integer... integers) {
            return moodEntryDao.checkForEntry(integers[0], integers [1], integers[2]);
        }
    }
}
