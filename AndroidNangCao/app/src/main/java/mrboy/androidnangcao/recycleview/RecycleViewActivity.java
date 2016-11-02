package mrboy.androidnangcao.recycleview;

import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import mrboy.androidnangcao.R;

public class RecycleViewActivity extends AppCompatActivity {
    private RecyclerView rcvDemo;
    AdapterDemo adapterDemo;
    private List<String> lstData;
    RelativeLayout rllRecycleDemo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle_view);
        rllRecycleDemo = (RelativeLayout) findViewById(R.id.rllRecycleDemo);
        rcvDemo = (RecyclerView) findViewById(R.id.rcvDemo);
        //recycle ngang
//        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
//        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
//        rcvDemo.setLayoutManager(layoutManager);
        rcvDemo.setLayoutManager(new GridLayoutManager(this, 1));
        lstData = new ArrayList<>();
        lstData.add("Item 1");
        lstData.add("Item 2");
        lstData.add("Item 3");
        lstData.add("Item 4");
        lstData.add("Item 5");
        adapterDemo = new AdapterDemo(lstData, this);
        rcvDemo.setAdapter(adapterDemo);
        rcvDemo.addOnItemTouchListener(new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Snackbar.make(rllRecycleDemo, String.valueOf(position), Snackbar.LENGTH_SHORT).show();
            }
        }));
    }
}
