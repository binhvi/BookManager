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

    public static final String TABLE_NAME = "HoaDonChiTiet";
    public static final String SQL_HOA_DON_CHI_TIET = "CREATE TABLE HoaDonChiTiet(maHDCT INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "maHoaDon text NOT NULL, maSach text NOT NULL, soLuong INTEGER);";
    public static final String TAG = "HoaDonChiTietLog";
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    //column
    public static final String COLUMN_MA_HOA_DON = "mahoadon";
    public static final String COLUMN_MA_SACH = "maSach";
    public static final String COLUMN_SO_LUONG = "soLuong";
    public static final String COLUMN_MA_HDCT = "maHDCT";

    HoaDonDAO hoaDonDAO;

    public HoaDonChiTietDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
        hoaDonDAO = new HoaDonDAO(context);
    }

    //insert
    public long insertHoaDonChiTiet(HoaDonChiTiet hdct) {
        long result;
        //xin quyen
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        //ghep cap du lieu
        ContentValues values = new ContentValues();
        values.put(COLUMN_MA_HOA_DON, hdct.getHoaDon().getMaHoaDon());
        values.put(COLUMN_MA_SACH, hdct.getSach().getMaSach());
        values.put(COLUMN_SO_LUONG, hdct.getSoLuongMua());
        //insert
        try {
            result = database.insert(TABLE_NAME, null, values);
            return result;
        } catch (Exception exc) {
            Log.e(TAG, exc.toString());
            return -1;
        } finally {
            database.close();
        }
    }

    //getAll
    public List<HoaDonChiTiet> getAllHoaDonChiTiet() throws ParseException {
        List<HoaDonChiTiet> dsHoaDonChiTiet = new ArrayList<>();
        //xin quyen
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        //cau lenh select
        String selectScript = "SELECT maHDCT, HoaDon.maHoaDon,HoaDon.ngayMua, " +
                "Sach.maSach, Sach.maTheLoai, Sach.tenSach, Sach.tacGia, Sach.NXB, Sach.giaBia, " +
                "Sach.soLuong, HoaDonChiTiet.soLuong FROM HoaDonChiTiet INNER JOIN HoaDon " +
                "on HoaDonChiTiet.maHoaDon = HoaDon.maHoaDon INNER JOIN Sach on Sach.maSach = HoaDonChiTiet.maSach";
        //su dung cau lenh rawQuery
        Cursor cursor = database.rawQuery(selectScript, null);

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
                HoaDonChiTiet hdct = new HoaDonChiTiet();
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

    //update
    public int updateHoaDonChiTiet(HoaDonChiTiet hdct) {
        //xin quyen
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        //ghep cap du lieu
        ContentValues values = new ContentValues();
        values.put(COLUMN_MA_HOA_DON, hdct.getHoaDon().getMaHoaDon());
        values.put(COLUMN_MA_SACH, hdct.getSach().getMaSach());
        values.put(COLUMN_SO_LUONG, hdct.getSoLuongMua());
        //update
        int result = database.update(
                TABLE_NAME,
                values,
                COLUMN_MA_HDCT + "=?",
                new String[]{String.valueOf(hdct.getMaHDCT())}
        );
        //dong ket noi db
        database.close();
        return result;
    }

    //delete
    public int deleteHoaDonChiTietByID(int maHDCT) {
        //xin quyen
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        //xoa
        int result = database.delete(
                TABLE_NAME,
                COLUMN_MA_HDCT + "=?",
                new String[]{String.valueOf(maHDCT)}
        );
        //dong ket noi db
        database.close();
        return result;
    }

    //truy van hoa don chi tiet theo ma hoa don
    public List<HoaDonChiTiet> getAllHoaDonChiTiet(int maHoaDon) throws ParseException {
        List<HoaDonChiTiet> dsHoaDonChiTiet = new ArrayList<>();
        //xin quyen
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        //cau lenh select
        String selectScript = "SELECT maHDCT, HoaDon.maHoaDon,HoaDon.ngayMua, " +
                "Sach.maSach, Sach.maTheLoai, Sach.tenSach, Sach.tacGia, Sach.NXB, Sach.giaBia, " +
                "Sach.soLuong, HoaDonChiTiet.soLuong FROM HoaDonChiTiet INNER JOIN HoaDon " +
                "on HoaDonChiTiet.maHoaDon = HoaDon.maHoaDon INNER JOIN Sach on Sach.maSach = HoaDonChiTiet.maSach " +
                "WHERE HoaDonChiTiet.maHoaDon=?";
        //su dung cau lenh rawQuery
        Cursor cursor = database.rawQuery(selectScript, new String[]{String.valueOf(maHoaDon)});

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
                HoaDonChiTiet hdct = new HoaDonChiTiet();
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
            } while (cursor.moveToNext());
        }
        cursor.close();
        database.close();
        return dsHoaDonChiTiet;
    }

    //khong duoc xoa ham nay
    //delete all records
    public int deleteAllHoaDonChiTiet() {
        //xin quyen
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        //xoa
        int result = database.delete(TABLE_NAME, null, null);
        //dong ket noi db
        return result;
    }

    //tinh tong tien cua mot hoa don
    public double getTotalOfCart(int maHoaDon) {
        double totalOfCart = 0.0;
        //xin quyen
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        //cau lenh select
        String selectQuery = "SELECT SUM(HoaDonChiTiet.soLuong*giaBia)" +
                " FROM HoaDonChiTiet INNER JOIN Sach ON HoaDonChiTiet.maSach=Sach.maSach " +
                "WHERE maHoaDon=?";
        //su dung cau lenh rawQuery
        Cursor cursor = database.rawQuery(selectQuery, new String[]{String.valueOf(maHoaDon)});
        if (cursor.moveToFirst()) {
            totalOfCart = cursor.getDouble(0);
        }
        //dong ket noi cursor va db
        cursor.close();
        database.close();
        return totalOfCart;
    }


    /**
     * Truy vấn số tiền bán sách trong ngày.
     * CÂU LỆNH TRUY VẤN SỐ TIỀN BÁN ĐƯỢC TRONG NGÀY:
     * Đầu tiên là truy vấn ra số tiền bán được trong mỗi hóa đơn chi tiết
     * ('tongtien') = giá bìa*số lượng bán được trong 1 hdct
     * -> ra một cột 'tongtien' chứa số tiền bán được trong mỗi hóa đơn chi tiết
     * FROM bảng HoaDon
     * inner join với bảng HoaDonChiTiet với mã hóa đơn ở hai bảng khớp với nhau
     * inner join với bảng Sach với mã sách của bảng HoaDonChiTiet khớp với mã sách ở trong bảng Sach
     * WHERE ngày mua là ngày hiện tại
     * Sau đó cộng tất cả các hàng của cột đó lại (SUM(tongtien)) là ra tổng số
     * tiền bán được trong các hóa đơn chi tiết
     *
     * @return số tiền bán sách trong ngày
     */
    public double getDoanhThuTrongNgay() {
        double doanhThu = -1; //neu truy van bi loi thi tra ve -1
        //xin quyen
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        //cau lenh select
        String selectQuery =
                "SELECT SUM(SO_TIEN_CUA_MOT_HDCT) " +
                        "FROM ( " +
                        "     SELECT(HoaDonChiTiet.soLuong * Sach.giaBia) AS SO_TIEN_CUA_MOT_HDCT " +
                        "     FROM HoaDonChiTiet INNER JOIN Sach ON HoaDonChiTiet.maSach = Sach.maSach " +
                        "                        INNER JOIN HoaDon ON HoaDonChiTiet.maHoaDon = HoaDon.mahoadon " +
                        "     WHERE ngaymua = date('now') " +
                        ")";
        //su dung cau lenh rawQuery
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            doanhThu = cursor.getDouble(0);
        }
        //dong ket noi cursor va db
        cursor.close();
        database.close();
        return doanhThu;
    }

    /**
     * Truy vấn số tiền bán sách trong tháng
     * CÂU LỆNH TRUY VẤN SỐ TIỀN BÁN ĐƯỢC TRONG THÁNG:
     * Đầu tiên là truy vấn ra số tiền bán được trong mỗi hóa đơn chi tiết
     * ('tongtien') = giá bìa*số lượng bán được trong 1 hdct
     * -> ra một cột 'tongtien' chứa số tiền bán được trong mỗi hóa đơn chi tiết
     * FROM bảng HoaDon
     * inner join với bảng HoaDonChiTiet với mã hóa đơn ở hai bảng khớp với nhau
     * inner join với bảng Sach với mã sách của bảng HoaDonChiTiet khớp với mã sách ở trong bảng Sach
     * WHERE tháng lấy ra trong cột ngayMua của bảng hóa đơn = tháng của thời điểm hiện tại
     * và năm của hóa đơn bằng năm hiện tại (tránh cộng cùng một tháng của tất cả các năm)
     *
     * @return số tiền bán sách trong tháng
     */
    public double getDoanhThuTrongThang() {
        double doanhThu = -1; //neu truy van bi loi thi tra ve -1
        //xin quyen
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        //cau lenh select
        String selectQuery =
                "SELECT SUM(SO_TIEN_CUA_MOT_HDCT) " +
                        "FROM ( " +
                        "     SELECT(HoaDonChiTiet.soLuong * Sach.giaBia) AS SO_TIEN_CUA_MOT_HDCT " +
                        "     FROM HoaDonChiTiet INNER JOIN Sach ON HoaDonChiTiet.maSach = Sach.maSach " +
                        "                        INNER JOIN HoaDon ON HoaDonChiTiet.maHoaDon = HoaDon.mahoadon " +
                        "     WHERE strftime('%m', ngaymua) = strftime('%m', 'now') " +
                        "           AND strftime('%Y', ngaymua) = strftime('%Y', 'now') " +
                        ")";
        //su dung cau lenh rawQuery
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            doanhThu = cursor.getDouble(0);
        }
        //dong ket noi cursor va db
        cursor.close();
        database.close();
        return doanhThu;
    }

    /**
     * Truy vấn số tiền bán sách trong năm
     * CÂU LỆNH TRUY VẤN SỐ TIỀN BÁN ĐƯỢC TRONG NĂM:
     * Đầu tiên là truy vấn ra số tiền bán được trong mỗi hóa đơn chi tiết
     * ('tongtien') = giá bìa*số lượng bán được trong 1 hdct
     * -> ra một cột 'tongtien' chứa số tiền bán được trong mỗi hóa đơn chi tiết
     * FROM bảng HoaDon
     * inner join với bảng HoaDonChiTiet với mã hóa đơn ở hai bảng khớp với nhau
     * inner join với bảng Sach với mã sách của bảng HoaDonChiTiet khớp với mã sách ở trong bảng Sach
     * WHERE năm của ngày mua = năm của thời điểm hiện tại
     * (năm phải là %Y (y hoa) mới được)
     *
     * @return số tiền bán sách trong năm
     */
    public double getDoanhThuTrongNam() {
        double doanhThu = -1; //neu truy van bi loi thi tra ve -1
        //xin quyen
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        //cau lenh select
        String selectQuery =
                "SELECT SUM(SO_TIEN_CUA_MOT_HDCT) " +
                        "FROM ( " +
                        "     SELECT(HoaDonChiTiet.soLuong * Sach.giaBia) AS SO_TIEN_CUA_MOT_HDCT " +
                        "     FROM HoaDonChiTiet INNER JOIN Sach ON HoaDonChiTiet.maSach = Sach.maSach " +
                        "                        INNER JOIN HoaDon ON HoaDonChiTiet.maHoaDon = HoaDon.mahoadon " +
                        "     WHERE strftime('%Y', ngaymua) = strftime('%Y', 'now') " +
                        ")";
        //su dung cau lenh rawQuery
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            doanhThu = cursor.getDouble(0);
        }
        //dong ket noi cursor va db
        cursor.close();
        database.close();
        return doanhThu;
    }

    /**
     * Xóa tất cả hóa đơn chi tiết có mã sách truyền vào.
     * @param bookId Mã sách: xóa những hóa đơn chi tiết có mã sách này
     * @return Số lượng bản ghi hóa đơn chi tiết bị xóa
     */
    public int deleteAllBillDetailHaveThisBookId(String bookId) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        //cau lenh delete
        String deleteScript = "DELETE FROM "+TABLE_NAME+
                " WHERE "+COLUMN_MA_SACH+" = ?";
        //delete
        int result = database.delete(
                TABLE_NAME,
                COLUMN_MA_SACH+" = ?",
                new String[] {bookId});
        //dong ket noi db
        database.close();

        /*
        Xóa những hóa đơn không có hóa đơn chi tiết (xóa hóa đơn không có hàng nào).
        Vì khi xóa sách --> xóa sang hóa đơn chi tiết có mã sách đó, khi mà tất cả hóa đơn chi tiết
         trong một hóa đơn bị xóa thì khi tạo list, hóa đơn sẽ truy vấn ra hóa đơn chi tiết,
         nếu không có sẽ bị lỗi, nên khi hóa đơn không còn hàng hóa nào nữa thì xóa luôn.
         */
        hoaDonDAO.deleteHoaDonKhongCoHdct();
        return result;
    }
}
