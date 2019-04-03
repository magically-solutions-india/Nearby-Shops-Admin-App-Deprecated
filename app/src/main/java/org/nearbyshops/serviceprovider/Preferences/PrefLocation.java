package org.nearbyshops.serviceprovider.Preferences;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;


import org.nearbyshops.serviceprovider.ModelUtility.LocationWithAddress;
import org.nearbyshops.serviceprovider.R;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by sumeet on 24/6/17.
 */

public class PrefLocation {


    public static final String TAG_LOCATION_CURRENT = "tag_location_current";
    public static final String TAG_LATITUDE_CURRENT = "tag_lat_current";
    public static final String TAG_LONGITUDE_CURRENT = "tag_lon_current";



    public static void saveLocationCurrent(LocationWithAddress currentLocation, Context context)
    {

        //Creating a shared preference

        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.preference_file_name), MODE_PRIVATE);

        SharedPreferences.Editor prefsEditor = sharedPref.edit();

        Gson gson = UtilityFunctions.provideGson();
        String json = gson.toJson(currentLocation);
        prefsEditor.putString(TAG_LOCATION_CURRENT, json);

        prefsEditor.apply();
    }




    public static LocationWithAddress getLocationCurrent(Context context)
    {
        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.preference_file_name), MODE_PRIVATE);

        Gson gson = UtilityFunctions.provideGson();
        String json = sharedPref.getString(TAG_LOCATION_CURRENT, null);

        return gson.fromJson(json, LocationWithAddress.class);
    }




    public static void saveLatLonCurrent(double lat,double lon, Context context)
    {
        //Creating a shared preference
        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.preference_file_name), MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = sharedPref.edit();

        prefsEditor.putFloat(TAG_LATITUDE_CURRENT, (float) lat);
        prefsEditor.putFloat(TAG_LONGITUDE_CURRENT, (float) lon);

        prefsEditor.apply();
    }




    public static float getLatitideCurrent(Context context)
    {
        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.preference_file_name), MODE_PRIVATE);
        return sharedPref.getFloat(TAG_LATITUDE_CURRENT, -300);
    }



    public static float getLongitudeCurrent(Context context)
    {
        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.preference_file_name), MODE_PRIVATE);
        return sharedPref.getFloat(TAG_LONGITUDE_CURRENT, -300);
    }


}
