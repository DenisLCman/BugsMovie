package com.example.bugmovie;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.app.Activity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    private long backPressedTime;
    private Toast backToast;
    float x;
    float y;
    static int LastScore = 0;
    static int BestScore = 0;
    public static final String APP_PREFERENCES = "mysettings";
    public static final String APP_PREFERENCES_Name = "score";
    public static final String APP_PREFERENCES_Value = "value";

    SharedPreferences mSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(LastScore > BestScore){
            BestScore = LastScore;
        }

        mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);


        System.out.println(BestScore);
        TextView tv;
        TextView besttv;
        tv = findViewById(R.id.textScore);
        tv.setText("LAST SCORE:" + Integer.toString(LastScore));
        besttv = findViewById(R.id.textBest);
        besttv.setText("BEST SCORE:" + Integer.toString(BestScore));





        Button buttonStart = (Button) findViewById(R.id.buttonStart);
        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    Intent intent = new Intent(MainActivity.this, GameLevel.class);
                    startActivity(intent);
                }
                catch(Exception ex){

                }
            }
        });


        Button buttonSave = (Button) findViewById(R.id.buttonSave);
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    SharedPreferences.Editor editor = mSettings.edit();
                    editor.putInt(APP_PREFERENCES_Name, BestScore);
                    editor.apply();
                }
                catch(Exception ex){

                }
            }
        });


        Button buttonLoad = (Button) findViewById(R.id.buttonLoad);
        buttonLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    BestScore = mSettings.getInt(APP_PREFERENCES_Name, 0);
                    besttv.setText("BEST SCORE:" + Integer.toString(BestScore));
                }
                catch(Exception ex){

                }
            }
        });


        //Window w = getWindow();
        //w.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


    }
    //Системная кнопка

    @Override
    public void onBackPressed() {

        if(backPressedTime + 2000 > System.currentTimeMillis()){
            backToast.cancel();
            //super.onBackPressed();
            this.finishAffinity();
            return;
        }
        else{
            backToast = Toast.makeText(getBaseContext(), "Нажмите ещё раз, чтобы выйти", Toast.LENGTH_SHORT);
            backToast.show();
        }

        backPressedTime = System.currentTimeMillis();

    }


}