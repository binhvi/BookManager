package vn.poly.mob204.bookmanager_binhvttph07052.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import vn.poly.mob204.bookmanager_binhvttph07052.R;

public class HoaDonActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hoa_don);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    public void datePicker(View view) {
    }

    public void AddHoaDon(View view) {
        Intent intent = new Intent(this, HoaDonChiTietActivity.class);
        startActivity(intent);
    }
}
