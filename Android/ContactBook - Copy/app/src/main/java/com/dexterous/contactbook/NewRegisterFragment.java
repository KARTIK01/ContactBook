package com.dexterous.contactbook;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.androidquery.callback.AjaxStatus;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.gc.materialdesign.views.ButtonRectangle;
import com.rengwuxian.materialedittext.MaterialEditText;

import org.json.JSONObject;


public class NewRegisterFragment extends Fragment {

    MaterialEditText name;
    MaterialEditText id;
    MaterialEditText phone;
    MaterialEditText ext;
    MaterialEditText dept;
    MaterialEditText email;

    static Context _context;


    public static NewRegisterFragment instantiate(Context context) {
        NewRegisterFragment fragment = new NewRegisterFragment();
        Bundle args = new Bundle();
        _context = context;
        fragment.setArguments(args);
        return fragment;
    }

    public NewRegisterFragment() {
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
        View rootView = inflater.inflate(R.layout.fragment_new_register, container, false);
        ButtonRectangle confirm = (ButtonRectangle) rootView.findViewById(R.id.confirm);
        ButtonRectangle cancle = (ButtonRectangle) rootView.findViewById(R.id.cancle);
        name = (MaterialEditText) rootView.findViewById(R.id.name);
        id = (MaterialEditText) rootView.findViewById(R.id.id);
        email = (MaterialEditText) rootView.findViewById(R.id.email);
        dept = (MaterialEditText) rootView.findViewById(R.id.dept);
        ext = (MaterialEditText) rootView.findViewById(R.id.ext);
        phone = (MaterialEditText) rootView.findViewById(R.id.phone);


        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateEditText()) {
                    asyncJson();
                }
            }
        });

        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        return rootView;
    }


    public void asyncJson() {
        String IP_ADDRESS = getResources().getString(R.string.ip);
        String URL = "http://" + IP_ADDRESS + ":8080/ContactBook/newinsert?empID=" + id.getText().toString().replaceAll(" ", "%20") +
                "&name=" + name.getText().toString().replaceAll(" ", "%20") +
                "&speedDialNo=" + ext.getText().toString().replaceAll(" ", "%20") +
                "&mobileNo=" + phone.getText().toString().replaceAll(" ", "%20") +
                "&email=" + email.getText().toString().replaceAll(" ", "%20") +
                "&dept=" + dept.getText().toString().replaceAll(" ", "%20");
//        Log.e("TAG", URL);
        MainActivity.getAQ().ajax(URL, JSONObject.class, this, "jsonCallback");

    }

    public void jsonCallback(String url, JSONObject json, AjaxStatus status) {
        if (json != null) {
            Toast.makeText(getActivity(), "Your Request allready send\nYou Will be Registered Soon", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getActivity(), "You Will be Registered Soon", Toast.LENGTH_LONG).show();
        }
        getActivity().onBackPressed();
    }


    private boolean validateEditText() {
        if (id.getText().toString().equals("")) {
            vibrateEditText(id);
        } else if (name.getText().toString().equals("")) {
            vibrateEditText(name);
        } else if (ext.getText().toString().equals("")) {
            vibrateEditText(ext);
        } else if (phone.getText().toString().equals("")) {
            vibrateEditText(phone);
        } else if (dept.getText().toString().equals("")) {
            vibrateEditText(dept);
        } else if (email.getText().toString().equals("")) {
            vibrateEditText(email);
        } else {
            return true;
        }
        return false;
    }


    public static void vibrateEditText(MaterialEditText materialEditText) {
        YoYo.with(Techniques.Shake)
                .duration(700)
                .playOn(materialEditText);
    }
}
