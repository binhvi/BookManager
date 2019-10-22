package vn.poly.mob204.bookmanager_binhvttph07052.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;
import java.util.zip.Inflater;

import vn.poly.mob204.bookmanager_binhvttph07052.R;
import vn.poly.mob204.bookmanager_binhvttph07052.dao.NguoiDungDAO;
import vn.poly.mob204.bookmanager_binhvttph07052.model.NguoiDung;

import static vn.poly.mob204.bookmanager_binhvttph07052.activity.LoginActivity.username;

public class NguoiDungAdapterListView extends ArrayAdapter {
    private static final String TAG = "UserAdapterListViewLog";
    Activity context;
    List<NguoiDung> arrNguoiDung;
    NguoiDungDAO nguoiDungDAO;
    int resource;

    public static final String USERNAME = "USERNAME";
    public static final String PHONE = "PHONE";
    public static final String FULLNAME = "FULLNAME";

    public NguoiDungAdapterListView(@NonNull Activity context, int resource, List<NguoiDung> arrNguoiDung) {
        super(context, resource);
        this.arrNguoiDung = arrNguoiDung;
        this.context = context;
        nguoiDungDAO = new NguoiDungDAO(context);
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = this.context.getLayoutInflater();
        View customView = inflater.inflate(resource, parent, false);

        //bind view
        final NguoiDung nguoiDung = arrNguoiDung.get(position);
        final int pos = position;

        //view
        ImageView img;
        TextView txtName;
        TextView txtPhone;
        ImageView imgDelete;

        img = (ImageView) customView.findViewById(R.id.ivIcon);
        txtName = (TextView) customView.findViewById(R.id.tvName);
        txtPhone = (TextView) customView.findViewById(R.id.tvPhone);
        imgDelete = (ImageView) customView.findViewById(R.id.ivDelete);

        //icon
        if (position % 3 == 0) {
            img.setImageResource(R.drawable.emone);
        } else if (position % 3 == 1) {
            img.setImageResource(R.drawable.emtwo);
        } else {
            img.setImageResource(R.drawable.emthree);
        }
        //hien thi name
        txtName.setText(nguoiDung.getHoTen());
        //hien thi so dien thoai
        txtPhone.setText(nguoiDung.getPhone());

        //An user cannot delete his own account while he is logging in
        Log.i(TAG, "username hien tai: "+nguoiDung.getUserName());
        Log.i(TAG, "username dang nhap: "+username);
        if (nguoiDung.getUserName().equals(username)) {
            //View.GONE: //This view is invisible, and it doesn't take any space for layout
            imgDelete.setVisibility(View.GONE);
            Log.i(TAG, "img da bi xoa");
        }

        //xoa
        imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //xoa trong db
                int result = nguoiDungDAO.deleteNguoiDungByID(nguoiDung.getUserName());
                //neu thanh cong thi refresh adapter
                if (result > 0) {
                    refreshAdapter();
                } else {
                    //neu khong thanh cong thi bao xoa loi
                    Toast.makeText(
                            context,
                            R.string.delete_failed,
                            Toast.LENGTH_SHORT
                    ).show();
                }
            }
        });

        return customView;
    }

    private void refreshAdapter() {
        arrNguoiDung.clear();
        arrNguoiDung.addAll(nguoiDungDAO.getAllNguoiDung());
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return arrNguoiDung.size();
    }
}
