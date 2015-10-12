package com.dexterous.contactbook;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.androidquery.callback.AjaxStatus;
import com.dexterous.contactbook.util.JsonUtil;
import com.dexterous.contactbook.util.SharedPreferencesProvider;
import com.gc.materialdesign.views.ProgressBarCircularIndeterminate;
import com.rengwuxian.materialedittext.MaterialEditText;

import org.json.JSONObject;


public class EnterMobileFragment extends Fragment {

    static Context _context;
    static final int PICK_CONTACT = 1;
    ImageView pickContacts;
    MaterialEditText noEditText;
    ProgressBarCircularIndeterminate progressBarCircularIndeterminate;

    public static EnterMobileFragment instantiate(Context context) {
        EnterMobileFragment fragment = new EnterMobileFragment();
        _context = context;
        return fragment;
    }

    public EnterMobileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_enter_mobile, container, false);
        pickContacts = (ImageView) rootView.findViewById(R.id.pickContacts);
        noEditText = (MaterialEditText) rootView.findViewById(R.id.no_edit_text);
        noEditText.setText("");
        progressBarCircularIndeterminate = (ProgressBarCircularIndeterminate) rootView.findViewById(R.id.progressBarCircularIndeterminate);
        noEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String no = s.toString();
                if (no.length() == 10) {
                    Log.e("TAG", "afterTextChanged");
                    progressBarCircularIndeterminate.setVisibility(View.VISIBLE);
                    asyncJson(no);
                }
            }
        });

        pickContacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pickContactIntent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                startActivityForResult(pickContactIntent, PICK_CONTACT);
            }
        });

        return rootView;
    }


    private boolean sendNoToServer(String no) {
        return false;
    }


    public void asyncJson(String no) {
        String IP_ADDRESS = getResources().getString(R.string.ip);
        String URL = "http://" + IP_ADDRESS + ":8080/ContactBook/checkPhoneNO?pNo=" + no;
//        Log.e("TAG", URL);
        MainActivity.getAQ().ajax(URL, JSONObject.class, this, "jsonCallback");

    }

    public void jsonCallback(String url, JSONObject json, AjaxStatus status) {
        if (json != null) {
            //successful ajax call
            if (JsonUtil.getData(json, "success").equalsIgnoreCase("true")) {
//                Log.e("TAG", "application");
                progressBarCircularIndeterminate.setVisibility(View.GONE);
                SharedPreferencesProvider.setFirstRun(_context, false);
                SharedPreferencesProvider.saveUser(_context, json);
                getFragmentManager().beginTransaction()
                        .replace(R.id.container, MainApplicationFragment.instantiate(_context))
                        .commit();
            } else {
                progressBarCircularIndeterminate.setVisibility(View.GONE);
                NewRegisterFragment.vibrateEditText(noEditText);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
//                        Log.e("TAG", "NewRegisterFragment");
                        ((FragmentManager) getFragmentManager()).beginTransaction()
                                .replace(R.id.container, NewRegisterFragment.instantiate(_context))
                                .addToBackStack(null)
                                .commit();
                    }
                }, 2000);

            }
        } else {
            //ajax error
            Log.e("TAG", "error" + status);
            getFragmentManager().beginTransaction()
                    .replace(R.id.container, ConnectWithInterNetFragment.instantiate(_context))
                    .commit();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_CONTACT) {
            switch (requestCode) {
                case (PICK_CONTACT):
                    if (resultCode == Activity.RESULT_OK) {
                        Uri contactData = data.getData();
                        Cursor c = _context.getContentResolver().query(contactData, null, null, null, null);
                        if (c.moveToFirst()) {
                            String id = c.getString(c.getColumnIndexOrThrow(ContactsContract.Contacts._ID));
                            String hasPhone = c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
                            if (hasPhone.equalsIgnoreCase("1")) {
                                Cursor phones = _context.getContentResolver().query(
                                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + id,
                                        null, null);
                                phones.moveToFirst();
                                String number = phones.getString(phones.getColumnIndex("data1")).replaceAll("\\s+", "");
                                String noToDisplay = number.substring(number.length() - 10, number.length());
                                noEditText.setText(noToDisplay);
                            }
                        }
                    }
                    break;
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        noEditText.setText("");
    }
}
