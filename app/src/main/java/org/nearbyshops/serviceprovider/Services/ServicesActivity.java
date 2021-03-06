package org.nearbyshops.serviceprovider.Services;

import android.Manifest;
import android.app.SearchManager;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.core.view.MenuItemCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.wunderlist.slidinglayer.SlidingLayer;


import org.nearbyshops.serviceprovider.Interfaces.NotifyLocation;
import org.nearbyshops.serviceprovider.Interfaces.NotifySearch;
import org.nearbyshops.serviceprovider.ItemCategoriesTabs.Interfaces.NotifySort;
import org.nearbyshops.serviceprovider.ItemCategoriesTabs.Interfaces.ToggleFab;
import org.nearbyshops.serviceprovider.R;
import org.nearbyshops.serviceprovider.Services.ServiceFragment.ServicesFragment;
import org.nearbyshops.serviceprovider.Services.SlidingLayerSort.SlidingLayerSortServices;
import org.nearbyshops.serviceprovider.Services.SubmitURLDialog.SubmitURLDialog;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ServicesActivity extends AppCompatActivity implements NotifySort, ToggleFab,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {


    // variables for fetching location
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    LocationRequest mLocationRequest;
//    Location locationResult;


    @BindView(R.id.slidingLayer)
    SlidingLayer slidingLayer;
    public static final String TAG_SLIDING_LAYER = "sliding_layer";

    public static final String TAG_FRAGMENT = "tag_fragment";

    @BindView(R.id.fab) FloatingActionButton fab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//
//
////                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
////                        .setAction("Action", null).show();
//            }
//        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if(getSupportFragmentManager().findFragmentByTag(TAG_FRAGMENT) == null)
        {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container,new ServicesFragment(),TAG_FRAGMENT)
                    .commit();
        }



        // Location Code

        // Create an instance of GoogleAPIClient.
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }


        setupSlidingLayer();
    }


    @OnClick(R.id.fab)
    void fabClick()
    {
        showDialogSubmitURL();
    }


    void setupSlidingLayer()
    {

        ////slidingLayer.setShadowDrawable(R.drawable.sidebar_shadow);
        //slidingLayer.setShadowSizeRes(R.dimen.shadow_size);

        if(slidingLayer!=null) {
            slidingLayer.setChangeStateOnTap(true);
            slidingLayer.setSlidingEnabled(true);
            slidingLayer.setPreviewOffsetDistance(15);
            slidingLayer.setOffsetDistance(10);
            slidingLayer.setStickTo(SlidingLayer.STICK_TO_RIGHT);

//            DisplayMetrics metrics = new DisplayMetrics();
//            getWindowManager().getDefaultDisplay().getMetrics(metrics);

            //RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(250, ViewGroup.LayoutParams.MATCH_PARENT);

            //slidingContents.setLayoutParams(layoutParams);

            //slidingContents.setMinimumWidth(metrics.widthPixels-50);


            if (getSupportFragmentManager().findFragmentByTag(TAG_SLIDING_LAYER)==null)
            {
                getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.slidinglayerfragment,new SlidingLayerSortServices(),TAG_SLIDING_LAYER)
                        .commit();
            }

        }
    }




    @OnClick({R.id.icon_sort,R.id.text_sort})
    void sortClick()
    {
        slidingLayer.openLayer(true);
    }


    @Override
    protected void onStart() {
        super.onStart();

        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (mGoogleApiClient != null) {
            mGoogleApiClient.disconnect();
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        stopLocationUpdates();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},
                    1);


            return;
        }


        if (mGoogleApiClient == null) {

            return;
        }


