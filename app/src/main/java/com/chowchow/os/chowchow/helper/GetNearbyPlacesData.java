package com.chowchow.os.chowchow.helper;

import android.os.AsyncTask;
import android.util.Log;

import com.chowchow.os.chowchow.R;
import com.chowchow.os.chowchow.constant.Constant;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

public class GetNearbyPlacesData extends AsyncTask<Object, String, String> {

    private String googlePlacesData;
    private GoogleMap mMap;
    private String url;
    private String type;

    @Override
    protected String doInBackground(Object... params) {
        try {
            Log.d("GetNearbyPlacesData", "doInBackground entered");
            mMap = (GoogleMap) params[0];
            url = (String) params[1];
            type = (String) params[2];
            DownloadUrl downloadUrl = new DownloadUrl();
            googlePlacesData = downloadUrl.readUrl(url);
            Log.d("GooglePlacesReadTask", "doInBackground Exit");
        } catch (Exception e) {
            Log.d("GooglePlacesReadTask", e.toString());
        }
        return googlePlacesData;
    }

    @Override
    protected void onPostExecute(String result) {
        Log.d("GooglePlacesReadTask", "onPostExecute Entered");
        List<HashMap<String, String>> nearbyPlacesList = null;
        DataParser dataParser = new DataParser();
        nearbyPlacesList =  dataParser.parse(result);
        ShowNearbyPlaces(nearbyPlacesList);
        Log.d("GooglePlacesReadTask", "onPostExecute Exit");
    }

    private void ShowNearbyPlaces(List<HashMap<String, String>> nearbyPlacesList) {
        for (int i = 0; i < nearbyPlacesList.size(); i++) {
            Log.d("onPostExecute","Entered into showing locations");
            MarkerOptions markerOptions = new MarkerOptions();
            HashMap<String, String> googlePlace = nearbyPlacesList.get(i);
            double lat = Double.parseDouble(googlePlace.get("lat"));
            double lng = Double.parseDouble(googlePlace.get("lng"));
            String placeName = googlePlace.get("place_name");
            String vicinity = googlePlace.get("vicinity");
            LatLng latLng = new LatLng(lat, lng);
            String types = googlePlace.get("types");
            Log.d("ShowNearbyPlaces types ",types);

            markerOptions.position(latLng);
            markerOptions.title(placeName + " : " + vicinity);
            if (Constant.RESTAURENT.equals(type)) {
                markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker_restaurant_36dp));
            } else if (Constant.HOTEL.equals(type)) {
                markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker_hotel_36dp));
            } else if (Constant.HOSPITAL.equals(type)) {
                markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker_hostpital_36dp));
            } else if (Constant.GAS_STATION.equals(type)) {
                markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker_gas_36dp));
            } else if (Constant.ATM.equals(type)) {
                markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker_atm_36dp));
            } else if (Constant.CAFE.equals(type)) {
                markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_marker_coffee_36dp));
            } else {
                markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
            }
            mMap.addMarker(markerOptions);

            //move map camera
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(14));
        }
    }
}