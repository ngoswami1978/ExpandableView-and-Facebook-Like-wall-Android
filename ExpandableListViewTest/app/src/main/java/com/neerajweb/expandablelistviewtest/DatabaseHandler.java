package com.neerajweb.expandablelistviewtest;

/**
 * Created by Admin on 15/09/2015.
 */
import java.util.ArrayList;
import java.util.List;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper {
    // Database Version
    private static final int DATABASE_VERSION = 2;

    // Database Name
    private static final String DATABASE_NAME = "Apartmentdb";

    // Title Master table name
    private static final String MS_TABLE_TITLE = "T_MS_POST_TITLE";
    // Title Master Columns names
    private static final String MS_KEY_ID = "id";
    private static final String MS_KEY_NAME = "title";

    // Comment Detail table name
    private static final String DTLS_TABLE_COMMENT = "T_DTLS_POST_COMMENTS";
    // Comment Detail Columns names
    private static final String DTLS_KEY_ID = "id";
    private static final String DTLS_MS_TITLE_KEY_ID = "_Titleid";
    private static final String DTLS_USERNAME = "Username";
    private static final String DTLS_COMMENTS = "Postcomment";


    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Category table create query
        String CREATE_MASTER_TABLE = "CREATE TABLE " + MS_TABLE_TITLE + "("
                + MS_KEY_ID + " INTEGER PRIMARY KEY," + MS_KEY_NAME + " VARCHAR(2000))";

        String CREATE_DETAIL_TABLE = "CREATE TABLE " + DTLS_TABLE_COMMENT + "("
                + DTLS_KEY_ID + " INTEGER PRIMARY KEY,"
                + DTLS_MS_TITLE_KEY_ID + " INTEGER ,"
                + DTLS_USERNAME + " VARCHAR(50),"
                + DTLS_COMMENTS + " TEXT)";

        db.execSQL(CREATE_MASTER_TABLE);
        db.execSQL(CREATE_DETAIL_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + MS_TABLE_TITLE);
        db.execSQL("DROP TABLE IF EXISTS " + DTLS_TABLE_COMMENT);
        // Create tables again
        onCreate(db);
    }

    /**
     * Inserting new Title table into lables table
     * */
    public void insertTitle(String title){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(MS_KEY_NAME, title);

        // Inserting Row
        db.insert(MS_TABLE_TITLE, null, values);
        db.close(); // Closing database connection
    }

    /**
     * Getting all labels
     * returns list of labels
     * */
    public List<String> getAllLabels(){
        List<String> labels = new ArrayList<String>();

        // Select All Query
        String selectQuery = "SELECT  id as _id , title FROM " + MS_TABLE_TITLE;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                labels.add(cursor.getString(1));
            } while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();
        db.close();

        // returning lables
        return labels;
    }

    /**
     * Getting all labels
     * returns list of labels
     * */
    public Cursor getAllTitles(){
        // Select All Query
        String selectQuery = "SELECT  id as _id , title FROM " + MS_TABLE_TITLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // returning cursor
        return cursor;
    }
}
