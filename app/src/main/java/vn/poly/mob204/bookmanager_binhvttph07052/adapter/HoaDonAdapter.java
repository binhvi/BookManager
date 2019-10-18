package vn.poly.mob204.bookmanager_binhvttph07052.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import vn.poly.mob204.bookmanager_binhvttph07052.R;
import vn.poly.mob204.bookmanager_binhvttph07052.dao.HoaDonChiTietDAO;
import vn.poly.mob204.bookmanager_binhvttph07052.model.HoaDon;
import vn.poly.mob204.bookmanager_binhvttph07052.model.HoaDonChiTiet;

public class HoaDonAdapter extends RecyclerView.Adapter<HoaDonAdapter.ViewHolder> {
    public Activity context;
    List<HoaDon> arrHoaDon;
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    //hoa don chi tiet
    HoaDonChiTietDAO hoaDonChiTietDAO;

    public HoaDonAdapter(Activity context, List<HoaDon> arrHoaDon) {
        this.context = context;
        this.arrHoaDon = arrHoaDon;
        hoaDonChiTietDAO = new HoaDonChiTietDAO(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_hoadon, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String allDetailsOfThisBill = "";
        int maHoaDon;
        String ngayMua;
        double thanhTien;

        final HoaDon hoaDon = arrHoaDon.get(position);

        //lay ma hoa don, ngay
        maHoaDon = hoaDon.getMaHoaDon();
        ngayMua = sdf.format(hoaDon.getNgayMua());
        holder.tvBillIdDate.setText(String.format("%d - %s", maHoaDon, ngayMua));

        //lay hoa don chi tiet cua hoa don nay
        try {
            List<HoaDonChiTiet> hdctList = hoaDonChiTietDAO.getAllHoaDonChiTiet(maHoaDon);
            for (int i = 0; i < hdctList.size(); i++) {
                //lay thong tin hoa don chi tiet cho vao string
                //lay hdct
                HoaDonChiTiet hdct = hdctList.get(i);
                String tenSach = hdct.getSach().getTenSach();
                double giaBia = hdct.getSach().getGiaBia();
                int soLuongMua = hdct.getSoLuongMua();
                //cho vao string
                allDetailsOfThisBill += String.format(
                        "%s - %.0f VNĐ - %d\n",
                        tenSach, giaBia, soLuongMua);
            }
            //Bỏ dấu \n ở dòng cuối
            allDetailsOfThisBill = allDetailsOfThisBill.substring(0, allDetailsOfThisBill.length() - 1);
            holder.tvBillDetails.setText(allDetailsOfThisBill);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //lay thanh tien
        thanhTien = hoaDonChiTietDAO.getTotalOfCart(maHoaDon);
        holder.tvTotalOfCart.setText(String.format("%.0f VNĐ", thanhTien));
    }

    @Override
    public int getItemCount() {
        return arrHoaDon.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvBillIdDate;
        private TextView tvBillDetails;
        private TextView tvTotalOfCart;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);

            tvBillIdDate = (TextView) itemView.findViewById(R.id.tvBillIdDate);
            tvBillDetails = (TextView) itemView.findViewById(R.id.tvBillDetails);
            tvTotalOfCart = (TextView) itemView.findViewById(R.id.tvTotalOfCart);
        }
    }
}
