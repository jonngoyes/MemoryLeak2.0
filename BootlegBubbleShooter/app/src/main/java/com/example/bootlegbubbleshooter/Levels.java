package com.example.bootlegbubbleshooter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.text.method.ScrollingMovementMethod;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class Levels extends AppCompatActivity {

    private int screenWidth;
    private int screenHeight;

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

    //Screen Size


    // SCREEN SIZE END


    //Question text
    public static TextView q_data;

    @Override
        protected void onCreate(Bundle savedInstanceState) {

        // SCREEN SIZE INITIALIZATION - BEGIN
        WindowManager wm = getWindowManager();
        Display disp = wm.getDefaultDisplay();
        Point size = new Point();
        disp.getSize(size);
        screenWidth = size.x;
        screenHeight = size.y;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_levels);

        rocketButton = findViewById(R.id.imageButton6);
        bullet = findViewById(R.id.imageView2);
        bullet.setVisibility(View.VISIBLE);

        rocketX = rocketButton.getX();
        rocketY = rocketButton.getY();
        rocketButton.setX(rocketX);
        rocketButton.setY(rocketY);

        cloudA = findViewById(R.id.CloudA);
        cloudA.setX(cloudA.getX());
        cloudA.setY(cloudA.getY());

        cloudAY = cloudA.getY();
        cloudAYorig = cloudAY;

        cloudB = findViewById(R.id.CloudB);
        cloudB.setX(cloudB.getX());
        cloudB.setY(cloudB.getY());
        cloudBY = cloudB.getY();
        cloudBYorig = cloudBY;

        cloudC = findViewById(R.id.CloudC);
        cloudC.setX(cloudC.getX());
        cloudC.setY(cloudC.getY());
        cloudD = findViewById(R.id.CloudD);
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
                Toast.makeText(Levels.this, "It works", Toast.LENGTH_LONG).show();
                x_bullet = rocketButton.getX() + 26;
                y_bullet = rocketButton.getY() - 90;
                bullet.setX(x_bullet);
                bullet.setY(y_bullet);

                bullet.setVisibility(View.VISIBLE);


            }
        });

        //Question Data
        q_data = findViewById(R.id.QuestionBox);
        q_data.setMovementMethod(new ScrollingMovementMethod());
        fetchQuestionData process2 = new fetchQuestionData();
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
                rocketButton.setX(event.getX() - 90);
        }
        return true;
    }


    public int collisionDetect()
    {

     //   int[] locationB = new int[2];
        /*
        int[] locationD = new int[2];
        */


       // cloudB.getLocationOnScreen(locationB);


        if(bullet.getX()>0 && bullet.getX()<(screenWidth/4)) {
            return 0;
        }
        else if(bullet.getX()>(screenWidth/4) && bullet.getX()<(screenWidth/2))
        {
            return 1;
        }
        else if(bullet.getX()>(screenWidth/2) && bullet.getX()<(3*screenWidth/4)) {
            return 2;
        }
        else if(bullet.getX()>(3*screenWidth/4)&& bullet.getX()<screenWidth){
            return 3;
        }
        return 4;
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

        switch (collisionDetect())
        {
            case 0:
                Toast.makeText(Levels.this, "A works",).show();
                break;
            case 1:
                Toast.makeText(Levels.this, "B works", Toast.LENGTH_SHORT).show();
                break;
            case 2:
                Toast.makeText(Levels.this, "C works", Toast.LENGTH_SHORT).show();
                break;
            case 3:
                Toast.makeText(Levels.this, "D works", Toast.LENGTH_SHORT).show();
                break;
            case 4:
                Toast.makeText(Levels.this, "No answer given", Toast.LENGTH_SHORT).show();
                break;
        }




        return temp;


    }
}



 //           cloudAX = 1*screenWidth/5;//(float)Math.floor(Math.random() * (screenWidth - cloud1.getWidth()));
//            cloudAY = -195.0f;
//        }
//        cloudA.setX(cloudAX);
//        cloudA.setY(cloudAY + 10);

//        cloud2Y +=10;
//        if(cloud2.getY() > screenHeight)
//        {
//            cloud2X = screenWidth/1000;//(float)Math.floor(Math.random() * (screenWidth - cloud2.getWidth()));
//            cloud2Y = -100.0f;
//        }
//        cloud2.setX(cloud2X);
//        cloud2.setY(cloud2Y);
//
//        cloud3Y +=10;
//        if(cloud3.getY() > screenHeight)
//        {
//            cloud3X = 1*screenWidth/2;//(float)Math.floor(Math.random() * (screenWidth - cloud3.getWidth()));
//            cloud3Y = -100.0f;
//        }
//        cloud3.setX(cloud3X);
//        cloud3.setY(cloud3Y);



