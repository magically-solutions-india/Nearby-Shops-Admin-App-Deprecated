package org.nearbyshops.serviceprovider.Preferences;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;


import org.nearbyshops.serviceprovider.ModelServiceConfig.ServiceConfigurationLocal;
import org.nearbyshops.serviceprovider.R;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by sumeet on 31/8/17.
 */




public class PrefServiceConfiguration {



    // simple or advanced at service selection screen
    // role selected at login screen


    // constants
    public static final int SERVICE_SELECT_MODE_SIMPLE = 1;
    public static final int SERVICE_SELECT_MODE_ADVANCED = 2;



    public static final String DEFAULT_SDS_URL = "http://sds.taxireferralservice.com:5600";
    public static final String DEFAULT_SDS_URL_BACKUP = "http://192.168.1.36:5600";









    public static void saveSDSURL(String service_url, Context context)
    {

//        Context context = MyApplication.getAppContext();
        // get a handle to shared Preference
        SharedPreferences sharedPref = context.getSharedPreferences(
                context.getString(R.string.preference_file_name),
                MODE_PRIVATE);

        // write to the shared preference
        SharedPreferences.Editor editor = sharedPref.edit();

        editor.putString(
                "sds_url",
                service_url);

        editor.apply();



    }






    public static String getSDSURL(Context context) {

        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.preference_file_name), MODE_PRIVATE);
        //service_url = "http://localareademo-env.ap-southeast-1.elasticbeanstalk.com";

        return sharedPref.getString("sds_url", DEFAULT_SDS_URL);
    }






    public static void saveServiceConfig(ServiceConfigurationLocal serviceConfig, Context context)
    {

        if(context==null)
        {
            return;
        }

        //Creating a shared preference

        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.preference_file_name), MODE_PRIVATE);

        SharedPreferences.Editor prefsEditor = sharedPref.edit();

        Gson gson = UtilityFunctions.provideGson();
        String json = gson.toJson(serviceConfig);
        prefsEditor.putString("service_config_local", json);

        prefsEditor.apply();
    }





    public static ServiceConfigurationLocal getServiceConfig(Context context)
    {
        if(context==null)
        {
            return null;
        }

        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.preference_file_name), MODE_PRIVATE);

        Gson gson = UtilityFunctions.provideGson();
        String json = sharedPref.getString("service_config_local", null);

        return gson.fromJson(json, ServiceConfigurationLocal.class);
    }






    public static void saveServiceLightStatus(Context context, int status)
    {

        // get a handle to shared Preference
        SharedPreferences sharedPref;

        sharedPref = context.getSharedPreferences(
                context.getString(R.string.preference_file_name),
                MODE_PRIVATE);

        // write to the shared preference
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("service_light_status",status);
        editor.apply();
    }



    public static int getServiceLightStatus(Context context)
    {
        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.preference_file_name), MODE_PRIVATE);
        return sharedPref.getInt("service_light_status", 3);
    }


}
