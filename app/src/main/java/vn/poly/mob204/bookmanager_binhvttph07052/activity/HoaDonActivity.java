package vn.poly.mob204.bookmanager_binhvttph07052.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import vn.poly.mob204.bookmanager_binhvttph07052.R;
import vn.poly.mob204.bookmanager_binhvttph07052.ValidateFunctionLibrary;
import vn.poly.mob204.bookmanager_binhvttph07052.adapter.BookForCartAdapter;
import vn.poly.mob204.bookmanager_binhvttph07052.adapter.CartAdapter;
import vn.poly.mob204.bookmanager_binhvttph07052.dao.HoaDonChiTietDAO;
import vn.poly.mob204.bookmanager_binhvttph07052.dao.HoaDonDAO;
import vn.poly.mob204.bookmanager_binhvttph07052.dao.SachDAO;
import vn.poly.mob204.bookmanager_binhvttph07052.database.DatabaseHelper;
import vn.poly.mob204.bookmanager_binhvttph07052.model.HoaDon;
import vn.poly.mob204.bookmanager_binhvttph07052.model.HoaDonChiTiet;
import vn.poly.mob204.bookmanager_binhvttph07052.model.Sach;

public class HoaDonActivity extends AppCompatActivity {
    //debug
    private static final String TAG = "HoaDonActivityLog";

    //view
    private EditText edNgayMua;
    private AutoCompleteTextView auBook;
    private EditText edSoLuongMua;
    private RecyclerView rvCart;
    public static TextView tvTotalOfCart;

    //thong tin
    private String dateString;
    String bookId;
    String numberOfBookToBuyText;
    int numberOfBookToBuy;

    //dd-MM-yyyy
    Calendar calendarDate = Calendar.getInstance();
    SimpleDateFormat sdfDate = new SimpleDateFormat("dd-MM-yyyy");

    //database
    HoaDonDAO hoaDonDAO;
    HoaDonChiTietDAO hoaDonChiTietDAO;

    //get book list from database
    BookForCartAdapter bookForCartAdapter;
    SachDAO sachDAO;

    //validate
    ValidateFunctionLibrary validateFunctionLibrary;

