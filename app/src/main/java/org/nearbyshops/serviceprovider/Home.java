package org.nearbyshops.serviceprovider;


import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.widget.Toast;

import org.nearbyshops.serviceprovider.AdminDashboard.AdminDashboardFragment;
import org.nearbyshops.serviceprovider.Interfaces.LocationUpdated;
import org.nearbyshops.serviceprovider.Login.Interfaces.NotifyAboutLogin;
import org.nearbyshops.serviceprovider.LoginPlaceholders.FragmentSignInMessage;
import org.nearbyshops.serviceprovider.ModelRoles.User;
import org.nearbyshops.serviceprovider.Preferences.PrefLocation;
import org.nearbyshops.serviceprovider.StaffHome.StaffHomeFragment;
import org.nearbyshops.serviceprovider.Preferences.PrefLogin;









public class Home extends AppCompatActivity implements NotifyAboutLogin {

    boolean isDestroyed = false;


    public static final String TAG_LOGIN = "tag_login";
    public static final String TAG_SELECT_CITY = "tag_select_city";
    public static final String TAG_PROFILE = "tag_profile_fragment";
    public static final String TAG_TRIP_REQUEST = "tag_trip_request_fragment";
    public static final String TAG_VEHICLE_FRAGMENT = "tag_vehicle_fragment";
    public static final String TAG_VEHICLE_FRAGMENT_LOCAL = "tag_vehicle_fragment_local";
    public static final String TAG_TRIP_CURRENT_FRAGMENT = "tag_current_fragment";

    public static final String TAG_MAP_PICKER = "tag_map_picker";
    public static final String TAG_TRIP_DETAILS = "tag_trip_details";

    private static final int REQUEST_CHECK_SETTINGS = 2;


    // fragments
//    VehiclesFragment vehiclesFragment;
//    ProfileFragmentOld profileFragment;



    LocationManager locationManager;
    LocationListener locationListener;



    boolean isFirstLaunch = true;



    public Home() {
//
//        DaggerComponentBuilder.getInstance()
//                .getNetComponent()
//                .Inject(this);
    }





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_new);


        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        showDashboard();



        checkPermissions();
        fetchLocation();



//        if(PrefOneSignal.getToken(this)!=null)
//        {
//            startService(new Intent(getApplicationContext(), UpdateOneSignalID.class));
//        }

    }












    @Override
    public void loginSuccess() {


        showDashboard();
    }

    @Override
    public void loggedOut() {

    }


    public void showLoginFragment()
    {

        if(getSupportFragmentManager().findFragmentByTag(TAG_LOGIN)==null)
        {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container,new FragmentSignInMessage(),TAG_LOGIN)
                    .commit();
        }
    }









    public void showDashboard() {

        User user = PrefLogin.getUser(getBaseContext());


        if (user == null) {
            showLoginFragment();

            return;
        }


        if (user.getRole() == User.ROLE_ADMIN_CODE) {

            if (getSupportFragmentManager().findFragmentByTag(TAG_PROFILE) == null) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new AdminDashboardFragment(), TAG_PROFILE)
                        .commit();
            }

        } else if (user.getRole() == User.ROLE_STAFF_CODE) {

            if (getSupportFragmentManager().findFragmentByTag(TAG_PROFILE) == null) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new StaffHomeFragment(), TAG_PROFILE)
                        .commit();
            }
        }


    }









    void showToastMessage(String message)
    {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }






    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopLocationUpdates();
    }









    void fetchLocation() {

        // Acquire a reference to the system Location Manager
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        // Define a listener that responds to location updates
        locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                // Called when a new location is found by the network location provider.

                saveLocation(location);
                stopLocationUpdates();
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            public void onProviderEnabled(String provider) {
            }

            public void onProviderDisabled(String provider) {
            }
        };


        // Register the listener with the Location Manager to receive location updates
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



        if(location==null)
        {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 100, locationListener);
        }
        else
        {
            saveLocation(location);
        }

    }










    void saveLocation(Location location)
    {


        Location currentLocation = PrefLocation.getLocation(this);


//        showToast("Distance Change : " + currentLocation.distanceTo(location));

        if(currentLocation.distanceTo(location)>100)
        {
            // save location only if there is a significant change in location

            PrefLocation.saveLatitude((float) location.getLatitude(), Home.this);
            PrefLocation.saveLongitude((float) location.getLongitude(), Home.this);


//        showToast("Home : Location Updated");

            Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);

            if (fragment instanceof LocationUpdated) {
                ((LocationUpdated) fragment).locationUpdated();
            }

        }

    }







    protected void stopLocationUpdates() {

        if(locationManager!=null && locationListener!=null)
        {
            locationManager.removeUpdates(locationListener);
        }
    }










    void checkPermissions() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.


            //
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    2);
            return;
        }
    }









    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {


        if (grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            // permission was granted, yay! Do the
            // contacts-related task you need to do.

            showToastMessage("Permission Granted !");

            fetchLocation();

        } else {
            // permission denied, boo! Disable the
            // functionality that depends on this permission.
            showToastMessage("Permission Rejected");
        }


    }






}



