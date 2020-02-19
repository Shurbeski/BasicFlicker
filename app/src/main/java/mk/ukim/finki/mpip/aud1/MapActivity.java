package mk.ukim.finki.mpip.aud1;

import android.Manifest;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Map;

import mk.ukim.finki.mpip.aud1.constants.Constants;
import mk.ukim.finki.mpip.aud1.services.FetchAddressIntentService;
import mk.ukim.finki.mpip.aud1.viewmodels.MapMarkerViewModel;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback, LocationListener {

    private static final int REQUEST_ACCESS_FINE_LOCATION = 1;
    private LocationManager locationManager = null;
    private GoogleMap googleMap = null;
    private MapMarkerViewModel mapMarkerViewModel;
    private Marker marker;
    protected Location mLastLocation;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        mapMarkerViewModel = ViewModelProviders.of(MapActivity.this).get(MapMarkerViewModel.class);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);
        bindObservers();
    }

    @Override
    protected void onStart() {
        super.onStart();
        startGPSUpdating();
    }


    @Override
    protected void onStop() {
        super.onStop();
        locationManager.removeUpdates(MapActivity.this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        LatLng sydney = new LatLng(-33.852, 151.211);
        MarkerOptions markerOptions = new MarkerOptions()
                .position(sydney)
                .draggable(false);

        marker = googleMap.addMarker(markerOptions);
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    @Override
    public void onLocationChanged(Location location) {
        mapMarkerViewModel.setLatLng(location);
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    public void getGeocode(View view) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        startIntentService(location);
    }

    public void startGPSUpdating() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MapActivity.this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION},
                    REQUEST_ACCESS_FINE_LOCATION);
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
    }

    private void bindObservers() {
        mapMarkerViewModel.getLatLng().observe(MapActivity.this, new Observer<Location>() {
            @Override
            public void onChanged(@Nullable Location location) {
                if (marker==null) {
                    return;
                }
                marker.setPosition(new LatLng(location.getLatitude(),location.getLongitude()));
                marker.setTitle("Current location from: "+location.getProvider());
                marker.setSnippet("Accuracy: "+location.getAccuracy() + " Attitude:"+(int)location.getAltitude());
                marker.setPosition(new LatLng(location.getLatitude(), location.getLongitude()));
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(marker.getPosition(), 16f));
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        startGPSUpdating();
    }

    protected void startIntentService(Location location) {
        Intent intent = new Intent(this, FetchAddressIntentService.class);
        intent.putExtra(Constants.LOCATION_DATA_EXTRA, location);
        startService(intent);
    }


}
