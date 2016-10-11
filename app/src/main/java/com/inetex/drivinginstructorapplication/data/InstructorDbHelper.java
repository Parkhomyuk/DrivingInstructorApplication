package com.inetex.drivinginstructorapplication.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.inetex.drivinginstructorapplication.InstructorAdapterActivity;
import com.inetex.drivinginstructorapplication.data.InstructorContract.InstructorEntry;

/**
 * Created by Кирилл on 01.10.2016.
 */
public class InstructorDbHelper extends SQLiteOpenHelper {
    /** Name of the database file */
    private static final String DATABASE_NAME = "instructors.db";
    /**
     * Database version. If you change the database schema, you must increment the database version.
     */
    private static final int DATABASE_VERSION = 1;

    /**
     * Constructs a new instance of {@link InstructorDbHelper}.
     *
     * @param context of the app
     */
    public InstructorDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }



    /**
     * This is called when the database is created for the first time.
     */
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





    /**
     * This is called when the database needs to be upgraded.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
