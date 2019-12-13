package com.example.bootlegbubbleshooter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.TestLooperManager;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ProgressBar;


import java.util.Random;

public class MainActivity2 extends AppCompatActivity {

    private TextView mScoreView, mQuestion;
    private Button mTrueButton, mFalseButton;
    private ProgressBar bossHP;
    private ProgressBar userHP;

    private boolean mAnswer;
    private int mScore  = 0;
    private int mQuestionNumber = 0;
    private int bossProgress = 100;
    private int userProgress = 100;

    private int counter = 0;

    MediaPlayer space;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


        mScoreView = (TextView) findViewById(R.id.Score);
        mQuestion = (TextView) findViewById(R.id.textQuestion);
        mTrueButton = (Button) findViewById(R.id.trueButton);
        mFalseButton = (Button) findViewById(R.id.falseButton);
        bossHP = (ProgressBar) findViewById(R.id.boss);
        userHP = (ProgressBar) findViewById(R.id.user);

        final Handler updateProgress = new Handler();

        //play music in the background
        space = MediaPlayer.create(this,R.raw.spacey);
        space.setLooping(true);
        space.setScreenOnWhilePlaying(true);
        space.setVolume(100,100);
        space.start();

        final MediaPlayer mp = MediaPlayer.create(this, R.raw.buttonsound);


        //logic for True button
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                counter++;
                mp.start();
                bossHP.setMax(100);
                userHP.setMax(100);

                if (mAnswer == true) {

                    bossProgress = bossProgress - 20;
                    bossHP.setProgress(bossProgress);

                    mScore++;
                    updateScore(mScore);

                    //Perform a check to see if you are on the last question
                    if (mQuestionNumber == com.example.bootlegbubbleshooter.Questions.questions.length || userProgress == 0) {
                        Intent i = new Intent(MainActivity2.this, com.example.bootlegbubbleshooter.ResultsActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putInt("finalScore", mScore);
                        i.putExtras(bundle);
                        MainActivity2.this.finish();
                        startActivity(i);
                    }
                    else if (bossProgress == 0)
                    {
                        Intent i = new Intent(MainActivity2.this, com.example.bootlegbubbleshooter.WinningActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putInt("finalScore", mScore);
                        i.putExtras(bundle);
                        MainActivity2.this.finish();
                        startActivity(i);
                    }
                    else {
                        updateQuestion();
                    }

                } else {
                    userProgress = userProgress - 20;
                    userHP.setProgress(userProgress);
                    //Perform a check to see if you are on the last question
                    if (mQuestionNumber == com.example.bootlegbubbleshooter.Questions.questions.length || userProgress == 0) {
                        Intent i = new Intent(MainActivity2.this, com.example.bootlegbubbleshooter.ResultsActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putInt("finalScore", mScore);
                        i.putExtras(bundle);
                        MainActivity2.this.finish();
                        startActivity(i);
                    }
                    else if (bossProgress == 0)
                    {
                        Intent i = new Intent(MainActivity2.this, com.example.bootlegbubbleshooter.WinningActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putInt("finalScore", mScore);
                        i.putExtras(bundle);
                        MainActivity2.this.finish();
                        startActivity(i);
                    }
                    else {
                        updateQuestion();
                    }
                }

            }
        });

        //logic for false button
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                counter++;
               mp.start();
                if (mAnswer == false) {

                    bossProgress = bossProgress - 20;
                    bossHP.setProgress(bossProgress);

                    mScore++;
                    updateScore(mScore);

                    //Perform a check to see if you are on the last question
                    //Perform a check to see if you are on the last question
                    if (mQuestionNumber == com.example.bootlegbubbleshooter.Questions.questions.length || userProgress == 0) {
                        Intent i = new Intent(MainActivity2.this, com.example.bootlegbubbleshooter.ResultsActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putInt("finalScore", mScore);
                        i.putExtras(bundle);
                        MainActivity2.this.finish();
                        startActivity(i);
                    }
                    else if (bossProgress == 0)
                    {
                        Intent i = new Intent(MainActivity2.this, com.example.bootlegbubbleshooter.WinningActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putInt("finalScore", mScore);
                        i.putExtras(bundle);
                        MainActivity2.this.finish();
                        startActivity(i);
                    }
                    else {
                        updateQuestion();
                    }

                } else {
                    userProgress = userProgress - 20;
                    userHP.setProgress(userProgress);
                    //Perform a check to see if you are on the last question
                    if (mQuestionNumber == com.example.bootlegbubbleshooter.Questions.questions.length || userProgress == 0) {
                        Intent i = new Intent(MainActivity2.this, com.example.bootlegbubbleshooter.ResultsActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putInt("finalScore", mScore);
                        i.putExtras(bundle);
                        MainActivity2.this.finish();
                        startActivity(i);
                    }
                    else if (bossProgress == 0)
                    {
                        Intent i = new Intent(MainActivity2.this, com.example.bootlegbubbleshooter.WinningActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putInt("finalScore", mScore);
                        i.putExtras(bundle);
                        MainActivity2.this.finish();
                        startActivity(i);
                    }
                    else {
                        updateQuestion();
                    }
                }

                 updateProgress.post(new Runnable() {

                    public void run() {
                        bossHP.setProgress(bossProgress);
                        userHP.setProgress(userProgress);
                    }
                });


            }
        });



    }


            private void updateQuestion(){
                mQuestion.setText(com.example.bootlegbubbleshooter.Questions.questions[mQuestionNumber]);
                mAnswer = com.example.bootlegbubbleshooter.Questions.answer[mQuestionNumber];
                mQuestionNumber++;

            }

            public void updateScore(int point){
                mScoreView.setText(""+mScore);
               if (counter >6)
               {
                    if (mScore>6)
                    {

                    }
                    else
                    {

                    }
               }
            }
}
