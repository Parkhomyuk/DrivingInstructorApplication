package com.inetex.drivinginstructorapplication.data;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;

import com.inetex.drivinginstructorapplication.Instructors;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Кирилл on 21.10.2016.
 */
public class GetInstructorsByQuery extends GetInstructors {
    ArrayList<String> city;
    String selection=null;
    String[] selectionArgs=null;
    String groupBy=null;
    String having=null;
    String orderBy=null;

    public GetInstructorsByQuery(Context context, Cursor cursor, InstructorDbHelper mdHelper,
         ArrayList<Instructors> insts, ArrayList<String> city, String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
        super(context, cursor, mdHelper, insts);
        this.city = city;
        this.selection = selection;
        this.selectionArgs = selectionArgs;
        this.groupBy = groupBy;
        this.having = having;
        this.orderBy = orderBy;
    }

    @Override
    public Cursor getCursor() {
        String[] projection = {

                InstructorContract.InstructorEntry.COLUMN_INSTRUCTOR_NAME,
                InstructorContract.InstructorEntry.COLUMN_INSTRUCTOR_CITY,
                InstructorContract.InstructorEntry.COLUMN_INSTRUCTOR_AVATAR,
                InstructorContract.InstructorEntry.COLUMN_INSTRUCTOR_AGE,
                InstructorContract.InstructorEntry.COLUMN_INSTRUCTOR_EXPERIENCE,
                InstructorContract.InstructorEntry.COLUMN_INSTRUCTOR_RATING,
                InstructorContract.InstructorEntry.COLUMN_INSTRUCTOR_VEHICLE,
                InstructorContract.InstructorEntry.COLUMN_INSTRUCTOR_PRICE,
                InstructorContract.InstructorEntry.COLUMN_INSTRUCTOR_URL,
                InstructorContract.InstructorEntry.COLUMN_INSTRUCTOR_WORKDAY,
                InstructorContract.InstructorEntry.COLUMN_INSTRUCTOR_WORKHOURS,
                InstructorContract.InstructorEntry.COLUMN_INSTRUCTOR_PHON,
                InstructorContract.InstructorEntry.COLUMN_INSTRUCTOR_SCHOOL,
                InstructorContract.InstructorEntry.COLUMN_INSTRUCTOR_EMAIL
        };
        Cursor cursor = mdHelper.query("Instructor", projection, selection,selectionArgs ,groupBy, having, orderBy);
        return cursor;
    }
}
