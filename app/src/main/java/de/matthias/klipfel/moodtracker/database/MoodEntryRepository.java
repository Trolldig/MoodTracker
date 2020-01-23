package de.matthias.klipfel.moodtracker.database;

import android.app.Application;
import android.os.AsyncTask;

import java.util.Calendar;
import java.util.Date;
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

    public void updateMoodEntry (int pA, int nA, Calendar date) throws ExecutionException, InterruptedException {
        new updateAsyncTask(mMoodEntryDao, date).execute(pA, nA);
    }

    /**
     * Returns Entries of a given month
     *
     * @param selectedMonth
     * @return MoodEntries of the given month
     */
    public List<MoodEntry> getAllEntriesMonth(Calendar from, Calendar to) throws ExecutionException, InterruptedException {
        return new getAsyncTask(mMoodEntryDao).execute(from, to).get();
    }

    /**
     * Returns an entry if one already exists on this date
     *
     * @param day
     * @param month
     * @param year
     * @return Entry of the given date
     */
    public MoodEntry checkForEntry(Calendar date) throws ExecutionException, InterruptedException {
        return new checkForEntryAsyncTask(mMoodEntryDao).execute(date).get();
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
        private final Calendar date;

        updateAsyncTask(MoodEntryDao moodEntryDao, Calendar date) {
            this.moodEntryDao = moodEntryDao;
            this.date = date;
        }

        @Override
        protected Void doInBackground(Integer... integers) {
            moodEntryDao.update(integers[0], integers[1], date);
            return null;
        }
    }

    private static class getAsyncTask extends AsyncTask<Calendar, Void, List<MoodEntry>> {

        private final MoodEntryDao moodEntryDao;

        getAsyncTask(MoodEntryDao dao) {
            this.moodEntryDao = dao;
        }

        @Override
        protected List<MoodEntry> doInBackground(Calendar... dates) {
            return moodEntryDao.getAllEntriesMonth(dates[0], dates[1]);
        }
    }

    private static class checkForEntryAsyncTask extends AsyncTask<Calendar, Void, MoodEntry> {

        private final MoodEntryDao moodEntryDao;

        checkForEntryAsyncTask(MoodEntryDao dao) {
            this.moodEntryDao = dao;
        }

        @Override
        protected MoodEntry doInBackground(Calendar... dates) {
            return moodEntryDao.checkForEntry(dates[0]);
        }
    }
}
