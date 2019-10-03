package vn.poly.mob204.bookmanager_binhvttph07052.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.List;

import vn.poly.mob204.bookmanager_binhvttph07052.R;
import vn.poly.mob204.bookmanager_binhvttph07052.adapter.NguoiDungAdapter;
import vn.poly.mob204.bookmanager_binhvttph07052.dao.NguoiDungDAO;
import vn.poly.mob204.bookmanager_binhvttph07052.model.NguoiDung;

import static vn.poly.mob204.bookmanager_binhvttph07052.activity.LoginActivity.username;

public class ListNguoiDungActivity extends AppCompatActivity {
    private RecyclerView rvNguoiDung;
    NguoiDungAdapter nguoiDungAdapter;
    public static List<NguoiDung> nguoiDungList;
    NguoiDungDAO nguoiDungDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_nguoi_dung);

        //up button
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        rvNguoiDung = (RecyclerView) findViewById(R.id.rvNguoiDung);
        nguoiDungList = new ArrayList<>();
        nguoiDungAdapter = new NguoiDungAdapter(nguoiDungList, this);
        rvNguoiDung.setAdapter(nguoiDungAdapter);
        //layout theo chieu doc
        LinearLayoutManager vertical = new LinearLayoutManager(this);
        rvNguoiDung.setLayoutManager(vertical);

        //Lay du lieu tu db ra
        nguoiDungDAO = new NguoiDungDAO(this);
        //refresh adapter
        refreshAdapter();

        addEvents();
    }

    private void addEvents() {
    }

    private void refreshAdapter() {
        nguoiDungList.clear();
        nguoiDungList.addAll(nguoiDungDAO.getAllNguoiDung());
        nguoiDungAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_user, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mnu_them_nguoi_dung:
                Intent intent = new Intent(this, NguoiDungActivity.class);
                startActivity(intent);
                break;
            case R.id.mnu_doi_mat_khau:
                startActivity(new Intent(this, ChangePasswordActivity.class));
                break;
            case R.id.mnu_log_out:
                username = "";
                startActivity(new Intent(this, LoginActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshAdapter();
    }
}
