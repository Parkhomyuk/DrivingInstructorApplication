package com.inetex.drivinginstructorapplication;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.SimpleExpandableListAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.database.sqlite.SQLiteOpenHelper;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.inetex.drivinginstructorapplication.data.GetInstructors;
import com.inetex.drivinginstructorapplication.data.GetInstructorsByQuery;
import com.inetex.drivinginstructorapplication.data.InstructorDbHelper;
import com.twotoasters.jazzylistview.JazzyListView;
import com.twotoasters.jazzylistview.effects.ZipperEffect;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class InstructorAdapterActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {


    BoxAdapter boxAdapter;
    Intent intent;

    //varible
    private String[] mGroupsArray = new String[]{"City", "Type Vechile", "Tramsmission", "Experience", "Rating", "Gender"};

    final ArrayList<Map<String, String>> groupDataList = new ArrayList<>();
    //Create a shared collection of items for collections
    final ArrayList<ArrayList<Map<String, String>>> сhildDataList = new ArrayList<>();
    ArrayList<String> child = new ArrayList<>();
    // create a collection of items for the City group
    ArrayList<Map<String, String>> сhildDataItemList = new ArrayList<>();
    private String[] mTypeVehicleArray = new String[]{"A", "B", "C", "D"};
    private String[] mTransmissionArray = new String[]{"Automatic", "Manual"};
    private String[] mExperienceArray = new String[]{"1-4", "5-8", "9-12", "13 >"};
    private String[] mRatingArray = new String[]{"0-19", "20-39", "40-59", "60-99", "100 >"};
    private String[] mSexArray = new String[]{"no matter", "Man", "Woman"};
    //varible
    TextView quantity;
    Map<String, String> map;
    ArrayList<Instructors> insts = new ArrayList<Instructors>();
    ArrayList<Instructors> instsFilter = new ArrayList<Instructors>();

    Cursor cursor;
    TextView selection;
    TextView selectionTypeVehicle;

    String  str="City :";
    String  strVehicle="Type vehicle :";
    ArrayList<String> cityinfo=new ArrayList<>();
    JazzyListView lvMain;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    InstructorDbHelper mdHelper;
    SQLiteDatabase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor_adapter);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //----AsyncTask filldata
        GetInstructors getInstructorsDB = new GetInstructors(this, cursor, mdHelper, insts);
        getInstructorsDB.execute();
        final GetInstructor instructors = new GetInstructor();
        instructors.execute();
       // mdHelper=getInstructorsDB.getMdHelper();
        mdHelper = new InstructorDbHelper(this);
        try {
            mdHelper.createDataBase();
        } catch (IOException ioe) {
            throw new Error("Unable to create database");
        }
        try {
            mdHelper.openDataBase();
        } catch (SQLException sqle) {
            throw sqle;
        }

        //----AsyncTask filldata
       /* FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        }); */

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        quantity = (TextView) findViewById(R.id.quantity);

        Button reset=(Button)findViewById(R.id.reset);
        reset.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
               selection.setText("");
               selection.setVisibility(View.GONE);
               //cityinfo.clear();
               instsFilter.clear();
               insts.clear();
                db=mdHelper.getWritableDatabase();

                Cursor cursor = db.query("Instructor", null, null, null, null, null, null);
                fillData(insts, cursor);


            //   GetInstructors getInstructorsDB = new GetInstructors(getApplicationContext(), cursor, mdHelper, insts);

                boxAdapter.notifyDataSetChanged();
                quantity.setText(String.valueOf(insts.size()) + getString(R.string.Instructors));

            }
        });
        Button ready=(Button)findViewById(R.id.ready);
        ready.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                instsFilter.clear();
                insts.clear();
                db=mdHelper.getWritableDatabase();
                Cursor cursor = null;
                for(int i=0;i<cityinfo.size();i++) {
                    cursor = db.query("Instructor", null, "city = ?", new String[]{cityinfo.get(i)}, null, null, null);
                    fillData(insts, cursor);
                }
                boxAdapter.notifyDataSetChanged();
                quantity.setText(String.valueOf(insts.size()) + getString(R.string.Instructors));

            }
        });

        boxAdapter = new BoxAdapter(this, insts);

        // настраиваем список
        lvMain = (JazzyListView) findViewById(R.id.instVar);
        lvMain.setTransitionEffect(new ZipperEffect()); //new ZipperEffect() new TiltEffect()
        lvMain.setOnItemClickListener(this);
        lvMain.setChoiceMode(JazzyListView.CHOICE_MODE_MULTIPLE);
        lvMain.setAdapter(boxAdapter);

        //------------ExpandbleListView City, Vechicle,Transsmission,Experrience,Rating--------------------

        // a list of attribute groups to read
        String groupFrom[] = new String[]{"groupName"};
        // list ID view-elements, which will be placed groups attributes
        int groupTo[] = new int[]{android.R.id.text1};

        // a list of attribute elements to read
        String childFrom[] = new String[]{"name"};

        // list ID view-elements, which will be placed attributes elements

        int childTo[] = new int[]{android.R.id.text1};

        final SimpleExpandableListAdapter adapter = new SimpleExpandableListAdapter(
                this, groupDataList,
                android.R.layout.simple_expandable_list_item_1, groupFrom,

              groupTo, сhildDataList, android.R.layout.simple_list_item_1,
                childFrom, childTo);

        final ExpandableListView expandableListView = (ExpandableListView) findViewById(R.id.listViewFilter);

        expandableListView.setAdapter(adapter);
        expandableListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        expandableListView.setItemsCanFocus(false);


        if (Build.VERSION.SDK_INT <
                Build.VERSION_CODES.JELLY_BEAN_MR2) {
            expandableListView.setIndicatorBounds(170, 200);
        } else {
            expandableListView.setIndicatorBoundsRelative(170, 200);
        }
        //Filter listner
        selection=(TextView)findViewById(R.id.text_view_city);
        selectionTypeVehicle=(TextView)findViewById(R.id.text_view_type_vechile);

        /* expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener(){*/


        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {


            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {



                for(int i=0;i<insts.size();i++){
                    if(insts.get(i).getCity().equals(сhildDataList.get(groupPosition).get(childPosition).get("name"))){
                        if(instsFilter.contains(insts.get(i))){
                            instsFilter.remove(insts.get(i));
                        }
                        else {
                            instsFilter.add(insts.get(i));

                        }
                    }
                    else{
                        i++;
                    }
                }
                if(cityinfo.contains(сhildDataList.get(groupPosition).get(childPosition).get("name"))){

                    cityinfo.remove(сhildDataList.get(groupPosition).get(childPosition).get("name"));

                }
                else{
                    cityinfo.add(сhildDataList.get(groupPosition).get(childPosition).get("name"));
                }


                for(int j=0;j<cityinfo.size();j++){
                    str=str+(cityinfo.get(j))+", ";

                }

                /*strBilder.append(сhildDataList.get(groupPosition).get(childPosition).get("name")+", ");*/
                selection.setText(str);
                selection.setVisibility(View.VISIBLE);
                str="City :";

               Toast.makeText(getApplicationContext(),instsFilter.get(0).getCity()+""+сhildDataList.get(groupPosition).get(childPosition).get("name"),Toast.LENGTH_LONG).show();

               // insts.clear();
            //    insts.addAll(instsFilter);
                boxAdapter.notifyDataSetChanged();
                quantity.setText(String.valueOf(insts.size()) + getString(R.string.Instructors));


                return false;

            }

        });
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
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
        Instructors instructors = insts.get(position);

        /*Toast.makeText(this, instructors.toString(), Toast.LENGTH_LONG).show();*/
        intent = new Intent(InstructorAdapterActivity.this, TabInstructorActivity.class);
        /*name=(TextView)findViewById(R.id.tvName);*/
        String n = instructors.name;
        /*city=(TextView)findViewById(R.id.tvCity);*/
        String c = instructors.city;
        String e = instructors.experience;
        int r = instructors.rating;
        int a = instructors.age;
        String image = instructors.avatar;
        int price = instructors.pricePerHours;
        String url = instructors.url;
        String worDays = instructors.workingDays;
        String worHours = instructors.workingHours;


        intent.putExtra("name", n);
        intent.putExtra("city", c);
        intent.putExtra("exper", e);
        intent.putExtra("rating", r);
        intent.putExtra("age", a);
        intent.putExtra("avatar", image);
        intent.putExtra("price", price);
        intent.putExtra("url", url);
        intent.putExtra("workDay", worDays);
        intent.putExtra("workHour", worHours);


        startActivity(intent);
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "InstructorAdapter Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.inetex.drivinginstructorapplication/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "InstructorAdapter Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.inetex.drivinginstructorapplication/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }

    public class GetInstructor extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {

            ArrayList<String> mCityArray = new ArrayList<>();
            for (int i = 0; i < insts.size(); i++) {
                if (insts.get(i) != null) {
                    if (mCityArray.contains(insts.get(i).getCity())) i++;
                    else
                        mCityArray.add(insts.get(i).getCity());
                } else {
                    i++;
                }
            }
            //complete the collection of groups from the array with the names of groups
            for (String group : mGroupsArray) {
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
            сhildDataItemList = new ArrayList<>();
            for (String vichele : mTypeVehicleArray) {
                map = new HashMap<>();
                map.put("name", vichele);
                сhildDataItemList.add(map);
            }
            сhildDataList.add(сhildDataItemList);
            //create a collection of items for the Transmission
            сhildDataItemList = new ArrayList<>();
            for (String transmission : mTransmissionArray) {
                map = new HashMap<>();
                map.put("name", transmission);
                сhildDataItemList.add(map);
            }
            сhildDataList.add(сhildDataItemList);
            //create a collection of items for the Experiences
            сhildDataItemList = new ArrayList<>();
            for (String exper : mExperienceArray) {
                map = new HashMap<>();
                map.put("name", exper);
                сhildDataItemList.add(map);
            }
            сhildDataList.add(сhildDataItemList);
            //create a collection of items for the Ratings
            сhildDataItemList = new ArrayList<>();
            for (String rating : mRatingArray) {
                map = new HashMap<>();
                map.put("name", rating);
                сhildDataItemList.add(map);
            }
            сhildDataList.add(сhildDataItemList);
            //create a collection of items for the Sex
            сhildDataItemList = new ArrayList<>();
            for (String sex : mSexArray) {
                map = new HashMap<>();
                map.put("name", sex);
                сhildDataItemList.add(map);
            }
            сhildDataList.add(сhildDataItemList);


            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {


            super.onPostExecute(aVoid);
            quantity.setText(String.valueOf(insts.size()) + " Instructors");

        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }
    public ArrayList<Instructors> fillData(ArrayList<Instructors> insts,Cursor cursor){
        try {
            if (cursor.moveToFirst()) {
                do {
                    Instructors i = new Instructors();

                    i.setName(cursor.getString(1));
                    i.setCity(cursor.getString(2));
                    i.setAvatar(cursor.getString(3));
                    i.setAge(cursor.getInt(4));
                    i.setExperience(cursor.getString(5));
                    i.setRating(cursor.getInt(6));
                    i.setTypeVehicle(cursor.getString(7));
                    i.setPricePerHours(cursor.getInt(8));
                    i.setUrl(cursor.getString(9));
                    i.setWorkingDays(cursor.getString(10));
                    i.setWorkingHours(cursor.getString(11));
                    i.setEmail(cursor.getString(12));
                    i.setPhon(cursor.getString(13));
                    i.setSchool(cursor.getString(14));
                    insts.add(i);


                } while (cursor.moveToNext());

            }
        }catch (Exception e){}
        finally {
            cursor.close();
        }
        return insts;
    }
}



