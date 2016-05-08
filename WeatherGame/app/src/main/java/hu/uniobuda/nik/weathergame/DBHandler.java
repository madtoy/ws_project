package hu.uniobuda.nik.weathergame;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;


public class DBHandler {

    public final static String DB_NAME = "WG_highscores";
    public final static int DB_VERSION = 2;

    private DBOpenHelper dbHelper;
    private Context context;

    public DBHandler(Context context) {
        this.context = context;
        this.dbHelper = new DBOpenHelper(context);
    }

    public long AddUser(String name, String point, String date){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("name", name);
        cv.put("point", point);
        cv.put("date", date);
        long id = db.insert("users", null, cv);
        db.close();
        return 1;
    }

    public long AddOption(String name, String characterValue) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        ContentValues cv = new ContentValues();
        Cursor cursor = db.query("options", null, null, null, null, null, null);
        cv.put("name", name);
        cv.put("character", characterValue);
        if (!cursor.moveToFirst())
            db.insert("options", null, cv);
        else
            db.update("options", cv, null, null);
        db.close();
        return 1;
    }

    public Cursor LoadUser() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query("users", null, null, null, null, null, null);
        cursor.moveToFirst();
        db.close();
        return  cursor;
    }
    public String[] LoadOption() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query("options", null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            String[] options = {cursor.getString(cursor.getColumnIndex("name")), cursor.getString(cursor.getColumnIndex("character"))};
            db.close();
            return options;
        }
        else {
            return null;
        }
    }

    public void TruncateForTesting() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        //db.execSQL("DELETE FROM users");
        //db.execSQL("DELETE FROM options");
        db.execSQL("DROP TABLE IF EXISTS users");
        db.execSQL("DROP TABLE IF EXISTS options");
        dbHelper.onCreate(db);
    }

    public class DBOpenHelper extends SQLiteOpenHelper{

        public DBOpenHelper(Context context) {
            super(context, DB_NAME, null, DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE users(" +
                    "_id    INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "name   VARCHAR(255)," +
                    "point  VARCHAR(255)," +
                    "date   VARCHAR(255) UNIQUE" +
                    ")");

            db.execSQL("CREATE TABLE options(" +
                    "_id    INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "name   VARCHAR(255)," +
                    "character   VARCHAR(255)" +
                    ")");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // QnD -> Quick and Dirty
            db.execSQL("DROP TABLE IF EXISTS users");
            db.execSQL("DROP TABLE IF EXISTS options");
            onCreate(db);
        }
    }

}
