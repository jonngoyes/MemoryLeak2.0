package com.example.bootlegbubbleshooter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.method.ScrollingMovementMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class Levels extends AppCompatActivity {

    // fetchQuestionData Class INITIALIZATION - BEGIN
    fetchQuestionData process2 = new fetchQuestionData();
    // fetchQuestionData Class - END

    //ANSWER INITIALIZATION - BEGIN
    char correctAnswer;
    // ANSWER - END

    // BULLET INITIALIZATION - BEGIN
    ImageView bullet;
    float x_bullet, y_bullet;
    // BULLET - END


    // ROCKET INITIALIZATION - BEGIN
    ImageButton rocketButton;
    float rocketX;
    float rocketY;
    // ROCKET - END


    // CLOUDs A, B, C, D INITIALIZATIONS - BEGIN
    ImageView cloudA;
    ImageView cloudB;
    ImageView cloudC;
    ImageView cloudD;
    float cloudAY;
    float cloudAYorig;
    float cloudBY;
    float cloudBYorig;
    // CLOUDS A, B, C, D - END


    // TIME HANDLER INITIALIZATION - BEGIN
    private Handler handler = new Handler();
    private Timer timer = new Timer();
    // TIME HANDLER - END


    // SCREEN SIZE INITIALIZATION - BEGIN
    private int screenWidth;
    private int screenHeight;
    // SCREEN SIZE END


    //Question text
    public static TextView q_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_levels);

        rocketButton = (ImageButton) findViewById(R.id.imageButton6);
        bullet = (ImageView) findViewById(R.id.imageView2);
        bullet.setVisibility(View.VISIBLE);

        rocketX = rocketButton.getX();
        rocketY = rocketButton.getY();
        rocketButton.setX(rocketX);
        rocketButton.setY(rocketY);

        cloudA = (ImageView) findViewById(R.id.CloudA);
        cloudA.setX(cloudA.getX());
        cloudA.setY(cloudA.getY());

        cloudAY = cloudA.getY();
        cloudAYorig = cloudAY;

        cloudB = (ImageView) findViewById(R.id.CloudB);
        cloudB.setX(cloudB.getX());
        cloudB.setY(cloudB.getY());
        cloudBY = cloudB.getY();
        cloudBYorig = cloudBY;

        cloudC = (ImageView) findViewById(R.id.CloudC);
        cloudC.setX(cloudC.getX());
        cloudC.setY(cloudC.getY());
        cloudD = (ImageView) findViewById(R.id.CloudD);
        cloudD.setX(cloudD.getX());
        cloudD.setY(cloudD.getY());

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        cloudA.setX(cloudA.getX());
                        cloudB.setX(cloudB.getX());
                        cloudC.setX(cloudC.getX());
                        cloudD.setX(cloudD.getX());
                        if (changePos())
                        {
                            bullet.setX(rocketButton.getX());
                            bullet.setY(rocketButton.getY());
                        }
                    }
                });
            }
        }, 0, 10);


        rocketButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Levels.this, "It works", Toast.LENGTH_LONG).show();
                x_bullet = rocketButton.getX() +26;
               y_bullet = rocketButton.getY() - 90;
                bullet.setX(x_bullet);
               bullet.setY(y_bullet);

                bullet.setVisibility(View.VISIBLE);


            }
        });

        //Question Data
        q_data = (TextView) findViewById(R.id.QuestionBox);
        q_data.setMovementMethod(new ScrollingMovementMethod());
        process2.execute();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //Toast.makeText(Levels.this, "It works", Toast.LENGTH_LONG).show();
                bullet.setVisibility(View.GONE);

            case MotionEvent.ACTION_MOVE:
                rocketButton.setX(event.getX()-90);
        }
        return true;
    }

    public char answerDetect(int x, int y)
    {
        if (some coordinate)
            return 'A';
        else if (some coordinate)
            return 'B';
        else if (some coordinate)
            return 'C';
        else
            return 'D';
    }

    public boolean PlayerIsCorrect()
    {
        correctAnswer = process2.getCorrectAnswer();
        if (correctAnswer == answerDetect())
        {
            Toast.makeText(Levels.this, "Correct", Toast.LENGTH_LONG).show();
            return true;
        }
        else {
            Toast.makeText(Levels.this, "Incorrect!", Toast.LENGTH_LONG).show();
            return false;
        }
    }


    public boolean changePos() {

//        cloudAY = cloudA.getY();
//        float cloudAYorig= cloudAY;

        y_bullet -= 10;
//
//
        boolean temp = false;
        if (bullet.getY() < 0) {
             temp = true;
             bullet.setVisibility(View.GONE);
        }
//
           bullet.setY(y_bullet);

        //    float cloudAX = cloudA.getX();




        if (cloudA.getY() > 2600) {
            cloudA.setY(0.0f);
        }
        if (cloudB.getY() > 2600) {
            cloudB.setY(0.0f);
        }
        if (cloudC.getY() > 2600) {
            cloudC.setY(0.0f);
        }
        if (cloudD.getY() > 2600) {
            cloudD.setY(0.0f);
        }

        cloudA.setY(cloudA.getY() + 1);
        cloudB.setY(cloudB.getY() + 1);
        cloudC.setY(cloudC.getY() + 1);
        cloudD.setY(cloudD.getY() + 1);
        bullet.setY(bullet.getY() - 10);

        collisionDetect();


        return temp;


    }
}

