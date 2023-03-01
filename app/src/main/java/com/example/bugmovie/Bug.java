package com.example.bugmovie;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;

import java.util.Random;

public class Bug implements Runnable,View.OnTouchListener{

    private Bitmap bitmap;


    private Thread bugThread = null;
    private int x;
    private int y;
    private int speed = 1;

    private int maxX;
    private int minX;
    private int type;
    private int HP;
    private int maxY;
    private int minY;
    private boolean isLife;

    private int vektor;

    Matrix matrix = new Matrix();
    private Rect detectCollision;

    public Bug(Context context, int screenX, int screenY, boolean isLife){
        Random generator = new Random();
        type = generator.nextInt(3);
        if(type == 0){
            HP = 1;
            bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.bug1);
        }
        if(type == 1){
            HP = 2;
            bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.bug2);
        }
        if(type == 2){
            HP = 3;
            bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.bug3);
        }



        vektor = 1 + generator.nextInt(2);
        System.out.println(vektor);


        this.maxX = screenX;
        this.maxY = screenY;
        minX = 0;
        minY = 600;




        speed = generator.nextInt(6) + 2;

        matrix.postScale(0.8f,0.8f);

        if(vektor == 1){
            matrix.postRotate(-90);

            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);

            matrix.postRotate(90);

            x = screenX;
            y = generator.nextInt(maxY) - bitmap.getHeight();
        }
        if(vektor == 2){
            matrix.postRotate(90);

            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);

            matrix.postRotate(-90);

            x = -100;
            y = generator.nextInt(maxY) - bitmap.getHeight();
        }




        this.isLife = isLife;
        detectCollision = new Rect(x, y, bitmap.getWidth(), bitmap.getHeight());

        bugThread = new Thread(this);
        bugThread.start();

    }

    @Override
    public void run() {
        while(isLife){
            update(1);
            control();
        }

    }

    private  void control() {
        try{
            bugThread.sleep(17);
        }
        catch(Exception ex){

        }
    }


    public void update(int playerSpeed) {
        //decreasing x coordinate so that enemy will move right to left
        if(vektor == 1){
            x -= (speed + playerSpeed);
            if (x < minX - bitmap.getWidth()) {
                //adding the enemy again to the right edge
                Random generator = new Random();
                speed = generator.nextInt(6)+ 5;
                x = maxX;
                y = generator.nextInt(maxY) - bitmap.getHeight();
            }
        }
        if(vektor == 2){
            x += (speed + playerSpeed);
            if (x > maxX + bitmap.getWidth()) {
                //adding the enemy again to the right edge
                Random generator = new Random();
                speed = generator.nextInt(6)+ 5;
                x = -100;
                y = generator.nextInt(maxY) - bitmap.getHeight();
            }
        }



        /*


         */

        detectCollision.left = x;
        detectCollision.top = y;
        detectCollision.right = x + bitmap.getWidth();
        detectCollision.bottom = y + bitmap.getHeight();
    }



    public void setX(int x){
        this.x = x;
    }

    public void setLife(boolean value) throws InterruptedException {
        this.bugThread.sleep(100);
        this.isLife = value;
    }

    public void setHP(int HP){
        this.HP = HP;
    }
    public int getHP(){
        return HP;
    }



    public Rect getDetectCollision() {
        return detectCollision;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getSpeed() {
        return speed;
    }

    public boolean getLife() {
        return isLife;
    }

    public int getType() {
        return type;
    }


    public void getTrauma() throws InterruptedException{
        matrix.postScale(0.97f,0.97f,bitmap.getWidth()/2,bitmap.getHeight()/2);
        bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }


    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return true;
    }
}
