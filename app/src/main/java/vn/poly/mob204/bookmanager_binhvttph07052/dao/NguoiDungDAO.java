package vn.poly.mob204.bookmanager_binhvttph07052.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import vn.poly.mob204.bookmanager_binhvttph07052.database.DatabaseHelper;
import vn.poly.mob204.bookmanager_binhvttph07052.model.NguoiDung;

import static vn.poly.mob204.bookmanager_binhvttph07052.ValidateFunctionLibrary.SQLITE_CONSTRANT_EXCEPTION_ID;

public class NguoiDungDAO {
    private DatabaseHelper dbHelper;

    public static final String TABLE_NAME="NguoiDung";
    public static final String SQL_NGUOI_DUNG="CREATE TABLE NguoiDung (username text primary key, password text, phone text, hoten text);";
    public static final String TAG="NguoiDungDAO";

    //column
    public static final String COLUMN_NGUOI_DUNG_USERNAME="username";
    public static final String COLUMN_NGUOI_DUNG_PASSWORD="password";
    public static final String COLUMN_NGUOI_DUNG_PHONE="phone";
    public static final String COLUMN_NGUOI_DUNG_HO_TEN="hoten";

    //error id

    public NguoiDungDAO(Context context) {
        dbHelper=new DatabaseHelper(context);
    }

    public long insertNguoiDung(NguoiDung nd) {
        //xin quyen
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        //content values
        ContentValues values=new ContentValues();
        values.put(COLUMN_NGUOI_DUNG_USERNAME, nd.getUserName());
        values.put(COLUMN_NGUOI_DUNG_PASSWORD, nd.getPassword());
        values.put(COLUMN_NGUOI_DUNG_PHONE, nd.getPhone());
        values.put(COLUMN_NGUOI_DUNG_HO_TEN, nd.getHoTen());
        //cau lenh insert
        try {
            long result=db.insert(TABLE_NAME, null, values);
            return result;
        } catch (Exception exc) {
            Log.e(TAG, exc.toString());
            return -1;
        } finally {
            db.close();
        }
    }

    //get All
    public List<NguoiDung> getAllNguoiDung() {
        List<NguoiDung> dsNguoiDung=new ArrayList<>();
        //xin quyen
        SQLiteDatabase database=dbHelper.getReadableDatabase();
        //cau lenh select
        String selectQuery="SELECT * FROM "+TABLE_NAME;
        //su dung cau lenh rawQuery
        Cursor cursor=database.rawQuery(selectQuery, null);
        //lay het thong tin ra cho vao doi tuong, them vao dsNguoiDung, in doi tuong ra
        if (cursor.moveToFirst()) {
            do {
                NguoiDung nguoiDung=new NguoiDung();
                nguoiDung.setUserName(cursor.getString(cursor.getColumnIndex(COLUMN_NGUOI_DUNG_USERNAME)));
                nguoiDung.setPassword(cursor.getString(cursor.getColumnIndex(COLUMN_NGUOI_DUNG_PASSWORD)));
                nguoiDung.setPhone(cursor.getString(cursor.getColumnIndex(COLUMN_NGUOI_DUNG_PHONE)));
                nguoiDung.setHoTen(cursor.getString(cursor.getColumnIndex(COLUMN_NGUOI_DUNG_HO_TEN)));

                dsNguoiDung.add(nguoiDung);

                //in thong tin nguoi dung ra log
                Log.d(TAG, nguoiDung.toString());
            } while (cursor.moveToNext());
        }
        //Dong ket noi db, cursor
        cursor.close();
        database.close();
        return dsNguoiDung;
    }

