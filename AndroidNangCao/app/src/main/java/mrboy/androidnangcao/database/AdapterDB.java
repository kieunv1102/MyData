package mrboy.androidnangcao.database;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import mrboy.androidnangcao.R;

/**
 * Created by Kieunv on 5/31/2016.
 */
public class AdapterDB extends BaseAdapter{
    List<Book> listBook;
    private LayoutInflater mInflater;
    public AdapterDB(Context context,List<Book> listBook) {
        this.listBook = listBook;
        this.mInflater = LayoutInflater.from( context );
    }

    @Override
    public int getCount() {
        return listBook.size();
    }

    @Override
    public Object getItem(int position) {
        return listBook.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = mInflater.inflate( R.layout.item_rcv_sql, parent, false );
        ViewHolder viewHolder = new ViewHolder();
        viewHolder.txtId = (TextView)view.findViewById(R.id.txtId);
        viewHolder.txtTitle = (TextView)view.findViewById(R.id.txtName);
        viewHolder.txtId.setText(listBook.get(position).getId());
        viewHolder.txtTitle.setText(listBook.get(position).getTitle());
        return view;
    }
    private static class ViewHolder
    {
        public TextView txtId,txtTitle;
    }
}
