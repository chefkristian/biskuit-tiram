package city.stage.com.bustelolet;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import java.util.Random;

/**
 * Created by indomegabyte on 06/03/17.
 */
public class Kids {
    private Context context;
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

    private int[] kidsimage = {
            R.drawable.kidsa,
            R.drawable.kidsa,
            R.drawable.kidsa,
            R.drawable.kidsb,
            R.drawable.kidsb,
            R.drawable.kidsb,
    };
    private int counterStep = 0;


    public Kids(Context context, int screenX, int screenY) {

        this.context = context;
        bitmap = BitmapFactory.decodeResource(context.getResources(),R.drawable.kidsb);

        maxX = screenX;
        maxY = 0;
        minX = 0;
        minY = 0 ;

        //generating a random coordinate to add enemy
        Random generator = new Random();
        speed = 8;
        x = screenX;
        y = 0;
//        y = screenY/8+generator.nextInt(screenY*7/8);
        detectCollision = new Rect(x, y, bitmap.getWidth(), bitmap.getHeight());
        counterStep = 0;
    }


    public void update(int playerSpeed) {
        //decreasing x coordinate so that enemy will move right to left
//
        counterStep++;
//        if (counterStep % kidsimage.length == 0) {
//            counterStep = 0;
//            bitmap = BitmapFactory.decodeResource(context.getResources(),kidsimage[counterStep % kidsimage.length]);
//        }
        if(counterStep == 1){
            bitmap = BitmapFactory.decodeResource(context.getResources(), kidsimage[0]);
        }
        if(counterStep == 2){
            bitmap = BitmapFactory.decodeResource(context.getResources(), kidsimage[1]);
        }
        if(counterStep == 3){
            bitmap = BitmapFactory.decodeResource(context.getResources(), kidsimage[2]);
        }
        if(counterStep == 4){
            bitmap = BitmapFactory.decodeResource(context.getResources(), kidsimage[3]);
        }
        if(counterStep == 5){
            bitmap = BitmapFactory.decodeResource(context.getResources(), kidsimage[4]);
        }
        if(counterStep == 6){
            bitmap = BitmapFactory.decodeResource(context.getResources(), kidsimage[5]);
            counterStep = 0;
        }


//        else {
//            bitmap = BitmapFactory.decodeResource(context.getResources(), kidsimage[1]);
//        }
        x -= playerSpeed;
        x -= speed;
        //if the enemy reaches the left edge
        if (x < minX - bitmap.getWidth()) {
            //adding the enemy again to the right edge
            Random generator = new Random();
            speed = 5 + generator.nextInt(10);
            x = maxX;
            y = 0;


//     y = maxY/8+generator.nextInt(maxY*7/8);
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
