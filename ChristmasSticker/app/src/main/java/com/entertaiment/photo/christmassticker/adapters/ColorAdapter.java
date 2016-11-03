package com.entertaiment.photo.christmassticker.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.entertaiment.photo.christmassticker.R;
import com.entertaiment.photo.christmassticker.utils.Var;

import java.util.ArrayList;

/**
 * Created by Kieu on 9/2/2015.
 */
public class ColorAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;

    private ArrayList<String> arColor;

    public ColorAdapter(Context mContext, ArrayList<String> arColor) {
        this.mContext = mContext;
        this.arColor = arColor;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return arColor.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = mInflater.inflate(R.layout.item_color, viewGroup, false);
        }
        TextView img = Var.ViewHolder.get(view, R.id.itemColor);
        img.setBackgroundColor(Color.parseColor(arColor.get(i)));
        return view;
    }
}
