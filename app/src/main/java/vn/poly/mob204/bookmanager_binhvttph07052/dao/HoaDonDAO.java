package vn.poly.mob204.bookmanager_binhvttph07052.dao;

import android.database.sqlite.SQLiteDatabase;

import vn.poly.mob204.bookmanager_binhvttph07052.database.DatabaseHelper;

public class HoaDonDAO {
    private SQLiteDatabase db;
    private DatabaseHelper dbHelper;

    public static final String TABLE_NAME="HoaDon";

    //todo: da sua lai mahoadon thanh integer
    public static final String SQL_HOA_DON="CREATE TABLE HoaDon (mahoadon integer primary key, ngaymua date);";
    public static final String TAG="HoaDonDAO";
}
