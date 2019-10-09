package vn.poly.mob204.bookmanager_binhvttph07052.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import vn.poly.mob204.bookmanager_binhvttph07052.R;
import vn.poly.mob204.bookmanager_binhvttph07052.dao.HoaDonDAO;
import vn.poly.mob204.bookmanager_binhvttph07052.model.HoaDon;

public class HoaDonActivity extends AppCompatActivity {
    private static final String TAG = "HoaDonActivityLog";
    private EditText edNgayMua;

    //thong tin
    private String dateString;

    //dd-MM-yyyy
    Calendar calendarDate=Calendar.getInstance();
    SimpleDateFormat sdfDate=new SimpleDateFormat("dd-MM-yyyy");

    //database
    HoaDonDAO hoaDonDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hoa_don);
        addControls();
        init();

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    private void init() {
        hoaDonDAO=new HoaDonDAO(this);
    }

    private void addControls() {
        edNgayMua = (EditText) findViewById(R.id.edNgayMua);
    }

    public void openDatePicker(View view) {
        DatePickerDialog.OnDateSetListener callBack = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendarDate.set(Calendar.YEAR, year);
                calendarDate.set(Calendar.MONTH, month);
                calendarDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                edNgayMua.setText(sdfDate.format(calendarDate.getTime()));
            }
        };
        DatePickerDialog dialog = new DatePickerDialog(
                this,
                callBack,
                calendarDate.get(Calendar.YEAR),
                calendarDate.get(Calendar.MONTH),
                calendarDate.get(Calendar.DAY_OF_MONTH)
        );
        dialog.show();
    }

    public void saveHoaDon(View view) {
        //lay thong tin
        dateString=edNgayMua.getText().toString().trim();
        //validate: neu chua chon ngay -> dateString empty --> thong bao phai chon ngay
        if (dateString.isEmpty()) {
            Toast.makeText(
                    this,
                    R.string.pls_pick_date,
                    Toast.LENGTH_SHORT
            ).show();
            return;
        }

        //todo: switch insert - update
        //if insert
        addHoaDon();

    }

    private void addHoaDon() {
        //tao doi tuong
        HoaDon hoaDon=new HoaDon(calendarDate.getTime());
        //luu vao db
        long result=hoaDonDAO.insertHoaDon(hoaDon);
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

    public void addBookToCart(View view) {
    }

    public void AddBillAndDetailsToDatabase(View view) {
    }
}
