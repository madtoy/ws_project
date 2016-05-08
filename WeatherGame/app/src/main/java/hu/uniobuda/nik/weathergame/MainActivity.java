package hu.uniobuda.nik.weathergame;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;



public class MainActivity extends AppCompatActivity {

    private AppCompatButton newGamebutton;
    private AppCompatButton optionsButton;
    private AppCompatButton highscoresButton;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // taskbar eltüntetése
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);


        newGamebutton = (AppCompatButton) findViewById(R.id.newGameButton);
        optionsButton = (AppCompatButton) findViewById(R.id.optionsButton);
        highscoresButton = (AppCompatButton) findViewById(R.id.highscoresButton);


        // font beállítása
        AssetManager assetman = getAssets();
        Typeface typeface = Typeface.createFromAsset(assetman,"fonts/planetkosmosfont.TTF");
        newGamebutton.setTypeface(typeface);
        optionsButton.setTypeface(typeface);
        highscoresButton.setTypeface(typeface);

        newGamebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MainActivity.this,
                        NewGameActivity.class);
                startActivity(myIntent);
            }
        });

        optionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MainActivity.this,
                        OptionsActivity.class);
                startActivity(myIntent);
            }
        });

        highscoresButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MainActivity.this,
                        HighscoresActivity.class);
                startActivity(myIntent);
            }
        });

    }


}
