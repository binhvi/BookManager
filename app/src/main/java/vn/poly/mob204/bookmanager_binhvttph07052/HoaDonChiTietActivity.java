package vn.poly.mob204.bookmanager_binhvttph07052;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import vn.poly.mob204.bookmanager_binhvttph07052.adapter.CartAdapter;
import vn.poly.mob204.bookmanager_binhvttph07052.model.HoaDon;
import vn.poly.mob204.bookmanager_binhvttph07052.model.HoaDonChiTiet;
import vn.poly.mob204.bookmanager_binhvttph07052.model.Sach;

public class HoaDonChiTietActivity extends AppCompatActivity {
    private EditText edMaHoaDon;
    private AutoCompleteTextView auTenSach;
    private EditText edSoLuongMua;
    private TextView tvThanhTien;
    private RecyclerView rvCart;
    public static List<HoaDonChiTiet> dsHDCT;
    CartAdapter adapter;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hoa_don_chi_tiet);
        addControls();

        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        //recycler view configuration
        dsHDCT=new ArrayList<>();
        adapter=new CartAdapter(this, dsHDCT);
        rvCart.setAdapter(adapter);
        LinearLayoutManager vertical=new LinearLayoutManager(this);
        rvCart.setLayoutManager(vertical);

        //fake du lieu
        for(int i=0; i<30; i++) {
            //tao doi tuong hoa don chi tiet moi
            HoaDonChiTiet hoaDonChiTiet=new HoaDonChiTiet(
                    i,
                    new HoaDon(i, new Date(System.currentTimeMillis())),
                    new Sach(String.valueOf(i), "", "", "", "", 50000, 1),
                    i
            );
            //them vao list
            dsHDCT.add(hoaDonChiTiet);
        }
    }

    private void addControls() {
        edMaHoaDon = (EditText) findViewById(R.id.edMaHoaDon);
        auTenSach = (AutoCompleteTextView) findViewById(R.id.auTenSach);
        edSoLuongMua = (EditText) findViewById(R.id.edSoLuongMua);
        tvThanhTien = (TextView) findViewById(R.id.tvThanhTien);
        rvCart = (RecyclerView) findViewById(R.id.rvCart);
    }

    public void AddHoaDonChiTiet(View view) {
    }

    public void thanhToanHoaDon(View view) {
    }
}
