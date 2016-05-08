package hu.uniobuda.nik.weathergame;

import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.Timer;
import java.util.concurrent.RunnableFuture;


public class NewGameActivity extends AppCompatActivity {

    private AppCompatButton gameOverButton;
    private AppCompatButton moveLeftButton;
    private AppCompatButton moveRightButton;
    private AppCompatImageView character;
    private AppCompatImageView blocker1,blocker2,blocker3, blocker4, blocker5;
    private AppCompatTextView scoreTextView;
    private int mapHeight = 600; // ez így pontatlan, getwidth/height-el kell de az oncreate-be nem megy, mert nincs betöltődve még a layout... onstart?
    private int mapWidth = 1100;
    private long score = 0;
    private int dieCounter = 0;
    static private Random rand = new Random();
    int actualPosX = 10;
    // figyelni arra, hogy a lépték befolyással van a falhoz ütközésre. tehát ha a lépték 10,
    // és a kezdőpoz. 10 akkor balra lépve 0-ba vagyunk. ami jó. egyéb esetben ha a lépték 1
    // de 5-ről kezdünk akkor balra lépve -5-be vagyunk és nem fogja ütközésnek érzékelni
    private DBHandler dbHandler;
    private String[] optionResult;
    private Date date = new Date();
    CharSequence s  = DateFormat.format("yyyy.MM.dd HH:mm:ss", date.getTime());
    Handler handler;


    @Override
    protected void onStart() {
        super.onStart();

        if (optionResult != null) {

            // IN-GAME CALCULATIONS
            ScoreCounter();
            MoveBlock(blocker1, 3);
            MoveBlock(blocker2, 20);
            MoveBlock(blocker3, 10);
            MoveBlock(blocker4, 30);
            MoveBlock(blocker5, 3);

            GetInTouchWithBlock(blocker1, 10);
            GetInTouchWithBlock(blocker2, 10);
            GetInTouchWithBlock(blocker3, 10);
            GetInTouchWithBlock(blocker4, 10);
            GetInTouchWithBlock(blocker5, 10);
        }
        else {
            Toast.makeText(getBaseContext(), "Please add your name in the Options menu!", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // taskbar eltüntetése
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        setContentView(R.layout.newgame_activity);

        gameOverButton = (AppCompatButton) findViewById(R.id.gameOverButton);
        moveLeftButton = (AppCompatButton) findViewById(R.id.moveLeftButton);
        moveRightButton = (AppCompatButton) findViewById(R.id.moveRightButton);
        character = (AppCompatImageView) findViewById(R.id.character);
        blocker1 = (AppCompatImageView) findViewById(R.id.blocker1);
        blocker2 = (AppCompatImageView) findViewById(R.id.blocker2);
        blocker3 = (AppCompatImageView) findViewById(R.id.blocker3);
        blocker4 = (AppCompatImageView) findViewById(R.id.blocker4);
        blocker5 = (AppCompatImageView) findViewById(R.id.blocker5);
        scoreTextView = (AppCompatTextView) findViewById(R.id.score);
        handler = new Handler();
        dbHandler = new DBHandler(this);
        optionResult = dbHandler.LoadOption(); // optionResult[0]: játékos neve, optionResult[1] a kiválasztott karakter ID-ja

        // SETTING THE CHARACTER
        if (optionResult == null)
            character.setImageResource(R.drawable.shit);
        else
        {
            String characterName = getResources().getResourceEntryName(Integer.parseInt(optionResult[1]));
            int resID = getResources().getIdentifier(characterName,"drawable",getPackageName());
            character.setImageResource(resID);
        }




        // END OF GAME BUTTON
        gameOverButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GameOver();
            }
        });

        // MOVE LEFT BUTTON
        moveLeftButton.setOnTouchListener(new View.OnTouchListener() {
                                              @Override
                                              public boolean onTouch(View v, MotionEvent event) {

                                                  MoveCharacter(false);
                                                  return true;
                                              }
                                          }
        );

