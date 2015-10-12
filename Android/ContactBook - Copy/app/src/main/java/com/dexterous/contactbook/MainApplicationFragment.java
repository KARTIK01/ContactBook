package com.dexterous.contactbook;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.dexterous.contactbook.util.JsonUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Honey on 7/11/2015.
 */
public class MainApplicationFragment extends Fragment {


    static public String IP_ADDRESS;
    EditText bootstrapEditText;
    TextView textView;
    private View rootView;
    static ArrayList<String> data = new ArrayList<>();

    public MainApplicationFragment() {
    }

    public static Fragment instantiate(Context context) {
        MainApplicationFragment mainApplicationFragment = new MainApplicationFragment();
        return mainApplicationFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_main, container, false);


        bootstrapEditText = (EditText) rootView.findViewById(R.id.edit_text);
        textView = (TextView) rootView.findViewById(R.id.desc_text_view);
        bootstrapEditText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;

                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (bootstrapEditText.getRight() - bootstrapEditText.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        bootstrapEditText.setText("");
                        return true;
                    }
                }
                return false;
            }
        });


        bootstrapEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String text = s.toString();
                if (text.length() > 2) {
                    Log.e("TAG", text);
                    new JSONParse().execute(text);
                }
            }
        });


        return rootView;
    }


    class JSONParse extends AsyncTask<String, String, JSONArray> {
//             private ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            //TODO put process
//                super.onPreExecute();
//                pDialog = new ProgressDialog(getActivity());
//                pDialog.setMessage("Getting Data ...");
//                pDialog.setIndeterminate(false);
//                pDialog.setCancelable(true);
//                pDialog.show();
//                data.clear();
        }

        @Override
        protected JSONArray doInBackground(String... args) {
            JsonUtil jsonUtil = new JsonUtil();
            IP_ADDRESS = getResources().getString(R.string.ip);
            String URL = "http://" + IP_ADDRESS + ":8080/ContactBook/search?term=";

            if (args[0].contains(" ")) {
                args[0] = args[0].replaceAll(" ", "%20");
            }
            JSONArray jsonArray = jsonUtil.readJsonFeed(URL + args[0]);
            return jsonArray;
        }

        @Override
        protected void onPostExecute(JSONArray jsonArray) {
//                pDialog.dismiss();
            List<String> listDataHeader = new ArrayList<>();
            HashMap<String, List<String>> listDataChild = new HashMap<>();

            ExpandableListView expListView = (ExpandableListView) rootView.findViewById(R.id.list_view);

//               listView.setAdapter(stringArrayAdapter);
            data.clear();
            try {
                if (jsonArray.length() > 0) {
                    textView.setVisibility(View.GONE);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        listDataHeader.add(jsonObject.get("name").toString());

                        List<String> dataValue = new ArrayList<String>();
                        try {
                            dataValue.add(jsonObject.get("empId").toString());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        try {
                            dataValue.add(jsonObject.get("mobileNo").toString());

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        try {
                            dataValue.add(jsonObject.get("speedDialNo").toString());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        try {
                            dataValue.add(jsonObject.get("email").toString());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        try {
                            dataValue.add(jsonObject.get("dept").toString());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        listDataChild.put(listDataHeader.get(i), dataValue); // Header, Child data
//                        data.add(jsonObject.get("name").toString());
                    }
                } else {
                    textView.setVisibility(View.VISIBLE);
                    textView.setText("No Such Record found");
                }
                ExpandableListAdapter listAdapter = new ExpandableListAdapter(getActivity(), listDataHeader, listDataChild);
                expListView.setAdapter(listAdapter);
                expListView.setGroupIndicator(null);

                expListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

                    @Override
                    public boolean onGroupClick(ExpandableListView parent, View v,
                                                int groupPosition, long id) {
                        // TO HIDE KEYBOARD
                        InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                        inputMethodManager.hideSoftInputFromWindow(
                                parent.getWindowToken(), 0);
                        return false;
                    }
                });

                // Listview Group expanded listener
                expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
                    @Override
                    public void onGroupExpand(int groupPosition) {

                    }
                });

                // Listview Group collasped listener
                expListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
                    @Override
                    public void onGroupCollapse(int groupPosition) {


                    }
                });

                // Listview on child click listener
                expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

                    @Override
                    public boolean onChildClick(ExpandableListView parent, View v,
                                                int groupPosition, int childPosition, long id) {
//                        Log.e("TAG", childPosition + " ");
//                        if (childPosition == 1) {
//                            Intent teleCall = new Intent(Intent.ACTION_CALL);
//                            teleCall.setData(Uri.parse("tel:" + "000000000"));
//                            startActivity(teleCall);
//                        }
                        return false;
                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // TODO Add your menu entries here
        super.onCreateOptionsMenu(menu, inflater);
    }


}