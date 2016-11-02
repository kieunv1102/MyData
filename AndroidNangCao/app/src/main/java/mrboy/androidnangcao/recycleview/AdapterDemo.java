package mrboy.androidnangcao.recycleview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import mrboy.androidnangcao.R;

/**
 * Created by Kieunv on 5/30/2016.
 */
public class AdapterDemo extends RecyclerView.Adapter<DemoViewHolder>{
    private List<String> lstDemo;
    private Context mContext;

    public AdapterDemo(List<String> lstDemo, Context mContext) {
        this.lstDemo = lstDemo;
        this.mContext = mContext;
    }

    @Override
    public DemoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_rcv_demo,null);
        DemoViewHolder demoViewHolder = new DemoViewHolder(view);
        return demoViewHolder;
    }

    @Override
    public void onBindViewHolder(DemoViewHolder holder, final int position) {
        holder.txtDemo.setText(lstDemo.get(position));
        holder.txtDemo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext,lstDemo.get(position),Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return lstDemo.size();
    }
}
