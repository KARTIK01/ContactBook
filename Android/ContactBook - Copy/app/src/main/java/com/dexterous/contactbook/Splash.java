package com.dexterous.contactbook;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.dexterous.contactbook.util.Network;

import java.util.Timer;
import java.util.TimerTask;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class Splash extends Activity {

    private static final int SPLASH_SCREEN_DISPLAY_TIME = 1000;
    public static final String SERVER_CONNECTION = "connection";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashlayout);
        Timer t = new Timer();
        boolean checkConnection = Network.isNetworkAvailable(this);
        if (checkConnection) {
            t.schedule(new splash(), SPLASH_SCREEN_DISPLAY_TIME);
        } else {
            Intent i = new Intent(Splash.this, MainActivity.class);
            i.putExtra(SERVER_CONNECTION, false);
            finish();
            startActivity(i);
        }
    }

    class splash extends TimerTask {
        @Override
        public void run() {
            Intent i = new Intent(Splash.this, MainActivity.class);
            i.putExtra(SERVER_CONNECTION, true);
            finish();
            startActivity(i);
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

}
