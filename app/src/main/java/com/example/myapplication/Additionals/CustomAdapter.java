package com.example.myapplication.Additionals;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.myapplication.Additionals.CustomButton;

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

        return bugs.get(position);
    }
}
