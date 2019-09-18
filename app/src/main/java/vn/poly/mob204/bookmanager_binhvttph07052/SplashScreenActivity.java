package vn.poly.mob204.bookmanager_binhvttph07052;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Start home activity
        startActivity(new Intent(SplashScreenActivity.this, LoginActivity.class));
        //close splash activity
        finish();
    }
}
