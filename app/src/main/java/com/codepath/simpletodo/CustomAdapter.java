package com.codepath.simpletodo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by deepasaini on 1/16/16.
 */
public class CustomAdapter extends ArrayAdapter<Item> {

    private List<Item> data;

    public CustomAdapter(Context context, ArrayList<Item> users) {
        super(context, 0, users);
        data = users;
    }

    @Override
    public Item getItem(int position) {
        return data.get(position);
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Item user = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_layout, parent, false);
        }
        // Lookup view for data population
        TextView tvName = (TextView) convertView.findViewById(R.id.tvName);
        // Populate the data into the template view using the data object
        tvName.setText(user.name);
        // Return the completed view to render on screen
        return convertView;
    }
}
