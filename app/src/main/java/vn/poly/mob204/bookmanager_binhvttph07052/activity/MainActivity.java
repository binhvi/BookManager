package vn.poly.mob204.bookmanager_binhvttph07052.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import vn.poly.mob204.bookmanager_binhvttph07052.R;
import vn.poly.mob204.bookmanager_binhvttph07052.dao.HoaDonChiTietDAO;
import vn.poly.mob204.bookmanager_binhvttph07052.dao.HoaDonDAO;
import vn.poly.mob204.bookmanager_binhvttph07052.database.DatabaseHelper;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void viewNguoiDung(View view) {
        Intent intent = new Intent(this, ListNguoiDungActivity.class);
        startActivity(intent);
    }

    public void viewTheLoai(View view) {
        Intent intent = new Intent(this, ListTheLoaiActivity.class);
        startActivity(intent);
    }

    public void viewListBookActivity(View view) {
        Intent intent = new Intent(this, ListBookActivity.class);
        startActivity(intent);
    }

    public void ViewListHoaDonActivity(View view) {
        Intent intent = new Intent(this, ListHoaDonActivity.class);
        startActivity(intent);
    }

    public void ViewTopSach(View view) {
        Intent intent = new Intent(this, LuotSachBanChayActivity.class);
        startActivity(intent);
    }

    public void ViewThongKeActivity(View view) {
        Intent intent = new Intent(this, ThongKeDoanhThuActivity.class);
        startActivity(intent);
    }
}
