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
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import vn.poly.mob204.bookmanager_binhvttph07052.R;
import vn.poly.mob204.bookmanager_binhvttph07052.adapter.NguoiDungAdapterListView;
import vn.poly.mob204.bookmanager_binhvttph07052.dao.NguoiDungDAO;
import vn.poly.mob204.bookmanager_binhvttph07052.model.NguoiDung;

import static vn.poly.mob204.bookmanager_binhvttph07052.activity.LoginActivity.username;
import static vn.poly.mob204.bookmanager_binhvttph07052.adapter.NguoiDungAdapterListView.FULLNAME;
import static vn.poly.mob204.bookmanager_binhvttph07052.adapter.NguoiDungAdapterListView.PHONE;
import static vn.poly.mob204.bookmanager_binhvttph07052.adapter.NguoiDungAdapterListView.USERNAME;

public class ListNguoiDungActivity extends AppCompatActivity {
    private ListView lvNguoiDung;
    NguoiDungAdapterListView nguoiDungAdapterListView;
    public static List<NguoiDung> nguoiDungList;
    NguoiDungDAO nguoiDungDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_nguoi_dung);

        //up button
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        lvNguoiDung = findViewById(R.id.lvNguoiDung);
        nguoiDungList = new ArrayList<>();
        nguoiDungAdapterListView = new NguoiDungAdapterListView(
                this,
                R.layout.item_nguoi_dung,
                nguoiDungList);
        lvNguoiDung.setAdapter(nguoiDungAdapterListView);

        //Lay du lieu tu db ra
        nguoiDungDAO = new NguoiDungDAO(this);
        //refresh adapter
        refreshAdapter();

        addEvents();
    }

    private void addEvents() {
        lvNguoiDung.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                NguoiDung nguoiDung = nguoiDungList.get(position);
                //lay ten, phone
                String username = nguoiDung.getUserName();
                String fullName = nguoiDung.getHoTen();
                String phone = nguoiDung.getPhone();
                //gui thong tin nguoi dung sang NguoiDungDetailActivity
                Intent intent = new Intent(ListNguoiDungActivity.this, NguoiDungDetailActivity.class);
                intent.putExtra(USERNAME, username);
                intent.putExtra(PHONE, phone);
                intent.putExtra(FULLNAME, fullName);

                startActivity(intent);
            }
        });
    }

    private void refreshAdapter() {
        nguoiDungList.clear();
        nguoiDungList.addAll(nguoiDungDAO.getAllNguoiDung());
        nguoiDungAdapterListView.notifyDataSetChanged();
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