        // MOVE RIGHT BUTTON
        moveRightButton.setOnTouchListener(new View.OnTouchListener() {
                                               @Override
                                               public boolean onTouch(View v, MotionEvent event) {

                                                   MoveCharacter(true);

                                                   return true;
                                               }
                                           }
        );



    }

    private void MoveCharacter(boolean toTheRight)
    {
        if(EndOfArena() == 0)
        {
            MakeStep(toTheRight);
        }
        else if(EndOfArena() == 1 && toTheRight)
        {
            MakeStep(toTheRight);
        }
        else if(EndOfArena() == 1 && !toTheRight)
        {
             // do nothing
        }
        else if(EndOfArena() == 2 && toTheRight)
        {
            // do nothing
        }
        else if (EndOfArena() == 2 && !toTheRight)
        {
            MakeStep(toTheRight);
        }
    }

    private void MakeStep(boolean toTheRight)
    {
        AppCompatImageView character = (AppCompatImageView) findViewById(R.id.character);
        if(toTheRight)
            character.setX(actualPosX+10);
        else
            character.setX(actualPosX-10);
        actualPosX = (int) character.getX();
        if (actualPosX / 500 > score)
            score = actualPosX / 500;
    }

    private int EndOfArena()
    {
        AppCompatImageView character = (AppCompatImageView) findViewById(R.id.character);
        AppCompatImageView floor = (AppCompatImageView) findViewById(R.id.floor);
        int corrigation = 100; // 100 volt az én készülékemen pont az a kis bar szélessége oldalt/alul...
        if(character.getX() == floor.getX())
            return 1; // bal oldalt van
        else if(character.getX() > floor.getWidth()-corrigation)
            return 2; // jobb oldalt van
        else
            return  0;
    }

    private void MoveBlock(final AppCompatImageView blocker, final int speed)
    {

        final Runnable blockMover = new Runnable() {
            @Override
            public void run() {

                blocker.setY(blocker.getY() + 10);
                if (blocker.getY() > mapHeight) {
                    // repositioning
                    blocker.setX(rand.nextInt(mapWidth) + speed * 2);
                    blocker.setY(-100);
                }
                handler.postDelayed(this, speed);
            }
        };
        handler.postDelayed(blockMover,speed);
    }

    private void ScoreCounter()
    {
        final Runnable scoreCounter = new Runnable() {
            @Override
            public void run() {
                score = score + 1;
                String scoreToShow = String.valueOf(score);
                scoreTextView.setText(scoreToShow);
                handler.postDelayed(this , 3000);
            }
        };
        handler.postDelayed(scoreCounter,3000);
    }

    private void GetInTouchWithBlock(final AppCompatImageView blocker, final int speed)
    {
        final Runnable blockMover = new Runnable() {
            @Override
            public void run() {

                if(rectangle_collision(
                        (int)blocker.getX(),
                        (int)blocker.getY(),
                        (int)blocker.getWidth(),
                        (int)blocker.getHeight(),
                        (int)character.getX(),
                        (int)character.getY(),
                        (int)character.getWidth(),
                        (int)character.getHeight()))
                    GameOver();

                handler.postDelayed(this, speed);
            }
        };
        handler.postDelayed(blockMover,speed);
        /*RectF blockingObject = new RectF(); blockingObject.set(blocker.getLeft(), blocker.getTop(), blocker.getRight(), blocker.getBottom());
        RectF characterObject = new RectF(character.getLeft() , character.getTop(), character.getRight(), character.getBottom());

        RectF r1 = new RectF(10,10,10,10);
        RectF r2 = new RectF(10,10,10,10);

        if(RectF.intersects(r1,r2))
            return true;
        else
            return false;*/

        /*if(rectangle_collision(10,10,10,10,30,30,30,30))
            return true;
        else return false;*/

    }

    private boolean rectangle_collision(int x_1, int y_1, int width_1, int height_1, int x_2, int y_2, int width_2, int height_2)
    {
        return !(x_1 > x_2+width_2 || x_1+width_1 < x_2 || y_1 > y_2+height_2 || y_1+height_1 < y_2);
    }

    private void GameOver()
    {

        if (optionResult != null)
        {
            if(dieCounter < 1)
            {
                dbHandler.AddUser(optionResult[0], String.valueOf(score), String.valueOf(s));
            }
        }
        else
            Toast.makeText(getBaseContext(), "Please add your name in the Options menu!", Toast.LENGTH_SHORT).show();
        //dbHandler.TruncateForTesting();

        finish();
    }
}
