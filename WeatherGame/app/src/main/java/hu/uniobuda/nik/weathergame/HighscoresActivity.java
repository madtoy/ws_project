package hu.uniobuda.nik.weathergame;

import android.content.res.AssetManager;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HighscoresActivity extends AppCompatActivity {

    private ListView listView;
    private NewsAdapter adapter;
    private DBHandler dbHandler;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // taskbar eltüntetése
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.highscores_activity);
        dbHandler = new DBHandler(this);

        ArrayList<News> items = getHighscoreList(dbHandler.LoadUser());

        if (!items.isEmpty()) {
            listView = (ListView) findViewById(R.id.listview);
            adapter = new NewsAdapter(items);
            listView.setAdapter(adapter);
        }
        else {
            Toast.makeText(this, "There is no saved highscore to show!", Toast.LENGTH_LONG).show();
        }
    }

    private ArrayList<News> getHighscoreList(Cursor cursor) {
        ArrayList<News> items = new ArrayList<News>();
        while(!cursor.isAfterLast()) {
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String point = cursor.getString(cursor.getColumnIndex("point"));
            String date = cursor.getString(cursor.getColumnIndex("date"));
            items.add(new News(name, point + " points " + date));
            cursor.moveToNext();
        }
        return items;
    }

}
