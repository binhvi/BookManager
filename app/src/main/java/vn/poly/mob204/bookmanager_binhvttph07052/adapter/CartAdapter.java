package vn.poly.mob204.bookmanager_binhvttph07052.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import vn.poly.mob204.bookmanager_binhvttph07052.R;
import vn.poly.mob204.bookmanager_binhvttph07052.model.HoaDonChiTiet;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> implements Filterable {
    Activity context;
    List<HoaDonChiTiet> arrHoaDonChiTiet;

    public CartAdapter(Activity context, List<HoaDonChiTiet> arrHoaDonChiTiet) {
        this.context = context;
        this.arrHoaDonChiTiet = arrHoaDonChiTiet;
    }

    @Override
    public Filter getFilter() {
        return null;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = this.context.getLayoutInflater();
        View view = inflater.inflate(R.layout.item_cart, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //lay doi tuong tai vi tri position
        HoaDonChiTiet hoaDonChiTiet = arrHoaDonChiTiet.get(position);

        //set len view
        //todo: khong hieu cai getSach().getMaSach() nay la nhu nao
        holder.txtMaSach.setText("Mã sách: " + hoaDonChiTiet.getSach().getMaSach());
        holder.txtSoLuong.setText("Số lượng: " + hoaDonChiTiet.getSoLuongMua());
        holder.txtGiaBia.setText("Giá bìa: " + hoaDonChiTiet.getSach().getGiaBia() + "VNĐ");
        holder.txtThanhTien.setText(
                "Thành tiền: " +
                        hoaDonChiTiet.getSoLuongMua() * hoaDonChiTiet.getSach().getGiaBia() + "VNĐ");
        //xu ly su kien nut xoa
        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return arrHoaDonChiTiet.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtMaSach;
        TextView txtSoLuong;
        TextView txtGiaBia;
        TextView txtThanhTien;
        ImageView imgDelete;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtMaSach = (TextView) itemView.findViewById(R.id.tvMaSach);
            txtSoLuong = (TextView) itemView.findViewById(R.id.tvSoLuong);
            txtGiaBia = (TextView) itemView.findViewById(R.id.tvGiaBia);
            txtThanhTien = (TextView) itemView.findViewById(R.id.tvThanhTien);
            imgDelete = (ImageView) itemView.findViewById(R.id.ivDelete);
        }
    }
}
