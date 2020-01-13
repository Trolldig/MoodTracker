package de.matthias.klipfel.moodtracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class MainActivity extends AppCompatActivity {

    private static final String DATA ="de.matthias.klipfel.moodtracker.MainActivity.DATA";

    private Button button;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Button for the voting activity
        button = (Button) findViewById(R.id.voteButton);
        button.setText("Wie Geht's?");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openVoteActivity();
            }
        });
        imageView = findViewById(R.id.imageView);
        Glide.with(this)
                .load(R.drawable.background_main)
                .into(imageView);
    }

    public void openVoteActivity(){
        Intent intent = new Intent(this, VoteActivity.class);
        startActivity(intent);
    }
}
