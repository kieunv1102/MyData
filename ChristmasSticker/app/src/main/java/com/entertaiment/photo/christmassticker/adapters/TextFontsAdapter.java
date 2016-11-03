package com.entertaiment.photo.christmassticker.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.entertaiment.photo.christmassticker.R;

import java.util.List;

/**
 * Created by DucNguyen on 5/14/2015.
 */
public class TextFontsAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mInflater;
    private List<String> mListFonts;

    public TextFontsAdapter(Context context, List<String> listFonts) {
        mContext = context;
        mListFonts = listFonts;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mListFonts.size();
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
        final ViewHolder vh;
        if (convertView == null) {
            View view = mInflater.inflate(R.layout.item_ltv_textfonts, parent, false);
            vh = ViewHolder.create((LinearLayout) view);
            view.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        vh.txvItemHlvTextfonts.setText("ABC");
        if (position == 0) {
            vh.txvItemHlvTextfonts.setTypeface(null, Typeface.NORMAL);
        } else {
            Typeface type = Typeface.createFromAsset(mContext.getAssets(), mListFonts.get(position));
            vh.txvItemHlvTextfonts.setTypeface(type);
        }
        // TODOBind your data to the views here

        return vh.rootView;
    }

    private static class ViewHolder {
        public final LinearLayout rootView;
        public final TextView txvItemHlvTextfonts;

        private ViewHolder(LinearLayout rootView, TextView txvItemHlvTextfonts) {
            this.rootView = rootView;
            this.txvItemHlvTextfonts = txvItemHlvTextfonts;
        }

        public static ViewHolder create(LinearLayout rootView) {
            TextView txvItemHlvTextfonts = (TextView) rootView.findViewById(R.id.txv_item_hlv_textfonts);
            return new ViewHolder(rootView, txvItemHlvTextfonts);
        }
    }
}
