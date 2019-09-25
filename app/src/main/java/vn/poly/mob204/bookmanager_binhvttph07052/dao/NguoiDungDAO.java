package vn.poly.mob204.bookmanager_binhvttph07052.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import vn.poly.mob204.bookmanager_binhvttph07052.database.DatabaseHelper;
import vn.poly.mob204.bookmanager_binhvttph07052.model.NguoiDung;

public class NguoiDungDAO {
    private DatabaseHelper dbHelper;

    public static final String TABLE_NAME="NguoiDung";
    public static final String SQL_NGUOI_DUNG="CREATE TABLE NguoiDung (username text primary key, password text, phone text, hoten text);";
    public static final String TAG="NguoiDungDAO";

    //column
    public static final String COLUMN_NGUOI_DUNG_USERNAME="username";
    public static final String COLUMN_NGUOI_DUNG_PASSWORD="password";
    public static final String COLUMN_NGUOI_DUNG_PHONE="phone";
    public static final String COLUMN_NGUOI_DUNG_HO_TEN="hoten";

    public NguoiDungDAO(Context context) {
        dbHelper=new DatabaseHelper(context);
    }

    public long insertNguoiDung(NguoiDung nd) {
        //xin quyen
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        //content values
        ContentValues values=new ContentValues();
        values.put(COLUMN_NGUOI_DUNG_USERNAME, nd.getUserName());
        values.put(COLUMN_NGUOI_DUNG_PASSWORD, nd.getPassword());
        values.put(COLUMN_NGUOI_DUNG_PHONE, nd.getPhone());
        values.put(COLUMN_NGUOI_DUNG_HO_TEN, nd.getHoTen());
        //cau lenh insert
        try {
            long result=db.insert(TABLE_NAME, null, values);
            return result;
        } catch (Exception exc) {
            Log.e(TAG, exc.toString());
            return -1;
        } finally {
            db.close();
        }
    }
}
