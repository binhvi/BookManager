package vn.poly.mob204.bookmanager_binhvttph07052.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import vn.poly.mob204.bookmanager_binhvttph07052.R;
import vn.poly.mob204.bookmanager_binhvttph07052.ValidateFunctionLibrary;
import vn.poly.mob204.bookmanager_binhvttph07052.dao.NguoiDungDAO;
import vn.poly.mob204.bookmanager_binhvttph07052.model.NguoiDung;

public class NguoiDungActivity extends AppCompatActivity {
    //view
    private EditText edUser;
    private EditText edPass;
    private EditText edRePass;
    private EditText edPhone;
    private EditText edFullName;
    private Button btnCancelUser;

    //database
    NguoiDungDAO nguoiDungDAO;

    //infomation
    String username, password, rePass, phone, hoTen;

    //validate
    ValidateFunctionLibrary validateFunctionLibrary;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nguoi_dung);
        addControls();

        //up button
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        //validate
        validateFunctionLibrary = new ValidateFunctionLibrary(this);

        //database
        nguoiDungDAO = new NguoiDungDAO(this);

        addEvents();
    }

    private void addEvents() {
        btnCancelUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //clear form
                edUser.setText("");
                edPass.setText("");
                edRePass.setText("");
                edPhone.setText("");
                edFullName.setText("");
            }
        });
    }

    private boolean validateTrong() {
        if (validateFunctionLibrary.isNotEmpty(username, getResources().getString(R.string.user_name))
                == ValidateFunctionLibrary.FAIL)
            return false;
        if (validateFunctionLibrary.isNotEmpty(password, getResources().getString(R.string.password))
                == ValidateFunctionLibrary.FAIL)
            return false;
        if (validateFunctionLibrary.isNotEmpty(rePass, getResources().getString(R.string.re_password))
                == ValidateFunctionLibrary.FAIL)
            return false;
        if (validateFunctionLibrary.isNotEmpty(phone, getResources().getString(R.string.phone_number))
                == ValidateFunctionLibrary.FAIL)
            return false;
        if (validateFunctionLibrary.isNotEmpty(hoTen, getResources().getString(R.string.full_name))
                == ValidateFunctionLibrary.FAIL)
            return false;
        return true;
    }

    private void addControls() {
        edUser = (EditText) findViewById(R.id.edUserName);
        edPass = (EditText) findViewById(R.id.edPassword);
        edRePass = (EditText) findViewById(R.id.edRePassword);
        edPhone = (EditText) findViewById(R.id.edPhone);
        edFullName = (EditText) findViewById(R.id.edFullName);
        btnCancelUser = (Button) findViewById(R.id.btnCancelUser);
    }

    public void addUser(View view) {
        //lay thong tin
        username = edUser.getText().toString().trim();
        password = edPass.getText().toString().trim();
        rePass = edRePass.getText().toString().trim();
        phone = edPhone.getText().toString().trim();
        hoTen = edFullName.getText().toString().trim();

        if (validateTrong() == ValidateFunctionLibrary.FAIL) {
            return;
        }

        //chuyen username thanh lower case
        //de luc so sanh khong bi nham
        username = username.toLowerCase();

        //tao doi tuong
        NguoiDung nguoiDung = new NguoiDung(
                username,
                password,
                phone,
                hoTen
        );
        //them vao db
        long result = nguoiDungDAO.insertNguoiDung(nguoiDung);
        //thong bao ket qua
        if (result > -1) {
            Toast.makeText(
                    NguoiDungActivity.this,
                    R.string.add_successfully,
                    Toast.LENGTH_SHORT
            ).show();
        } else {
            Toast.makeText(
                    NguoiDungActivity.this,
                    R.string.add_fail,
                    Toast.LENGTH_SHORT
            ).show();
        }
    }

    public void showUsers(View view) {
        Intent intent = new Intent(this, ListNguoiDungActivity.class);
        startActivity(intent);
    }
}
