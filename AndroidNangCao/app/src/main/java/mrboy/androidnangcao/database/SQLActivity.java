package mrboy.androidnangcao.database;

import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import mrboy.androidnangcao.R;

public class SQLActivity extends AppCompatActivity {
    private EditText edtTitle, edtContent;
    private Button btnInsert,btnShow;
    private ListView lvShow;
    JCGSQLiteHelper db = new JCGSQLiteHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        DBAdapter.init(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sql);
        edtTitle = (EditText) findViewById(R.id.edtTitle);
        edtContent = (EditText) findViewById(R.id.edtContent);
        btnInsert = (Button) findViewById(R.id.btnInsert);
        btnShow = (Button) findViewById(R.id.btnShow);
        lvShow = (ListView)findViewById(R.id.lvShow);
        db.onUpgrade(db.getWritableDatabase(), 1, 2);

        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.createBook(new Book(edtTitle.getText().toString(), edtContent.getText().toString()));
                Toast.makeText(SQLActivity.this,"them thanh cong",Toast.LENGTH_LONG).show();
            }
        });
        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Book> bookList = db.getAllBooks();
                AdapterDB adapterDB = new AdapterDB(SQLActivity.this,bookList);
                lvShow.setAdapter(adapterDB);
                adapterDB.notifyDataSetChanged();
            }
        });



    }
}
