package vn.poly.mob204.bookmanager_binhvttph07052.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.loader.content.CursorLoader;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import vn.poly.mob204.bookmanager_binhvttph07052.database.DatabaseHelper;
import vn.poly.mob204.bookmanager_binhvttph07052.model.HoaDon;
import vn.poly.mob204.bookmanager_binhvttph07052.model.HoaDonChiTiet;
import vn.poly.mob204.bookmanager_binhvttph07052.model.Sach;

import static vn.poly.mob204.bookmanager_binhvttph07052.dao.HoaDonDAO.COLUMN_NGAY_MUA;
import static vn.poly.mob204.bookmanager_binhvttph07052.dao.SachDAO.COLUMN_MA_THE_LOAI;
import static vn.poly.mob204.bookmanager_binhvttph07052.dao.SachDAO.COLUMN_TEN_SACH;
import static vn.poly.mob204.bookmanager_binhvttph07052.dao.SachDAO.COLUMN_TAC_GIA;
import static vn.poly.mob204.bookmanager_binhvttph07052.dao.SachDAO.COLUMN_NXB;
import static vn.poly.mob204.bookmanager_binhvttph07052.dao.SachDAO.COLUMN_GIA_BIA;

public class HoaDonChiTietDAO {
    private DatabaseHelper dbHelper;

