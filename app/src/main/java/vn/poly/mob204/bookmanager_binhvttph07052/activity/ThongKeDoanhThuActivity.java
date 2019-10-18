package vn.poly.mob204.bookmanager_binhvttph07052.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import vn.poly.mob204.bookmanager_binhvttph07052.R;
import vn.poly.mob204.bookmanager_binhvttph07052.dao.HoaDonChiTietDAO;

public class ThongKeDoanhThuActivity extends AppCompatActivity {
    private TextView tvThongKeNgay;
    private TextView tvThongKeThang;
    private TextView tvThongKeNam;

    //database
    HoaDonChiTietDAO hoaDonChiTietDAO;

    //doanh thu truy van duoc
    double doanhThuTrongNgay;
    double doanhThuTrongThang;
    double doanhThuTrongNam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_ke_doanh_thu);
        addControls();

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        init();

        //lay so tien ban duoc trong ngay hom nay
        doanhThuTrongNgay = hoaDonChiTietDAO.getDoanhThuTrongNgay();
        //lay so tien ban duoc trong thang nay
        doanhThuTrongThang = hoaDonChiTietDAO.getDoanhThuTrongThang();
        //lay so tien ban duoc trong nam nay
        doanhThuTrongNam = hoaDonChiTietDAO.getDoanhThuTrongNam();

        //set len text
        tvThongKeNgay.append(String.format("%.0f " + getResources().getString(R.string.vnd), doanhThuTrongNgay));
        tvThongKeThang.append(String.format("%.0f " + getResources().getString(R.string.vnd), doanhThuTrongThang));
        tvThongKeNam.append(String.format("%.0f " + getResources().getString(R.string.vnd), doanhThuTrongNam));
    }

    private void init() {
        hoaDonChiTietDAO = new HoaDonChiTietDAO(this);
    }

    private void addControls() {
        tvThongKeNgay = (TextView) findViewById(R.id.tvThongKeNgay);
        tvThongKeThang = (TextView) findViewById(R.id.tvThongKeThang);
        tvThongKeNam = (TextView) findViewById(R.id.tvThongKeNam);
    }
}
