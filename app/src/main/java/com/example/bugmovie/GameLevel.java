package com.example.bugmovie;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;


public class GameLevel extends AppCompatActivity implements View.OnTouchListener {
    float x;
    float y;
    TextView tv;

    public ProgressBar pb;
    boolean gameRun = true;


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
}