//        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
//
//
//        if (mLastLocation != null) {
//
//            saveLocation(mLastLocation);
//
//
//        }else
//        {
//
//            // if getlastlocation does not work then request the device to get the current location.
//            createLocationRequest();
//
//
//            if(mLocationRequest!=null)
//            {
//                startLocationUpdates();
//            }
//
//        }


        // if getlastlocation does not work then request the device to get the current location.
        createLocationRequest();

        if(mLocationRequest!=null)
        {
            startLocationUpdates();
        }
    }


    private static final int REQUEST_CHECK_SETTINGS = 3;


    protected void createLocationRequest() {

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setSmallestDisplacement(100);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(mLocationRequest);

        PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient, builder.build());


        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(@NonNull LocationSettingsResult locationSettingsResult) {

                final Status status = locationSettingsResult.getStatus();
                final LocationSettingsStates states = locationSettingsResult.getLocationSettingsStates();

                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        // All location settings are satisfied. The client can
                        // initialize location requests here.
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        // Location settings are not satisfied, but this can be fixed
                        // by showing the user a dialog.
                        try {
                            // Show the dialog by calling startResolutionForResult(),
                            // and check the result in onActivityResult().
                            status.startResolutionForResult(
                                    ServicesActivity.this,
                                    REQUEST_CHECK_SETTINGS);

                        } catch (IntentSender.SendIntentException e) {
                            // Ignore the error.
                        }
                        break;

                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        // Location settings are not satisfied. However, we have no way
                        // to fix the settings so we won't show the dialog.
                        // ...
                        break;

                }
            }

        });
    }




    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CHECK_SETTINGS) {

            if (resultCode == RESULT_OK) {


                showToastMessage("Permission granted !");

                onConnected(null);

            } else {


                showToastMessage("Permission not granted !");
            }
        }
    }



    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

        Log.d("applog","Google api client connection failed !");

    }


    protected void startLocationUpdates() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.


            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                    },
                    2);

            return;
        }


        if(mGoogleApiClient.isConnected())
        {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }

    }



    protected void stopLocationUpdates() {

        if(mGoogleApiClient.isConnected())
        {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }

    }


    @Override
    public void onLocationChanged(Location location) {

        saveLocation(location);

        UtilityLocationServices.saveLatitude((float) location.getLatitude(),this);
        UtilityLocationServices.saveLongitude((float) location.getLongitude(),this);

//        stopLocationUpdates();


    }


    private void saveLocation(Location mLastLocation) {
//        locationResult=mLastLocation;
//        makeRefreshNetworkCall();

        Fragment fragment = getSupportFragmentManager()
                .findFragmentByTag(TAG_FRAGMENT);

        if(fragment instanceof NotifyLocation)
        {
            ((NotifyLocation) fragment).fetchedLocation(mLastLocation);
        }


    }







    @Override
    public void notifySortChanged() {

        Fragment fragment = getSupportFragmentManager()
                .findFragmentByTag(TAG_FRAGMENT);

        if(fragment instanceof NotifySort)
        {
            ((NotifySort)fragment).notifySortChanged();
        }

    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_service_activity, menu);


        // Associate searchable configuration with the SearchView
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));


        MenuItemCompat.setOnActionExpandListener(menu.findItem(R.id.action_search), new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {

//                Fragment fragment = getSupportFragmentManager().findFragmentByTag(TAG_FRAGMENT);

                Fragment fragment = getSupportFragmentManager()
                        .findFragmentByTag(TAG_FRAGMENT);


                if(fragment instanceof NotifySearch)
                {
                    ((NotifySearch) fragment).endSearchMode();
                }

//                Toast.makeText(OrderHistoryHD.this, "onCollapsed Called ", Toast.LENGTH_SHORT).show();

                return true;
            }
        });

        return true;
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleIntent(intent);
    }


    private void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            //use the query to search your data somehow

//            Toast.makeText(this,query,Toast.LENGTH_SHORT).show();

//            Fragment fragment = getSupportFragmentManager().findFragmentByTag(TAG_FRAGMENT);

            Fragment fragment = getSupportFragmentManager()
                    .findFragmentByTag(TAG_FRAGMENT);

            if(fragment instanceof NotifySearch)
            {
                ((NotifySearch) fragment).search(query);
            }
        }
    }




    private void showDialogSubmitURL()
    {
        FragmentManager fm = getSupportFragmentManager();
        SubmitURLDialog submitURLDialog = new SubmitURLDialog();
        submitURLDialog.show(fm,"serviceUrl");
    }


    @Override
    public void showFab() {
        fab.animate().translationY(0);
    }

    @Override
    public void hideFab() {
        fab.animate().translationY(fab.getHeight()+50);
    }



    void showToastMessage(String message)
    {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }


}
