package vn.poly.mob204.bookmanager_binhvttph07052;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import vn.poly.mob204.bookmanager_binhvttph07052.adapter.TheLoaiAdapter;
import vn.poly.mob204.bookmanager_binhvttph07052.model.TheLoai;

public class ListTheLoaiActivity extends AppCompatActivity {
    private RecyclerView rvTheLoai;
    List<TheLoai> dsTheLoai;
    TheLoaiAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_the_loai);
        rvTheLoai = (RecyclerView) findViewById(R.id.rvTheLoai);

        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        //recycler view configuration
        dsTheLoai=new ArrayList<>();
        adapter=new TheLoaiAdapter(this, dsTheLoai);
        rvTheLoai.setAdapter(adapter);
        LinearLayoutManager vertical=new LinearLayoutManager(this);
        rvTheLoai.setLayoutManager(vertical);

        //fake du lieu
        for (int i=0; i<30; i++) {
            //tao doi tuong the loai moi
            TheLoai theLoai=new TheLoai(String.valueOf(i), "Van hoc "+i,
                    "Khong co mo ta", i);
            //them vao list
            dsTheLoai.add(theLoai);
        }
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
                Intent intent=new Intent(ListTheLoaiActivity.this, TheLoaiActivity.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
