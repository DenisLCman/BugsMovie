package com.example.bugmovie;

import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.media.AudioManager;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.media.AudioManager;
import android.media.SoundPool;
import android.media.SoundPool.OnLoadCompleteListener;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;


public class GameLevel extends AppCompatActivity implements View.OnTouchListener, OnLoadCompleteListener {
    float x;
    float y;
    TextView tv;

    public ProgressBar pb;
    boolean gameRun = true;
    static MediaPlayer mPunch;
    static MediaPlayer mFail;
    static MediaPlayer mKill;


    static SoundPool sp;
    static int soundIdPunch;
    static int soundIdWood;

    int streamIDShot;
    int streamIDExplosion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.active_game);
        /*
        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        */

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        mFail= MediaPlayer.create(this, R.raw.wood1);
        mKill= MediaPlayer.create(this, R.raw.finalpunch);
        mPunch= MediaPlayer.create(this, R.raw.roblox);


        sp = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
        sp.setOnLoadCompleteListener(this);
        soundIdPunch = sp.load(this, R.raw.finalpunch, 1);
        soundIdWood = sp.load(this, R.raw.wood1, 1);


        pb = findViewById(R.id.progressBar);

            GameView gameView = new GameView(this, size.x, size.y);
        try {
            ConstraintLayout gameLayout = (ConstraintLayout) findViewById(R.id.gameLayer);
            gameLayout.addView(gameView);
        }
        catch (Exception ex){

        }


        new CountDownTimer(15000, 1000){
            @Override
            public void onTick(long l) {
                pb.setProgress((int)(l/1000));
            }

            @Override
            public void onFinish() {
                Intent intent = new Intent(GameLevel.this, MainActivity.class);
                startActivity(intent);
                MainActivity.LastScore = GameView.Score;
                GameView.Score = 0;
                gameView.setRun(false);
            }
        }.start();



    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {


        return false;
    }


    public void onBackPressed() {


    }

    @Override
    public void onLoadComplete(SoundPool soundPool, int i, int i1) {

    }
}