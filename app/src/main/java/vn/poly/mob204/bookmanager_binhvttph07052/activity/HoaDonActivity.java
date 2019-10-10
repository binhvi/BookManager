package vn.poly.mob204.bookmanager_binhvttph07052.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
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
import java.util.Calendar;

import vn.poly.mob204.bookmanager_binhvttph07052.R;
import vn.poly.mob204.bookmanager_binhvttph07052.ValidateFunctionLibrary;
import vn.poly.mob204.bookmanager_binhvttph07052.adapter.BookForCartAdapter;
import vn.poly.mob204.bookmanager_binhvttph07052.dao.HoaDonDAO;
import vn.poly.mob204.bookmanager_binhvttph07052.dao.SachDAO;
import vn.poly.mob204.bookmanager_binhvttph07052.model.HoaDon;

public class HoaDonActivity extends AppCompatActivity {
    //debug
    private static final String TAG = "HoaDonActivityLog";

    //view
    private EditText edNgayMua;
    private AutoCompleteTextView auBook;
    private EditText edSoLuongMua;
    private RecyclerView rvCart;
    private TextView tvTotalOfCart;

    //thong tin
    private String dateString;
    String bookId;
    String numberOfBookToBuyText;
    int numberOfBookToBuy;

    //dd-MM-yyyy
    Calendar calendarDate=Calendar.getInstance();
    SimpleDateFormat sdfDate=new SimpleDateFormat("dd-MM-yyyy");

    //database
    HoaDonDAO hoaDonDAO;

    //get book list from database
    BookForCartAdapter bookForCartAdapter;
    SachDAO sachDAO;

    //get selected book id from autocompleteTextView

    //validate
    ValidateFunctionLibrary validateFunctionLibrary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hoa_don);
        addControls();
        init();

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        refreshBookForCartAdapter();
        
        addEvents();
    }

    private void addEvents() {
        auBook.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                bookId =bookForCartAdapter.getItem(position).toString();
            }
        });
    }

    private void init() {
        hoaDonDAO=new HoaDonDAO(this);

        //Đổ dữ liệu sách từ db vào adapter của autocompleteTextView
        sachDAO=new SachDAO(this);
        bookForCartAdapter = new BookForCartAdapter(this, R.layout.item_book_for_cart);
        bookForCartAdapter.addAll(sachDAO.getAllSach());
        auBook.setAdapter(bookForCartAdapter);

        //validate
        validateFunctionLibrary=new ValidateFunctionLibrary(this);
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

    //todo: chep code xong roi xoa ham nay di
    public void saveHoaDon(View view) {
        //lay thong tin
        dateString=edNgayMua.getText().toString().trim();
        //validate: neu chua chon ngay -> dateString empty --> thong bao phai chon ngay
        if (dateString.isEmpty()) {
            Toast.makeText(
                    this,
                    R.string.pls_pick_date,
                    Toast.LENGTH_SHORT
            ).show();
            return;
        }

        addHoaDon();

    }

    //todo: chep code xong roi xoa ham nay di
    private void addHoaDon() {
        //tao doi tuong
        HoaDon hoaDon=new HoaDon(calendarDate.getTime());
        //luu vao db
        long result=hoaDonDAO.insertHoaDon(hoaDon);
        if (result>-1) {
            Toast.makeText(
                    this,
                    R.string.insert_successfully,
                    Toast.LENGTH_SHORT
            ).show();
        } else {
            Toast.makeText(
                    this,
                    R.string.insert_fail,
                    Toast.LENGTH_SHORT
            ).show();
        }

    }

    /**
     * Lấy thông tin
     * - Lấy mã sách từ auTv (mã sách đã lấy từ khi người dùng chọn một mặt hàng
     * trên autoTv rồi)
     * - Lấy số lượng text
     * @param view
     */
    public void addBookToCart(View view) {
        //lay thong tin
        bookId = auBook.getText().toString().trim();
        numberOfBookToBuyText = edSoLuongMua.getText().toString().trim();

        //validate
        if (validateAddBookToCart()==ValidateFunctionLibrary.FAIL) {
            return;
        }


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

        if(validateFunctionLibrary.isTextEmpty(
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
     * @param bookId
     * @return
     */
    private boolean isNumberOfBookWantToBuyOverNumberArchived(String bookId) {
        int numberBookArchived;
        numberOfBookToBuy = Integer.parseInt(numberOfBookToBuyText);
        numberBookArchived=sachDAO.getNumberOfArchivedBook(bookId);
        Log.i(TAG, numberBookArchived+"");
        if (numberOfBookToBuy>numberBookArchived) {
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
        boolean isBookIdExist=sachDAO.isBookIdExists(bookId);
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

    public void AddBillAndDetailsToDatabase(View view) {
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

    ValidateFunctionLibrary validateFunctionLibraryCustom=new ValidateFunctionLibrary(this) {
        @Override
        public boolean canNotParseToInt(String text, String field) {
            try {
                Integer.parseInt(text);
                return false;
            } catch (NumberFormatException exc) {
                exc.printStackTrace();
                Toast.makeText(
                        HoaDonActivity.this,
                        field+" vượt quá giới hạn",
                        Toast.LENGTH_SHORT
                ).show();
                return true;
            }
        }
    };
}
