package vn.poly.mob204.bookmanager_binhvttph07052.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import vn.poly.mob204.bookmanager_binhvttph07052.database.DatabaseHelper;
import vn.poly.mob204.bookmanager_binhvttph07052.model.HoaDon;

public class HoaDonDAO {
    private DatabaseHelper dbHelper;

    public static final String TABLE_NAME = "HoaDon";

    //da sua lai mahoadon thanh integer
    public static final String SQL_HOA_DON = "CREATE TABLE HoaDon (mahoadon integer primary key, ngaymua date);";
    public static final String TAG = "HoaDonDAO";

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    //column
    public static final String COLUMN_MA_HOA_DON = "mahoadon";
    public static final String COLUMN_NGAY_MUA = "ngaymua";

    public HoaDonDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    //insert
    public long insertHoaDon(HoaDon hoaDon) {
        //xin quyen
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        //ghep cap du lieu
        ContentValues values = new ContentValues();
        //khong put ma hoa don, de tu sinh
        values.put(COLUMN_NGAY_MUA, sdf.format(hoaDon.getNgayMua()));
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

    //khong xu ly exception ma throw de bao loi tren he thong thoi
    public List<HoaDon> getAllHoaDon() throws ParseException {
        List<HoaDon> dsHoaDon = new ArrayList<>();
        //xin quyen
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        //cau lenh select
        String selectQuery = "SELECT * FROM " + TABLE_NAME;
        //su dung cau lenh rawQuery
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                HoaDon hoaDon = new HoaDon();
                hoaDon.setMaHoaDon(cursor.getInt(cursor.getColumnIndex(COLUMN_MA_HOA_DON)));
                hoaDon.setNgayMua(sdf.parse(cursor.getString(cursor.getColumnIndex(COLUMN_NGAY_MUA))));

                dsHoaDon.add(hoaDon);
                Log.d(TAG, hoaDon.toString());
            } while (cursor.moveToNext());
        }
        //dong ket noi cursor, db
        cursor.close();
        database.close();
        return dsHoaDon;
    }

    //update
    public int updateHoaDon(HoaDon hd) {
        //xin quyen
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        //ghep cap du lieu
        ContentValues values = new ContentValues();
        //khong put ma hoa don, de tu sinh
        values.put(COLUMN_NGAY_MUA, sdf.format(hd.getNgayMua()));
        //update
        int result = database.update(
                TABLE_NAME,
                values,
                COLUMN_MA_HOA_DON + "=?",
                new String[]{String.valueOf(hd.getMaHoaDon())}
        );
        //dong ket noi db
        database.close();
        return result;
    }

    //delete
    public int deleteHoaDonByID(int maHoaDon) {
        //xin quyen
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        //xoa
        int result = database.delete(
                TABLE_NAME,
                COLUMN_MA_HOA_DON + "=?",
                new String[]{String.valueOf(maHoaDon)}
        );
        //dong ket noi db
        database.close();
        return result;
    }

    public HoaDon getHoaDonFromRowId(long rowId) {
        HoaDon hoaDon = new HoaDon();
        //xin quyen
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        //cau lenh select
        String selectQuery = "SELECT * FROM " + TABLE_NAME + " WHERE rowid=?";
        //su dung cau lenh rawQuery
        Cursor cursor = database.rawQuery(selectQuery, new String[]{String.valueOf(rowId)});
        if (cursor.moveToFirst()) {
            hoaDon.setMaHoaDon(cursor.getInt(cursor.getColumnIndex(COLUMN_MA_HOA_DON)));
            try {
                hoaDon.setNgayMua(sdf.parse(cursor.getString(cursor.getColumnIndex(COLUMN_NGAY_MUA))));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        //dong ket noi cursor, db
        cursor.close();
        database.close();
        return hoaDon;
    }

    //khong duoc xoa
    //delete all records
    public int deleteAllHoaDon() {
        //xin quyen
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        //xoa
        int result = database.delete(TABLE_NAME, null, null);
        //dong ket noi db
        return result;
    }

    /**
     * Xóa những hóa đơn không có hóa đơn chi tiết (xóa hóa đơn không có hàng nào)
     * Vì khi xóa sách --> xóa sang hóa đơn chi tiết có mã sách đó, khi mà tất cả
     * hóa đơn chi tiết trong một hóa đơn bị xóa thì khi tạo list,
     * hóa đơn sẽ truy vấn ra hóa đơn chi tiết, nếu không có sẽ bị lỗi,
     * nên khi hóa đơn không còn hàng hóa nào nữa thì xóa luôn
     * @return Số hóa đơn bị xóa
     */
    public int deleteHoaDonKhongCoHdct() {
        //xin quyen
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        //cau lenh delete
        //DELETE FROM HoaDon
        //WHERE mahoadon NOT IN
        //      (SELECT DISTINCT maHoaDon
        //      FROM HoaDonChiTiet)
        int result = database.delete(
                TABLE_NAME,
                "mahoadon NOT IN" +
                        " (SELECT DISTINCT maHoaDon" +
                        " FROM HoaDonChiTiet)", null);
        database.close();
        return result;
    }
}
