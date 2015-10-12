package com.dexterous.contactbook;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.dexterous.contactbook.util.Network;
import com.gc.materialdesign.views.ButtonFloat;

/**
 * Created by Honey on 7/11/2015.
 */
public class ConnectWithInterNetFragment extends Fragment {

    static Context _context;

    public ConnectWithInterNetFragment() {

    }

    public static Fragment instantiate(Context context) {
        ConnectWithInterNetFragment connectWithInterNetFragment = new ConnectWithInterNetFragment();
        _context = context;
        return connectWithInterNetFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_connect, container, false);
        ButtonFloat wifiBtn = (ButtonFloat) rootView.findViewById(R.id.wifi);
        wifiBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Network.turnOnDataAccess(getActivity());
            }
        });
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        boolean checkConnection = Network.isNetworkAvailable(getActivity());
        if (checkConnection) {
            MainActivity.updaetFragmentAccordingToFirstRun(_context);
        } else {
            Toast.makeText(getActivity(), "NOT CONNECTED", Toast.LENGTH_LONG).show();
        }
    }
}
