package vn.poly.mob204.bookmanager_binhvttph07052.dao;

import android.database.sqlite.SQLiteDatabase;

import vn.poly.mob204.bookmanager_binhvttph07052.database.DatabaseHelper;

public class SachDAO {
    private SQLiteDatabase db;
    private DatabaseHelper dbHelper;

    public static final String TABLE_NAME="Sach";
    public static final String SQL_SACH="CREATE TABLE Sach (maSach text primary key, maTheLoai text, tensach text," +
            "tacGia text, NXB text, giaBia double, soLuong number);";
    public static final String TAG="SachDAO";
}
