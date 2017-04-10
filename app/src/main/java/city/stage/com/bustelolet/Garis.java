package city.stage.com.bustelolet;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.Random;

/**
 * Created by indomegabyte on 02/03/17.
 */
public class Garis {
    private Bitmap bitmap;
    private int x;
    private int y;
    private int speed;

    private int maxX;
    private int maxY;
    private int minX;
    private int minY;
    int jarak = 500;


    public Garis(Context context, int screenX, int screenY) {
        maxX = screenX;
        maxY = screenY;
        minX = 0;
        minY = 0;

        speed = 10;
        bitmap = BitmapFactory.decodeResource(context.getResources(),R.drawable.garis);

        x = screenX;
        y = screenY / 2;
    }

    public void update(int playerSpeed) {
        //animating the star horizontally left side
        //by decreasing x coordinate with player speed
        x -= playerSpeed;
        x -= speed;
        //if the star reached the left edge of the screen
        if (x < 0-bitmap.getWidth()) {
            //again starting the star from right edge
            Random generator = new Random();
            //this will give a infinite scrolling background effect
            x = maxX;
            y = maxY / 2;
//            Random generator = new Random();
//            y = generator.nextInt(maxY);
            speed = 10;
        }
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public float getStarWidth() {
        //Making the star width random so that
        //it will give a real look
        int minX = 0;
        int maxX = maxY;
//        float maxX = 4.0f;
//        Random rand = new Random();
        float finalX = maxX;
        return finalX;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
