package city.stage.com.bustelolet;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

/**
 * Created by indomegabyte on 01/03/17.
 */
public class GameActivity extends AppCompatActivity {
    //declaring gameview
    private GameView gameView;
//    FrameLayout game;// Sort of "holder" for everything we are placing
//    RelativeLayout GameButtons;//Holder for the buttons

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setting the orientation to landscape
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        //Getting display object
        Display display = getWindowManager().getDefaultDisplay();

        //Getting the screen resolution into point object
        Point size = new Point();
        display.getSize(size);

        //Initializing game view object
        //this time we are also passing the screen size to the GameView constructor
        gameView = new GameView(this, size.x, size.y);



        //////////add button di surfaceview///////////////////
//        game = new FrameLayout(this);
//        GameButtons = new RelativeLayout(this);
//
//
//        ImageButton butOne = new Button(this);
//        butOne.setText("button");
//
//        RelativeLayout.LayoutParams b1 = new RelativeLayout.LayoutParams(
//                RelativeLayout.LayoutParams.WRAP_CONTENT,
//                                      RelativeLayout.LayoutParams.WRAP_CONTENT);
//        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
//                                    RelativeLayout.LayoutParams.MATCH_PARENT,
//                                   RelativeLayout.LayoutParams.MATCH_PARENT);
//                 GameButtons.setLayoutParams(params);
//
//        GameButtons.addView(butOne);
//         b1.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
//         b1.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
//         butOne.setLayoutParams(b1);
//
//
//
//        game.addView(gameView);
//        game.addView(GameButtons);
//        setContentView(game);




        //adding it to contentview
        setContentView(gameView);
    }

    //pausing the game when activity is paused
    @Override
    protected void onPause() {
        super.onPause();
        gameView.pause();
    }

    //running the game when activity is resumed
    @Override
    protected void onResume() {
        super.onResume();
        gameView.resume();
    }

    @Override
    public void onBackPressed() {
        gameView.pause();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        GameView.stopMusic();
//                        Intent intent = new Intent(GameActivity.this, MainActivity.class);
//                        startActivity(intent);
//                        finish();
                        Intent startMain = new Intent(Intent.ACTION_MAIN);
                        startMain.addCategory(Intent.CATEGORY_HOME);
                        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(startMain);
                        finish();

                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
//                        gameView.resumeMusic();
                        gameView.resume();

                    }
                });
        AlertDialog alert = builder.create();
        alert.show();

    }

    @Override
    protected void onDestroy() {
//        gameOnSound.stop();
        super.onDestroy();
    }

}
