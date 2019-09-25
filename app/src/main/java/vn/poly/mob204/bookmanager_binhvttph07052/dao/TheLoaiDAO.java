package vn.poly.mob204.bookmanager_binhvttph07052.dao;

import android.database.sqlite.SQLiteDatabase;

import vn.poly.mob204.bookmanager_binhvttph07052.database.DatabaseHelper;

public class TheLoaiDAO {
    private SQLiteDatabase db;
    private DatabaseHelper dbHelper;

    public static final String TABLE_NAME="TheLoai";
    public static final String SQL_THE_LOAI="CREATE TABLE TheLoai (matheloai text primary key, tentheloai text, mota text, vitri int);";
    public static final String TAG="TheLoaiDAO";
}
