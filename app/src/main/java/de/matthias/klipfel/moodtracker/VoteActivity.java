package de.matthias.klipfel.moodtracker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.hsalf.smilerating.BaseRating;
import com.hsalf.smilerating.SmileRating;

public class VoteActivity extends AppCompatActivity {

    private static final String TAG = "VoteActivity";
    private static String [] pa1 = {"Aktiv","Interessiert","Freudig erregt", "Stark"};
    private static String [] pa2 = {"Angeregt","Stolz","Begeistert", "Wach"};
    private static String [] pa3 = {"Entschlossen","Aufmerksam"};
    private static String [] neg1 = {"Bekümmert","Verärgert","Schuldig", "Erschrocken"};
    private static String [] neg2 = {"Feindselig","Gereizt","Beschämt", "Nervös"};
    private static String [] neg3 = {"Durcheinander","Ängstlich"};

    private TextView textPos1;
    private TextView textPos2;
    private TextView textPos3;
    private TextView textNeg1;
    private TextView textNeg2;
    private TextView textNeg3;
    private SmileRating posAff1;
    private SmileRating posAff2;
    private SmileRating posAff3;
    private SmileRating negAff1;
    private SmileRating negAff2;
    private SmileRating negAff3;
    private Button safeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote);

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

    protected void setSmileRatingNames(SmileRating rating){
        rating.setNameForSmile(BaseRating.TERRIBLE, "Gar nicht");
        rating.setNameForSmile(BaseRating.BAD, "Wenig");
        rating.setNameForSmile(BaseRating.OKAY, "Einigermaßen");
        rating.setNameForSmile(BaseRating.GOOD, "Erheblich");
        rating.setNameForSmile(BaseRating.GREAT, "Äußerst");
    }

    protected static String selectRand(String[] array){
        int rnd = (int)(Math.random()*array.length);
        return array[rnd];
    }

}
