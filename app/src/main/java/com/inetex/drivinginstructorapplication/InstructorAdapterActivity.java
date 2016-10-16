package com.inetex.drivinginstructorapplication;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
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

import com.inetex.drivinginstructorapplication.data.GetInstructors;
import com.inetex.drivinginstructorapplication.data.InstructorContract;
import com.inetex.drivinginstructorapplication.data.InstructorDbHelper;

import java.io.File;
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
    InstructorDbHelper mdHelper;
    Cursor cursor;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor_adapter);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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
        //----AsyncTask filldata
        GetInstructors getInstructorsDB= new GetInstructors(this,cursor,mdHelper,insts);
        getInstructorsDB.execute();
        GetInstructor instructors = new GetInstructor();
        instructors.execute();
        //----AsyncTask filldata




        boxAdapter = new BoxAdapter(this, insts);

        // настраиваем список
        final ListView lvMain = (ListView) findViewById(R.id.instVar);
        lvMain.setOnItemClickListener(this);
        lvMain.setAdapter(boxAdapter);

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
                                + " : " +"Vau "+сhildDataList.get(groupPosition).get(childPosition).values()+" "+insts.get(0).getCity(),Toast.LENGTH_LONG).show();
                for(int i=0;i<insts.size();i++){
                    if(insts.get(i).getCity().equals(сhildDataList.get(groupPosition).get(childPosition).get("name"))){
                        instsFilter.add(insts.get(i));
                    }
                    else{
                        i++;
                    }
                }
               Toast.makeText(getApplicationContext(),instsFilter.get(0).getCity()+""+сhildDataList.get(groupPosition).get(childPosition).get("name"),Toast.LENGTH_LONG).show();

                insts.clear();
                insts.addAll(instsFilter);
                boxAdapter.notifyDataSetChanged();
                quantity.setText(String.valueOf(insts.size()) + " Instructors");





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
}



