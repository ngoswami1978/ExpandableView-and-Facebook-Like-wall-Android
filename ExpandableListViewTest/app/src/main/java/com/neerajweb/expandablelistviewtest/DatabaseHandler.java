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
    private static final int DATABASE_VERSION = 5;

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
    private static final String DTLS_COMMENTS_SEQUENCE = "Sequence";
    private static final String DTLS_MS_TITLE_KEY_ID = "_Titleid";
    private static final String DTLS_USERNAME = "Username";
    private static final String DTLS_COMMENT_DATETIME="PostcommentDateTime";
    private static final String DTLS_COMMENTS = "Postcomment";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Category table create query
        String CREATE_MASTER_TABLE = "CREATE TABLE " + MS_TABLE_TITLE + "("
                + MS_KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ," + MS_KEY_NAME + " VARCHAR(2000))";

        String CREATE_DETAIL_TABLE = "CREATE TABLE " + DTLS_TABLE_COMMENT + "("
                + DTLS_KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + DTLS_COMMENTS_SEQUENCE + " INTEGER ,"
                + DTLS_MS_TITLE_KEY_ID + " INTEGER ,"
                + DTLS_USERNAME + " VARCHAR(50),"
                + DTLS_COMMENT_DATETIME + " VARCHAR(30),"
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

    /*
    * Insert an Post and Comments ’s information
    */
    public long insertPostComment(String strSequence, long intTitleid , String strUsername , String strPostcommentDateTime,String strPostcomment ) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DTLS_COMMENTS_SEQUENCE,strSequence);
        values.put(DTLS_MS_TITLE_KEY_ID, intTitleid);
        values.put(DTLS_USERNAME, strUsername);
        values.put(DTLS_COMMENT_DATETIME, strPostcommentDateTime);
        values.put(DTLS_COMMENTS, strPostcomment);

        long insertId = db.insert(DTLS_TABLE_COMMENT, null, values);

        return insertId;
    }

     /**
     * Getting all Titles
     * returns list of Titles
     **/
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

    /**
     * Getting all Titles
     * returns list of Titles
     **/
    public ArrayList<DetailInfo> getAllPostsComments(){
        /*
        List<String> titles = new ArrayList<String>();

        // Select All Query
        String selectQuery = "SELECT T_DTLS_POST_COMMENTS.id, title,Postcomment FROM T_DTLS_POST_COMMENTS INNER JOIN T_MS_POST_TITLE ON T_DTLS_POST_COMMENTS._Titleid = T_MS_POST_TITLE.id order by _Titleid ";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                titles.add(0,cursor.getString(0));
                titles.add(1,cursor.getString(1));
            } while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();
        db.close();

        // returning lables
        return titles;
        */
        ArrayList<DetailInfo> modelDetailInfo = new ArrayList<DetailInfo>();
        // Select All Query
        String selectQuery = "SELECT T_DTLS_POST_COMMENTS.id,T_DTLS_POST_COMMENTS._Titleid as TitleId,Username,Sequence,title,PostcommentDateTime,Postcomment FROM T_DTLS_POST_COMMENTS INNER JOIN T_MS_POST_TITLE ON T_DTLS_POST_COMMENTS._Titleid = T_MS_POST_TITLE.id order by _Titleid ";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                DetailInfo dtlInfo = new DetailInfo();

                dtlInfo.setPostcommentId(cursor.getString(0));
                dtlInfo.setPostcommentTitleId(cursor.getString(1));
                dtlInfo.setLogedInUserName(cursor.getString(2));
                dtlInfo.setSequence(cursor.getString(3));
                dtlInfo.setPostcommentTitle(cursor.getString(4));
                dtlInfo.setpostDatetime(cursor.getString(5));
                dtlInfo.setPostcomment(cursor.getString(6));

                // all your column
                modelDetailInfo.add(dtlInfo);
            } while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();
        db.close();

        return modelDetailInfo;
    }
}
