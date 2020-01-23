package de.matthias.klipfel.moodtracker;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.room.Room;
import de.matthias.klipfel.moodtracker.database.MoodEntry;
import de.matthias.klipfel.moodtracker.database.MoodEntryRepository;
import de.matthias.klipfel.moodtracker.database.MoodEntryRoomDatabase;
import de.matthias.klipfel.moodtracker.database.MoodEntryViewModel;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.hsalf.smilerating.BaseRating;
import com.hsalf.smilerating.SmileRating;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class VoteActivity extends AppCompatActivity {

    //public MoodEntryRepository repository;
    private MoodEntryViewModel xmoodEntryViewModel;
    private static final String TAG = "VoteActivity";
    private static final String[] pa1 = {"Aktiv", "Interessiert", "Freudig erregt", "Stark"};
    private static final String[] pa2 = {"Angeregt", "Stolz", "Begeistert", "Wach"};
    private static final String[] pa3 = {"Entschlossen", "Aufmerksam"};
    private static final String[] neg1 = {"Bekümmert", "Verärgert", "Schuldig", "Erschrocken"};
    private static final String[] neg2 = {"Feindselig", "Gereizt", "Beschämt", "Nervös"};
    private static final String[] neg3 = {"Durcheinander", "Ängstlich"};


    private TextView textPos1;
    private TextView textPos2;
    private TextView textPos3;
    private TextView textNeg1;
    private TextView textNeg2;
    private TextView textNeg3;
    private TextView testText;
    private SmileRating posAff1;
    private SmileRating posAff2;
    private SmileRating posAff3;
    private SmileRating negAff1;
    private SmileRating negAff2;
    private SmileRating negAff3;
    private Button safeButton;
    private int PATotal = 0;
    private int NATotal = 0;
    private Calendar calendar;
    private int day;
    private int month;
    private int year;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote);

        //moodEntryRoomDatabase = Room.databaseBuilder(getApplicationContext(),
        //        MoodEntryRoomDatabase.class, "mooddb").build();

        //repository = new MoodEntryRepository(getApplication());

        xmoodEntryViewModel = ViewModelProviders.of(this).get(MoodEntryViewModel.class);

        //Set random Text for the textViews
        textPos1 = findViewById(R.id.textPos1);
        textPos1.setText(selectRand(pa1));
        textPos2 = findViewById(R.id.textPos2);
        textPos2.setText(selectRand(pa2));
        textPos3 = findViewById(R.id.textPos3);
        textPos3.setText(selectRand(pa3));
        textNeg1 = findViewById(R.id.textNeg1);
        textNeg1.setText(selectRand(neg1));
        textNeg2 = findViewById(R.id.textNeg2);
        textNeg2.setText(selectRand(neg2));
        textNeg3 = findViewById(R.id.textNeg3);
        textNeg3.setText(selectRand(neg3));

        //Get Id of the smiles ratings and add them to an array
        posAff1 = findViewById(R.id.ratingPos1);
        posAff2 = findViewById(R.id.ratingPos2);
        posAff3 = findViewById(R.id.ratingPos3);
        negAff1 = findViewById(R.id.ratingNeg1);
        negAff2 = findViewById(R.id.ratingNeg2);
        negAff3 = findViewById(R.id.ratingNeg3);

        //Change name of smileys
        setSmileRatingNames(posAff1);
        setSmileRatingNames(posAff2);
        setSmileRatingNames(posAff3);
        setSmileRatingNames(negAff1);
        setSmileRatingNames(negAff2);
        setSmileRatingNames(negAff3);

        safeButton = (Button) findViewById(R.id.safeButton);
        safeButton.setText("Übernehmen");

        /**Test to show the selected data
         safeButton.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View view) {
        int levelPA1 = posAff1.getRating();
        int levelPA2 = posAff2.getRating();
        testText = (TextView) findViewById(R.id.testTextView);
        testText.setText(Integer.toString(levelPA1)+" "+Integer.toString(levelPA2));
        }
        });
         **/

        //Set Listener for the first Vote
        /**
         posAff1.setOnSmileySelectionListener(new SmileRating.OnSmileySelectionListener() {
        @Override public void onSmileySelected( int smiley, boolean reselected){
        // reselected is false when user selects different smiley that previously selected one
        // true when the same smiley is selected.
        // Except if it first time, then the value will be false.
        switch (smiley) {
        case SmileRating.BAD:
        Log.i(TAG, "Wenig");
        break;
        case SmileRating.GOOD:
        Log.i(TAG, "Good");
        break;
        case SmileRating.GREAT:
        Log.i(TAG, "Great");
        break;
        case SmileRating.OKAY:
        Log.i(TAG, "Okay");
        break;
        case SmileRating.TERRIBLE:
        Log.i(TAG, "Terrible");
        break;
        }
        }
        });
         //Get selected Smiley for the first Vote
         posAff1.setOnRatingSelectedListener(new SmileRating.OnRatingSelectedListener() {
        @Override public void onRatingSelected(int level, boolean reselected) {
        Log.i(TAG, "Level: " + level);
        }
        });
         **/
    }

    protected void setSmileRatingNames(SmileRating rating) {
        rating.setNameForSmile(BaseRating.TERRIBLE, "Gar nicht");
        rating.setNameForSmile(BaseRating.BAD, "Wenig");
        rating.setNameForSmile(BaseRating.OKAY, "Einigermaßen");
        rating.setNameForSmile(BaseRating.GOOD, "Erheblich");
        rating.setNameForSmile(BaseRating.GREAT, "Äußerst");
    }

    protected static String selectRand(String[] array) {
        int rnd = (int) (Math.random() * array.length);
        return array[rnd];
    }

    public void returnVote(View view) {
        int levelPA1 = posAff1.getRating();
        int levelPA2 = posAff2.getRating();
        int levelPA3 = posAff3.getRating();
        int levelNA1 = negAff1.getRating();
        int levelNA2 = negAff2.getRating();
        int levelNA3 = negAff3.getRating();
        //prepares data for saving
        PATotal = levelPA1 + levelPA2 + levelPA3;
        NATotal = levelNA1 + levelNA2 + levelNA3;
        //check if a vote is missing
        boolean allVotesSelected = false;
        if (levelPA1 != 0 && levelPA2 != 0 && levelPA3 != 0 && levelNA1 != 0 && levelNA2 != 0 && levelNA3 != 0) {
            allVotesSelected = true;
        }
        //get current date
        //formats date as described
        //DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        long timeMillies = calendar.getTimeInMillis();


        //display in text view for testing
        testText = (TextView) findViewById(R.id.testTextView);
        testText.setText(PATotal + " " + NATotal + " " + month);
        //repository.insertMoodEntry(new MoodEntry(PATotal,NATotal,datum));
        //save in database
        //check if all votes are selected
        if(allVotesSelected){
            //check if a vote with the same date exists already
            //insert if no vote exists, else show dialog
            if(xmoodEntryViewModel.checkForEntry(calendar) == null){
                xmoodEntryViewModel.insert(new MoodEntry(PATotal, NATotal, calendar));
                Toast.makeText(VoteActivity.this, "Hinzugefügt!",
                        Toast.LENGTH_SHORT).show();
                Log.i("Room", "added");
                finish();
            } else {
                showAlertDialogUpdateWarning(view);
            }
        } else {
            showAlertDialogVoteMissing(view);
        }
    }

    public void showAlertDialogVoteMissing(View view) {        // setup the alert builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Warnung");
        builder.setMessage("Es wurden nicht alle Felder ausgewählt.");        // add a button
        builder.setPositiveButton("OK", null);        // create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void showAlertDialogUpdateWarning(View view) {        // setup the alert builder

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Warnung");
        builder.setMessage("Es kann pro Tag nur eine Eingabe getätigt werden. " +
                "Möchten sie die Eingabe von heute überschreiben?");        // add the buttons
        builder.setPositiveButton("Überschreiben", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                xmoodEntryViewModel.updateMoodEntry(PATotal, NATotal, calendar);
                PATotal = 0;
                NATotal = 0;
                day = 0;
                month = 0;
                year = 0;
                Log.i("Room", "updated");
                Toast.makeText(VoteActivity.this, "Hinzugefügt!",
                        Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        builder.setNegativeButton("Abbrechen", null);     // create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }

}
