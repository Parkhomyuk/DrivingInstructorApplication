package com.inetex.drivinginstructorapplication.data;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.inetex.drivinginstructorapplication.InstructorAdapterActivity;
import com.inetex.drivinginstructorapplication.data.InstructorContract.InstructorEntry;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Кирилл on 01.10.2016.
 */
public class InstructorDbHelper extends SQLiteOpenHelper {
   /* *//** Name of the database file *//*
    private static final String DATABASE_NAME = "instructors.db";
    *//**
     * Database version. If you change the database schema, you must increment the database version.
     *//*
    private static final int DATABASE_VERSION = 1;

    *//**
     * Constructs a new instance of {@link InstructorDbHelper}.
     *
     * @param context of the app
     *//*
    public InstructorDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }



    *//**
     * This is called when the database is created for the first time.
     *//*
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create a String that contains the SQL statement to create the pets table
        String SQL_CREATE_INSTRUCTOR_TABLE =  "CREATE TABLE " + InstructorEntry.TABLE_NAME + " ("
                + InstructorEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + InstructorEntry.COLUMN_INSTRUCTOR_NAME + " TEXT NOT NULL, "
                + InstructorEntry.COLUMN_INSTRUCTOR_CITY + " TEXT NOT NULL, "
                + InstructorEntry.COLUMN_INSTRUCTOR_AVATAR + " INTEGER , "
                + InstructorEntry.COLUMN_INSTRUCTOR_AGE + " INTEGER , "
                + InstructorEntry.COLUMN_INSTRUCTOR_EXPERIENCE + " TEXT NOT NULL , "
                + InstructorEntry.COLUMN_INSTRUCTOR_RATING + " INTEGER , "
                + InstructorEntry.COLUMN_INSTRUCTOR_VEHICLE + " TEXT NOT NULL, "
                + InstructorEntry.COLUMN_INSTRUCTOR_PRICE + " INTEGER , "
                + InstructorEntry.COLUMN_INSTRUCTOR_URL + " TEXT , "
                + InstructorEntry.COLUMN_INSTRUCTOR_WORKDAY + " TEXT , "
                + InstructorEntry.COLUMN_INSTRUCTOR_WORKHOURS + " TEXT , "
                + InstructorEntry.COLUMN_INSTRUCTOR_EMAIL + " TEXT NOT NULL, "
                + InstructorEntry.COLUMN_INSTRUCTOR_SCHOOL + " TEXT, "
                + InstructorEntry.COLUMN_INSTRUCTOR_PHON + " TEXT NOT NULL, "
                + InstructorEntry.COLUMN_INSTRUCTOR_PASSWORD + " TEXT NOT NULL); ";


        // Execute the SQL statement
        db.execSQL(SQL_CREATE_INSTRUCTOR_TABLE);
    }





    *//**
     * This is called when the database needs to be upgraded.
     *//*
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }*/
   /* private static String DB_PATH = "/data/data/com.inetex.drivinginstructorapplication/databases/";*/
    String DB_PATH=null;
   private static String DB_NAME = "instructoroutside.db";

    private static SQLiteDatabase myDataBase;

    private static final int SCHEMA = 1; // версия базы данных
    public static final String TABLE = "Instructor";

    public final static String COLUMN_INSTRUCTOR_NAME="name";
    public final static String COLUMN_INSTRUCTOR_EMAIL="email";
    public final static String COLUMN_INSTRUCTOR_CITY="city";
    public final static String COLUMN_INSTRUCTOR_SCHOOL="school";
    public final static String COLUMN_INSTRUCTOR_VEHICLE="vehicle";
    public final static String COLUMN_INSTRUCTOR_PASSWORD="password";
    public final static String COLUMN_INSTRUCTOR_PHON="phon";
    public final static String COLUMN_INSTRUCTOR_AVATAR="avatar";
    public final static String COLUMN_INSTRUCTOR_AGE="age";
    public final static String COLUMN_INSTRUCTOR_EXPERIENCE="experience";
    public final static String COLUMN_INSTRUCTOR_RATING="rating";
    public final static String COLUMN_INSTRUCTOR_PRICE="price";
    public final static String COLUMN_INSTRUCTOR_URL="url";
    public final static String COLUMN_INSTRUCTOR_WORKDAY="workday";
    public final static String COLUMN_INSTRUCTOR_WORKHOURS="workhours";
    public SQLiteDatabase database;
    private Context myContext;

    public InstructorDbHelper(Context context) {
        super(context, DB_NAME, null, SCHEMA);
        this.myContext=context;
        this.DB_PATH="data/data/"+context.getPackageName()+"/"+"databases/";
        Log.e("Path 1",DB_PATH+DB_NAME);
    }
    public void createDataBase() throws IOException {
        boolean dbExist = checkDataBase();
        if (dbExist) {
        } else {
            this.getReadableDatabase();
            try {
                copyDataBase();
            } catch (IOException e) {
                throw new Error("Error copying database");
            }
        }
    }

    private boolean checkDataBase() {
        SQLiteDatabase checkDB = null;
        try {
            String myPath = DB_PATH + DB_NAME;
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
        } catch (SQLiteException e) {
        }
        if (checkDB != null) {
            checkDB.close();
        }
        return checkDB != null ? true : false;
    }

    private void copyDataBase() throws IOException {
        InputStream myInput = myContext.getAssets().open(DB_NAME);
        String outFileName = DB_PATH + DB_NAME;
        OutputStream myOutput = new FileOutputStream(outFileName);
        byte[] buffer = new byte[10];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }
        myOutput.flush();
        myOutput.close();
        myInput.close();

    }

    public void openDataBase() throws SQLException {
        String myPath = DB_PATH + DB_NAME;
        myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);

    }

    @Override
    public synchronized void close() {
        if (myDataBase != null)
            myDataBase.close();
        super.close();
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > oldVersion)
            try {
                copyDataBase();
            } catch (IOException e) {
                e.printStackTrace();

            }
    }

    public Cursor query(String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
        return myDataBase.query("Instructor", null, null, null, null, null, null);
    }


}
/*

    @Override
    public void onCreate(SQLiteDatabase db) {
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,  int newVersion) {

    }

    public void create_db(){
        InputStream myInput = null;
        OutputStream myOutput = null;
        try {
            File file = new File(DB_PATH + DB_NAME);
            if (!file.exists()) {
                this.getReadableDatabase();
                //получаем локальную бд как поток
                myInput = myContext.getAssets().open(DB_NAME);
                // Путь к новой бд
                String outFileName = DB_PATH + DB_NAME;

                // Открываем пустую бд
                myOutput = new FileOutputStream(outFileName);

                // побайтово копируем данные
                byte[] buffer = new byte[1024];
                int length;
                while ((length = myInput.read(buffer)) > 0) {
                    myOutput.write(buffer, 0, length);
                }

                myOutput.flush();
                myOutput.close();
                myInput.close();
            }
        }
        catch(IOException ex){

        }

    }
    public void open() throws SQLException {
        String path = DB_PATH + DB_NAME;
        database = SQLiteDatabase.openDatabase(path, null,
                SQLiteDatabase.OPEN_READWRITE);

    }

    @Override
    public synchronized void close() {
        if (database != null) {
            database.close();
        }
        super.close();
    }
}
*/
