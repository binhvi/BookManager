package vn.poly.mob204.bookmanager_binhvttph07052.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import vn.poly.mob204.bookmanager_binhvttph07052.R;
import vn.poly.mob204.bookmanager_binhvttph07052.dao.NguoiDungDAO;
import vn.poly.mob204.bookmanager_binhvttph07052.model.NguoiDung;

public class LoginActivity extends AppCompatActivity {
    private EditText edUserName;
    private EditText edPassword;
    private CheckBox chkRememberPass;
    private Button btnLogin;
    private Button btnCancel;
    String strUser, strPass;
    NguoiDungDAO nguoiDungDAO;

    public static String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edUserName = (EditText) findViewById(R.id.edUserName);
        edPassword = (EditText) findViewById(R.id.edPassword);
        chkRememberPass = (CheckBox) findViewById(R.id.chkRememberPass);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnCancel = (Button) findViewById(R.id.btnCancel);

        nguoiDungDAO=new NguoiDungDAO(this);
    }


    public void checkLogin(View view) {
        strUser=edUserName.getText().toString().trim();
        strPass=edPassword.getText().toString().trim();
        if (strUser.isEmpty()||strPass.isEmpty()) {
            Toast.makeText(
                    getApplicationContext(),
                    "Tên đăng nhập và mật khẩu không được bỏ trống",
                    Toast.LENGTH_SHORT
            ).show();
        } else {
            //xem xem có tên nào lưu trong cơ sở dữ liệu và pass có đúng với tên đó không
            if (nguoiDungDAO.checkLogin(strUser, strPass)) {
                Toast.makeText(
                        getApplicationContext(),
                        "Đăng nhập thành công",
                        Toast.LENGTH_SHORT
                ).show();
                //luu lai username de co gi thay doi duoc password
                username=strUser;
//                chuyen sang mh chinh
                startActivity(new Intent(this, MainActivity.class));
                return;
            } else {
                if (strUser.equalsIgnoreCase("admin") &&
                        strPass.equalsIgnoreCase("admin")){
                    rememberUser(strUser, strPass, chkRememberPass.isChecked());
                    username="admin";
                    startActivity(new Intent(this, MainActivity.class));
                    return;
                } else {
                    Toast.makeText(
                            getApplicationContext(),
                            "Tên đăng nhập và mật khẩu không đúng",
                            Toast.LENGTH_SHORT
                    ).show();
                }
            }
        }
    }

    /**
     * Truyền vào username, password, trạng thái checkbox xem có tích hay không
     * Tạo đối tượng SharedPreference, lưu vào file có tên là "USER_FILE", MODE_PRIVATE là gì không nhớ
     * Nếu checkbox không tích
     * 	xóa dữ liệu đã lưu trong file trước đó
     * Nếu checkbox tích
     * 	lấy dữ liệu truyền vào (username, password, trạng thái remember (checkbox có tích hay không) lưu vào file có tên là "USER_FILE"
     * @param u username
     * @param p password
     * @param status trang thai tick hay khong tick cua checkbox rememberPassword
     */
    private void rememberUser(String u, String p, boolean status) {
        //khong hieu MODE_PRIVATE la gi todo:search
        SharedPreferences pref=getSharedPreferences("USER_FILE", MODE_PRIVATE);
        SharedPreferences.Editor edit=pref.edit();
        if (!status) {
            //J: neu trang thai check la false
            //xoa tinh trang luu truoc do
            edit.clear();
        } else {
            //luu du lieu
            edit.putString("USERNAME", u);
            edit.putString("PASSWORD", p);
            edit.putBoolean("REMBMBER", status);
        }
        //luu lai toan bo
        edit.commit();
    }


}
