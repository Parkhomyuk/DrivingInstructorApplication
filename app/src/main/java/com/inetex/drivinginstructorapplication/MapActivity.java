package com.inetex.drivinginstructorapplication;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Address;
import android.location.Geocoder;

import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import android.location.LocationListener;

import com.google.android.gms.common.server.converter.StringToIntConverter;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.*;
import com.inetex.drivinginstructorapplication.data.GetInstructors;
import com.inetex.drivinginstructorapplication.data.InstructorDbHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;


public class MapActivity extends Activity implements OnMapReadyCallback, LocationListener {
    GoogleMap googleMap;
    boolean mapReady = false;
    Double latitude;
    Double longtitude;
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    protected LocationRequest mLocationRequest;
    Marker marker;
    String cityName;
    Cursor cursor;
    InstructorDbHelper mdHelper;
    LatLng pos;
    ArrayList<Instructors> insts = new ArrayList<Instructors>();
    ArrayList<String> instructorCity = new ArrayList<>();
    HashMap<String, Integer> instructorQuantity = new HashMap<>();
    Marker instructorMarker;
    ListView lvMain;

    String where=null;
    String addressStr;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_layout);

        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.mapView);
        mapFragment.getMapAsync(this);

        GetInstructors instructorsDB= new GetInstructors(this,cursor,mdHelper,insts);
        instructorsDB.execute();
        //AsyncTask filldata
        GetInstructorMap instructorsMap = new GetInstructorMap();
        instructorsMap.execute();

        //
        final EditText textName = (EditText) findViewById(R.id.findCity);
        textName.setOnKeyListener(new View.OnKeyListener() {
                                      public boolean onKey(View v, int keyCode, KeyEvent event) {
                                          if (event.getAction() == KeyEvent.ACTION_DOWN &&
                                                  (keyCode == KeyEvent.KEYCODE_ENTER)) {

                                              cityName = textName.getText().toString();
                                              String addressStr = cityName + ",IL";
                                              Geocoder geoCoder = new Geocoder(MapActivity.this, Locale.getDefault());

                                              try {
                                                  List<Address> addresses =
                                                          geoCoder.getFromLocationName(addressStr, 1);
                                                  if (addresses.size() > 0) {
                                                      latitude = addresses.get(0).getLatitude();
                                                      longtitude = addresses.get(0).getLongitude();
                                                  }

                                              } catch (IOException e) { // TODO Auto-generated catch block
                                                  e.printStackTrace();
                                              }


                                              pos = new LatLng(latitude, longtitude);
                                             /* googleMap.addMarker(new MarkerOptions().icon(
                                                      BitmapDescriptorFactory
                                                              .defaultMarker(BitmapDescriptorFactory.HUE_RED))
                                                      .position(pos));*/

                                              CameraPosition target = CameraPosition.builder().target(pos).zoom(8).build();
                                              googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(target), 3000, null);
                                              return true;
                                          }
                                          return false;
                                      }
                                  }
        );


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

    }

    @Override
    public void onMapReady(GoogleMap map) {
        mapReady = true;
        googleMap = map;
        LatLng ny = new LatLng(32.08088, 34.78057);


        CameraPosition target = CameraPosition.builder().target(ny).zoom(8).build();


        googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(target));

        try {
            createMarkerWithLocation();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private void createMarkerWithLocation() throws IOException {
        //* Use the LocationManager class to obtain GPS locations *//*
        LocationManager mlocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 300000, 40, this);

        Location location = mlocManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        //*check both providers even for lastKnownLocation*//*
        if (location == null)
            location = mlocManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        if (location != null) {

            double latitude = location.getLatitude();
            double longitude = location.getLongitude();

            LatLng currentLatLng = new LatLng(latitude, longitude);

            if (isConnected(this)) {
                Geocoder gCoder = new Geocoder(MapActivity.this);
                List<Address> addresses = gCoder.getFromLocation(latitude, longitude, 1);
                String address = addresses.get(0).getAddressLine(0);
                String city = addresses.get(0).getAddressLine(1);
                String country = addresses.get(0).getAddressLine(2);
                Toast.makeText(this, country + ", " + city + ", " + address, Toast.LENGTH_SHORT).show();

                marker = googleMap.addMarker(new MarkerOptions()
                        .position(currentLatLng)
                        .title("My location: ")
                        .snippet(city)
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.streetview))
                );

                if(marker.isVisible())
                    marker.showInfoWindow();
            }



            MapActivity.this.marker.showInfoWindow();
        }

        //-----------add markers------//

        for (Map.Entry<String, Integer> entry : instructorQuantity.entrySet()) {
              addressStr = entry.getKey();
            Geocoder geoCoder = new Geocoder(MapActivity.this, Locale.getDefault());
            try {
                List<Address> addresses =
                        geoCoder.getFromLocationName(addressStr, 1);
                if (addresses.size() > 0) {
                    latitude = addresses.get(0).getLatitude();
                    longtitude = addresses.get(0).getLongitude();
                }

            } catch (IOException e) { // TODO Auto-generated catch block
                e.printStackTrace();
            }
            LatLng positionInstructors = new LatLng(latitude, longtitude);
            instructorMarker = googleMap.addMarker(new MarkerOptions()
                    .title(addressStr)
                    .snippet("Instructors " + entry.getValue())
                    .alpha(0.8f)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.drivingschoolicon))
                    .position(positionInstructors));

            MapActivity.this.instructorMarker.showInfoWindow();

        }

        //-----------add markers------//
//------ markerListener

        // настраиваем список
        lvMain = (ListView) findViewById(R.id.instVar);
        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {

            @Override
            public boolean onMarkerClick(Marker marker) {
                instructorMarker.getTitle();
                where=addressStr;
                Intent intent = new Intent(MapActivity.this, InstructorAdapterActivity.class);

                startActivity(intent);


                return false;
            }
        });
//------ markerListener

    }

    public static boolean isConnected(Context context) {
        NetworkInfo ni = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        return (ni != null && ni.isAvailable() && ni.isConnected());
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Map Page", // TODO: Define a title for the content shown.
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
                "Map Page", // TODO: Define a title for the content shown.
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



    class GetInstructorMap extends AsyncTask<Void, Void, Void> {


        public GetInstructorMap() {
        }


        @Override
        protected Void doInBackground(Void... params) {
            try {

                for (int i = 0; i <  insts.size(); i++) {
                    String quant = insts.get(i).getCity();
                    if (instructorQuantity.containsKey(quant)) {
                        int value = instructorQuantity.get(quant).intValue();
                        instructorQuantity.put(quant, value + 1);
                    } else instructorQuantity.put(quant, 1);
                }


            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }


    }





}