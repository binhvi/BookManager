package vn.poly.mob204.bookmanager_binhvttph07052.dao;

import android.database.sqlite.SQLiteDatabase;

import vn.poly.mob204.bookmanager_binhvttph07052.database.DatabaseHelper;

public class HoaDonChiTietDAO {
    private SQLiteDatabase db;
    private DatabaseHelper dbHelper;

    public static final String TABLE_NAME="HoaDonChiTiet";
    public static final String SQL_HOA_DON_CHI_TIET="CREATE TABLE HoaDonChiTiet(maHDCT INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "maHoaDon text NOT NULL, maSach text NOT NULL, soLuong INTEGER);";
    public static final String TAG="HoaDonChiTiet";
}
