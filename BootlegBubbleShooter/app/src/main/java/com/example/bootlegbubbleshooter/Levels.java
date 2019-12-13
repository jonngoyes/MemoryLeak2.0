package com.example.bootlegbubbleshooter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.text.method.ScrollingMovementMethod;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.CollationElementIterator;
import java.util.Timer;
import java.util.TimerTask;

public class Levels extends AppCompatActivity {
    private int screenWidth;
    private int screenHeight;

    // fetchQuestionData Class INITIALIZATION - BEGIN
    fetchQuestionData process2 = new fetchQuestionData();
    // fetchQuestionData Class - END

    //ANSWER INITIALIZATION - BEGIN
    char correctAnswer;
    // ANSWER - END

    MediaPlayer levelmusic;

    //Pause Button
    private Button pause;
    private boolean pause_flag;

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
  //  private int screenWidth;
 //   private int screenHeight;
    // SCREEN SIZE END


    //Question text
    public static TextView q_data;
    private Toast myToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        myToast = Toast.makeText(getApplicationContext(), null, Toast.LENGTH_SHORT);

        WindowManager wm = getWindowManager();
        Display disp = wm.getDefaultDisplay();
        Point size = new Point();
        disp.getSize(size);
        screenWidth = size.x;
        screenHeight = size.y;


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_levels);

        //Levels Background Music
        levelmusic = MediaPlayer.create(this,R.raw.levanter);
        levelmusic.setLooping(true);
        levelmusic.setScreenOnWhilePlaying(true);
        levelmusic.setVolume(100,100);
        levelmusic.start();


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
                        if (changePos()) {
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
               // Toast.makeText(Levels.this, "It works", Toast.LENGTH_LONG).show();
                x_bullet = rocketButton.getX() + 26;
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

        //Pause Button
        pause = (Button) findViewById(R.id.pauseButton);

    }

    public void pausePushed(View view)
    {
        if(pause_flag==false)
        {
            pause_flag = true;
            timer.cancel();
            timer = null;
            pause.setText("START");
        }
        else {
            pause_flag = false;
            pause.setText("PAUSE");
            timer = new Timer();
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
        }
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
                rocketButton.setX(event.getX() - 90);
        }
        return true;
    }

//    public char answerDetect(int x, int y)
//    {
//        if (some coordinate)
//            return 'A';
//        else if (some coordinate)
//            return 'B';
//        else if (some coordinate)
//            return 'C';
//        else
//            return 'D';
//    }

    public boolean IsPlayerCorrect(char playerAnswer)
    {
        char correctAnswer;
        correctAnswer = process2.getCorrectAnswer();
        if (correctAnswer == playerAnswer)
        {

            myToast.show();
            myToast.setText("Correct");
           // myToast.setText(null);
            return true;

        }
        else {
         //   Toast.makeText(Levels.this, "Incorrect", Toast.LENGTH_LONG).show();

            //myToast.setText("Incorrect");
          //  myToast.show();
            return false;
        }
    }


    public void collisionDetect()
    {

        /*
        int[] locationD = new int[2];
        */
        int [] locationBullet = new int [2];
        bullet.getLocationOnScreen(locationBullet);
        float bullet_x = locationBullet[0];
        float bullet_y = locationBullet[1];
        System.out.print(bullet_x);
        System.out.print(bullet_y);

        //int[] locationB = cloudB.getLocationOnScreen();


        if(bullet.getX()>0 && bullet.getX()<(screenWidth/4)) {
            IsPlayerCorrect('A');
            cloudA.setVisibility(View.GONE);
            //return 'A';
        }
        else if(bullet.getX()>(screenWidth/4) && bullet.getX()<(screenWidth/2))
        {
            IsPlayerCorrect('B');
            cloudB.setVisibility(View.GONE);
            //return 'B';
        }
        else if(bullet.getX()>(screenWidth/2) && bullet.getX()<(3*screenWidth/4)) {
            IsPlayerCorrect('C');
            cloudC.setVisibility(View.GONE);
            //return 'C';
        }
        else if(bullet.getX()>(3*screenWidth/4)&& bullet.getX()<screenWidth){
            IsPlayerCorrect('D');
            cloudD.setVisibility(View.GONE);
            //return 'D';
        }
        //return 'N'; //no collision yet
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
            cloudA.setVisibility(View.VISIBLE);
            cloudA.setY(0.0f);
        }
        if (cloudB.getY() > 2600) {
            cloudB.setVisibility(View.VISIBLE);
            cloudB.setY(0.0f);
        }
        if (cloudC.getY() > 2600) {
            cloudC.setVisibility(View.VISIBLE);
            cloudC.setY(0.0f);
        }
        if (cloudD.getY() > 2600) {
            cloudD.setVisibility(View.VISIBLE);
            cloudD.setY(0.0f);
        }

        cloudA.setY(cloudA.getY() + 1);
        cloudB.setY(cloudB.getY() + 1);
        cloudC.setY(cloudC.getY() + 1);
        cloudD.setY(cloudD.getY() + 1);
        bullet.setY(bullet.getY() - 10);


        collisionDetect();
//
//        switch (collisionDetect())
//        {
//            case 0:
//                Toast.makeText(Levels.this, "A works", Toast.LENGTH_SHORT).show();
//                break;
//            case 1:
//                Toast.makeText(Levels.this, "B works", Toast.LENGTH_SHORT).show();
//                break;
//            case 2:
//                Toast.makeText(Levels.this, "C works", Toast.LENGTH_SHORT).show();
//                break;
//            case 3:
//                Toast.makeText(Levels.this, "D works", Toast.LENGTH_SHORT).show();
//                break;
//            case 4:
//                Toast.makeText(Levels.this, "No answer given", Toast.LENGTH_SHORT).show();
//                break;
//        }
//
//        IsPlayerCorrect();

        return temp;


    }

    protected void onPause()
    {
        super.onPause();
        levelmusic.release();
    }

    protected void onResume()
    {
        super.onResume();
        levelmusic.start();
    }
}

