package edu.psu.slparker.loyaltyapp;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class FragmentStores extends Fragment implements OnMapReadyCallback {

    private LocationManager locationManager;
    private LocationListener locationListener;
    private SupportMapFragment googleMapFragment;
    private FusedLocationProviderClient fusedLocationProvClient;
    private JSONArray results;
    private GoogleMap gMap;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stores, container, false);
        locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
        fusedLocationProvClient = LocationServices.getFusedLocationProviderClient(getContext());
        googleMapFragment = (SupportMapFragment) this.getChildFragmentManager().findFragmentById(R.id.google_map);
        googleMapFragment.getMapAsync(this);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Stores");
    }


    private void loadNearByPlaces(final double latitude, final double longitude) {

        try {
            Thread background = new Thread(new Runnable() {

                // After call for background.start this run method call
                public void run() {
                    try {
                        URL url = null;
                        url = new URL("https://maps.googleapis.com/maps/api/place/textsearch/json?query=starbucks&location=" + latitude + "," + longitude + "&radius=10&key=AIzaSyBIT_JX-qFj3asxTTJKALJkZSYVValcHeA");
                        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                        String inputLine;
                        String jsonResult = "";
                        while ((inputLine = in.readLine()) != null) {
                            jsonResult += inputLine;
                        }
                        in.close();
                        results = new JSONObject(jsonResult).getJSONArray("results");

                    } catch (Throwable t) {
                        // just end the background thread
                    }
                }

            });
            background.start();
            background.join();
            parseGoogleResults();

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        gMap = googleMap;
        if (ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(),
                android.Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION}, 200);
        }
        gMap.setMyLocationEnabled(true);
        gMap.getUiSettings().setCompassEnabled(true);
        gMap.getUiSettings().setZoomControlsEnabled(true);

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5,
                5, new LocationListener(){
                    // @Override
                    public void onStatusChanged(String provider, int status, Bundle extras) {
                        // TODO locationListenerGPS onStatusChanged

                    }

                    @Override
                    public void onProviderEnabled(String provider) {

                    }

                    @Override
                    public void onProviderDisabled(String provider) {

                    }

                    // @Override
                    public void onLocationChanged(Location location) {


                    }
                });

        showCurrentLocation();
    }

    private void showCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(getContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION}, 200);
        }

        fusedLocationProvClient.getLastLocation()
                .addOnSuccessListener(new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {

                        if (location != null) {
                            double latitude = location.getLatitude();
                            double longitude = location.getLongitude();

                            LatLng latLng = new LatLng(latitude, longitude);
                            gMap.addMarker(new MarkerOptions().position(latLng).title("My Location"));
                            gMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                            gMap.animateCamera(CameraUpdateFactory.zoomTo(15));

                            loadNearByPlaces(latitude, longitude);
                        }
                    }
                });
    }

    private void parseGoogleResults()
    {
        for( int i = 0; i < results.length(); i++)
        {
            try {
                JSONObject jsonObject = results.getJSONObject(i).getJSONObject("geometry").getJSONObject("location");
                double latitude = jsonObject.getDouble("lat");
                double longitude = jsonObject.getDouble("lng");
                String title = results.getJSONObject(i).getString("name");

                LatLng latLng = new LatLng(latitude, longitude);
                gMap.addMarker(new MarkerOptions().position(latLng).title(title));
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
    }
}