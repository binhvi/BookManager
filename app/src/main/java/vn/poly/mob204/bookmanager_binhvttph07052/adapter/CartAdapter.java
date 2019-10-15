package vn.poly.mob204.bookmanager_binhvttph07052.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import vn.poly.mob204.bookmanager_binhvttph07052.R;
import vn.poly.mob204.bookmanager_binhvttph07052.model.HoaDonChiTiet;

import static vn.poly.mob204.bookmanager_binhvttph07052.activity.HoaDonActivity.booksInCartList;
import static vn.poly.mob204.bookmanager_binhvttph07052.activity.HoaDonActivity.refreshTotalOfCart;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    Activity context;
    List<HoaDonChiTiet> arrHoaDonChiTiet;

    public CartAdapter(Activity context, List<HoaDonChiTiet> arrHoaDonChiTiet) {
        this.context = context;
        this.arrHoaDonChiTiet = arrHoaDonChiTiet;
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
        final int pos=position; //de position khong bi final khi su dung trong ham xu ly su kien
        //lay doi tuong tai vi tri position
        HoaDonChiTiet hoaDonChiTiet = arrHoaDonChiTiet.get(position);

        //set len view
        //ten hang - id
        holder.tvIdNameBook.setText(
                String.format(
                        "%s - %s",
                        hoaDonChiTiet.getSach().getMaSach(),
                        hoaDonChiTiet.getSach().getTenSach()
                )
        );
        //so luong
        holder.tvQuantity.setText(String.valueOf(hoaDonChiTiet.getSoLuongMua()));
        //gia
        holder.tvPrice.setText(String.valueOf(hoaDonChiTiet.getSach().getGiaBia()));
        //xu ly su kien xoa
        holder.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //xoa khoi lish
                booksInCartList.remove(pos);
                notifyDataSetChanged();
                refreshTotalOfCart();
            }
        });

    }

    @Override
    public int getItemCount() {
        return arrHoaDonChiTiet.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvIdNameBook;
        TextView tvQuantity;
        TextView tvPrice;
        ImageView ivDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvIdNameBook = (TextView) itemView.findViewById(R.id.tvIdNameBook);
            tvQuantity = (TextView) itemView.findViewById(R.id.tvQuantity);
            tvPrice = (TextView) itemView.findViewById(R.id.tvPrice);
            ivDelete = (ImageView) itemView.findViewById(R.id.ivDelete);

        }
    }
}
