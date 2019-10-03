package vn.poly.mob204.bookmanager_binhvttph07052.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import vn.poly.mob204.bookmanager_binhvttph07052.database.DatabaseHelper;
import vn.poly.mob204.bookmanager_binhvttph07052.model.Sach;

public class SachDAO {
    private DatabaseHelper dbHelper;

    public static final String TABLE_NAME = "Sach";
    public static final String SQL_SACH = "CREATE TABLE Sach (maSach text primary key, maTheLoai text, tensach text," +
            "tacGia text, NXB text, giaBia double, soLuong number);";
    public static final String TAG = "SachDAO";

    //column
    public static final String COLUMN_MA_SACH = "maSach";
    public static final String COLUMN_MA_THE_LOAI = "maTheLoai";
    public static final String COLUMN_TEN_SACH = "tensach";
    public static final String COLUMN_TAC_GIA = "tacGia";
    public static final String COLUMN_NXB = "NXB";
    public static final String COLUMN_GIA_BIA = "giaBia";
    public static final String COLUMN_SO_LUONG = "soLuong";

    public SachDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    //insert
    public long insertSach(Sach sach) {
        //xin quyen
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        //ghep cap du lieu
        ContentValues values = new ContentValues();
        values.put(COLUMN_MA_SACH, sach.getMaSach());
        values.put(COLUMN_MA_THE_LOAI, sach.getMaTheLoai());
        values.put(COLUMN_TEN_SACH, sach.getTenSach());
        values.put(COLUMN_NXB, sach.getNXB());
        values.put(COLUMN_GIA_BIA, sach.getGiaBia());
        values.put(COLUMN_SO_LUONG, sach.getSoLuong());

        //insert
        try {
            long result = database.insert(TABLE_NAME, null, values);
            return result;
        } catch (Exception exc) {
            Log.e(TAG, exc.toString());
            return -1;
        } finally {
            database.close();
        }
    }

    //getAll
    public List<Sach> getAllSach() {
        List<Sach> dsSach = new ArrayList<>();
        //xin quyen
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        //cau lenh select
        String selectQuery = "SELECT * FROM " + TABLE_NAME;
        //su dung cau lenh rawQuery
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Sach sach = new Sach();
                sach.setMaSach(cursor.getString(cursor.getColumnIndex(COLUMN_MA_SACH)));
                sach.setTenSach(cursor.getString(cursor.getColumnIndex(COLUMN_TEN_SACH)));
                sach.setTacGia(cursor.getString(cursor.getColumnIndex(COLUMN_TAC_GIA)));
                sach.setNXB(cursor.getString(cursor.getColumnIndex(COLUMN_NXB)));
                sach.setGiaBia(cursor.getDouble(cursor.getColumnIndex(COLUMN_GIA_BIA)));
                sach.setSoLuong(cursor.getInt(cursor.getColumnIndex(COLUMN_SO_LUONG)));

                dsSach.add(sach);
                Log.d(TAG, sach.toString());
            } while (cursor.moveToNext());
        }
        //dong ket noi cursor va db
        cursor.close();
        database.close();
        return dsSach;
    }

    public int updateSach(Sach sach) {
        //xin quyen
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        //ghep cap du lieu
        ContentValues values = new ContentValues();
        values.put(COLUMN_MA_SACH, sach.getMaSach());
        values.put(COLUMN_MA_THE_LOAI, sach.getMaTheLoai());
        values.put(COLUMN_TEN_SACH, sach.getTenSach());
        values.put(COLUMN_NXB, sach.getNXB());
        values.put(COLUMN_GIA_BIA, sach.getGiaBia());
        values.put(COLUMN_SO_LUONG, sach.getSoLuong());
        //update
        int result = database.update(
                TABLE_NAME,
                values,
                COLUMN_MA_SACH + "=?",
                new String[]{sach.getMaSach()}
        );
        //dong ket noi db
        database.close();
        return result;
    }

    //delete
    public int deleteSachByID(String maSach) {
        //xin quyen
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        //xoa
        int result = database.delete(
                TABLE_NAME,
                COLUMN_MA_SACH + "=?",
                new String[]{maSach}
        );
        //dong ket noi db
        database.close();
        return result;
    }
}
