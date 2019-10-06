package vn.poly.mob204.bookmanager_binhvttph07052.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import vn.poly.mob204.bookmanager_binhvttph07052.R;
import vn.poly.mob204.bookmanager_binhvttph07052.ValidateFunctionLibrary;
import vn.poly.mob204.bookmanager_binhvttph07052.dao.TheLoaiDAO;
import vn.poly.mob204.bookmanager_binhvttph07052.model.Sach;
import vn.poly.mob204.bookmanager_binhvttph07052.model.TheLoai;

import static vn.poly.mob204.bookmanager_binhvttph07052.activity.TheLoaiActivity.STATUS_INSERT;
import static vn.poly.mob204.bookmanager_binhvttph07052.activity.TheLoaiActivity.STATUS_UPDATE;

public class ThemSachActivity extends AppCompatActivity {
    private EditText edMaSach;
    private Spinner spnTheLoai;
    private EditText edTenSach;
    private EditText edTacGia;
    private EditText edNXB;
    private EditText edGiaBia;
    private EditText edSoLuong;

    private int status; //xac dinh xem la insert hay update
    Intent intent;

    //thong tin
    String maSach, maTheLoai, tenSach, tacGia, nxb, giaBiaText, soLuongText;
    double giaBia;
    int soLuong;

    //validate
    ValidateFunctionLibrary validateFunctionLibrary;

    //cho spinner the loai
    TheLoaiDAO theLoaiDAO;
    ArrayAdapter<TheLoai> adapterTheLoai;
    List<TheLoai> theLoaiList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_sach);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        addControls();
        init();


        //Đổ dữ liệu thể loại từ db vào spinner
        adapterTheLoai = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item
        );
        adapterTheLoai.setDropDownViewResource(
                android.R.layout.simple_list_item_single_choice
        );
        spnTheLoai.setAdapter(adapterTheLoai);

        //xac dinh xem la insert hay update
        intent=getIntent();//intent mang doi tuong Sach ben ListBookActivity sang
        if (intent==null) {
            status=STATUS_INSERT;
        } else {
            status=STATUS_UPDATE;
        }
    }

    private void init() {
        validateFunctionLibrary=new ValidateFunctionLibrary(this);
        theLoaiList=new ArrayList<>();
        theLoaiDAO=new TheLoaiDAO(this);
    }

    private void addControls() {
        edMaSach = (EditText) findViewById(R.id.edMaSach);
        spnTheLoai = (Spinner) findViewById(R.id.spnTheLoai);
        edTenSach = (EditText) findViewById(R.id.edTenSach);
        edTacGia = (EditText) findViewById(R.id.edTacGia);
        edNXB = (EditText) findViewById(R.id.edNXB);
        edGiaBia = (EditText) findViewById(R.id.edGiaBia);
        edSoLuong = (EditText) findViewById(R.id.edSoLuong);
    }

    //nut luu
    public void saveBook(View view) {
        //lay thong tin
        layThongTinTrenForm();
        //validate
        if (allValidate()==ValidateFunctionLibrary.FAIL)
            return;
        //xac dinh xem la them hay xoa
        if (status==STATUS_INSERT) {
            addBook();
        } else if (status==STATUS_UPDATE) {
            updateBook();
        }
    }

    //validate trong,
    //phai co it nhat mot the loai
    // gia sach phai la so,
    // so luong phai la so nguyen
    private boolean allValidate() {
        if (
                validateFunctionLibrary.isNotEmpty(
                        maSach,
                        getResources().getString(R.string.ma_sach)
                )
        ) {
            return false;
        }

        return true;
    }

    private void layThongTinTrenForm() {
        maSach=edMaSach.getText().toString().trim();

        if (isThereAtLeastOneCategory()) {
            //lay the loai dang chon tren spinner, roi lay ma the loai
            maTheLoai=((TheLoai) spnTheLoai.getSelectedItem()).getMaTheLoai();
        } else {
            Toast.makeText(
                    this,
                    R.string.pls_create_at_least_one_category,
                    Toast.LENGTH_SHORT
            ).show();
            return;
        }

        tenSach=edTenSach.getText().toString().trim();
        tacGia=edTacGia.getText().toString().trim();
        nxb=edNXB.getText().toString().trim();
        giaBiaText=edGiaBia.getText().toString().trim();
        soLuongText=edSoLuong.getText().toString().trim();
    }

    private boolean isThereAtLeastOneCategory() {
        if (adapterTheLoai.getCount()>0)
            return true;
        return false;
    }

    private void updateBook() {
    }

    private void addBook() {
    }

    public void showBook(View view) {
        finish();
    }

    public void clearForm(View view) {
        edMaSach.setText("");
        edTenSach.setText("");
        edTacGia.setText("");
        edNXB.setText("");
        edGiaBia.setText("");
        edSoLuong.setText("");
    }

    public void toTheLoaiActivity(View view) {
        Intent toTheLoaiActivityIntent = new Intent(this, TheLoaiActivity.class);
        startActivity(toTheLoaiActivityIntent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshSpinner();
    }

    private void refreshSpinner() {
        adapterTheLoai.clear();
        adapterTheLoai.addAll(theLoaiDAO.getAllTheLoai());
        adapterTheLoai.notifyDataSetChanged();
    }
}
