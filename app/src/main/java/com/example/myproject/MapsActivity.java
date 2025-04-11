package com.example.myproject;


import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;


public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMyLocationButtonClickListener, GoogleMap.OnMyLocationClickListener, ActivityCompat.OnRequestPermissionsResultCallback {

    private GoogleMap myMap;
    private SearchView mapSearchView;

    private ImageButton imageButton;

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    private boolean permissionDenied = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        mapSearchView = findViewById(R.id.mapSearch);
        imageButton = findViewById(R.id.bookstores);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);


        mapSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                String location = mapSearchView.getQuery().toString();
                List<Address> addressList = null;

                if (location != null && !location.isEmpty()) {
                    Geocoder geocoder = new Geocoder(MapsActivity.this);
                    try {
                        addressList = geocoder.getFromLocationName(location, 1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    if (addressList != null && !addressList.isEmpty()) {
                        Address address = addressList.get(0);
                        LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                        myMap.addMarker(new MarkerOptions().position(latLng).title(location));
                        myMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
                    } else {
                        Toast.makeText(MapsActivity.this, "No results found for " + location, Toast.LENGTH_SHORT).show();
                    }
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFavoriteBookstore();
            }
        });

        mapFragment.getMapAsync(this);
    }

    private void showFavoriteBookstore() {
        LatLng noviSadLatLng = new LatLng( 45.256509247747395, 19.847801712264832);
        myMap.addMarker(new MarkerOptions().position(noviSadLatLng).title("Favorite bookstore in Novi Sad"));
        myMap.animateCamera(CameraUpdateFactory.newLatLngZoom(noviSadLatLng, 12));
    }
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {

        myMap = googleMap;
        myMap.setOnMyLocationButtonClickListener(this);
        myMap.setOnMyLocationClickListener(this);
        enableMyLocation();

    }

    @SuppressLint("MissingPermision")
    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            myMap.setMyLocationEnabled(true);
            return;
        }

        PermissionUtils.requestLocationPermissions(this, LOCATION_PERMISSION_REQUEST_CODE, true);

    }

    @Override
    public boolean onMyLocationButtonClick() {
        Toast.makeText(this, "MyLocation button clicked", Toast.LENGTH_SHORT).show();
        return false;
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {
        Toast.makeText(this, "Current location:\n" + location, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode != LOCATION_PERMISSION_REQUEST_CODE) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            return;
        }

        if (PermissionUtils.isPermissionGranted(permissions, grantResults,
                Manifest.permission.ACCESS_FINE_LOCATION) || PermissionUtils
                .isPermissionGranted(permissions, grantResults,
                        Manifest.permission.ACCESS_COARSE_LOCATION)) {
            enableMyLocation();
        } else {
            Toast.makeText(this, "Access denied", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        if (permissionDenied) {
            showMissingPermissionError();
            permissionDenied = false;
        }
    }

    private void showMissingPermissionError() {
        PermissionUtils.PermissionDeniedDialog
                .newInstance(true).show(getSupportFragmentManager(), "dialog");
    }
}