    //update dua vao username
    public int updateNguoiDung(NguoiDung nd) {
        int result;
        //xin quyen
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        //content values
        ContentValues values=new ContentValues();
        values.put(COLUMN_NGUOI_DUNG_USERNAME, nd.getUserName());
        values.put(COLUMN_NGUOI_DUNG_PASSWORD, nd.getPassword());
        values.put(COLUMN_NGUOI_DUNG_PHONE, nd.getPhone());
        values.put(COLUMN_NGUOI_DUNG_HO_TEN, nd.getHoTen());
        //cau lenh update
        result=db.update(
                    TABLE_NAME,
                    values,
                    COLUMN_NGUOI_DUNG_USERNAME+"=?",
                    new String[] {nd.getUserName()}
                    );
         //dong ket noi db
        db.close();
        return result;
    }

    /**
     * cái này là kiểu như là chuyển hẳn đổi pass sang một chỗ mới ý.
     * Khi ấn vào một người dùng sang sửa thì chỉ sửa được những tên
     * cơ bản như họ tên, số điện thoại thôi. Username truyền vào là
     * để dựa vào đó để update (kiểu như id) chứ không phải để đổi,
     * vì content values có mỗi put phone với hoten thôi.
     */
    public int updateInfoNguoiDung(String username, String phone, String name) {
        //b1: xin quyen ghi
        SQLiteDatabase database=dbHelper.getWritableDatabase();
        //ghep cap du lieu
        ContentValues values=new ContentValues();
        values.put(COLUMN_NGUOI_DUNG_PHONE, phone);
        values.put(COLUMN_NGUOI_DUNG_HO_TEN, name);
        //cau lenh update
        int result=database.update(
                TABLE_NAME,
                values,
                COLUMN_NGUOI_DUNG_USERNAME+"=?",
                new String[] {username}
        );
        //dong ket noi db
        database.close();
        return result;
    }

    //delete
    public int deleteNguoiDungByID(String username) {
        int result;
        //xin quyen
        SQLiteDatabase database=dbHelper.getWritableDatabase();
        //xoa
        result=database.delete(
                TABLE_NAME,
                COLUMN_NGUOI_DUNG_USERNAME+"=?",
                new String[] {username}
        );
        //dong ket noi db
        database.close();
        return result;
    }

    /**
     * kiểm tra xem trong csdl có cặp tên và pass đó trong DB không
     * nếu có, return true
     * nếu không, return false
     * @param username
     * @param password
     * @return true if pair username-password is exists in database, else return false
     */
    public boolean checkLogin(String username, String password) {
        //xin quyen
        SQLiteDatabase database=dbHelper.getReadableDatabase();
        //cau lenh truy van
        String selectQuery="SELECT COUNT(*) FROM "+TABLE_NAME+ " WHERE "+
                COLUMN_NGUOI_DUNG_USERNAME+"=? AND "+COLUMN_NGUOI_DUNG_PASSWORD+"=?";
        //su dung cau lenh rawQuery
        Cursor cursor=database.rawQuery(selectQuery, new String[] {username, password});
        cursor.moveToFirst();
        int result=cursor.getInt(0);
        //dong ket noi cursor va db
        cursor.close();
        database.close();
        if(result>0) {
            return true;
        } return false;
    }

    /**
     * Tạo content values
     * lấy username và pass của đối tượng người dùng vừa truyền vào cho vào content values
     * update thông tin người dùng trong database
     * Thông báo update thành công (trả về true) hay thất bại (false)
     * @param nd doi tuong NguoiDung lay tu UI (?)
     * @return thay doi pass thanh cong hay that bai
     */
    public boolean changePasswordNguoiDung(NguoiDung nd) {
        SQLiteDatabase database=dbHelper.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(COLUMN_NGUOI_DUNG_USERNAME, nd.getUserName());
        values.put(COLUMN_NGUOI_DUNG_PASSWORD, nd.getPassword());
        int result=database.update(
                TABLE_NAME,
                values,
                COLUMN_NGUOI_DUNG_USERNAME+"=?",
                new String[] {nd.getUserName()}
        );
        database.close();
        if (result>0) {
            return true;
        }
        return false;
    }
}
