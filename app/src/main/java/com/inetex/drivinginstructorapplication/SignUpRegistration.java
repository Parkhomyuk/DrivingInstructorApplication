package com.inetex.drivinginstructorapplication;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

 import com.inetex.drivinginstructorapplication.data.InstructorDbHelper;

import java.util.ArrayList;

/**
 * Created by Кирилл on 17.09.2016.
 */
public class SignUpRegistration extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {
    private RadioGroup radioGroup;
    /** EditText field to enter the Instructors's name */
    private EditText mNameEditText;
    /** EditText field to enter the Instructo's email */
    private EditText mEmailEditText;
    /** EditText field to enter the Instructo's city */
    private EditText mCityEditText;
    /** EditText field to enter the Instructo's phon */
    private EditText mPhonEditText;
    /** EditText field to enter the Instructo's Driving school */
    private EditText mSchoolEditText;

    /** EditText field to enter the Instructo's password */
    private EditText mPasswordEditText;
    /** EditText field to enter the Instructo's confirm password */
    private EditText mConfirmPasswordEditText;

    /** EditText field to enter the Instructo's Type vehicle */
    private CheckBox mVehicleA;
    private CheckBox mVehicleB;
    private CheckBox mVehicleC;
    private CheckBox mVehicleD;

    String typeA;
    String typeB;
    String typeC;
    String typeD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_layout);

        final LinearLayout visLL=(LinearLayout)findViewById(R.id.contInstr);
        final RadioButton butStud=(RadioButton)findViewById(R.id.studentButton);
        final RadioButton butInst=(RadioButton)findViewById(R.id.instructorButton);
        // Find all relevant views that we will need to read user input from
        mNameEditText=(EditText)findViewById(R.id.your_full_name);
        mEmailEditText=(EditText)findViewById(R.id.your_email_address);
        mCityEditText=(EditText)findViewById(R.id.your_city);
        mPhonEditText=(EditText)findViewById(R.id.your_phone);
        mSchoolEditText=(EditText)findViewById(R.id.your_school);
        //password Authentication
        mPasswordEditText=(EditText)findViewById(R.id.create_new_password);
        mConfirmPasswordEditText=(EditText)findViewById(R.id.confirm_new_password);
        //choose type vehicle
        mVehicleA=(CheckBox)findViewById(R.id.checkA);
        mVehicleA.setOnCheckedChangeListener(this);
        mVehicleB=(CheckBox)findViewById(R.id.checkB);
        mVehicleB.setOnCheckedChangeListener(this);
        mVehicleC=(CheckBox)findViewById(R.id.checkC);
        mVehicleC.setOnCheckedChangeListener(this);
        mVehicleD=(CheckBox)findViewById(R.id.checkD);
        mVehicleD.setOnCheckedChangeListener(this);

        //Choose view SignUp
        radioGroup=(RadioGroup)findViewById(R.id.rbgroupStatus);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){


            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = (RadioButton) group.findViewById(checkedId);
                if (radioButton != null && checkedId > -1) {
                    if(butInst.isChecked()){

                        visLL.setVisibility(View.VISIBLE);
                    // if choose Instructor start registration
                    }

                    if(butStud.isChecked()){

                        visLL.setVisibility(View.GONE);}

                }
            }
        } );


    Button signUp=(Button)findViewById(R.id.signUp);
signUp.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        insertInstructor();
    }
});

    }


    public void onClear(View v){
radioGroup.clearCheck();
    }

    /**
     * Get user input from editor and save new Instructor into database.
     */
    private void insertInstructor(){
        // Read from input fields
        // Use trim to eliminate leading or trailing white space
        String nameString=mNameEditText.getText().toString().trim();
        String emailString=mEmailEditText.getText().toString().trim();
        String cityString=mCityEditText.getText().toString().trim();
        String phonString=mPhonEditText.getText().toString().trim();
        String schoolString=mSchoolEditText.getText().toString().trim();
        String typeVehicle=createTypeVehicle(typeA,typeB,typeC,typeD);
        String passwordString=mPasswordEditText.getText().toString().trim();

        // Create database helper

        InstructorDbHelper mdHelper=new InstructorDbHelper(this);
        // Gets the database in write mode
        SQLiteDatabase db= mdHelper.getWritableDatabase();
        // Create a ContentValues object where column names are the keys,
        // and pet attributes from the editor are the values.
       /* ContentValues values= new ContentValues();
        values.put(InstructorContract.InstructorEntry.COLUMN_INSTRUCTOR_NAME, nameString);
        values.put(InstructorContract.InstructorEntry.COLUMN_INSTRUCTOR_EMAIL, emailString);
        values.put(InstructorContract.InstructorEntry.COLUMN_INSTRUCTOR_CITY, cityString);
        values.put(InstructorContract.InstructorEntry.COLUMN_INSTRUCTOR_PHON, phonString);
        values.put(InstructorContract.InstructorEntry.COLUMN_INSTRUCTOR_SCHOOL, schoolString);
        values.put(InstructorContract.InstructorEntry.COLUMN_INSTRUCTOR_VEHICLE, typeVehicle);
        values.put(InstructorContract.InstructorEntry.COLUMN_INSTRUCTOR_PASSWORD, passwordString);

        // Insert a new row for pet in the database, returning the ID of that new row.
        long newRowId=db.insert(InstructorContract.InstructorEntry.TABLE_NAME,null,values);
        // Show a toast message depending on whether or not the insertion was successful
       if(newRowId==-1){
            // If the row ID is -1, then there was an error with insertion.
            Toast.makeText(this, "Error with saving Instructor", Toast.LENGTH_SHORT).show();
        }
        else{
            // Otherwise, the insertion was successful and we can display a toast with the row ID.
            Toast.makeText(this, "Instructor saved with row id: " + newRowId, Toast.LENGTH_SHORT).show();
        }*/

    }

   @Override
   public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

       if (buttonView==mVehicleA){
           if(isChecked)
               typeA="A";

       }
       if (buttonView==mVehicleB){
           if(isChecked)
               typeB="B";

       }
       if (buttonView==mVehicleC){
           if(isChecked)
               typeC="C";

       }
       if (buttonView==mVehicleD){
           if(isChecked)
               typeD="D";

       }

   }
    private String createTypeVehicle( String a,String b,String c,String d) {

        String typeVehicle ="";
if(a!=null){
        typeVehicle =a;}
if(b!=null){
        typeVehicle =typeVehicle+" "+b;}
if(c!=null){
        typeVehicle=typeVehicle+" "+ c;}
if(d!=null) {
    typeVehicle = typeVehicle+" " + d;
}
        typeVehicle.trim();
        return typeVehicle;
    }







}