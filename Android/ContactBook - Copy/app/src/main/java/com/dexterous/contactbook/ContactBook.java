package com.dexterous.contactbook;

import android.app.Application;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by Honey on 7/31/2015.
 */
public class ContactBook extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                        .setDefaultFontPath("fonts/Roboto-Thin.ttf")
                        .setFontAttrId(R.attr.fontPath)
                        .build()
        );
    }
}
