package com.example.bootlegbubbleshooter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends AppCompatActivity {

    MediaPlayer mainscreenmusic;
    private Button SettingsButton;
    private Button StartButton;
    private Button InstructionButton;

    //Screen Size
    private int screenWidth;
    private int screenHeight;

    //Images
    private ImageView cloud1;
    private ImageView cloud2;
    private ImageView cloud3;

    //Position
    private float cloud1X;
    private float cloud1Y;
    private float cloud2X;
    private float cloud2Y;
    private float cloud3X;
    private float cloud3Y;

    //Initialize Class
    private Handler handler = new Handler();
    private Timer timer = new Timer();

    //Fun Fact text
    public static TextView data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Main Screen Background Music
        mainscreenmusic = MediaPlayer.create(this,R.raw.heartbeat);
        mainscreenmusic.setLooping(true);
        mainscreenmusic.setScreenOnWhilePlaying(true);
        mainscreenmusic.setVolume(100,100);
        mainscreenmusic.start();

        //ButtonSounds
        final MediaPlayer buttonsound = MediaPlayer.create(this,R.raw.dustyroom_multimedia_select_digital_button);
        Button StartButton = (Button) this.findViewById(R.id.StartButton);
        StartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonsound.start();
            }
        });

        final MediaPlayer buttonsound2 = MediaPlayer.create(this,R.raw.dustyroom_multimedia_select_digital_button);
        Button SettingsButton = (Button) this.findViewById(R.id.SettingsButton);
        SettingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonsound2.start();
                SettingsOnClick();
            }
        });

        //MainScreen Animation
        cloud1 = (ImageView)findViewById(R.id.Cloud1);
        cloud2 = (ImageView)findViewById(R.id.Cloud2);
        cloud3 = (ImageView)findViewById(R.id.Cloud3);

        //Get Screen Size
        WindowManager wm = getWindowManager();
        Display disp = wm.getDefaultDisplay();
        Point size = new Point();
        disp.getSize(size);
        screenWidth = size.x;
        screenHeight = size.y;

        //Move out of screen.
        cloud1.setX(-80.0f);
        cloud1.setY(screenHeight+80.0f);
        cloud2.setX(-80.0f);
        cloud2.setY(screenHeight+80.0f);
        cloud3.setX(-80.0f);
        cloud3.setY(screenHeight+80.0f);

        timer.schedule(new TimerTask()
        {
            @Override
            public void run()
            {
                handler.post(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        changePos();
                    }
                });
            }
        },0,10); 

        //Start Button
        StartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StartOnClick();
            }
        });

        //Instruction Button
        Button InstructionButton = (Button) this.findViewById(R.id.instructionbutton);
        InstructionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StartOnClick2();
            }
        });

        //Fun Fact Data
        data = (TextView) findViewById(R.id.funFact);
        fetchFunFactData process = new fetchFunFactData();
        process.execute();
    }

    protected void onPause()
    {
        super.onPause();
        mainscreenmusic.release();
    }

   protected void onResume()
    {
        super.onResume();
        mainscreenmusic.start();
    }

    //Navigate to Settings Menu
    public void SettingsOnClick()
    {
        Intent intent = new Intent(this,SettingsActivity.class);
        startActivity(intent);
    }

    //Navigate to Start Button
    public void StartOnClick()
    {
        Intent intent = new Intent(this,Levels.class);
        startActivity(intent);
    }

    //Navigate to Start Button
    public void StartOnClick2()
    {
        Intent intent = new Intent(this,Instructions.class);
        startActivity(intent);
    }

    //Change position of clouds for animation
    public void changePos()
    {
        cloud1Y +=10;
        if(cloud1.getY() > screenHeight)
        {
            cloud1X = 1*screenWidth/5;//(float)Math.floor(Math.random() * (screenWidth - cloud1.getWidth()));
            cloud1Y = -195.0f;
        }
        cloud1.setX(cloud1X);
        cloud1.setY(cloud1Y);

        cloud2Y +=10;
        if(cloud2.getY() > screenHeight)
        {
            cloud2X = screenWidth/1000;//(float)Math.floor(Math.random() * (screenWidth - cloud2.getWidth()));
            cloud2Y = -100.0f;
        }
        cloud2.setX(cloud2X);
        cloud2.setY(cloud2Y);

        cloud3Y +=10;
        if(cloud3.getY() > screenHeight)
        {
            cloud3X = 1*screenWidth/2;//(float)Math.floor(Math.random() * (screenWidth - cloud3.getWidth()));
            cloud3Y = -100.0f;
        }
        cloud3.setX(cloud3X);
        cloud3.setY(cloud3Y);

    }

}