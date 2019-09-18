package vn.poly.mob204.bookmanager_binhvttph07052;

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

import java.util.ArrayList;
import java.util.List;

import vn.poly.mob204.bookmanager_binhvttph07052.adapter.NguoiDungAdapter;
import vn.poly.mob204.bookmanager_binhvttph07052.model.NguoiDung;

public class ListNguoiDungActivity extends AppCompatActivity {
    private RecyclerView rvNguoiDung;
    NguoiDungAdapter nguoiDungAdapter;
    List<NguoiDung> nguoiDungList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_nguoi_dung);

        //up button
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        rvNguoiDung = (RecyclerView) findViewById(R.id.rvNguoiDung);
        nguoiDungList=new ArrayList<>();

        //fake du lieu
        for (int i=0; i<10; i++) {
            NguoiDung nguoiDung=new NguoiDung();
            nguoiDung.setHoTen("Hoang Thuy Linh "+i);
            nguoiDung.setPhone("1800100"+i);
            nguoiDungList.add(nguoiDung);
        }

        nguoiDungAdapter=new NguoiDungAdapter(nguoiDungList, this);
        rvNguoiDung.setAdapter(nguoiDungAdapter);

        //layout theo chieu doc
        LinearLayoutManager vertical=new LinearLayoutManager(this);
        rvNguoiDung.setLayoutManager(vertical);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu_user, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mnu_them_nguoi_dung:
                Intent intent=new Intent(this, NguoiDungActivity.class);
                startActivity(intent);
                break;
            case R.id.mnu_doi_mat_khau:
                startActivity(new Intent(this, ChangePasswordActivity.class));
                break;
            case R.id.mnu_log_out:
                //todo: log out
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
