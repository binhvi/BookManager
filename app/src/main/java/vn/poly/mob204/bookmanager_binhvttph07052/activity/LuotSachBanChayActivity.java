package vn.poly.mob204.bookmanager_binhvttph07052.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import vn.poly.mob204.bookmanager_binhvttph07052.R;
import vn.poly.mob204.bookmanager_binhvttph07052.adapter.TopTenBookSoldAmoutAdadapter;
import vn.poly.mob204.bookmanager_binhvttph07052.dao.SachDAO;
import vn.poly.mob204.bookmanager_binhvttph07052.model.Sach;

public class LuotSachBanChayActivity extends AppCompatActivity {
    private RecyclerView rvBookTop;
    SachDAO sachDAO;
    List<Sach> listSachBanChayNhatTrongThang;

    //debug
    private static final String TAG = "SachBanChayLog";

    //adapter
    TopTenBookSoldAmoutAdadapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_luot_sach_ban_chay);
        addControls();

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        
        init();
        //thu lay list ra
        listSachBanChayNhatTrongThang.addAll(sachDAO.getSachTop10());


    }

    private void init() {
        sachDAO = new SachDAO(this);
        listSachBanChayNhatTrongThang = new ArrayList<>();
        adapter = new TopTenBookSoldAmoutAdadapter(this, listSachBanChayNhatTrongThang);
        LinearLayoutManager vertical = new LinearLayoutManager(this);
        rvBookTop.setAdapter(adapter);
        rvBookTop.setLayoutManager(vertical);
    }

    private void addControls() {
        rvBookTop = (RecyclerView) findViewById(R.id.rvBookTop);
    }
}
