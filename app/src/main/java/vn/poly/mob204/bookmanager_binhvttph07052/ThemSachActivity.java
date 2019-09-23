package vn.poly.mob204.bookmanager_binhvttph07052;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class ThemSachActivity extends AppCompatActivity {
    private EditText edMaSach;
    private Spinner spnTheLoai;
    private EditText edTenSach;
    private EditText edTacGia;
    private EditText edNXB;
    private EditText edGiaBia;
    private EditText edSoLuong;
    private Button btnAddBook;
    private Button btnCancelBook;
    private Button btnShowBook;
    ArrayAdapter<String> adapterTheLoai;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_sach);

        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        addControls();

        String arr[]={
                "Văn học", "Lịch sử", "Chính trị", "CNTT"};
        adapterTheLoai=new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                arr
        );
        adapterTheLoai.setDropDownViewResource(
                android.R.layout.simple_list_item_single_choice
        );
        spnTheLoai.setAdapter(adapterTheLoai);
    }

    private void addControls() {
        edMaSach = (EditText) findViewById(R.id.edMaSach);
        spnTheLoai = (Spinner) findViewById(R.id.spnTheLoai);
        edTenSach = (EditText) findViewById(R.id.edTenSach);
        edTacGia = (EditText) findViewById(R.id.edTacGia);
        edNXB = (EditText) findViewById(R.id.edNXB);
        edGiaBia = (EditText) findViewById(R.id.edGiaBia);
        edSoLuong = (EditText) findViewById(R.id.edSoLuong);
        btnAddBook = (Button) findViewById(R.id.btnAddBook);
        btnCancelBook = (Button) findViewById(R.id.btnCancelBook);
        btnShowBook = (Button) findViewById(R.id.btnShowBook);
    }

    public void addBook(View view) {
    }

    public void showBook(View view) {
    }
}