    public static final String TABLE_NAME="HoaDonChiTiet";
    public static final String SQL_HOA_DON_CHI_TIET="CREATE TABLE HoaDonChiTiet(maHDCT INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "maHoaDon text NOT NULL, maSach text NOT NULL, soLuong INTEGER);";
    public static final String TAG="HoaDonChiTiet";
    SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");

    //column
    public static final String COLUMN_MA_HOA_DON="mahoadon";
    public static final String COLUMN_MA_SACH="maSach";
    public static final String COLUMN_SO_LUONG="soLuong";
    public static final String COLUMN_MA_HDCT="maHDCT";


    public HoaDonChiTietDAO(Context context) {
        dbHelper=new DatabaseHelper(context);
    }

    //insert
    public long insertHoaDonChiTiet(HoaDonChiTiet hdct) {
        long result;
        //xin quyen
        SQLiteDatabase database=dbHelper.getWritableDatabase();
        //ghep cap du lieu
        ContentValues values=new ContentValues();
        values.put(COLUMN_MA_HOA_DON, hdct.getHoaDon().getMaHoaDon());
        values.put(COLUMN_MA_SACH, hdct.getSach().getMaSach());
        values.put(COLUMN_SO_LUONG, hdct.getSoLuongMua());
        //insert
        try {
            result=database.insert(TABLE_NAME, null, values);
            return result;
        } catch (Exception exc) {
            Log.e(TAG, exc.toString());
            return -1;
        } finally {
            database.close();
        }
    }

    //getAll
    public List<HoaDonChiTiet> getAllHoaDon() throws ParseException {
        List<HoaDonChiTiet> dsHoaDonChiTiet=new ArrayList<>();
        //xin quyen
        SQLiteDatabase database=dbHelper.getReadableDatabase();
        //cau lenh select
        String selectScript="SELECT maHDCT, HoaDon.maHoaDon,HoaDon.ngayMua, " +
                "Sach.maSach, Sach.maTheLoai, Sach.tenSach, Sach.tacGia, Sach.NXB, Sach.giaBia, " +
        "Sach.soLuong, HoaDonChiTiet.soLuong FROM HoaDonChiTiet INNER JOIN HoaDon " +
        "on HoaDonChiTiet.maHoaDon = HoaDon.maHoaDon INNER JOIN Sach on Sach.maSach = HoaDonChiTiet.maSach";
        //su dung cau lenh rawQuery
        Cursor cursor=database.rawQuery(selectScript, null);

//        0 maHDCT,
//        1 HoaDon.maHoaDon,
//        2 HoaDon.ngayMua,
//        3 Sach.maSach,
//        4 Sach.maTheLoai,
//        5 Sach.tenSach,
//        6 Sach.tacGia,
//        7 Sach.NXB,
//        8 Sach.giaBia,
//        9 Sach.soLuong,
//        10 HoaDonChiTiet.soLuong

        if (cursor.moveToFirst()) {
            do {
                HoaDonChiTiet hdct=new HoaDonChiTiet();
                hdct.setMaHDCT(cursor.getInt(0));
                hdct.setHoaDon(new HoaDon(cursor.getInt(1), sdf.parse(cursor.getString(2))));
                hdct.setSach(new
                        Sach(cursor.getString(3),
                            cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6),
                        cursor.getString(7),
                        cursor.getDouble(8),
                        cursor.getInt(9)));
                hdct.setSoLuongMua(cursor.getInt(10));

                dsHoaDonChiTiet.add(hdct);
                Log.d(TAG, hdct.toString());
            } while (cursor.moveToNext());
        }
        cursor.close();
        database.close();
        return dsHoaDonChiTiet;
    }

    public List<HoaDonChiTiet> getAllHoaDonChiTietByID(int maHoaDon) throws ParseException {
        List<HoaDonChiTiet> dsHoaDonChiTiet=new ArrayList<>();
        //xin quyen
        SQLiteDatabase database=dbHelper.getReadableDatabase();
        //cau lenh select
        String selectQuery= "SELECT maHDCT, HoaDon.maHoaDon,HoaDon.ngayMua, Sach.maSach, Sach.maTheLoai, Sach.tenSach, Sach.tacGia, Sach.NXB, Sach.giaBia, " +
        "Sach.soLuong,HoaDonChiTiet.soLuong FROM HoaDonChiTiet INNER JOIN HoaDon " +
        "on HoaDonChiTiet.maHoaDon = HoaDon.maHoaDon INNER JOIN Sach on Sach.maSach = HoaDonChiTiet.maSach where HoaDonChiTiet.maHoaDon="+maHoaDon;
        //su dung cau lenh rawQuery
        Cursor cursor=database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                HoaDonChiTiet hdct=new HoaDonChiTiet();
                hdct.setMaHDCT(cursor.getInt(0));
                hdct.setHoaDon(new HoaDon(cursor.getInt(1), sdf.parse(cursor.getString(2))));
                hdct.setSach(new
                        Sach(cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6),
                        cursor.getString(7),
                        cursor.getDouble(8),
                        cursor.getInt(9)));
                hdct.setSoLuongMua(cursor.getInt(10));

                dsHoaDonChiTiet.add(hdct);
                Log.d(TAG, hdct.toString());
            } while (cursor.moveToNext());
        }
        //dong ket noi cursor va database
        cursor.close();
        database.close();
        return dsHoaDonChiTiet;
    }

    //update
    public int updateHoaDonChiTiet(HoaDonChiTiet hdct) {
        //xin quyen
        SQLiteDatabase database=dbHelper.getWritableDatabase();
        //ghep cap du lieu
        ContentValues values=new ContentValues();
        values.put(COLUMN_MA_HOA_DON, hdct.getHoaDon().getMaHoaDon());
        values.put(COLUMN_MA_SACH, hdct.getSach().getMaSach());
        values.put(COLUMN_SO_LUONG, hdct.getSoLuongMua());
        //update
        int result=database.update(
                TABLE_NAME,
                values,
                COLUMN_MA_HDCT+"=?",
                new String[] {String.valueOf(hdct.getMaHDCT())}
        );
        //dong ket noi db
        database.close();
        return result;
    }

    //delete
    public int deleteHoaDonChiTietByID(int maHDCT) {
        //xin quyen
        SQLiteDatabase database=dbHelper.getWritableDatabase();
        //xoa
        int result=database.delete(
                TABLE_NAME,
                COLUMN_MA_HDCT+"=?",
                new String[] {String.valueOf(maHDCT)}
        );
        //dong ket noi db
        database.close();
        return result;
    }
}
