package com.example.bugmovie;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;

import androidx.fragment.app.Fragment;

import java.util.Random;

public class Bug implements Runnable,View.OnTouchListener{

    private Bitmap bitmap;


    private Thread bugThread = null;
    private int x;
    private int y;
    private int speed = 1;

    private int maxX;
    private int minX;

    private int maxY;
    private int minY;
    private boolean isLife;

    private Rect detectCollision;

    public Bug(Context context, int screenX, int screenY, boolean isLife){
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.bug1);

        Matrix matrix = new Matrix();
        matrix.postRotate(-90);

        bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);

        maxX = screenX;
        maxY = screenY-300;
        minX = 0;
        minY = 600;
        Random generator = new Random();
        speed = generator.nextInt(6) + 5;
        x = screenX;
        
        y = generator.nextInt(maxY) - bitmap.getHeight()*2;
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
        x -= playerSpeed;
        x -= speed;
        //if the enemy reaches the left edge
        if (x < minX - bitmap.getWidth()) {
            //adding the enemy again to the right edge
            Random generator = new Random();
            speed = generator.nextInt(6)+ 5;
            x = maxX;
            y = generator.nextInt(maxY) - bitmap.getHeight();
        }

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


    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return true;
    }
}
