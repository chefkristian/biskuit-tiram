package city.stage.com.bustelolet;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import java.util.Random;

/**
 * Created by indomegabyte on 02/03/17.
 */
public class CyanCar {
    Context context;
    //bitmap for the enemy
    //we have already pasted the bitmap in the drawable folder
    private Bitmap bitmap;

    //x and y coordinates
    private int x;
    private int y;

    //enemy speed
    private int speed = 1;

    //min and max coordinates to keep the enemy inside the screen
    private int maxX;
    private int minX;

    private int maxY;
    private int minY;
    //creating a rect object
    private Rect detectCollision;
    private int [] blueimage ={
            R.drawable.bluea,
            R.drawable.bluea,
            R.drawable.bluea,
            R.drawable.blueb,
            R.drawable.blueb,
            R.drawable.blueb
    };
    int counterblue;

    public CyanCar(Context context, int screenX, int screenY) {
        this.context = context;
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.bluea);
        counterblue = 0;

        //initializing min and max coordinates
        maxX = screenX;
        maxY = screenY - bitmap.getHeight();
        minX = 0;
        minY = 200;

        //generating a random coordinate to add enemy
        Random generator = new Random();
        speed = 10;
        x = screenX;
        y = screenY/8+generator.nextInt(screenY*7/8);
        detectCollision = new Rect(x, y, bitmap.getWidth(), bitmap.getHeight());

    }

    public void update(int playerSpeed) {
        counterblue++;
        if(counterblue == 1){
            bitmap = BitmapFactory.decodeResource(context.getResources(), blueimage[0]);
        }
        if(counterblue == 2){
            bitmap = BitmapFactory.decodeResource(context.getResources(), blueimage[1]);
        }
        if(counterblue == 3){
            bitmap = BitmapFactory.decodeResource(context.getResources(), blueimage[2]);
        }
        if(counterblue == 4){
            bitmap = BitmapFactory.decodeResource(context.getResources(), blueimage[3]);
        }
        if(counterblue == 5){
            bitmap = BitmapFactory.decodeResource(context.getResources(), blueimage[4]);
        }
        if(counterblue == 6){
            bitmap = BitmapFactory.decodeResource(context.getResources(), blueimage[5]);
            counterblue = 0;
        }
        //decreasing x coordinate so that enemy will move right to left
        x -= playerSpeed;
        x -= speed;
        //if the enemy reaches the left edge
        if (x < minX - bitmap.getWidth()) {
            //adding the enemy again to the right edge
            Random generator = new Random();
            speed = 5 + generator.nextInt(10);
            x = maxX;
            y = maxY/8+generator.nextInt(maxY*7/8);
        }
        //Adding the top, left, bottom and right to the rect object
        detectCollision.left = x;
        detectCollision.top = y;
        detectCollision.right = x + bitmap.getWidth();
        detectCollision.bottom = y + bitmap.getHeight();

    }

    public void setX(int x) {
        this.x = x;
    }

    public Rect getDetectCollision() {
        return detectCollision;
    }

    //getters
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

    public void setSpeed(int speed) {
        this.speed = speed;
    }
}
