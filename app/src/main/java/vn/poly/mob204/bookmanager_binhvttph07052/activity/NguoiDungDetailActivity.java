package vn.poly.mob204.bookmanager_binhvttph07052.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import vn.poly.mob204.bookmanager_binhvttph07052.R;
import vn.poly.mob204.bookmanager_binhvttph07052.ValidateFunctionLibrary;
import vn.poly.mob204.bookmanager_binhvttph07052.dao.NguoiDungDAO;

import static vn.poly.mob204.bookmanager_binhvttph07052.activity.NguoiDungActivity.regexHoTen;
import static vn.poly.mob204.bookmanager_binhvttph07052.activity.NguoiDungActivity.regexPhone;
import static vn.poly.mob204.bookmanager_binhvttph07052.adapter.NguoiDungAdapter.FULLNAME;
import static vn.poly.mob204.bookmanager_binhvttph07052.adapter.NguoiDungAdapter.PHONE;
import static vn.poly.mob204.bookmanager_binhvttph07052.adapter.NguoiDungAdapter.USERNAME;

public class NguoiDungDetailActivity extends AppCompatActivity {
    Intent intent;
    String username, fullName, phone;
    private EditText edPhone;
    private EditText edFullName;
    NguoiDungDAO nguoiDungDAO;
    ValidateFunctionLibrary validateFunctionLibrary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nguoi_dung_detail);
        addControls();

        //lay thong tin doi tuong duoc gui tu intent
        intent = getIntent();
        username = intent.getStringExtra(USERNAME);
        phone = intent.getStringExtra(PHONE);
        fullName = intent.getStringExtra(FULLNAME);

        //set thong tin len view
        edPhone.setText(phone);
        edFullName.setText(fullName);

        //validate
        validateFunctionLibrary = new ValidateFunctionLibrary(this);

        //database
        nguoiDungDAO = new NguoiDungDAO(this);
    }

    private void addControls() {
        edPhone = (EditText) findViewById(R.id.edPhone);
        edFullName = (EditText) findViewById(R.id.edFullName);
    }

    public void updateUser(View view) {
        //lay thong tin
        phone = edPhone.getText().toString().trim();
        fullName = edFullName.getText().toString().trim();
        //validate
        if (validateFunctionLibrary.isTextEmpty(phone, "Số điện thoại")) {
            return;
        }

        //phone: phải chứa 10 hoặc 11 kí tự chữ số
        if (!phone.matches(regexPhone)) {
            Toast.makeText(
                    this,
                    R.string.phone_must_be_number_characters_length_ten_or_elevent,
                    Toast.LENGTH_SHORT
            ).show();
            return;
        }

        if (validateFunctionLibrary.isTextEmpty(fullName, "Tên đầy đủ")) {
            return;
        }

        //Chỉ bắt được lỗi trong họ tên có số thôi
        if (!fullName.matches(regexHoTen)) {
            Toast.makeText(
                    this,
                    R.string.full_name_must_not_have_number_character,
                    Toast.LENGTH_SHORT
            ).show();
            return;
        }

        //update, thong bao
        int result = nguoiDungDAO.updateInfoNguoiDung(username, phone, fullName);
        if (result > 0) {
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

    public void huy(View view) {
        finish();
    }
}
