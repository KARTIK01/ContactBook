package com.dexterous.contactbook;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.gc.materialdesign.views.ButtonFloat;

import java.util.HashMap;
import java.util.List;

public class ExpandableListAdapter extends BaseExpandableListAdapter {

    private Context _context;
    private List<String> _listDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<String, List<String>> _listDataChild;

    public ExpandableListAdapter(Context context, List<String> listDataHeader,
                                 HashMap<String, List<String>> listChildData) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final String childText = (String) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_item, null);
            convertView.setClickable(false);
        }

        TextView txtListChild = (TextView) convertView
                .findViewById(R.id.lblListItem);
        //String uri = "@drawable/ic_perm_scan_wifi_white.png";
        ButtonFloat imageView = (ButtonFloat) convertView.findViewById(R.id.img);
        if (childPosition == 0) { //ID
            imageView.setDrawableIcon(_context.getResources().getDrawable(R.drawable.identity_theft));
        } else if (childPosition == 1) {//phone
            imageView.setDrawableIcon(_context.getResources().getDrawable(R.drawable.ic_settings_phone_white));
        } else if (childPosition == 2) {//speed dial no
            imageView.setDrawableIcon(_context.getResources().getDrawable(R.drawable.phone_office));
        } else if (childPosition == 3) {//email
            imageView.setDrawableIcon(_context.getResources().getDrawable(R.drawable.mail_inv));
        } else if (childPosition == 4) {//dept
            imageView.setDrawableIcon(_context.getResources().getDrawable(R.drawable.department));
        }
        txtListChild.setText(childText);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (childPosition) {
                    case 0:
                        break;
                    case 1:
                        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + childText));
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        _context.startActivity(intent);
                        break;
                    case 2:
                        break;
                    case 3:
                        Intent emailIntent = new Intent(Intent.ACTION_SEND);
                        emailIntent.setType("message/rfc822");
                        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{childText});
                        try {
                            _context.startActivity(Intent.createChooser(emailIntent, "Send mail..."));
                        } catch (android.content.ActivityNotFoundException ex) {
                            Toast.makeText(_context, "There is no email client installed.", Toast.LENGTH_SHORT).show();
                        }
                        break;
                }
            }
        });


        return convertView;

    }


    @Override
    public int getChildrenCount(int groupPosition) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_group, null);
            convertView.setClickable(false);
        }

        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.lblListHeader);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

}
