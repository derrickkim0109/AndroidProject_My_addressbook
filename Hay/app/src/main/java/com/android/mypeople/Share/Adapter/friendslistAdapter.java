package com.android.mypeople.Share.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.android.mypeople.Share.Bean.Bean_friendslist;

import java.util.ArrayList;

public class friendslistAdapter extends BaseAdapter {

    /*공용이므로 항상 수정사항 알려주시기 바랍니다.*/

    Context mContext = null;
    int layout = 0;
    ArrayList<Bean_friendslist> data = null;
    LayoutInflater inflater = null;

    public friendslistAdapter(Context mContext, int layout, ArrayList<Bean_friendslist> data) {
        this.mContext = mContext;
        this.layout = layout;
        this.data = data;
        this.inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position).getfSeqno();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return convertView;
    }
}
