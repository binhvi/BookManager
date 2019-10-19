package vn.poly.mob204.bookmanager_binhvttph07052.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import vn.poly.mob204.bookmanager_binhvttph07052.R;
import vn.poly.mob204.bookmanager_binhvttph07052.ValidateFunctionLibrary;
import vn.poly.mob204.bookmanager_binhvttph07052.dao.NguoiDungDAO;
import vn.poly.mob204.bookmanager_binhvttph07052.model.NguoiDung;

import static vn.poly.mob204.bookmanager_binhvttph07052.activity.LoginActivity.username;

public class ChangePasswordActivity extends AppCompatActivity {
    private EditText edPass;
    private EditText edRePass;
    NguoiDungDAO nguoiDungDAO;
    String password, rePassword;

    //validate
    ValidateFunctionLibrary validateFunctionLibrary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        edPass = (EditText) findViewById(R.id.edPassword);
        edRePass = (EditText) findViewById(R.id.edRePassword);
        nguoiDungDAO = new NguoiDungDAO(this);
        validateFunctionLibrary = new ValidateFunctionLibrary(this);
    }

    /**
     * Lấy pass trên UI,
     * validate,
     * validate pass thì lưu lại thông tin vào db, (username la bien static tu LoginActivity
     * nếu có username đó thì sẽ lưu thành công,
     * nếu là admin hoặc không có username đó trong db thì sẽ lưu thất bại,
     * rồi trở lại màn hình trước đó
     *
     * @param view
     */
    public void changePassword(View view) {

        password = edPass.getText().toString().trim();
        rePassword = edRePass.getText().toString().trim();
        //validate
        if (validateForm() == true) {
            //tao doi tuong NguoiDung
            NguoiDung nguoiDung = new NguoiDung(username, password);
            try {
                if (nguoiDungDAO.changePasswordNguoiDung(nguoiDung) == true) {
                    Toast.makeText(
                            getApplicationContext(),
                            "Lưu thành công",
                            Toast.LENGTH_SHORT
                    ).show();
                } else {
                    Toast.makeText(
                            getApplicationContext(),
                            "Lưu thất bại",
                            Toast.LENGTH_SHORT
                    ).show();
                }
                finish();
            } catch (Exception exc) {
                //de phong xay ra loi khi thao tac voi db
                Log.e("Error", exc.toString());
                exc.printStackTrace();
            }
        }
    }

    public boolean validateForm() {
        // Tài khoản phải tồn tại trong csdl
        if (!nguoiDungDAO.isUsernameAlreadyExists(username)) {
            //tai khoan khong ton tai trong db
            Toast.makeText(
                    this,
                    R.string.can_not_change_password_because_account_dont_exists_in_database,
                    Toast.LENGTH_SHORT
            ).show();
            return false;
        }

        if (validateFunctionLibrary.isTextEmpty(password, getResources().getString(R.string.password))) {
            return false;
        }

        if (password.length() < 6) {
            Toast.makeText(
                    this,
                    R.string.password_must_have_at_least_six_character,
                    Toast.LENGTH_SHORT
            ).show();
            return false;
        }

        //bat loi repass trong
        if (validateFunctionLibrary.isTextEmpty(rePassword, getResources().getString(R.string.re_password))) {
            return false;
        }

        if (!password.equals(rePassword)) {
            Toast.makeText(
                    getApplicationContext(),
                    "Mật khẩu không trùng khớp",
                    Toast.LENGTH_SHORT
            ).show();
            return false;
        }
        return true;
    }
}
