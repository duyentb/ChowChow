package com.chowchow.os.chowchow.ui.view.main.view;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chowchow.os.chowchow.R;
import com.chowchow.os.chowchow.callback.DirectionFinderListener;
import com.chowchow.os.chowchow.helper.DirectionFinder;
import com.chowchow.os.chowchow.model.Attractions;
import com.chowchow.os.chowchow.model.Event;
import com.chowchow.os.chowchow.model.Hotel;
import com.chowchow.os.chowchow.model.Restaurant;
import com.chowchow.os.chowchow.model.Route;
import com.chowchow.os.chowchow.model.Shop;
import com.chowchow.os.chowchow.model.TourDetail;
import com.chowchow.os.chowchow.ui.view.main.view.attractions.AttractionsActivity;
import com.chowchow.os.chowchow.ui.view.main.view.event.EventActivity;
import com.chowchow.os.chowchow.ui.view.main.view.hotel.HotelActivity;
import com.chowchow.os.chowchow.ui.view.main.view.restaurant.RestaurantActivity;
import com.chowchow.os.chowchow.ui.view.main.view.shopping.ShoppingActivity;
import com.chowchow.os.chowchow.ui.view.main.view.tour.ItineraryDetailActivity;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class DirectionActivity extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener, DirectionFinderListener {
    SupportMapFragment locationMap;
    LocationRequest mLocationRequest;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    Marker mCurrLocationMarker;
    private GoogleMap mGoogleMap;
    private List<Marker> originMarkers = new ArrayList<>();
    private List<Marker> destinationMarkers = new ArrayList<>();
    private List<Polyline> polylinePaths = new ArrayList<>();
    private ProgressDialog progressDialog;
    private Attractions attractions;
    private Restaurant restaurant;
    private Event event;
    private Shop shop;
    private Hotel hotel;
    private TourDetail tourDetail;
    private String latAttractions;
    private String lngAttractions;
    private Button btnFindPath;
    private ImageView iv_back, imgAppName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_direction);

        imgAppName = (ImageView) findViewById(R.id.image_app);
        imgAppName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        // Use the attractions to populate the data into our views
        attractions = (Attractions) getIntent().getSerializableExtra(AttractionsActivity.ATTRACTIONS_DETAIL_KEY);
        restaurant = (Restaurant) getIntent().getSerializableExtra(RestaurantActivity.RESTAURANT_DETAIL_KEY);
        event = (Event) getIntent().getSerializableExtra(EventActivity.EVENT_DETAIL_KEY);
        shop = (Shop) getIntent().getSerializableExtra(ShoppingActivity.SHOPPING_DETAIL_KEY);
        hotel = (Hotel) getIntent().getSerializableExtra(HotelActivity.HOTEL_DETAIL_KEY);
        tourDetail = (TourDetail) getIntent().getSerializableExtra(ItineraryDetailActivity.ITINERARY_DETAIL_KEY);

        if (attractions != null) {
            latAttractions = attractions.getLat();
            lngAttractions = attractions.getLng();
            Log.d("ChauNB","Attractions");
        } else if (restaurant != null) {
            latAttractions = restaurant.getLat();
            lngAttractions = restaurant.getLng();
            Log.d("ChauNB","Restaurant");
        } else if (event != null) {
            latAttractions = event.getLat();
            lngAttractions = event.getLng();
            Log.d("ChauNB","Event");
        } else if (shop != null) {
            latAttractions = shop.getLat();
            lngAttractions = shop.getLng();
            Log.d("ChauNB","Shopping");
        } else if (hotel != null) {
            latAttractions = hotel.getLat();
            lngAttractions = hotel.getLng();
            Log.d("ChauNB","Hotel");
        } else if (tourDetail != null) {
            latAttractions = tourDetail.getLat();
            lngAttractions = tourDetail.getLong();
            Log.d("ChauNB","TourDetail");
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        locationMap = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map_direction);
        locationMap.getMapAsync(this);
        btnFindPath = (Button) findViewById(R.id.btnFindPath);
        btnFindPath.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGoogleApiClient.reconnect();
            }
        });

    }



    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();

        //stop location updates when Activity is no longer active
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        mGoogleMap = googleMap;

        //Initialize Google Play Services
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                //Location Permission already granted
                buildGoogleApiClient();
                mGoogleMap.setMyLocationEnabled(true);
            } else {
                //Request Location Permission
                checkLocationPermission();
            }
        }
        else {
            buildGoogleApiClient();
            mGoogleMap.setMyLocationEnabled(true);
        }

    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnected(Bundle bundle) {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(1000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {}

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {}

    @Override
    public void onLocationChanged(Location location)
    {
        mLastLocation = location;
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        }

        //Place current location marker
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("Vị trí hiện tại của bạn");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
        mCurrLocationMarker = mGoogleMap.addMarker(markerOptions);

        //move map camera
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,15f));

        sendRequest();

        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
    }

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(this)
                        .setTitle("Location Permission Needed")
                        .setMessage("This app needs the Location permission, please accept to use location functionality")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(getParent(),
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION );
                            }
                        })
                        .create()
                        .show();


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION );
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        if (mGoogleApiClient == null) {
                            buildGoogleApiClient();
                        }
                        mGoogleMap.setMyLocationEnabled(true);
                    }

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    private void sendRequest() {
        if (mLastLocation != null) {
            String origin = mLastLocation.getLatitude() + "," + mLastLocation.getLongitude();
            String destination = latAttractions + "," + lngAttractions;

            try {
                new DirectionFinder(this, origin, destination).execute();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onDirectionFinderStart() {
        progressDialog = ProgressDialog.show(this, "Vui lòng đợi...",
                "Đang tìm đường..!", true);

        if (originMarkers != null) {
            for (Marker marker : originMarkers) {
                marker.remove();
            }
        }

        if (destinationMarkers != null) {
            for (Marker marker : destinationMarkers) {
                marker.remove();
            }
        }

        if (polylinePaths != null) {
            for (Polyline polyline:polylinePaths ) {
                polyline.remove();
            }
        }
    }

    @Override
    public void onDirectionFinderSuccess(List<Route> routes) {
        progressDialog.dismiss();
        polylinePaths = new ArrayList<>();
        originMarkers = new ArrayList<>();
        destinationMarkers = new ArrayList<>();

        mGoogleMap.clear();
        String title = "";
        String snippet = "";
        for (Route route : routes) {
            mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(route.startLocation, 16));
            ((TextView) findViewById(R.id.tvDuration)).setText(route.duration.text);
            ((TextView) findViewById(R.id.tvDistance)).setText(route.distance.text);

            originMarkers.add(mGoogleMap.addMarker(new MarkerOptions()
                    .title(route.startAddress)
                    .position(route.startLocation)));

            if (attractions != null) {
                title = attractions.getAttrName();
                snippet = attractions.getAttrAddress();
            } else if (restaurant != null) {
                title = restaurant.getRestaurantName();
                snippet = restaurant.getRestaurantAddress();
            } else if (shop != null) {
                title = shop.getShopName();
                snippet = shop.getShopAddress();
            } else if (hotel != null) {
                title = hotel.getHotelName();
                snippet = hotel.getHotelAddress();
            } else if (event != null) {
                title = event.getEventName();
                snippet = event.getEventAddress();
            } else if (tourDetail != null) {
                title = tourDetail.getAttrName();
                snippet = tourDetail.getAttrAddress();
            }

            destinationMarkers.add(mGoogleMap.addMarker(new MarkerOptions()
                    .title(title)
                    .snippet(snippet)
                    .position(route.endLocation)));

            PolylineOptions polylineOptions = new PolylineOptions().
                    geodesic(true).
                    color(this.getResources().getColor(R.color.blue)).
                    width(10);

            for (int i = 0; i < route.points.size(); i++)
                polylineOptions.add(route.points.get(i));

            polylinePaths.add(mGoogleMap.addPolyline(polylineOptions));
        }
    }
}
