package com.android.mypeople.Minwoo.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.mypeople.R;

public class MainSpinnerAdapter extends BaseAdapter {

    Context context;
    int flags[];
    String tagNames[];
    LayoutInflater layoutInflater;

    public MainSpinnerAdapter(Context context, int[] flags, String[] tagNames) {
        this.context = context;
        this.flags = flags;
        this.tagNames = tagNames;
        layoutInflater = (LayoutInflater.from(context));
    }

    @Override
    public int getCount() {
        return flags.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = layoutInflater.inflate(R.layout.main_spinner_items, null);
        ImageView items = convertView.findViewById(R.id.main_Spinner_Tag);
        TextView names = convertView.findViewById(R.id.main_Spinner_TagName);
        items.setImageResource(flags[position]);
        names.setText(tagNames[position]);
        return convertView;
    }
}
