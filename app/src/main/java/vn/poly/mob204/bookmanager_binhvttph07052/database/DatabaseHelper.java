package vn.poly.mob204.bookmanager_binhvttph07052.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import vn.poly.mob204.bookmanager_binhvttph07052.dao.HoaDonChiTietDAO;
import vn.poly.mob204.bookmanager_binhvttph07052.dao.HoaDonDAO;
import vn.poly.mob204.bookmanager_binhvttph07052.dao.NguoiDungDAO;
import vn.poly.mob204.bookmanager_binhvttph07052.dao.SachDAO;
import vn.poly.mob204.bookmanager_binhvttph07052.dao.TheLoaiDAO;

import static vn.poly.mob204.bookmanager_binhvttph07052.dao.HoaDonChiTietDAO.SQL_HOA_DON_CHI_TIET;
import static vn.poly.mob204.bookmanager_binhvttph07052.dao.HoaDonDAO.SQL_HOA_DON;
import static vn.poly.mob204.bookmanager_binhvttph07052.dao.NguoiDungDAO.SQL_NGUOI_DUNG;
import static vn.poly.mob204.bookmanager_binhvttph07052.dao.SachDAO.SQL_SACH;
import static vn.poly.mob204.bookmanager_binhvttph07052.dao.TheLoaiDAO.SQL_THE_LOAI;

//todo: document trang 33

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME="dbBookManager.db";
    public static final int VERSION=1;


    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_NGUOI_DUNG);
        db.execSQL(SQL_THE_LOAI);
        db.execSQL(SQL_SACH);
        db.execSQL(SQL_HOA_DON);
        db.execSQL(SQL_HOA_DON_CHI_TIET);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("Drop table if exists " + NguoiDungDAO.TABLE_NAME);
        db.execSQL("Drop table if exists " + TheLoaiDAO.TABLE_NAME);
        db.execSQL("Drop table if exists " + SachDAO.TABLE_NAME);
        db.execSQL("Drop table if exists " + HoaDonDAO.TABLE_NAME);
        db.execSQL("Drop table if exists " + HoaDonChiTietDAO.TABLE_NAME);

        onCreate(db);
    }
}
