package vn.poly.mob204.bookmanager_binhvttph07052;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {
    private EditText edUserName;
    private EditText edPassword;
    private CheckBox chkRememberPass;
    private Button btnLogin;
    private Button btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edUserName = (EditText) findViewById(R.id.edUserName);
        edPassword = (EditText) findViewById(R.id.edPassword);
        chkRememberPass = (CheckBox) findViewById(R.id.chkRememberPass);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnCancel = (Button) findViewById(R.id.btnCancel);
    }

    public void checkLogin(View view) {
        Intent intent=new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
