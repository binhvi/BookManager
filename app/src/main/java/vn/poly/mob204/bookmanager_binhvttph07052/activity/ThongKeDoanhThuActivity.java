package vn.poly.mob204.bookmanager_binhvttph07052.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import vn.poly.mob204.bookmanager_binhvttph07052.R;

public class ThongKeDoanhThuActivity extends AppCompatActivity {
    private TextView tvThongKeNgay;
    private TextView tvThongKeThang;
    private TextView tvThongKeNam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_ke_doanh_thu);

        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        tvThongKeNgay = (TextView) findViewById(R.id.tvThongKeNgay);
        tvThongKeThang = (TextView) findViewById(R.id.tvThongKeThang);
        tvThongKeNam = (TextView) findViewById(R.id.tvThongKeNam);

    }
}
