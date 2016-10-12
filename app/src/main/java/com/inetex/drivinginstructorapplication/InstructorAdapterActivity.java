package com.inetex.drivinginstructorapplication;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleExpandableListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.inetex.drivinginstructorapplication.data.InstructorContract;
import com.inetex.drivinginstructorapplication.data.InstructorDbHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class InstructorAdapterActivity extends AppCompatActivity implements AdapterView.OnItemClickListener
        /*implements NavigationView.OnNavigationItemSelectedListener*/ {
    ArrayList<Instructors> insts = new ArrayList<Instructors>();
    BoxAdapter boxAdapter;
    Intent intent;

    //varible
    private String[] mGroupsArray = new String[] { "City", "Type Vechile", "Tramsmission", "Experience","Rating" ,"Gender"};
   /* private String[] mCityArray = new String[] { "Haifa", "Nazaret", "Tel Aviv", "Ierusalim","Rehovot" ,"Naarua","Netania"};*/
    private String[] mTypeVehicleArray = new String[] { "A", "B","C","D"};
    private String[] mTransmissionArray = new String[] { "Automatic", "Manual" };
    private String[] mExperienceArray = new String[] { "1-4", "5-8", "9-12","13 >" };
    private String[] mRatingArray = new String[] { "0-19", "20-39", "40-59","60-99","100 >" };
    private String[] mSexArray = new String[] { "no matter", "Man", "Woman" };

    //varible
    TextView quantity;
    Map<String, String> map;
    final ArrayList<Map<String,String>> groupDataList= new ArrayList<>();
    //Create a shared collection of items for collections
    final ArrayList<ArrayList<Map<String, String>>> сhildDataList = new ArrayList<>();
    // create a collection of items for the City group
    ArrayList<Map<String, String>> сhildDataItemList = new ArrayList<>();
    InstructorDbHelper mdHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor_adapter);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mdHelper=new InstructorDbHelper(this);
       /* FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        /*NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);*/

        //-------------------------adapter------------------
       //AsyncTask filldata
        GetInstructor instructors=new GetInstructor();
        instructors.execute();
        //
         quantity= (TextView)findViewById(R.id.quantity);


        boxAdapter = new BoxAdapter(this, insts);

        // настраиваем список
        ListView lvMain = (ListView) findViewById(R.id.instVar);

        lvMain.setOnItemClickListener(this);

        lvMain.setAdapter(boxAdapter);
        //-------------------------adapter------------------

        //Filters in sliding left menu
        //------------ExpandbleListView City, Vechicle,Transsmission,Experrience,Rating--------------------

             // a list of attribute groups to read
        String groupFrom[] = new String[] { "groupName" };
        // list ID view-elements, which will be placed groups attributes
        int groupTo[] = new int[] { android.R.id.text1 };

        // a list of attribute elements to read
        String childFrom[] = new String[] { "name" };

        // list ID view-elements, which will be placed attributes elements

        int childTo[] = new int[] { android.R.id.text1 };

        SimpleExpandableListAdapter adapter = new SimpleExpandableListAdapter(
                this, groupDataList,
                android.R.layout.simple_expandable_list_item_1, groupFrom,
                groupTo, сhildDataList, android.R.layout.simple_list_item_1,
                childFrom, childTo);

        final ExpandableListView expandableListView = (ExpandableListView) findViewById(R.id.listViewFilter);
        expandableListView.setAdapter(adapter);
        //Filter listner
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener(){


            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Toast.makeText(getApplicationContext(),
                        groupDataList.get(groupPosition)
                                + " : " +"Vau "+сhildDataList.get(groupPosition).get(childPosition),Toast.LENGTH_LONG).show();

                return false;
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_instructor_adapter, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Instructors instructors=insts.get(position);

        /*Toast.makeText(this, instructors.toString(), Toast.LENGTH_LONG).show();*/
        intent= new Intent(InstructorAdapterActivity.this, TabInstructorActivity.class);
        /*name=(TextView)findViewById(R.id.tvName);*/
        String n=instructors.name;
        /*city=(TextView)findViewById(R.id.tvCity);*/
        String c=instructors.city;
        String e=instructors.experience;
        String r=instructors.rating;
        String a=instructors.age;
        int image=instructors.avatar;
        int price=instructors.pricePerHours;
        String url=instructors.url;
        String worDays=instructors.workingDays;
        String worHours=instructors.workingHours;


        intent.putExtra("name",n);
        intent.putExtra("city",c);
        intent.putExtra("exper",e);
        intent.putExtra("rating",r);
        intent.putExtra("age",a);
        intent.putExtra("avatar",image);
        intent.putExtra("price",price);
        intent.putExtra("url",url);
        intent.putExtra("workDay",worDays);
        intent.putExtra("workHour",worHours);


        startActivity(intent);
    }
    class GetInstructor extends AsyncTask<Void,Void,Void> {
        SQLiteDatabase db= mdHelper.getWritableDatabase();
        ContentValues values= new ContentValues();
        ContentValues values2= new ContentValues();
        public GetInstructor() {
        }



        @Override
        protected Void doInBackground(Void... params) {
           //connection to db



            values.put(InstructorContract.InstructorEntry.COLUMN_INSTRUCTOR_NAME, "Dodik Moshe");
            values.put(InstructorContract.InstructorEntry.COLUMN_INSTRUCTOR_AVATAR,  R.drawable.christophe);
            values.put(InstructorContract.InstructorEntry.COLUMN_INSTRUCTOR_AGE,  "24 year");
            values.put(InstructorContract.InstructorEntry.COLUMN_INSTRUCTOR_EXPERIENCE,  "24 year");
            values.put(InstructorContract.InstructorEntry.COLUMN_INSTRUCTOR_RATING,  9);
            values.put(InstructorContract.InstructorEntry.COLUMN_INSTRUCTOR_VEHICLE, "A");
            values.put(InstructorContract.InstructorEntry.COLUMN_INSTRUCTOR_PRICE, 120);
            values.put(InstructorContract.InstructorEntry.COLUMN_INSTRUCTOR_URL, "www.dodic.com");
            values.put(InstructorContract.InstructorEntry.COLUMN_INSTRUCTOR_WORKDAY, "every day except Shabbat");
            values.put(InstructorContract.InstructorEntry.COLUMN_INSTRUCTOR_WORKHOURS, "8-20");
            values.put(InstructorContract.InstructorEntry.COLUMN_INSTRUCTOR_EMAIL, "dodic@gmai.com");
            values.put(InstructorContract.InstructorEntry.COLUMN_INSTRUCTOR_CITY, "Tel Aviv");
            values.put(InstructorContract.InstructorEntry.COLUMN_INSTRUCTOR_PHON, "050 266 66 66");
            values.put(InstructorContract.InstructorEntry.COLUMN_INSTRUCTOR_SCHOOL, "Driving School");
            values.put(InstructorContract.InstructorEntry.COLUMN_INSTRUCTOR_PASSWORD, "doddic");

            values2.put(InstructorContract.InstructorEntry.COLUMN_INSTRUCTOR_NAME, "Angelina Jolie");

            values2.put(InstructorContract.InstructorEntry.COLUMN_INSTRUCTOR_AVATAR, R.drawable.angela);
            values2.put(InstructorContract.InstructorEntry.COLUMN_INSTRUCTOR_AGE,  "40 year");
            values2.put(InstructorContract.InstructorEntry.COLUMN_INSTRUCTOR_EXPERIENCE,  "12 year");
            values2.put(InstructorContract.InstructorEntry.COLUMN_INSTRUCTOR_RATING,  6);
            values2.put(InstructorContract.InstructorEntry.COLUMN_INSTRUCTOR_VEHICLE, "A B");
            values2.put(InstructorContract.InstructorEntry.COLUMN_INSTRUCTOR_PRICE, 110);
            values2.put(InstructorContract.InstructorEntry.COLUMN_INSTRUCTOR_URL, "www");
            values2.put(InstructorContract.InstructorEntry.COLUMN_INSTRUCTOR_WORKDAY, "every day except Shabbat");
            values2.put(InstructorContract.InstructorEntry.COLUMN_INSTRUCTOR_WORKHOURS, "8-16");
            values2.put(InstructorContract.InstructorEntry.COLUMN_INSTRUCTOR_EMAIL, "angela@gmai.com");
            values2.put(InstructorContract.InstructorEntry.COLUMN_INSTRUCTOR_CITY, "Tel Aviv");
            values2.put(InstructorContract.InstructorEntry.COLUMN_INSTRUCTOR_PHON, "050 267 67 67");
            values2.put(InstructorContract.InstructorEntry.COLUMN_INSTRUCTOR_SCHOOL, "Driving School Pit");
            values2.put(InstructorContract.InstructorEntry.COLUMN_INSTRUCTOR_PASSWORD, "angela");

            db.insert(InstructorContract.InstructorEntry.TABLE_NAME,null,values);
            db.insert(InstructorContract.InstructorEntry.TABLE_NAME,null,values2);

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
                    InstructorContract.InstructorEntry.COLUMN_INSTRUCTOR_SCHOOL

            };
            String[] projection2 = {

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
                    InstructorContract.InstructorEntry.COLUMN_INSTRUCTOR_SCHOOL

            };
            // Perform a query on the Instructors table
            Cursor cursor = db.query(
                    InstructorContract.InstructorEntry.TABLE_NAME,   // The table to query
                    projection,            // The columns to return
                    null,                  // The columns for the WHERE clause
                    null,                  // The values for the WHERE clause
                    null,                  // Don't group the rows
                    null,                  // Don't filter by row groups
                    null);                   // The sort order
            // Perform a query on the Instructors table
            Cursor cursor2 = db.query(
                    InstructorContract.InstructorEntry.TABLE_NAME,   // The table to query
                    projection2,            // The columns to return
                    null,                  // The columns for the WHERE clause
                    null,                  // The values for the WHERE clause
                    null,                  // Don't group the rows
                    null,                  // Don't filter by row groups
                    null);
            int nameColumnName=cursor.getColumnIndex(InstructorContract.InstructorEntry.COLUMN_INSTRUCTOR_NAME);
            int cityColumnName=cursor.getColumnIndex(InstructorContract.InstructorEntry.COLUMN_INSTRUCTOR_CITY);
            int avatarColumnName=cursor.getColumnIndex(InstructorContract.InstructorEntry.COLUMN_INSTRUCTOR_AVATAR);
            int ageColumnName=cursor.getColumnIndex(InstructorContract.InstructorEntry.COLUMN_INSTRUCTOR_AGE);
            int expeColumnName=cursor.getColumnIndex(InstructorContract.InstructorEntry.COLUMN_INSTRUCTOR_EXPERIENCE);
            int ratColumnName=cursor.getColumnIndex(InstructorContract.InstructorEntry.COLUMN_INSTRUCTOR_RATING);
            int typeVeuicleColumnName=cursor.getColumnIndex(InstructorContract.InstructorEntry.COLUMN_INSTRUCTOR_VEHICLE);
            int priceColumnName=cursor.getColumnIndex(InstructorContract.InstructorEntry.COLUMN_INSTRUCTOR_PRICE);
            int urlColumnName=cursor.getColumnIndex(InstructorContract.InstructorEntry.COLUMN_INSTRUCTOR_URL);
            int workdayColumnName=cursor.getColumnIndex(InstructorContract.InstructorEntry.COLUMN_INSTRUCTOR_WORKDAY);
            int workhourColumnName=cursor.getColumnIndex(InstructorContract.InstructorEntry.COLUMN_INSTRUCTOR_WORKHOURS);
            int phonColumnName=cursor.getColumnIndex(InstructorContract.InstructorEntry.COLUMN_INSTRUCTOR_PHON);
            int schoolColumnName=cursor.getColumnIndex(InstructorContract.InstructorEntry.COLUMN_INSTRUCTOR_SCHOOL);
            Instructors instructors= new Instructors();
            Instructors instructors2= new Instructors();
            try{
                while (cursor.moveToNext()) {
                    instructors.setName(cursor.getString(nameColumnName));
                    instructors.setCity(cursor.getString(cityColumnName));
                    instructors.setAvatar(Integer.parseInt(cursor.getString(avatarColumnName)));
                    instructors.setAge(cursor.getString(ageColumnName));
                    instructors.setExperience(cursor.getString(expeColumnName));
                    instructors.setRating(cursor.getString(ratColumnName));
                    instructors.setTypeVehicle(cursor.getString(typeVeuicleColumnName));
                    instructors.setPricePerHours(Integer.parseInt(cursor.getString(priceColumnName)));
                    instructors.setUrl(cursor.getString(urlColumnName));
                    instructors.setWorkingDays(cursor.getString(workdayColumnName));
                    instructors.setWorkingHours(cursor.getString(workhourColumnName));
                    instructors.setPhon(cursor.getString(phonColumnName));
                    instructors.setSchool(cursor.getString(schoolColumnName));


                }

            }
            catch (Exception e){}
            finally {
                cursor.close();

            }
            try{

                while (cursor2.moveToNext()) {
                    instructors2.setName(cursor2.getString(nameColumnName));
                    instructors2.setCity(cursor2.getString(cityColumnName));
                    instructors2.setAvatar(Integer.parseInt(cursor2.getString(avatarColumnName)));
                    instructors2.setAge(cursor2.getString(ageColumnName));
                    instructors2.setExperience(cursor2.getString(expeColumnName));
                    instructors2.setRating(cursor2.getString(ratColumnName));
                    instructors2.setTypeVehicle(cursor2.getString(typeVeuicleColumnName));
                    instructors2.setPricePerHours(Integer.parseInt(cursor2.getString(priceColumnName)));
                    instructors2.setUrl(cursor2.getString(urlColumnName));
                    instructors2.setWorkingDays(cursor2.getString(workdayColumnName));
                    instructors2.setWorkingHours(cursor2.getString(workhourColumnName));
                    instructors2.setPhon(cursor2.getString(phonColumnName));
                    instructors2.setSchool(cursor2.getString(schoolColumnName));
                }
            }
            catch (Exception e){}
            finally {

                cursor2.close();
            }
            //connection to db
            try {
                insts.add(instructors);
                insts.add(instructors2);
               /* insts.add(new Instructors("Dodik Moshe", "Tel Aviv", R.drawable.christophe, " 24 year", "2 year", "9", "A",120,"www.dodic.com","every day except Shabbat","8-20"));
                insts.add(new Instructors("Angelina Jolie", "Tel Aviv", R.drawable.angela, " 40 year", "12 year", " 84", "A B",110,"www","every day except Shabbat","8-16"));
                insts.add(new Instructors("Tom Zat", "Netania", R.drawable.z, " 27 year", "4 year", " 42", "A B C",110,"www.Tom.com","every day except Sunday","12-22"));
                insts.add(new Instructors("Bruce Willis", "Netania", R.drawable.bruce, " 52 year", "15 year", " 26", "A B C D",110,"www","every day except Shabbat","12-18"));
                insts.add(new Instructors("Zipora Zukerman", "Irusalim", R.drawable.savta, " 60 year", "27 year", " 17", "A B ",120,"www","every day except Shabbat","10-16"));
                insts.add(new Instructors("Tom Cruze", "Irusalim", R.drawable.tom, " 50 year", "17 year", " 85", "A B C D",120,"www","every day except Shabbat","6-16"));
                insts.add(new Instructors("Bill Geist", "Tel Aviv", R.drawable.bill, " 62 year", "22 year", " 48", "A B C D",110,"www","every day except Shabbat","8-16"));
                insts.add(new Instructors("Rostik Shahar", "Haifa", R.drawable.toto, " 27 year", "4 year", " 12", "B",110,"www","every day except Shabbat","10-16"));
                insts.add(new Instructors("Barack Abama", "Haifa", R.drawable.barack, " 55 year", "20 year", " 44", "A B",110,"www","every day except Shabbat","8-16"));
                insts.add(new Instructors("Jastin Timberlaike", "Rehovot", R.drawable.tim, " 35 year", "11 year", " 48", "A B C",120,"www","every day except Shabbat","8-16"));
                insts.add(new Instructors("Brad Pit", "Rehovot", R.drawable.brad, " 45 year", "18 year", " 68", "A B C D",130,"www","every day except Shabbat","8-16"));
                insts.add(new Instructors("Haim Kaz", "Netania", R.drawable.buch, " 52 year", "25 year", " 55", "C D",110,"www","every day except Shabbat","8-16"));
                insts.add(new Instructors("Rafik Golubian", "Tel Aviv", R.drawable.daty, " 34 year", "8 year", " 24", "A C D",120,"www","every day except Shabbat","8-16"));
                insts.add(new Instructors("Lusy Zack", "Tel Aviv", R.drawable.lucy, " 24 year", "1 year", " 24", "A B",110,"www","every day except Shabbat","8-16"));
                insts.add(new Instructors("Jack Nicolson", "Tel Aviv", R.drawable.nicola, " 62 year", "28 year", " 88", "A B C D",110,"www","every day except Shabbat","8-16"));
                insts.add(new Instructors("Yosy Ferdman", "Ashdod", R.drawable.saba, " 74 year", "35 year", " 98", "A B C D",110,"www","every day except Shabbat","8-16"));
                insts.add(new Instructors("Rohel Bell", "Ashkelon", R.drawable.savta, " 64 year", "15 year", " 55", "A B",110,"www","every day except Shabbat","8-16"));
                insts.add(new Instructors("David Zukerman", "Ashdod", R.drawable.saba2, " 88 year", "55 year", " 102", "A B C D",110,"www","every day except Shabbat","8-16"));*/

                ArrayList<String> mCityArray=new ArrayList<>();
                for(int i=0;i<insts.size();i++) {
                    if (insts.get(i) != null) {
                        if (mCityArray.contains(insts.get(i).getCity())) i++;
                        else
                            mCityArray.add(insts.get(i).getCity());
                    }
                    else{
                        i++;
                    }
                }
                //complete the collection of groups from the array with the names of groups
                for(String group: mGroupsArray) {
                    map = new HashMap<>();
                    map.put("groupName", group);
                    groupDataList.add(map);
                }
                    // fill the list of attributes for each element
                 for (String city : mCityArray) {
                     map = new HashMap<>();
                     map.put("name", city); // name of city
                     сhildDataItemList.add(map);
                    }
                // add to the collection of collections
                сhildDataList.add(сhildDataItemList);
                //create a collection of items for the vechile
                сhildDataItemList=new ArrayList<>();
                for(String vichele: mTypeVehicleArray){
                    map=new HashMap<>();
                    map.put("name",vichele);
                    сhildDataItemList.add(map);
                }
                сhildDataList.add(сhildDataItemList);
                //create a collection of items for the Transmission
                сhildDataItemList=new ArrayList<>();
                for(String transmission: mTransmissionArray ){
                    map=new HashMap<>();
                    map.put("name",transmission);
                    сhildDataItemList.add(map);
                }
                сhildDataList.add(сhildDataItemList);
                //create a collection of items for the Experiences
                сhildDataItemList=new ArrayList<>();
                for(String exper: mExperienceArray  ){
                    map=new HashMap<>();
                    map.put("name",exper);
                    сhildDataItemList.add(map);
                }
                сhildDataList.add(сhildDataItemList);
                //create a collection of items for the Ratings
                сhildDataItemList=new ArrayList<>();
                for(String rating: mRatingArray  ){
                    map=new HashMap<>();
                    map.put("name",rating);
                    сhildDataItemList.add(map);
                }
                сhildDataList.add(сhildDataItemList);
                //create a collection of items for the Sex
                сhildDataItemList=new ArrayList<>();
                for(String sex:  mSexArray ){
                    map=new HashMap<>();
                    map.put("name",sex);
                    сhildDataItemList.add(map);
                }
                сhildDataList.add(сhildDataItemList);



            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {


            super.onPostExecute(aVoid);
            quantity.setText(String.valueOf(insts.size()) + " Instructors");
        }
    }

    public ArrayList<Instructors> getInsts() {
        return insts;
    }
}


