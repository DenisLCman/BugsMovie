package com.example.bugmovie;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.SystemClock;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import java.util.ArrayList;

public class GameView extends SurfaceView implements Runnable, View.OnTouchListener{




    public static int maxX = 20;
    public static int maxY = 20;
    public static float unitW = 0;
    public static float unitH = 0;
    public boolean timeOver = false;
    private boolean gameRunning = true;
    private Thread gameThread = null;
    private Paint paint;
    private Canvas canvas;

    private Bitmap background;

    private Context context;
    private int screenX;
    private int screenY;

    //public Bug[] bugs;


    public int bugCount = 3;
    ArrayList<Bug> bugs;
    private SurfaceHolder surfaceHolder;

    @SuppressLint("ClickableViewAccessibility")
    public GameView(Context context, int screenX, int screenY) {
        super(context);

        background = BitmapFactory.decodeResource(context.getResources(), R.drawable.fotor_2023_2_21_15_1_56);


        surfaceHolder = getHolder();
        paint = new Paint();

        this.context = context;
        this.screenX = screenX;
        this.screenY = screenY;
        bugs = new ArrayList<>(bugCount);
        for(int i=0; i<bugCount; i++){
            bugs.add(new Bug(context, screenX, screenY, true));
        }
        this.setOnTouchListener(this::onTouch);
        gameThread = new Thread(this);

        gameThread.start();



    }

    @Override
    public void run() {
        while(gameRunning){
            try {
                update();
                draw();
                control();
                //System.out.println("Hi");
            }
            catch (Exception ex){

            }
        }
    }

    private void update() {

        if(bugs.size() < bugCount - 1){
            bugs.add(new Bug(context, screenX, screenY, true));
        }
    }
    private void draw() {
        if (surfaceHolder.getSurface().isValid()) {

            try {

                canvas = surfaceHolder.lockCanvas();

                canvas.drawColor(Color.BLACK);


                canvas.drawBitmap(
                        background,
                        00,
                        00,
                        paint
                );

                paint.setColor(Color.WHITE);


                for (Bug b : bugs) {

                    canvas.drawBitmap(
                            b.getBitmap(),
                            b.getX(),
                            b.getY(),
                            paint
                    );


                }


                surfaceHolder.unlockCanvasAndPost(canvas);
            }
            catch(Exception ex){

            }
            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }
    private  void control() {
        try{
            gameThread.sleep(17);
        }
        catch(Exception ex){

        }
    }

    public void pause() {
        //when the game is paused
        //setting the variable to false
        gameRunning = false;
        try {
            //stopping the thread
            gameThread.join();
        } catch (InterruptedException e) {
        }
    }

    public void resume() {
        //when the game is resumed
        //starting the thread again
        gameRunning = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void setRun(boolean value){
        this.gameRunning = value;
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        float x;
        float y;
        x = motionEvent.getX();
        y = motionEvent.getY();

        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                for(Bug b : bugs){
                    try {
                        if (b.getDetectCollision().contains((int) x, (int) y)) {
                            bugs.remove(b);
                            b.setLife(false);
                            break;
                        }
                    }
                    catch(Exception ex){

                    }
                }

        }
        return true;
    }
}
