package com.inetex.drivinginstructorapplication;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.graphics.drawable.Drawable;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpCookie;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Кирилл on 20.09.2016.
 */
public class TabInstructorActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private String textActivityVich;
    private String textActivityVichS;
    private String textActivityVichT;
    private String textActivityMe;
    private String textActivityRev;
    String txtName;
    String txtEm;

    ImageView image;
    EditText address, subject, emailtext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_instructor);
        txtName=getIntent().getStringExtra("name");
        String txtCity=getIntent().getStringExtra("city");
        int txtExp=getIntent().getIntExtra("exper",0);
        float txtRat=getIntent().getFloatExtra("rating",2);
        int txtAge=getIntent().getIntExtra("age",0);
        txtEm=getIntent().getStringExtra("email");

        String txtUrl=getIntent().getStringExtra("url");
        String txtwd=getIntent().getStringExtra("workDay");
        String txth=getIntent().getStringExtra("workHour");
        String  txtr=getIntent().getStringExtra("transmission");
        String txts=getIntent().getStringExtra("school");

        String avatar=getIntent().getStringExtra("avatar");
        int price=getIntent().getIntExtra("price",0);
        String priceText=price+"";

      TextView name=(TextView)findViewById(R.id.atName);
        name.setText(txtName);
      TextView city =(TextView)findViewById(R.id.atCity);
        city.setText(txtCity);
      TextView exper =(TextView)findViewById(R.id.atExp);
        exper.setText(txtExp+"");
      TextView rating =(TextView)findViewById(R.id.atRat);
        rating.setText(txtRat+"");
      TextView age=(TextView)findViewById(R.id.atAge);
        age.setText(txtAge+"");
        image=(ImageView) findViewById(R.id.im);
       loadImageFromAsset(avatar);

        final RatingBar smallRatingBar = (RatingBar) findViewById(R.id.ratingBar_small);

        smallRatingBar.setNumStars(5);
        smallRatingBar.setStepSize(0.5f);
        smallRatingBar.setRating(txtRat/2);


     TextView txtPrice=(TextView)findViewById(R.id.atPrice);
        txtPrice.setText(priceText);
      TextView url=(TextView)findViewById(R.id.atURL);
        url.setText(txtUrl);

      TextView workDay=(TextView)findViewById(R.id.atDays);
        workDay.setText(txtwd);
      TextView workHour=(TextView)findViewById(R.id.atHours);
        workHour.setText(txth);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        textActivityVich=txtName;
        textActivityVichS=txts;
        textActivityVichT=txtr;
        textActivityMe="I think that the best thing to do is go to a rally school. You can waste a lot of time and money by driving cars around not knowing what you’re doing. The best way to start is to let someone who has the experience teach you how to do it. I’ve been to several rally schools, the main one being Team O’Neil in the States. That was the first place I ever went and I got a lot of knowledge out of them. Travis Pastrana had gone there and I thought it looked fun, so I wanted to try and drive a rally car. I absolutely loved it but also found that I had a pretty decent amount of talent inside the car and since then I’ve just been pushing to learn more and more. Well the main thing is that I absolutely doing all this stuff. So the idea of me being able to get in a car and be able to drive all these courses at a demo is just for fun. But at a competition, like X Games or WRC, the competitive side takes over and I just want to win.";
        textActivityRev="No comments yet";
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabTabInstr);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recordToInstructor();

            }
        });

    }
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        //Fragment ONE of TableInstructor
        Bundle bundle1 = new Bundle();

        bundle1.putString("text", textActivityVich);
        bundle1.putString("texts", textActivityVichS);
        bundle1.putString("textr", textActivityVichT);
        OneFragment fragmentOne = new OneFragment();
        fragmentOne.setArguments(bundle1);

        //Fragment TWO of TableInstructor
        Bundle bundle2= new Bundle();
        bundle2.putString("text2",textActivityMe);
        TwoFragmet fragmentTwo= new TwoFragmet();
        fragmentTwo.setArguments(bundle2);
        //Fragment THREE of TableInstructor
        Bundle bundle3 = new Bundle();
        bundle3.putString("text3", textActivityRev);
        ThreeFragment fragmentThree = new ThreeFragment();
        fragmentThree.setArguments(bundle3);

        adapter.addFragment(fragmentOne, "VEHICLE");
        adapter.addFragment(fragmentTwo, "ABOUT ME");
        adapter.addFragment(fragmentThree, "REVIEWS");

        viewPager.setAdapter(adapter);
    }
    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
    @SuppressLint("ValidFragment")
    public static class OneFragment extends Fragment {
        public OneFragment() {
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
       /* // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_one, container, false);*/
            View v=inflater.inflate(R.layout.fragment_one,container,false);
            TextView tv=((TextView)v.findViewById(R.id.frVehc));
            TextView tranM=((TextView)v.findViewById(R.id.frVehcM));
            tranM.setText(getArguments().getString("textr"));
            TextView tranA=((TextView)v.findViewById(R.id.frVehcA));
            tranA.setText(getArguments().getString("textr"));
            TextView tranS=((TextView)v.findViewById(R.id.frSchool));
            tranS.setText(getArguments().getString("texts"));
            tv.setText(getArguments().getString("text"));

            return v;
        }
    }
    @SuppressLint("ValidFragment")
    public static class TwoFragmet extends Fragment {

        public TwoFragmet() {
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // Inflate the layout for this fragment
       /* return inflater.inflate(R.layout.fragment_two, container, false);*/

            View v=inflater.inflate(R.layout.fragment_two,container,false);
            TextView tv=((TextView)v.findViewById(R.id.frMe));
            tv.setText(getArguments().getString("text2"));
            return v;
        }
    }
    public static class ThreeFragment extends Fragment {
        public ThreeFragment() {
        }
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // Inflate the layout for this fragment
       /* return inflater.inflate(R.layout.fragment_three, container, false);*/
            View v=inflater.inflate(R.layout.fragment_three,container,false);
            TextView tv=((TextView)v.findViewById(R.id.frRev));
            tv.setText(getArguments().getString("text3"));
            return v;
        }

    }

    public void loadImageFromAsset(String str) {
        try {
            // получаем входной поток
            InputStream ims = getAssets().open(str);
            // загружаем как Drawable
            Drawable d = Drawable.createFromStream(ims, null);
            // выводим картинку в ImageView
            image.setImageDrawable(d);
        } catch (IOException ex) {
            return;
        }
    }
    private void recordToInstructor(){
        LayoutInflater inflater = LayoutInflater.from(TabInstructorActivity.this);
        View subView = inflater.inflate(R.layout.record_to_instructor, null);
        Button button =(Button)findViewById(R.id.recInstr);

        AlertDialog.Builder builder = new AlertDialog.Builder(TabInstructorActivity.this);
        builder.setTitle("      Record to the Instructor \n"+"    "+txtName)

                .setView(subView)
                .setCancelable(false)

                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });


        AlertDialog alert = builder.create();

        alert.show();
    }
}

