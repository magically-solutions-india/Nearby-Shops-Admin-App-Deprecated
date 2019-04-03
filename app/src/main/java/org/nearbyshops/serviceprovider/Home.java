package org.nearbyshops.serviceprovider;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.widget.Toast;

import org.nearbyshops.serviceprovider.AdminDashboard.AdminDashboardFragment;
import org.nearbyshops.serviceprovider.Login.Interfaces.NotifyAboutLogin;
import org.nearbyshops.serviceprovider.LoginPlaceholders.FragmentSignInMessage;
import org.nearbyshops.serviceprovider.ModelRoles.User;
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



//        if(PrefOneSignal.getToken(this)!=null)
//        {
//            startService(new Intent(getApplicationContext(), UpdateOneSignalID.class));
//        }

    }












    @Override
    public void loginSuccess() {


        showDashboard();
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





}



