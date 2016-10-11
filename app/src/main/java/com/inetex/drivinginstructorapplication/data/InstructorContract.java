package com.inetex.drivinginstructorapplication.data;

import android.provider.BaseColumns;

/**
 * API Contract for the Driving Instructor.
 */
public final class InstructorContract {
    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    private InstructorContract() {
    }

    /**
     * Inner class that defines constant values for the Instructor database table.
     * Each entry in the table represents a single Instructor.
     */
    public static final class InstructorEntry implements BaseColumns{

        public final static String TABLE_NAME="Instructors";

        /**
         * Unique ID number for the Instructor (only for use in the database table).
         *
         * Type: INTEGER
         */
        public final static String _ID=BaseColumns._ID;

        /*Name of Instructors
        *
        * */
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

        /**
         * Possible values for the gender of the Instructor.
         */



    }

}
