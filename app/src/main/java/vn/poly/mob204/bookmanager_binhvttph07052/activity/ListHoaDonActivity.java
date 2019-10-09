package vn.poly.mob204.bookmanager_binhvttph07052.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import vn.poly.mob204.bookmanager_binhvttph07052.R;
import vn.poly.mob204.bookmanager_binhvttph07052.adapter.HoaDonAdapter;
import vn.poly.mob204.bookmanager_binhvttph07052.dao.HoaDonDAO;
import vn.poly.mob204.bookmanager_binhvttph07052.model.HoaDon;

public class ListHoaDonActivity extends AppCompatActivity {
    private RecyclerView rvHoaDon;
    public static List<HoaDon> dsHoaDon;
    HoaDonAdapter adapter;
    HoaDonDAO hoaDonDAO;

    //debug
    private static final String TAG="ListHoaDonActivityLog";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_hoa_don);
        addControls();

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        try {
            init();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void init() throws ParseException {
        //recycler view configuration
        dsHoaDon = new ArrayList<>();
        adapter = new HoaDonAdapter(this, dsHoaDon);
        rvHoaDon.setAdapter(adapter);
        LinearLayoutManager vertical = new LinearLayoutManager(this);
        rvHoaDon.setLayoutManager(vertical);
        hoaDonDAO=new HoaDonDAO(this);

        refreshHoaDonAdapter();
    }

    private void refreshHoaDonAdapter() {
        dsHoaDon.clear();
        try {
            dsHoaDon.addAll(hoaDonDAO.getAllHoaDon()); //dong nay ban ra ParseException
        } catch (ParseException exc) {
            exc.printStackTrace();
            Log.e(TAG, exc.toString());
        } catch (Exception exc) {
            exc.printStackTrace();
            Log.e(TAG, exc.toString());
        }
        adapter.notifyDataSetChanged();
    }

    private void addControls() {
        rvHoaDon = (RecyclerView) findViewById(R.id.rvHoaDon);
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
                Intent intent = new Intent(this, HoaDonActivity.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
            refreshHoaDonAdapter();
    }
}
