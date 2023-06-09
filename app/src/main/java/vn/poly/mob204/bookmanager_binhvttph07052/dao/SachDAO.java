package vn.poly.mob204.bookmanager_binhvttph07052.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import vn.poly.mob204.bookmanager_binhvttph07052.database.DatabaseHelper;
import vn.poly.mob204.bookmanager_binhvttph07052.model.HoaDonChiTiet;
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

    //Để xóa hóa đơn chi tiết liên quan khi xóa sách
    HoaDonChiTietDAO hoaDonChiTietDAO;

    public SachDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
        hoaDonChiTietDAO = new HoaDonChiTietDAO(context);
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
        values.put(COLUMN_TAC_GIA, sach.getTacGia());
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
                sach.setMaTheLoai(cursor.getString(cursor.getColumnIndex(COLUMN_MA_THE_LOAI)));

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
        values.put(COLUMN_MA_THE_LOAI, sach.getMaTheLoai());
        values.put(COLUMN_TEN_SACH, sach.getTenSach());
        values.put(COLUMN_NXB, sach.getNXB());
        values.put(COLUMN_GIA_BIA, sach.getGiaBia());
        values.put(COLUMN_SO_LUONG, sach.getSoLuong());
        values.put(COLUMN_TAC_GIA, sach.getTacGia());
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
        //Xóa tất cả các hóa đơn chi tiết có mã sách này
        //Vì khi xóa sách mà không xóa hóa đơn chi tiết, bên hóa đơn list vẫn sẽ truy vấn những
        // hóa đơn chi tiết có sách này để lấy giá tiền, không tìm thấy sách sẽ bị lỗi
        hoaDonChiTietDAO.deleteAllBillDetailHaveThisBookId(maSach);

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

    /**
     * Kiểm tra xem trong db đã tồn tại mã sach cần kiểm tra chưa.
     * Truy vấn các bản ghi có mã sach là mã truyền vào,
     * nếu số bản ghi > 0 thì mã sach đó co tồn tại.
     *
     * @param bookId
     * @return true if ma sach exists in db, else return false
     */
    public boolean isBookIdExists(String bookId) {
        boolean isBookIdExists = false;
        //xin quyen
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        //select
        String selectQuery = "SELECT COUNT(" + COLUMN_MA_SACH + ") FROM " + TABLE_NAME +
                " WHERE " + COLUMN_MA_SACH + "=?";
        //rawQuery
        Cursor cursor = database.rawQuery(selectQuery, new String[]{bookId});
        if (cursor.moveToFirst()) {
            //lay so truy van duoc
            int numberOfRecords = cursor.getInt(0);
            if (numberOfRecords > 0) {
                isBookIdExists = true;
            } else {
                isBookIdExists = false;
            }
        }
        cursor.close();
        database.close();
        return isBookIdExists;
    }

    //lay so luong sach luu tru
    public int getNumberOfArchivedBook(String bookId) {
        int numberOfArchiveBook = 0;
        //xin quyen
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        //select query
        String selectQuery = "SELECT " + COLUMN_SO_LUONG + " FROM " + TABLE_NAME +
                " WHERE " + COLUMN_MA_SACH + "=?";
        //rawQuery
        Cursor cursor = database.rawQuery(selectQuery, new String[]{bookId});
        if (cursor.moveToFirst()) {
            numberOfArchiveBook = cursor.getInt(0);
        }
        //dong ket noi cursor, db
        cursor.close();
        database.close();
        return numberOfArchiveBook;
    }

    //lay sach theo id
    public Sach getSachById(String bookId) {
        Sach sach = new Sach();
        //xin quyen
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        //cau lenh select
        String selectQuery = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_MA_SACH + "=?";
        //su dung cau lenh rawQuery
        Cursor cursor = database.rawQuery(selectQuery, new String[]{bookId});
        if (cursor.moveToFirst()) {
            sach.setMaSach(cursor.getString(cursor.getColumnIndex(COLUMN_MA_SACH)));
            sach.setTenSach(cursor.getString(cursor.getColumnIndex(COLUMN_TEN_SACH)));
            sach.setTacGia(cursor.getString(cursor.getColumnIndex(COLUMN_TAC_GIA)));
            sach.setNXB(cursor.getString(cursor.getColumnIndex(COLUMN_NXB)));
            sach.setGiaBia(cursor.getDouble(cursor.getColumnIndex(COLUMN_GIA_BIA)));
            sach.setSoLuong(cursor.getInt(cursor.getColumnIndex(COLUMN_SO_LUONG)));
            sach.setMaTheLoai(cursor.getString(cursor.getColumnIndex(COLUMN_MA_THE_LOAI)));
        }
        //dong ket noi cursor va db
        cursor.close();
        database.close();
        return sach;
    }


    /**
     * Select 10 sach ban chay cua thang nay (nam nay).
     * Tạo một list.
     * Câu lệnh select: select mã sách, tên sách, tổng tất cả số lượng bán được của một mã sách trong tháng này của năm này
     * Sắp xếp giảm dần
     * Lấy 10 bản ghi đầu tiên
     *
     * @return list 10 sach ban chay cua thang nay (nam nay)
     */
    public List<Sach> getSachTop10() {
        //xin quyen
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        List<Sach> dsSach = new ArrayList<>();
        //cau lenh select
        String selectQuery =
                "SELECT HoaDonChiTiet.maSach, tensach, SUM(HoaDonChiTiet.soLuong) AS TONG_SO_LUONG_SACH_BAN_DUOC_TRONG_THANG_X " +
                        "FROM HoaDonChiTiet INNER JOIN Sach ON HoaDonChiTiet.maSach = Sach.maSach " +
                        "                   INNER JOIN HoaDon ON HoaDonChiTiet.maHoaDon = HoaDon.mahoadon " +
                        "WHERE strftime('%m', HoaDon.ngaymua) = (SELECT strftime('%m','now')) " +
                        "GROUP BY HoaDonChiTiet.maSach " +
                        "ORDER BY SUM(HoaDonChiTiet.soLuong) DESC " +
                        "LIMIT 10;";
        //su dung cau lenh rawQuery
        Cursor cursor = database.rawQuery(selectQuery, null);
        //0: ma sach
        //1: ten sach
        //2: so luong ban duoc trong thang x
        if (cursor.moveToFirst()) {
            do {
                Sach sach = new Sach();
                sach.setMaSach(cursor.getString(cursor.getColumnIndex(COLUMN_MA_SACH)));
                sach.setTenSach(cursor.getString(cursor.getColumnIndex(COLUMN_TEN_SACH)));
                sach.setSoLuong(cursor.getInt(cursor.getColumnIndex("TONG_SO_LUONG_SACH_BAN_DUOC_TRONG_THANG_X")));

                dsSach.add(sach);
            } while (cursor.moveToNext());
        }
        //dong ket noi cursor, db
        cursor.close();
        database.close();
        return dsSach;
    }


}
