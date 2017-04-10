package city.stage.com.bustelolet;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * Created by indomegabyte on 01/03/17.
 */
public class GameView extends SurfaceView implements Runnable {

    //boolean variable to track if the game is playing or not
    volatile boolean playing;
    //the game thread
    private Thread gameThread = null;
    //adding the player to this class
    private Player player;

    //These objects will be used for drawing
    private Paint paint;
    private Canvas canvas;
    private SurfaceHolder surfaceHolder;
    int garis_tengah;
    private int screenX, screenY;

    private Garis[] garis;
    private int garisCount = 3;

    private YellowCar[]yellow;
    private int yellowCount = 1;
    private RedCar[]red;
    private int redCount = 1;
    private CyanCar[]cyan;
    private int cyanCount = 1;

    private Kids kids;
    private Trompet trompet;

    int countNabrak;
    int countTelolet;

    static MediaPlayer backGround;
    MediaPlayer telolet;

    private boolean isGameOver;
    private final static int MAX_VOLUME = 100;

    int timer;
    Context context;
    SharedPreferences sharedPreferences;
    ArrayList hiScore , hiScore2;
    Integer highScore[] = new Integer [6];



    public GameView(Context context, int screenX, int screenY) {
        super(context);
        this.context = context;
        this.screenX = screenX;
        this.screenY = screenY;
        //initializing player object
        player = new Player(context, screenX, screenY);

        //initializing drawing objects
        surfaceHolder = getHolder();
        paint = new Paint();
        garis_tengah = screenY/2;

        isGameOver = false;

        garis = new Garis[garisCount];
        for (int i = 0; i<garisCount; i++){
            garis[i]= new Garis(context,screenX,screenY);
        }

        yellow = new YellowCar[yellowCount];
        for (int i=0; i <yellowCount;i ++){
            yellow[i] = new YellowCar(context, screenX, screenY);
        }
        red = new RedCar[redCount];
        for (int i=0; i <redCount;i ++){
            red[i] = new RedCar(context, screenX, screenY);
        }
        cyan = new CyanCar[cyanCount];
        for (int i=0; i <cyanCount;i ++){
            cyan[i] = new CyanCar(context, screenX, screenY);
        }
        kids = new Kids(context, screenX, screenY);
        trompet = new Trompet(context, screenX, screenY);

        countNabrak = 0;
        countTelolet = 0;
        timer = 1000;

        if (backGround==null)
        backGround = MediaPlayer.create(context,R.raw.bgnews);
        backGround.setLooping(true);
        backGround.start();

        telolet = MediaPlayer.create(context,R.raw.telolet);
        final float volume = (float) (1 - (Math.log(MAX_VOLUME - 70) / Math.log(MAX_VOLUME)));
        telolet.setVolume(volume, volume);

        hiScore = new ArrayList();
        hiScore2 = new ArrayList();

        sharedPreferences = context.getSharedPreferences("SHAR_PREF_NAME", Context.MODE_PRIVATE);
        //initializing the array high scores with the previous values
        highScore[0] = sharedPreferences.getInt("score1", 0);
        highScore[1] = sharedPreferences.getInt("score2", 0);
        highScore[2] = sharedPreferences.getInt("score3", 0);
        highScore[3] = sharedPreferences.getInt("score4", 0);
        highScore[4] = sharedPreferences.getInt("score5",0);
        highScore[5] = 0;

    }

    @Override
    public void run() {
        while (playing) {
            //to update the frame
            update();
            //to draw the frame
            draw();
            //to control
            control();
        }
    }

