package de.matthias.klipfel.moodtracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class MainActivity extends AppCompatActivity {

    private static final String DATA ="de.matthias.klipfel.moodtracker.MainActivity.DATA";

    private Button voteButton;
    private Button graphButton;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Button for the voting activity
        voteButton = (Button) findViewById(R.id.voteButton);
        voteButton.setText("Wie Geht's?");

        //Button for the graph activity
        graphButton = (Button) findViewById(R.id.graphButton);
        graphButton.setText("Übersicht");

        //set background image
        imageView = findViewById(R.id.imageView);
        Glide.with(this)
                .load(R.drawable.background_main)
                .into(imageView);
    }

    public void openVoteActivity(View view){
        Intent intent = new Intent(this, VoteActivity.class);
        startActivity(intent);
    }

    public void openGraphActivity(View view) {
        Log.i("Graphbutten","clicked");
        Intent intent = new Intent(this, GraphActivity.class);
        startActivity(intent);
    }
}
