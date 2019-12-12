package com.example.bootlegbubbleshooter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;

public class Instructions extends AppCompatActivity {

    MediaPlayer instructionmusic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructions);
        instructionmusic = MediaPlayer.create(this, R.raw.hug);
        instructionmusic.setLooping(true);
        instructionmusic.setScreenOnWhilePlaying(true);
        instructionmusic.setVolume(100, 100);
        instructionmusic.start();
    }
    protected void onPause()
    {
        super.onPause();
        instructionmusic.release();
    }

    protected void onResume()
    {
        super.onResume();
        instructionmusic.start();
    }

    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}

