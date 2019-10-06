package vn.poly.mob204.bookmanager_binhvttph07052.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import vn.poly.mob204.bookmanager_binhvttph07052.R;
import vn.poly.mob204.bookmanager_binhvttph07052.ValidateFunctionLibrary;
import vn.poly.mob204.bookmanager_binhvttph07052.dao.TheLoaiDAO;
import vn.poly.mob204.bookmanager_binhvttph07052.model.TheLoai;

import static vn.poly.mob204.bookmanager_binhvttph07052.adapter.TheLoaiAdapter.ViewHolder.KEY_MA_THE_LOAI;
import static vn.poly.mob204.bookmanager_binhvttph07052.adapter.TheLoaiAdapter.ViewHolder.KEY_MO_TA;
import static vn.poly.mob204.bookmanager_binhvttph07052.adapter.TheLoaiAdapter.ViewHolder.KEY_TEN_THE_LOAI;
import static vn.poly.mob204.bookmanager_binhvttph07052.adapter.TheLoaiAdapter.ViewHolder.KEY_VI_TRI;

public class TheLoaiActivity extends AppCompatActivity {
    //view
    private EditText edMaTheLoai;
    private EditText edTenTheLoai;
    private EditText edViTri;
    private EditText edMoTa;

    //thong tin the loai
    String maTheLoai, tenTheLoai, moTa, viTriText;
    int viTri;

    //validate
    ValidateFunctionLibrary validateFunctionLibrary;

    //database
    TheLoaiDAO theLoaiDAO;

    //intent from item the loai
    Intent intent;
    Bundle bundle;
    public static final int STATUS_INSERT=0;
    public static final int STATUS_UPDATE=1;
    private int status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_the_loai);
        addControls();
        init();

        //up button
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        intent=getIntent();
        bundle=intent.getExtras();
//        Kiểm tra xem có dữ liệu nào được gửi tới màn hình này qua Bundle không.
//        Nếu insert thì bundle==null,
//        nếu update, tức là có dữ liệu gửi tới màn hình này (để sửa, thì bundle!=null
        if (bundle!=null) {
            status=STATUS_UPDATE;
        } else {
            status=STATUS_INSERT;
        }

        if (status==STATUS_UPDATE) {
            //update
//            set thông tin lên giao diện
            edMaTheLoai.setText(bundle.getString(KEY_MA_THE_LOAI));
            edTenTheLoai.setText(bundle.getString(KEY_TEN_THE_LOAI));
            edViTri.setText(String.valueOf(bundle.getInt(KEY_VI_TRI)));
            edMoTa.setText(bundle.getString(KEY_MO_TA));
//            disable edt mã thể loại để người dùng không sửa mã thể loại
            edMaTheLoai.setEnabled(false);
        }
    }

    private void init() {
        validateFunctionLibrary=new ValidateFunctionLibrary(this);
        theLoaiDAO=new TheLoaiDAO(this);
    }

    private void addControls() {
        edMaTheLoai = (EditText) findViewById(R.id.edMaTheLoai);
        edTenTheLoai = (EditText) findViewById(R.id.edTenTheLoai);
        edViTri = (EditText) findViewById(R.id.edViTri);
        edMoTa = (EditText) findViewById(R.id.edMoTa);
    }

    public void addTheLoai(View view) {
        //lay thong tin
        maTheLoai=edMaTheLoai.getText().toString().trim();
        tenTheLoai=edTenTheLoai.getText().toString().trim();
        moTa=edMoTa.getText().toString().trim();
        viTriText=edViTri.getText().toString().trim();
        //validate
        if (allValidate()==ValidateFunctionLibrary.FAIL)
            return;
        //parse viTriText sang int
        viTri=Integer.parseInt(viTriText);

        if (status==STATUS_INSERT) {
            insertTheLoaiFromUi();
        } else if (status==STATUS_UPDATE) {
            updateTheLoaiFromUi();
        }
    }

    private void updateTheLoaiFromUi() {
        int result=theLoaiDAO.updateTheLoai(new TheLoai(maTheLoai, tenTheLoai, moTa, viTri));
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

    private void insertTheLoaiFromUi() {
        //tao doi tuong
        TheLoai theLoai=new TheLoai(maTheLoai, tenTheLoai, moTa, viTri);
        //kiem tra xem co bi trung ma the loai khong
        if (theLoaiDAO.checkPrimaryKey(maTheLoai)==true) {
            //tuc la ma the loai da ton tai trong db roi
            Toast.makeText(
                    this,
                    R.string.error_duplicate_category_id,
                    Toast.LENGTH_SHORT
            ).show();
            return;
        }
        //neu khong trung voi ma~ nao, them vao db
        long result=theLoaiDAO.insertTheLoai(theLoai);
        //thong bao ket qua
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

    /**
     *  Kiem tra trong va kiem tra xem viTri co chuyen thanh so nguyen duoc khong
     * @return true neu pass tat ca validate, false neu fail mot trong cac validate
     */
    private boolean allValidate() {
        if (
                validateFunctionLibrary.isTextEmpty(
                        maTheLoai,
                        getResources().getString(R.string.ma_the_loai))) {
            return false;
        }

        if (
                validateFunctionLibrary.isTextEmpty(
                        tenTheLoai,
                        getResources().getString(R.string.ten_the_loai))) {
            return false;
        }

        if (
                validateFunctionLibrary.isTextEmpty(
                        viTriText,
                        getResources().getString(R.string.vi_tri))) {
            return false;
        }

        //kiem tra xem viTri co chuyen sang so nguyen duoc khong
        if (
                validateFunctionLibrary.canNotParseToInt(
                        viTriText, getResources().getString(R.string.vi_tri))) {
            return false;
        }

        if (
                validateFunctionLibrary.isTextEmpty(
                        moTa,
                        getResources().getString(R.string.mo_ta))) {
            return false;
        }
        return true;
    }

    public void clearForm(View view) {
        edMaTheLoai.setText("");
        edTenTheLoai.setText("");
        edViTri.setText("");
        edMoTa.setText("");
    }

    public void returnListTheLoaiActivity(View view) {
        finish();
    }
}
