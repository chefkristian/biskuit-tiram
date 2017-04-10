package city.stage.com.bustelolet;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import java.util.Random;

/**
 * Created by indomegabyte on 06/03/17.
 */
public class Trompet {
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

    public Trompet(Context context, int screenX, int screenY) {
        bitmap = BitmapFactory.decodeResource(context.getResources(),R.drawable.trumpet);

        maxX = screenX;
        maxY = screenY - bitmap.getHeight();
        minX = 0;
        minY = 300 ;

        //generating a random coordinate to add enemy
        Random generator = new Random();
        speed = 10;
        x = screenX;
        y =300;
//        y = generator.nextInt(screenY - bitmap.getHeight());
// y = screenY/8+generator.nextInt(screenY*7/8);
        detectCollision = new Rect(x, y, bitmap.getWidth(), bitmap.getHeight());
    }
    public void update(int playerSpeed) {
        //decreasing x coordinate so that enemy will move right to left
        x -= playerSpeed;
        x -= speed;
        //if the enemy reaches the left edge
        if (x < minX - bitmap.getWidth()) {
            //adding the enemy again to the right edge
            Random generator = new Random();
            speed = 5 + generator.nextInt(10);
            x = maxX;
            y = minY + generator.nextInt((maxY-minY)+1);


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
