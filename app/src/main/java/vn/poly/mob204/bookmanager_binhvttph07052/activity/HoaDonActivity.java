package vn.poly.mob204.bookmanager_binhvttph07052.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import vn.poly.mob204.bookmanager_binhvttph07052.R;

public class HoaDonActivity extends AppCompatActivity {
    private EditText edNgayMua;
    private Button picDate;
    private Button btnADDHoaDon;

    //thong tin
    private String dateString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hoa_don);
        addControls();

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    private void addControls() {
        edNgayMua = (EditText) findViewById(R.id.edNgayMua);
        picDate = (Button) findViewById(R.id.picDate);
        btnADDHoaDon = (Button) findViewById(R.id.btnADDHoaDon);
    }

    public void datePicker(View view) {
    }

    public void AddHoaDon(View view) {
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
        Toast.makeText(
                this,
                "pass validate",
                Toast.LENGTH_SHORT
        ).show();
    }
}
