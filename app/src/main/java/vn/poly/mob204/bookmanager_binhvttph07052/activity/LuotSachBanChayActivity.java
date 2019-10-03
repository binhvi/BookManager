package vn.poly.mob204.bookmanager_binhvttph07052.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import vn.poly.mob204.bookmanager_binhvttph07052.R;

public class LuotSachBanChayActivity extends AppCompatActivity {
    private EditText edThang;
    private RecyclerView rvBookTop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_luot_sach_ban_chay);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        edThang = (EditText) findViewById(R.id.edThang);
        rvBookTop = (RecyclerView) findViewById(R.id.rvBookTop);


    }

    public void viewSachTop10(View view) {
    }
}
