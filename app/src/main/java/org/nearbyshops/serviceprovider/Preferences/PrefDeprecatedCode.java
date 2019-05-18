package org.nearbyshops.serviceprovider.Preferences;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import org.nearbyshops.serviceprovider.R;

import static android.content.Context.MODE_PRIVATE;

public class PrefDeprecatedCode {





//    public static void saveCurrencySymbol(String symbol, Context context)
//    {
//        //Creating a shared preference
//        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.preference_file_name), MODE_PRIVATE);
//        SharedPreferences.Editor prefsEditor = sharedPref.edit();
//        Gson gson = UtilityFunctions.provideGson();
//
//        String json = gson.toJson(symbol);
//        prefsEditor.putString(TAG_PREF_CURRENCY, json);
//        prefsEditor.apply();
//    }
//
//
//
//
//
//
//    public static String getCurrencySymbol(Context context)
//    {
//        SharedPreferences sharedPref = context.getSharedPreferences(context.getString(R.string.preference_file_name), MODE_PRIVATE);
//        Gson gson = UtilityFunctions.provideGson();
//
//        return sharedPref.getString(TAG_PREF_CURRENCY, context.getString(R.string.rupee_symbol));
//    }



//    public static boolean isNetworkAvailable(Context context) {
//        ConnectivityManager connectivityManager
//                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
//        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
//    }





//    public static void saveDistributorID(int distributorID)
//    {
//        Context context = MyApplication.getAppContext();
//        // Get a handle to shared preference
//        SharedPreferences sharedPref;
//        sharedPref = context.getSharedPreferences(context.getString(R.string.preference_file_name), context.MODE_PRIVATE);
//
//        // write to the shared preference
//        SharedPreferences.Editor editor = sharedPref.edit();
//        editor.putInt(context.getString(R.string.preference_distributor_id_key),distributorID);
//        editor.commit();
//
//    }
//
//    public static int getDistributorID(Context context) {
//        // Get a handle to shared preference
//        SharedPreferences sharedPref;
//        sharedPref = context.getSharedPreferences(context.getString(R.string.preference_file_name), context.MODE_PRIVATE);
//
//        // read from shared preference
//        int distributorID = sharedPref.getInt(context.getString(R.string.preference_distributor_id_key), 0);
//        return distributorID;
//    }




}