    private void update() {
        timer--;

        //updating player position
        player.update();
        //Updating the stars with player speed
        for (int i = 0 ;i <garisCount; i++) {
            garis[i].update(player.getSpeed());
        }

        for (int i=0; i < yellowCount; i++){
            yellow[i].update(player.getSpeed());

            if(Rect.intersects(yellow[i].getDetectCollision(),red[i].getDetectCollision())){
//                red[i].setX(screenX + 120);
                red[i].setSpeed(2);
            }
            if (Rect.intersects(yellow[i].getDetectCollision(),cyan[i].getDetectCollision())){
//                cyan[i].setX(screenX+120);
                cyan[i].setSpeed(0);
            }
            if (Rect.intersects(yellow[i].getDetectCollision(),player.getDetectCollison())){
                countNabrak= countNabrak+1;
                timer = timer -1;
//                countTelolet = 0;
//                if (countTelolet > 0)
//                    countTelolet = countTelolet -1;
//                if (countTelolet <0 ){
//                    countTelolet= 0;
//                }
//                player.stopBoosting();

            }

        }
        for (int i=0; i < redCount; i++) {
            red[i].update(player.getSpeed());
            if (Rect.intersects(red[i].getDetectCollision(), cyan[i].getDetectCollision())) {
//                cyan[i].setX(screenX+500);
                cyan[i].setSpeed(-1);
            }
            if (Rect.intersects(red[i].getDetectCollision(), yellow[i].getDetectCollision())) {
//                cyan[i].setX(screenX+500);
                red[i].setSpeed(2);
                }
            if (Rect.intersects(red[i].getDetectCollision(),player.getDetectCollison())){
                countNabrak= countNabrak+1;
                timer = timer -1;
//                countTelolet = 0;
//                if (countTelolet > 0)
//                    countTelolet = countTelolet -1;
//                if (countTelolet <0 ){
//                    countTelolet= 0;
//                }
//                player.setBoosting();
            }
        }
        for (int i=0; i < cyanCount; i++){
            cyan[i].update(player.getSpeed());
            if (Rect.intersects(cyan[i].getDetectCollision(),red[i].getDetectCollision())){
                cyan[i].setSpeed(-1);
            }
            if (Rect.intersects(cyan[i].getDetectCollision(),yellow[i].getDetectCollision())){
                cyan[i].setSpeed(0);
            }
            if (Rect.intersects(cyan[i].getDetectCollision(),player.getDetectCollison())){
                countNabrak= countNabrak+1;
                timer = timer -1;
//                if (countTelolet > 0)
//                countTelolet = countTelolet -1;
//                if (countTelolet <0 ){
//                    countTelolet= 0;
//                }
//                player.stopBoosting();
            }
        }

        kids.update(player.getSpeed());

        trompet.update(player.getSpeed());
        if (Rect.intersects(trompet.getDetectCollision(),player.getDetectCollison())){
            telolet.start();
            countTelolet = countTelolet + 10;
            trompet.setX(-400);
            timer = timer + 50;
//            player.setBoosting();
        }

        if(timer == 0 || countNabrak >= 200){
            isGameOver = true;
            playing = false;

            if(backGround!=null)
            {
                if(backGround.isPlaying()){
                    backGround.reset();//It requires again setDataSource for player object.
                    backGround.stop();// Stop it
                    backGround.release();// Release it
                    backGround=null; // Initilize to null so it can be used later
                }
            }

            highScore[5] = countTelolet;

            Arrays.sort(highScore, Collections.reverseOrder());

            SharedPreferences.Editor e = sharedPreferences.edit();
            for (int j = 0; j < 5; j++) {
                int k = j + 1;
                e.putInt("score" + k, highScore[j]);

            }
            e.apply();

        }
    }

