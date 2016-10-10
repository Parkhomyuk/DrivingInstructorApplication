package com.inetex.drivinginstructorapplication;


import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;

import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.common.api.GoogleApiClient;
import android.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.*;
import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class MapActivity extends Activity implements OnMapReadyCallback, LocationListener {
    GoogleMap googleMap;
    boolean mapReady = false;
    EditText editText;
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    protected LocationRequest mLocationRequest;
    Marker marker;
    String cityName;
    Double  latitude;
    Double  longtitude;
    LatLng pos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_layout);
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.mapView);
        mapFragment.getMapAsync(this);
        final EditText textName=(EditText)findViewById(R.id.findCity);
        textName.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {

                    cityName = textName.getText().toString();
                    String addressStr = cityName+ ",IL";
                    Geocoder geoCoder = new Geocoder(MapActivity.this, Locale.getDefault());

                    try {
                        List<Address> addresses =
                                geoCoder.getFromLocationName(addressStr, 1);
                        if (addresses.size() >  0) {
                            latitude = addresses.get(0).getLatitude();
                            longtitude =addresses.get(0).getLongitude(); }

                    } catch (IOException e) { // TODO Auto-generated catch block
                        e.printStackTrace(); }


                    pos = new LatLng(latitude, longtitude);
                    googleMap.addMarker(new MarkerOptions().icon(
                            BitmapDescriptorFactory
                                    .defaultMarker(BitmapDescriptorFactory.HUE_RED))
                            .position(pos));
                    /*googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(pos, 15));
                    googleMap.animateCamera(CameraUpdateFactory.zoomTo(8), 2000, null);*/
                    CameraPosition target=CameraPosition.builder().target(pos).zoom(8).build();
                    googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(target),3000,null);
                    return true;
                }
                return false;
            }
        }
        );

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

       mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 300000, 40,  this);

       Location location = mlocManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
       //*check both providers even for lastKnownLocation*//*
       if (location == null)
           location = mlocManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
       if(location != null) {

           double latitude = location.getLatitude();
           double longitude = location.getLongitude();

           LatLng currentLatLng = new LatLng(latitude, longitude);

           if(isConnected(this)) {
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

               marker.showInfoWindow();

           }
       }
   }

    public static boolean isConnected(Context context) {
        NetworkInfo ni = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        return (ni!=null && ni.isAvailable() && ni.isConnected());
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

}