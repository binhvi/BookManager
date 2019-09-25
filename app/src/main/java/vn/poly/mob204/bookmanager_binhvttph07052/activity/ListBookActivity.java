package vn.poly.mob204.bookmanager_binhvttph07052.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import vn.poly.mob204.bookmanager_binhvttph07052.R;
import vn.poly.mob204.bookmanager_binhvttph07052.adapter.BookAdapter;
import vn.poly.mob204.bookmanager_binhvttph07052.model.Sach;

public class ListBookActivity extends AppCompatActivity {
    private EditText edSearchBook;
    private Button btnTim;
    private RecyclerView rvBook;
    BookAdapter adapter;
    public static List<Sach> dsSach;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_book);
        addControls();

        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        //recycler view configuration
        dsSach=new ArrayList<>();
        adapter=new BookAdapter(this, dsSach);
        rvBook.setAdapter(adapter);
        LinearLayoutManager vertical=new LinearLayoutManager(this);
        rvBook.setLayoutManager(vertical);

        //fake du lieu
        for (int i=0; i<30; i++) {
            //tao doi tuong sach moi
            Sach sach=new Sach(
                    String.valueOf(i),
                    String.valueOf(i),
                "Toi thay hoa vang tren co xanh "+i,
                    "Nguyen Nhat Anh "+i,
            "NXB Kim Dong "+i,
            50000.0,
                    5);
            //them vao list
            dsSach.add(sach);
        }
        
    }

    private void addControls() {
        edSearchBook = (EditText) findViewById(R.id.edSearchBook);
        btnTim = (Button) findViewById(R.id.btnTim);
        rvBook = (RecyclerView) findViewById(R.id.rvBook);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_book, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add:
                Intent intent=new Intent(ListBookActivity.this, ThemSachActivity.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