    private void draw() {
        //checking if surface is valid
        if (surfaceHolder.getSurface().isValid()) {
            //locking the canvas
            canvas = surfaceHolder.lockCanvas();
            //drawing a background color for canvas
            canvas.drawColor(Color.parseColor("#A6AAAB"));



            paint.setColor(Color.parseColor("#9eeaed"));
            paint.setStyle(Paint.Style.FILL);
            canvas.drawRect(0,0,screenX,screenY-(screenY-200),paint);


            paint.setTextSize(55);
            paint.setColor(Color.BLACK);
            canvas.drawText("Score : " + String.valueOf(countTelolet), screenX-350, 120, paint);

            paint.setTextSize(55);
            paint.setColor(Color.BLACK);
            canvas.drawText("Timer : "+String.valueOf(timer),screenX-350,60,paint);

            paint.setTextSize(40);
            paint.setColor(Color.BLACK);
            canvas.drawText("Bus Damage : "+String.valueOf(countNabrak/2)+" %",20,60,paint);


            for (int i =0; i<garisCount; i++){
                canvas.drawBitmap(
                        garis[i].getBitmap(),
                        garis[i].getX(),
                        garis[i].getY(),paint
                );
            }
            canvas.drawBitmap(
                    kids.getBitmap(),
                    kids.getX(),
                    kids.getY(),
                    paint
            );

            canvas.drawBitmap(
                    trompet.getBitmap(),
                    trompet.getX(),
                    trompet.getY(),
                    paint
            );
//            paint.setARGB(255, 0, 0,0);
//            paint.setStyle(Paint.Style.STROKE);
//            paint.setPathEffect(new DashPathEffect(new float[] {10,20}, 0));
//            canvas.drawLine(0,200,screenX,200,paint);
            //Drawing the player
            canvas.drawBitmap(
                    player.getBitmap(),
                    player.getX(),
                    player.getY(),
                    paint);

            for (int i = 0; i <yellowCount; i ++){
                canvas.drawBitmap(
                        yellow[i].getBitmap(),
                        yellow[i].getX(),
                        yellow[i].getY(),
                        paint
                );
            }
            for (int i = 0; i <redCount; i ++){
                canvas.drawBitmap(
                        red[i].getBitmap(),
                        red[i].getX(),
                        red[i].getY(),
                        paint
                );
            }
            for (int i = 0; i <cyanCount; i ++){
                canvas.drawBitmap(
                        cyan[i].getBitmap(),
                        cyan[i].getX(),
                        cyan[i].getY(),
                        paint
                );
            }
            if(isGameOver) {
                paint.setTextSize(80);
                paint.setTextAlign(Paint.Align.CENTER);
                int yPos = (int) ((canvas.getHeight() / 2) - ((paint.descent() + paint.ascent()) / 2));
                canvas.drawText("Game Over", canvas.getWidth() / 2, yPos, paint);
                canvas.drawText("Your Score :" + countTelolet, canvas.getWidth() / 2, yPos - 80, paint);
            }
            //Unlocking the canvas
            surfaceHolder.unlockCanvasAndPost(canvas);
        }

    }

    private void control() {
        try {
            gameThread.sleep(17);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void pause() {
        //when the game is paused
        //setting the variable to false
        playing = false;
        try {
            //stopping the thread
            gameThread.join();
        } catch (InterruptedException e) {
        }
    }

    public void resume() {
        //when the game is resumed
        //starting the thread again
        playing = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {

        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_UP:
//                player.setBoosting();
                player.GRAVITY = 0;

                break;
            case MotionEvent.ACTION_DOWN:
                if(motionEvent.getX()<screenX/2) {
                    player.GRAVITY =-10;
                    if (motionEvent.getY() < screenY / 2 ) {
                        player.setBoosting();
                        player.setNaik();
                    }
                    if (motionEvent.getY() > screenY/ 2) {
                        player.setBoosting();
                        player.stopNaik();
                    }
                }
                if (motionEvent.getX()>screenX/2){
                    player.setBoosting();
                }
                break;
        }

        if(isGameOver){
            if(motionEvent.getAction()==MotionEvent.ACTION_DOWN){
                context.startActivity(new Intent(context, MainActivity.class));
                ((Activity) context).finish();
//                ((Activity) context).finish();
            }
        }
        return true;
    }

    public static void stopMusic(){

        if(backGround!=null)
        {
            if(backGround.isPlaying()){
                backGround.reset();//It requires again setDataSource for player object.
                backGround.stop();// Stop it
                backGround.release();// Release it
                backGround=null; // Initilize to null so it can be used later
            }
        }

    }
}
