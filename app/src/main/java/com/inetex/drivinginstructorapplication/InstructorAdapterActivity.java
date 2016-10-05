package com.inetex.drivinginstructorapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class InstructorAdapterActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    ArrayList<Instructors> insts = new ArrayList<Instructors>();
    BoxAdapter boxAdapter;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor_adapter);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

      FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //-------------------------adapter------------------
        fillData();
        boxAdapter = new BoxAdapter(this, insts);
        // настраиваем список
        ListView lvMain = (ListView) findViewById(R.id.instVar);


        lvMain.setAdapter(boxAdapter);
        //-------------------------adapter------------------
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    // генерируем данные для адаптера
    void fillData() {

        insts.add(new Instructors("Dodik Moshe", "Tel Aviv",R.drawable.christophe," 24 year","2 year","24","A"));
        insts.add(new Instructors("Angelina Jolie","Tel Aviv",R.drawable.angela," 40 year","12 year"," 84","A B"));
        insts.add(new Instructors("Tom Zat","Netania",R.drawable.z," 27 year","4 year"," 42","A B C"));
        insts.add(new Instructors("Bruce Willis","Netania",R.drawable.bruce," 52 year","15 year"," 26","A B C D"));
        insts.add(new Instructors("Zipora Zukerman","Irusalim",R.drawable.savta," 60 year","27 year"," 17","A B "));
        insts.add(new Instructors("Tom Cruze","Irusalim",R.drawable.tom," 50 year","17 year"," 85","A B C D"));
        insts.add(new Instructors("Bill Geist","Tel Aviv",R.drawable.bill," 62 year","22 year"," 48","A B C D"));
        insts.add(new Instructors("Rostik Shahar","Haifa",R.drawable.toto," 27 year","4 year"," 12","B"));
        insts.add(new Instructors("Barack Abama","Haifa",R.drawable.barack," 55 year","20 year"," 44","A B"));
        insts.add(new Instructors("Jastin Timberlake","Rehovot",R.drawable.tim," 35 year","11 year"," 48","A B C"));
        insts.add(new Instructors("Brad Pit","Rehovot",R.drawable.brad," 45 year","18 year"," 68","A B C D"));
        insts.add(new Instructors("Haim Kaz","Netania",R.drawable.buch," 52 year","25 year"," 55","C D"));
        insts.add(new Instructors("Rafik Golubian","Tel Aviv",R.drawable.daty," 34 year","8 year"," 24","A C D"));
        insts.add(new Instructors("Lusy Zack","Tel Aviv",R.drawable.lucy," 24 year","1 year"," 24","A B"));
        insts.add(new Instructors("Jack Nicolson","Tel Aviv",R.drawable.nicola," 62 year","28 year"," 88","A B C D"));
        insts.add(new Instructors("Yosy Ferdman","Ashdod",R.drawable.saba," 74 year","35 year"," 98","A B C D"));
        insts.add(new Instructors("Rohel Bell","Ashkelon",R.drawable.savta," 64 year","15 year"," 55","A B"));
        insts.add(new Instructors("David Zukerman","Ashdod",R.drawable.saba2," 88 year","55 year"," 102","A B C D"));
    }

}
