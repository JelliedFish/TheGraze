package com.example.myapplication;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import java.util.List;

public class CustomAdapter extends BaseAdapter {
    private Context context;
    private List<CustomButton> bugs;

    public CustomAdapter(Context context, List<CustomButton> bugs) {
        this.context = context;
        this.bugs = bugs;
    }

    @Override
    public int getCount() {
        return bugs.size();
    }

    @Override
    public Object getItem(int position) {
        return bugs.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Button button;

        if (convertView == null) {
            button = new Button(context);
        } else {
            button = (Button) convertView;
        }
        button.setId(position);

        return button;
    }
}
