package vn.poly.mob204.bookmanager_binhvttph07052.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import vn.poly.mob204.bookmanager_binhvttph07052.R;
import vn.poly.mob204.bookmanager_binhvttph07052.ValidateFunctionLibrary;
import vn.poly.mob204.bookmanager_binhvttph07052.dao.SachDAO;
import vn.poly.mob204.bookmanager_binhvttph07052.dao.TheLoaiDAO;
import vn.poly.mob204.bookmanager_binhvttph07052.model.Sach;
import vn.poly.mob204.bookmanager_binhvttph07052.model.TheLoai;

import static vn.poly.mob204.bookmanager_binhvttph07052.activity.TheLoaiActivity.STATUS_INSERT;
import static vn.poly.mob204.bookmanager_binhvttph07052.activity.TheLoaiActivity.STATUS_UPDATE;

public class ThemSachActivity extends AppCompatActivity {
    private static final String TAG = "ThemSachActivityLog";
    public static final String KEY_SACH = "Sach";
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

    //database sach
    SachDAO sachDAO;

    //lay thong tin sach tu intent
    Sach sach;

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

        refreshSpinner();

        //xac dinh xem la insert hay update
        intent=getIntent();//intent mang doi tuong Sach ben ListBookActivity sang
        sach=(Sach) intent.getSerializableExtra(KEY_SACH);
        if (sach!=null) {
            status=STATUS_UPDATE;
            edMaSach.setEnabled(false); //khong sua ma sach
            setThongTinCuaDoiTuongSachLenView();

        } else {
            status=STATUS_INSERT;
        }
    }

    /**
     * Lấy thông tin của đối tượng sách vừa gửi từ list sách lên view
     */
    private void setThongTinCuaDoiTuongSachLenView() {
        edMaSach.setText(sach.getMaSach());
        setTheLoaiToSpinner();
        edTenSach.setText(sach.getTenSach());
        edTacGia.setText(sach.getTacGia());
        edNXB.setText(sach.getNXB());
        edGiaBia.setText(String.valueOf(sach.getGiaBia()));
        edSoLuong.setText(String.valueOf(sach.getSoLuong()));
    }

    /**
     * Set thể loại trong spinner cho đúng với mã thể loại của đối tượng gửi sang.
     * Duyệt một lượt các thể loại trong spinner, thể loại nào có mã trùng với
     * mã thể loại của đối tượng gửi sang thì spinner sẽ chọn thể loại đó để
     * hiển thị lên giao diện khi chưa xổ ra (chọn thể loại đấy là selection)
     */
    private void setTheLoaiToSpinner() {
        String currentMaTheLoai=sach.getMaTheLoai();
        for (int i=0; i<adapterTheLoai.getCount(); i++) {
            TheLoai thisTheLoai=adapterTheLoai.getItem(i);
            String maTheLoaiOfThisTheLoai=thisTheLoai.getMaTheLoai();
            boolean isThisMaTheLoaiEqualsMaTheLoaiOfDeliveredSach=
                    maTheLoaiOfThisTheLoai.equals(currentMaTheLoai);
            if (isThisMaTheLoaiEqualsMaTheLoaiOfDeliveredSach) {
                spnTheLoai.setSelection(i);
                return;
            }
        }
    }

    private void init() {
        validateFunctionLibrary=new ValidateFunctionLibrary(this);
        theLoaiDAO=new TheLoaiDAO(this);
        sachDAO=new SachDAO(this);
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
        //bat buoc phai co it nhat mot the loai moi co the tao sach moi
        if (!isThereAtLeastOneCategory()) {
            Toast.makeText(
                    this,
                    R.string.pls_create_at_least_one_category,
                    Toast.LENGTH_SHORT
            ).show();
            return;
        }
        layThongTinTrenForm();
        //validate
        if (allValidate()==ValidateFunctionLibrary.FAIL) {
            return;
        }

        //chuyen gia thanh double, so luong thanh int
        giaBia=Double.parseDouble(giaBiaText);
        soLuong=Integer.parseInt(soLuongText);

        if (status==STATUS_INSERT) {
            addBook();
        } else if (status==STATUS_UPDATE) {
            updateBook();
        }
    }

    //validate trong,
    // gia sach phai la so,
    // so luong phai la so nguyen
    private boolean allValidate() {
        //ma sach
        if (validateFunctionLibrary.isTextEmpty(
                        maSach,
                        getResources().getString(R.string.ma_sach))) {
            return false;
        }
        //ten sach
        if (validateFunctionLibrary.isTextEmpty(
                tenSach,
                getResources().getString(R.string.ten_sach))) {
            return false;
        }

        //tac gia
        if (validateFunctionLibrary.isTextEmpty(
                tacGia,
                getResources().getString(R.string.tac_gia))) {
            return false;
        }

        //nxb
        if (validateFunctionLibrary.isTextEmpty(
                nxb,
                getResources().getString(R.string.nha_xuat_ban))) {
            return false;
        }

        //gia bia - empty
        if (validateFunctionLibrary.isTextEmpty(
                giaBiaText,
                getResources().getString(R.string.gia_bia))) {
            return false;
        }

        //gia bia - double
        if (validateFunctionLibrary.canNotParseToDouble(
                giaBiaText,
                getResources().getString(R.string.gia_bia))) {
            return false;
        }

        //so luong - empty
        if (validateFunctionLibrary.isTextEmpty(
                soLuongText,
                getResources().getString(R.string.so_luong))) {
            return false;
        }

        //so luong - int
        if (validateFunctionLibrary.canNotParseToInt(
                soLuongText,
                getResources().getString(R.string.so_luong))) {
            return false;
        }
        return true;
    }

    private void layThongTinTrenForm() {
        maSach=edMaSach.getText().toString().trim();
        //lay the loai dang chon tren spinner, roi lay ma the loai
        maTheLoai=((TheLoai) spnTheLoai.getSelectedItem()).getMaTheLoai();
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
        Sach bookToUpdate=new Sach(
                maSach,
                maTheLoai,
                tenSach,
                tacGia,
                nxb,
                giaBia,
                soLuong
        );
        int result=sachDAO.updateSach(bookToUpdate);
        if (result>0) {
            Toast.makeText(
                    this,
                    R.string.update_successfully,
                    Toast.LENGTH_SHORT
            ).show();
        } else {
            Toast.makeText(
                    this,
                    R.string.update_failed,
                    Toast.LENGTH_SHORT
            ).show();
        }
    }

    private void addBook() {
        //tao doi tuong
        Sach sach=new Sach(
                maSach,
                maTheLoai,
                tenSach,
                tacGia,
                nxb,
                giaBia,
                soLuong
        );
        Log.d(TAG, sach.toString());

        //them vao db, thong bao them thanh cong hay that bai
        long result=sachDAO.insertSach(sach);
        if (result>-1) {
            Toast.makeText(
                    this,
                    R.string.insert_successfully,
                    Toast.LENGTH_SHORT
            ).show();
        } else {
            Toast.makeText(
                    this,
                    R.string.insert_fail,
                    Toast.LENGTH_SHORT
            ).show();
        }
    }

    public void returnPreviousScreen(View view) {
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
