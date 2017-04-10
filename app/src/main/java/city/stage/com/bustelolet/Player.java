package city.stage.com.bustelolet;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

/**
 * Created by indomegabyte on 01/03/17.
 */
public class Player {
    Context context;
    //Bitmap to get character from image
    private Bitmap bitmap;
    //coordinates
    private int x;
    private int y;
    //motion speed of the character
    private int speed = 0;

    //boolean variable to track the ship is boosting or not
    private boolean boosting;

    //Gravity Value to add gravity effect on the ship
    public int GRAVITY = -10;

    //Controlling Y coordinate so that ship won't go outside the screen
    private int maxY;
    private int minY;

    //Limit the bounds of the ship's speed
    private final int MIN_SPEED = 1;
    private final int MAX_SPEED = 20;

    private boolean naik;
    private Rect detectCollison;

    private int [] busimage ={
            R.drawable.busa,
            R.drawable.busa,
            R.drawable.busa,
            R.drawable.busb,
            R.drawable.busb,
            R.drawable.busb
    };
    int counterBus = 0;
    //constructor

    public Player(Context context, int screenX, int screenY) {
        this.context = context;
        x = 75;
        y = screenY/2;
        speed = 1;
        //Getting bitmap from drawable resource
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.busa);

        //calculating maxY
        maxY = screenY - bitmap.getHeight();
//        maxY = screenY * 3/4 - bitmap.getHeight();

        //top edge's y point is 0 so min y will always be zero
        minY = 150;
        //setting the boosting value to false initially
        boosting = true;
        naik = false;
        detectCollison = new Rect(x,y,bitmap.getWidth(),bitmap.getHeight());

        counterBus = 0;
    }

    public  void setNaik(){
        naik = true;
    }
    public  void stopNaik(){
        naik = false;
    }
    //setting boosting true
    public void setBoosting() {
        boosting = true;
    }

    //setting boosting false
    public void stopBoosting() {
        boosting = false;
    }

    //Method to update coordinate of character
    public void update(){
//
        counterBus++;
        if(counterBus == 1){
            bitmap = BitmapFactory.decodeResource(context.getResources(), busimage[0]);
        }
        if(counterBus == 2){
            bitmap = BitmapFactory.decodeResource(context.getResources(), busimage[1]);
        }
        if(counterBus == 3){
            bitmap = BitmapFactory.decodeResource(context.getResources(), busimage[2]);
        }
        if(counterBus == 4){
            bitmap = BitmapFactory.decodeResource(context.getResources(), busimage[3]);
        }
        if(counterBus == 5){
            bitmap = BitmapFactory.decodeResource(context.getResources(), busimage[4]);
        }
        if(counterBus == 6){
            bitmap = BitmapFactory.decodeResource(context.getResources(), busimage[5]);
            counterBus = 0;
        }
        //updating x coordinate
        //if the ship is boosting
        if (boosting) {
            //speeding up the ship
            speed += 1;
        } else {
            //slowing down if not boosting
            speed -= 3;
        }
        //controlling the top speed
        if (speed > MAX_SPEED) {
            speed = MAX_SPEED;
        }
        //if the speed is less than min speed
        //controlling it so that it won't stop completely
        if (speed < MIN_SPEED) {
            speed = MIN_SPEED;
        }

        //moving the ship down
//        y -= speed + GRAVITY;

        y = y + 0;

        if (naik){

            y = y + GRAVITY;
//            y += speed + GRAVITY;
        }else {
//            y = y +1;
            y = y - GRAVITY ;
//            y -= speed + GRAVITY;
        }



        //but controlling it also so that it won't go off the screen
        if (y < minY) {
            y = minY;
        }
        if (y > maxY) {
            y = maxY;
        }

        detectCollison.left=x;
        detectCollison.top=y;
        detectCollison.right=x+bitmap.getWidth();
        detectCollison.bottom=y+bitmap.getHeight();

    }

    public Rect getDetectCollison() {
        return detectCollison;
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
}