    //hoa don chi tiet
    public static List<HoaDonChiTiet> booksInCartList;
    CartAdapter cartAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hoa_don);
        addControls();
        init();

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        refreshBookForCartAdapter();
        setCurrentDateForEdtNgayMua();
        addEvents();
    }

    private void setCurrentDateForEdtNgayMua() {
        Date currentDate = new Date(System.currentTimeMillis());
        edNgayMua.setText(sdfDate.format(currentDate));
    }

    private void addEvents() {
        auBook.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                bookId = bookForCartAdapter.getItem(position).toString();
            }
        });
    }

    private void init() {
        hoaDonDAO = new HoaDonDAO(this);

        //Đổ dữ liệu sách từ db vào adapter của autocompleteTextView
        sachDAO = new SachDAO(this);
        bookForCartAdapter = new BookForCartAdapter(this, R.layout.item_book_for_cart);
        bookForCartAdapter.addAll(sachDAO.getAllSach());
        auBook.setAdapter(bookForCartAdapter);

        //validate
        validateFunctionLibrary = new ValidateFunctionLibrary(this);

        //hoa don chi tiet
        booksInCartList = new ArrayList<>();
        cartAdapter = new CartAdapter(this, booksInCartList);
        rvCart.setAdapter(cartAdapter);
        LinearLayoutManager vertical = new LinearLayoutManager(this);
        rvCart.setLayoutManager(vertical);

        //insert vao hoa don chi tiet
        hoaDonChiTietDAO = new HoaDonChiTietDAO(this);
    }

    private void addControls() {
        edNgayMua = (EditText) findViewById(R.id.edNgayMua);
        auBook = (AutoCompleteTextView) findViewById(R.id.auBook);
        edSoLuongMua = (EditText) findViewById(R.id.edSoLuongMua);
        rvCart = (RecyclerView) findViewById(R.id.rvCart);
        tvTotalOfCart = (TextView) findViewById(R.id.tvTotalOfCart);
    }

    public void openDatePicker(View view) {
        DatePickerDialog.OnDateSetListener callBack = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendarDate.set(Calendar.YEAR, year);
                calendarDate.set(Calendar.MONTH, month);
                calendarDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                edNgayMua.setText(sdfDate.format(calendarDate.getTime()));
            }
        };
        DatePickerDialog dialog = new DatePickerDialog(
                this,
                callBack,
                calendarDate.get(Calendar.YEAR),
                calendarDate.get(Calendar.MONTH),
                calendarDate.get(Calendar.DAY_OF_MONTH)
        );
        dialog.show();
    }

    /**
     * Lấy thông tin
     * - Lấy mã sách từ auTv (mã sách đã lấy từ khi người dùng chọn một mặt hàng
     * trên autoTv rồi)
     * - Lấy số lượng text
     *
     * @param view
     */
    public void addBookToCart(View view) {
        //lay thong tin
        bookId = auBook.getText().toString().trim();
        numberOfBookToBuyText = edSoLuongMua.getText().toString().trim();

        //validate
        if (validateAddBookToCart() == ValidateFunctionLibrary.FAIL) {
            return;
        }

        //lay doi tuong sach tu db theo ma sach
        Sach sach = sachDAO.getSachById(bookId);



        //them vao list
        //Nếu giỏ hàng chưa có sách thì cứ thêm như bình thường
        //Nếu có sách rồi thì xem sách này đã có trong giỏ hàng chưa,
        // nếu có thì cộng thêm số lượng vào
        //Nếu chưa có thì lại thêm vào như bình thường
        if (booksInCartList.size()==0) {
            //tao doi tuong
            HoaDonChiTiet hdct = new HoaDonChiTiet(
                    sach,
                    numberOfBookToBuy
            );
            booksInCartList.add(hdct);
        } else {
            boolean chuaCoSachNayTrongGioHang = true;
            //Duyệt một vòng list
            for (int i = 0; i<booksInCartList.size(); i++) {
                HoaDonChiTiet hdctDaCoTrongList = booksInCartList.get(i);
                //Lấy ra mã sách
                String maSachCuaHdctNay = hdctDaCoTrongList.getSach().getMaSach();
                //lay so luong
                int soLuongDaMuaTruocDoCuaSachNay = hdctDaCoTrongList.getSoLuongMua();
                if (bookId.equals(maSachCuaHdctNay)) {//Nếu trùng với mã sách đang nhập ở form
                    //cong them so luong vao
                    int tongSoLuongMuaTruocVaHienTai = soLuongDaMuaTruocDoCuaSachNay + numberOfBookToBuy;
                    //sua lai so luong
                    hdctDaCoTrongList.setSoLuongMua(tongSoLuongMuaTruocVaHienTai);
                    chuaCoSachNayTrongGioHang = false; //đã có loại sách này trong giỏ hàng
                }
            }

            if (chuaCoSachNayTrongGioHang) {
                //them vao nhu binh thuong
                //tao doi tuong
                HoaDonChiTiet hdct = new HoaDonChiTiet(
                        sach,
                        numberOfBookToBuy
                );
                booksInCartList.add(hdct);
            }
        }

        //refresh adapter
        cartAdapter.notifyDataSetChanged();
        // refresh tong tien (totalOfCart)
        refreshTotalOfCart();
    }

    private boolean validateAddBookToCart() {
//        Khi người dùng chưa nhap ma sach trong auTv
        if (validateFunctionLibrary.isTextEmpty(
                bookId, getResources().getString(R.string.ma_sach))
        ) {
            return false;
        }

        //neu ma sach khong ton tai trong db
        if (!isBookIdExists()) {
            return false;
        }

        if (validateFunctionLibrary.isTextEmpty(
                numberOfBookToBuyText, getResources().getString(R.string.so_luong_can_mua))
        ) {
            return false;
        }

        //if enter too big number in quantity
        //use anonymous class
        if (validateFunctionLibraryCustom.canNotParseToInt(
                numberOfBookToBuyText, getResources().getString(R.string.so_luong_can_mua))

        ) {
            return false;
        }

        if (isNumberOfBookWantToBuyOverNumberArchived(bookId)) {
            return false;
        }
        return true;
    }

    /**
     * Kiểm tra xem số lượng sách cần mua có vượt quá số lượng có trong kho không.
     * Nếu có, trả về true, ngược lại trả về false.
     * Lấy số lượng sách cần mua
     * Lấy số lượng sách có trong kho của mã sách cần tìm
     * Nếu số lượng sách trong cần mua > số lượng sách có trong kho return true,
     * ngược lại return false
     *
     * @param bookId
     * @return
     */
    private boolean isNumberOfBookWantToBuyOverNumberArchived(String bookId) {
        int numberBookArchived;
        numberOfBookToBuy = Integer.parseInt(numberOfBookToBuyText);
        numberBookArchived = sachDAO.getNumberOfArchivedBook(bookId);
        if (numberOfBookToBuy > numberBookArchived) {
            Toast.makeText(
                    this,
                    R.string.number_book_to_buy_is_over_number_of_archived_book,
                    Toast.LENGTH_SHORT
            ).show();
            return true;
        }
        return false;
    }

    private boolean isBookIdExists() {
        //test: kiem tra xem ma sach co ton tai khong
        boolean isBookIdExist = sachDAO.isBookIdExists(bookId);
        if (isBookIdExist) {
            return true;
        } else {
            Toast.makeText(
                    this,
                    R.string.book_id_is_not_exist,
                    Toast.LENGTH_SHORT
            ).show();
            return false;
        }
    }

    public void addBillAndDetailsToDatabase(View view) {
        //lay thong tin hoa don
        //lay thong tin
        dateString = edNgayMua.getText().toString().trim();
        if (validateCheckout() == ValidateFunctionLibrary.FAIL) {
            return;
        }
        //tao doi tuong
        HoaDon hoaDon = new HoaDon(calendarDate.getTime());
        //luu hoa don
        long result = hoaDonDAO.insertHoaDon(hoaDon);
        if (result > -1) {
            //luu hoa don chi tiet
            insertBillDetailToDatabase(hoaDonDAO.getHoaDonFromRowId(result));
        } else {
            Toast.makeText(
                    this,
                    R.string.insert_bill_fail,
                    Toast.LENGTH_SHORT
            ).show();
            return;
        }

    }

    /**
     * Luu hoa don chi tiet sau khi da luu xong 1 hoa don
     * Duyệt một vòng list sách trong giỏ hàng, lần lượt thêm thuộc tính hoaDon
     * vào trong từng đối tượng HDCT
     * Xong rồi thì insert đối tượng đó vào db
     * Nếu thành công: do nothing (nhưng vẫn có câu lệnh if>-1 để tất cả các lỗi rơi vào trường hợp kia)
     * Nếu thất bại: tạo một biến đếm lỗi (khai báo trước khi chạy vòng for),
     * nếu thất bại thì biến đếm ++
     * Chạy xong vòng for thì kiểm tra xem biến đếm có >0 không,
     * nếu có thì báo là thêm hàng hóa vào bộ nhớ bị lỗi
     *
     * @param hoaDon doi tuong hoaDon vua tao ra tu form
     */
    private void insertBillDetailToDatabase(HoaDon hoaDon) {
        int countNumberOfInsertError = 0; //biến đếm lỗi
        //Duyệt một vòng list sách trong giỏ hàng
        for (int i = 0; i < booksInCartList.size(); i++) {
            //Lay ra doi tuong
            HoaDonChiTiet hdct = booksInCartList.get(i);
//          lần lượt thêm thuộc tính hoaDon
            hdct.setHoaDon(hoaDon);
            //insert vao db
            long resultInsertHdct = hoaDonChiTietDAO.insertHoaDonChiTiet(hdct);
            if (resultInsertHdct > -1) {
                //do nothing
            } else {
                countNumberOfInsertError++;
            }
        }
        //xem xem tat ca hoa don chi tiet da duoc insert vao db chua
        if (countNumberOfInsertError > 0) {
            //thong bao loi
            Toast.makeText(
                    this,
                    R.string.insert_book_to_bill_detail_fail,
                    Toast.LENGTH_SHORT
            ).show();
        } else {
            Toast.makeText(this,
                    R.string.insert_successfully,
                    Toast.LENGTH_SHORT).show();
            //xoa het thong tin tren form de tao hoa don moi
            clearHoaDonChiTietForm();
            clearCart();
            refreshTotalOfCart();
        }
    }

    private void clearCart() {
        booksInCartList.clear();
        cartAdapter.notifyDataSetChanged();
    }

    private void clearHoaDonChiTietForm() {
        auBook.setText("");
        edSoLuongMua.setText("");
    }

    private boolean validateCheckout() {
        if (cartAdapter.getItemCount() == 0) {
            Toast.makeText(
                    this,
                    R.string.cart_is_empty,
                    Toast.LENGTH_SHORT
            ).show();
            return false;
        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshBookForCartAdapter();
    }

    private void refreshBookForCartAdapter() {
        bookForCartAdapter.clear();
        bookForCartAdapter.addAll(sachDAO.getAllSach());
        bookForCartAdapter.notifyDataSetChanged();
    }

    ValidateFunctionLibrary validateFunctionLibraryCustom = new ValidateFunctionLibrary(this) {
        @Override
        public boolean canNotParseToInt(String text, String field) {
            try {
                Integer.parseInt(text);
                return false;
            } catch (NumberFormatException exc) {
                exc.printStackTrace();
                Toast.makeText(
                        HoaDonActivity.this,
                        field + " vượt quá giới hạn",
                        Toast.LENGTH_SHORT
                ).show();
                return true;
            }
        }
    };

    /**
     * Cập nhật tổng tiền theo giỏ hàng.
     * duyệt một vòng list
     * xong cộng tất cả giá*số lượng vào (giá lấy từ thuộc tính sach của một HDCT)
     * xong set lên text
     */
    public static void refreshTotalOfCart() {
        double totalOfCart = 0;
        for (int i = 0; i < booksInCartList.size(); i++) {
            //lay HDCT ra
            HoaDonChiTiet hdct = booksInCartList.get(i);
            //thanh tien cua mot hoa don
            double thanhTienCuaMotHoaDon = hdct.getSach().getGiaBia() * hdct.getSoLuongMua();
            totalOfCart += thanhTienCuaMotHoaDon;
        }
        tvTotalOfCart.setText(String.format("%.0f VNĐ", totalOfCart));
    }
}
