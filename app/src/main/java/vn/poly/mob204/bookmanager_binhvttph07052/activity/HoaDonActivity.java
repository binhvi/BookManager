package vn.poly.mob204.bookmanager_binhvttph07052.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import vn.poly.mob204.bookmanager_binhvttph07052.R;
import vn.poly.mob204.bookmanager_binhvttph07052.adapter.BookForCartAdapter;
import vn.poly.mob204.bookmanager_binhvttph07052.dao.HoaDonDAO;
import vn.poly.mob204.bookmanager_binhvttph07052.dao.SachDAO;
import vn.poly.mob204.bookmanager_binhvttph07052.model.HoaDon;
import vn.poly.mob204.bookmanager_binhvttph07052.model.Sach;

public class HoaDonActivity extends AppCompatActivity {
    private static final String TAG = "HoaDonActivityLog";

    //view
    private EditText edNgayMua;
    private AutoCompleteTextView auBook;
    private EditText edSoLuongMua;
    private RecyclerView rvCart;
    private TextView tvTotalOfCart;

    //thong tin
    private String dateString;

    //dd-MM-yyyy
    Calendar calendarDate=Calendar.getInstance();
    SimpleDateFormat sdfDate=new SimpleDateFormat("dd-MM-yyyy");

    //database
    HoaDonDAO hoaDonDAO;

    //get book list from database
    BookForCartAdapter bookForCartAdapter;
    SachDAO sachDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hoa_don);
        addControls();
        init();

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        refreshBookForCartAdapter();
        
        addEvents();
    }

    private void addEvents() {
        auBook.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }

    private void init() {
        hoaDonDAO=new HoaDonDAO(this);

        //Đổ dữ liệu sách từ db vào adapter của autocompleteTextView
        sachDAO=new SachDAO(this);
        bookForCartAdapter = new BookForCartAdapter(this, R.layout.item_book_for_cart);
        bookForCartAdapter.addAll(sachDAO.getAllSach());
        auBook.setAdapter(bookForCartAdapter);
    }

    private void addControls() {
        edNgayMua = (EditText) findViewById(R.id.edNgayMua);
        auBook = (AutoCompleteTextView) findViewById(R.id.auBook);
        edSoLuongMua = (EditText) findViewById(R.id.edSoLuongMua);
        rvCart = (RecyclerView) findViewById(R.id.rvCart);
        tvTotalOfCart = (TextView) findViewById(R.id.tvTotalOfCart);
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

    //todo: chep code xong roi xoa ham nay di
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

        addHoaDon();

    }

    //todo: chep code xong roi xoa ham nay di
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

    @Override
    protected void onResume() {
        super.onResume();
        refreshBookForCartAdapter();
    }

    private void refreshBookForCartAdapter() {
        bookForCartAdapter.clear();
        bookForCartAdapter.addAll(sachDAO.getAllSach());
        bookForCartAdapter.notifyDataSetChanged();
    }
}
