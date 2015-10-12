package com.dexterous.contactbook;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;

import com.androidquery.AQuery;
import com.dexterous.contactbook.util.SharedPreferencesProvider;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class MainActivity extends ActionBarActivity {

    //TODO chagen to
    boolean firstRun = true;
    static private AQuery aq;
    static Context context;

    public interface Callback {
        public void onItemSelected(String date);
    }

    static AQuery getAQ() {
        if (aq == null) {
            aq = new AQuery(context);
        }
        return aq;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        aq = new AQuery(this);
        Bundle bundle = getIntent().getExtras();
        context = this;
        // set icon on action bar
        // TODO put white image
//        ActionBar actionBar = getSupportActionBar();
//        actionBar.setLogo(R.drawable.ic_launcher_white);
//        actionBar.setDisplayUseLogoEnabled(true);
//        actionBar.setDisplayShowHomeEnabled(true);

        boolean connecetion = (boolean) bundle.get(Splash.SERVER_CONNECTION);

        SharedPreferences filName = SharedPreferencesProvider
                .getDefaultSharedPreferences(this);
        firstRun = filName.getBoolean(SharedPreferencesProvider.PREFERENCE_KEY_FOR_FIRST_RUN, true);

        if (savedInstanceState == null) {
            if (connecetion) {
                updaetFragmentAccordingToFirstRun(this);
            } else {
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.container, ConnectWithInterNetFragment.instantiate(this))
                        .commit();
            }
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


    public static void updaetFragmentAccordingToFirstRun(Context context) {
        //remove comment
        if (SharedPreferencesProvider.isFirstRun(context)) {
            ((FragmentManager) ((FragmentActivity) context).getSupportFragmentManager()).beginTransaction()
                    .replace(R.id.container, EnterMobileFragment.instantiate(context))
                    .commit();
        } else {
            ((FragmentManager) ((FragmentActivity) context).getSupportFragmentManager()).beginTransaction()
                    .replace(R.id.container, MainApplicationFragment.instantiate(context))
                    .commit();
        }
    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//
//        return super.onOptionsItemSelected(item);
//    }
}

