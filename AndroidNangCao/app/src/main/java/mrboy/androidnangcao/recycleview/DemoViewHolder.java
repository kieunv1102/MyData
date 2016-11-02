package mrboy.androidnangcao.recycleview;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import mrboy.androidnangcao.R;

/**
 * Created by Kieunv on 5/30/2016.
 */
public class DemoViewHolder extends RecyclerView.ViewHolder {
    public TextView txtDemo;
    public DemoViewHolder(View itemView) {
        super(itemView);
        txtDemo = (TextView)itemView.findViewById(R.id.txtDemo);
    }
}
