package vn.poly.mob204.bookmanager_binhvttph07052.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import vn.poly.mob204.bookmanager_binhvttph07052.database.DatabaseHelper;
import vn.poly.mob204.bookmanager_binhvttph07052.model.TheLoai;

public class TheLoaiDAO {
    private DatabaseHelper dbHelper;

    public static final String TABLE_NAME = "TheLoai";
    public static final String SQL_THE_LOAI = "CREATE TABLE TheLoai (matheloai text primary key, tentheloai text, mota text, vitri int);";
    public static final String TAG = "TheLoaiDAO";

    //column
    public static final String COLUMN_MA_THE_LOAI = "matheloai";
    public static final String COLUMN_TEN_THE_LOAI = "tentheloai";
    public static final String COLUMN_MO_TA = "mota";
    public static final String COLUMN_VI_TRI = "vitri";

    public TheLoaiDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    //insert
    public long insertTheLoai(TheLoai theLoai) {
        //xin quyen
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        //ghep cap du lieu
        ContentValues values = new ContentValues();
        values.put(COLUMN_MA_THE_LOAI, theLoai.getMaTheLoai());
        values.put(COLUMN_TEN_THE_LOAI, theLoai.getTenTheLoai());
        values.put(COLUMN_MO_TA, theLoai.getMoTa());
        values.put(COLUMN_VI_TRI, theLoai.getViTri());
        //insert
        try {
            long result = database.insert(TABLE_NAME, null, values);
            return result;
        } catch (Exception exc) {
            Log.e(TAG, exc.toString());
            return -1;
        } finally {
            //dong ket noi db
            database.close();
        }
    }

    //getAllTheLoai
    public List<TheLoai> getAllTheLoai() {
        List<TheLoai> dsTheLoai = new ArrayList<>();
        //xin quyen
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        //cau lenh select
        String selectQuery = "SELECT * FROM " + TABLE_NAME;
        //su dung cau lenh raw query
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                TheLoai theLoai = new TheLoai();
                theLoai.setMaTheLoai(cursor.getString(cursor.getColumnIndex(COLUMN_MA_THE_LOAI)));
                theLoai.setTenTheLoai(cursor.getString(cursor.getColumnIndex(COLUMN_TEN_THE_LOAI)));
                theLoai.setMoTa(cursor.getString(cursor.getColumnIndex(COLUMN_MO_TA)));
                theLoai.setViTri(cursor.getInt(cursor.getColumnIndex(COLUMN_VI_TRI)));

                dsTheLoai.add(theLoai);
                Log.d(TAG, theLoai.toString());
            } while (cursor.moveToNext());
        }
        //dong ket noi cursor va db
        cursor.close();
        database.close();
        return dsTheLoai;
    }

    //update
    public int updateTheLoai(TheLoai theLoai) {
        //xin quyen
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        //ghep cap du lieu
        ContentValues values = new ContentValues();
        values.put(COLUMN_MA_THE_LOAI, theLoai.getMaTheLoai());
        values.put(COLUMN_TEN_THE_LOAI, theLoai.getTenTheLoai());
        values.put(COLUMN_MO_TA, theLoai.getMoTa());
        values.put(COLUMN_VI_TRI, theLoai.getViTri());
        //update
        int result = database.update(TABLE_NAME, values,
                COLUMN_MA_THE_LOAI + "=?",
                new String[]{theLoai.getMaTheLoai()});
        //dong ket noi db
        database.close();
        return result;
    }

    //delete
    public int deleteTheLoaiByID(String maTheLoai) {
        //xin quyen
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        //xoa
        int result = database.delete(
                TABLE_NAME,
                COLUMN_MA_THE_LOAI + "=?",
                new String[]{maTheLoai});
        //dong ket noi db
        database.close();
        return result;
    }

    /**
     * check if the loai id exists
     *
     * @param strPrimaryKey ma the loai
     * @return true if ma the loai exists in db, else return false
     */
    public boolean checkPrimaryKey(String strPrimaryKey) {
        //xin quyen
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        //SELECT
        String[] columns = {COLUMN_MA_THE_LOAI};
        //WHERE clause argumants
        String selection = COLUMN_MA_THE_LOAI + "=?";
        //WHERE clause arguments
        String[] selectionArgs = {strPrimaryKey};
        Cursor cursor = database.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);
        cursor.moveToFirst();
        int numberOfRecords = cursor.getCount();
        cursor.close();
        database.close();
        if (numberOfRecords > 0) {
            return true;
        }
        return false;
    }
}
