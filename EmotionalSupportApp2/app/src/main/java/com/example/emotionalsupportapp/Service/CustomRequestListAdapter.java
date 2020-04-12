package com.example.emotionalsupportapp.Service;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.emotionalsupportapp.R;

public class CustomRequestListAdapter extends ArrayAdapter<String> {


    public CustomRequestListAdapter(Context context, String[] data) {
        super(context, R.layout.custom_listview, data);
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return super.getView(position, convertView, parent);
    }
}

