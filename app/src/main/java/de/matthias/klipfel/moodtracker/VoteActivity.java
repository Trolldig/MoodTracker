package de.matthias.klipfel.moodtracker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.hsalf.smilerating.BaseRating;
import com.hsalf.smilerating.SmileRating;

public class VoteActivity extends AppCompatActivity {

    private static final String TAG = "VoteActivity";
    private static String [] pa1 = {"aktiv","interessiert","freudig erregt", "stark"};
    private static String [] pa2 = {"angeregt","stolz","begeistert", "wach"};
    private static String [] pa3 = {"entschlossen","aufmerksam"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote);

        //Set random value for the textViews
        TextView textPos1 = (TextView) findViewById(R.id.textPos1);
        textPos1.setText(selectRand(pa1));
        TextView textPos2 = (TextView) findViewById(R.id.textPos2);
        textPos2.setText(selectRand(pa2));
        TextView textPos3 = (TextView) findViewById(R.id.textPos3);
        textPos3.setText(selectRand(pa3));

        //Get Id of the first positive Vote
        SmileRating posAff1 = (SmileRating) findViewById(R.id.ratingPos1);
        //Change name of smileys
        posAff1.setNameForSmile(BaseRating.TERRIBLE, "Gar nicht");
        posAff1.setNameForSmile(BaseRating.BAD, "Wenig");
        posAff1.setNameForSmile(BaseRating.OKAY, "Einigermaßen");
        posAff1.setNameForSmile(BaseRating.GOOD, "Erheblich");
        posAff1.setNameForSmile(BaseRating.GREAT, "Äußerst");
        //Set Listener for the first Vote
        posAff1.setOnSmileySelectionListener(new SmileRating.OnSmileySelectionListener() {
            @Override
            public void onSmileySelected( int smiley, boolean reselected){
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
            @Override
            public void onRatingSelected(int level, boolean reselected) {
                Log.i(TAG, "Level: " + level);
            }
        });
    }
    protected static String selectRand(String[] array){
        int rnd = (int)(Math.random()*array.length);
        return array[rnd];
    }
}
