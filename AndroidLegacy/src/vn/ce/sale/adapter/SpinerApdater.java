package vn.ce.sale.adapter;

import vn.ce.sale.R;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class SpinerApdater extends ArrayAdapter<String> {
	private Activity mcontext;
	LayoutInflater infalater;
	String[] spinnerValues;
	String[] spinnerSubs;
	int index = -1;
	String controlName = "";

	public SpinerApdater(Activity context, int textViewResourceId, String[] spinnerSubs, String[] spinnerValues,
			int index, String controlName) {
		super(context, textViewResourceId, spinnerSubs);
		// TODO Auto-generated constructor stub
		this.spinnerValues = spinnerValues;
		this.spinnerSubs = spinnerSubs;
		this.mcontext = context;
		this.index = index;
		this.controlName = controlName;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		return getCustomView(position, convertView, parent);
	}

	public View getCustomView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			infalater = mcontext.getLayoutInflater();
			convertView = infalater.inflate(R.layout.custom_spiner, parent, false);
			// holder.main_text = (TextView) convertView
			// .findViewById(R.id.text_main_seen);
			holder.subSpiner = (TextView) convertView.findViewById(R.id.sub_text_seen);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		// holder.main_text.setText(spinnerValues[position]);
		holder.subSpiner.setText(spinnerSubs[position]);

		return convertView;
	}

	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		return getCustomView(position, convertView, parent);
	}

	static class ViewHolder {
		TextView subSpiner;
		TextView main_text;
	}
}
