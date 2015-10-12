package com.dexterous.contactbook.util;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Vibrator;

/*
 * Class dealing with network of Application
 */
public class Network {

    /*
     * Function to check network connectivity
     * returns false if not connected to Internet.
     */
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager
                .getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static void exitApplication(Context context) {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static void turnOnDataAccess(Context context) {
        Intent actionSetting = new Intent(
                android.provider.Settings.ACTION_SETTINGS);
        context.startActivity(actionSetting);
    }



    public static Vibrator getVibrator(Context context) {
        return (Vibrator)context
                .getSystemService(Context.VIBRATOR_SERVICE);
    }
}
