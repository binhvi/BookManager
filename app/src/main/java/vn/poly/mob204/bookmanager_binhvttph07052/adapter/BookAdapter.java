package vn.poly.mob204.bookmanager_binhvttph07052.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import vn.poly.mob204.bookmanager_binhvttph07052.R;
import vn.poly.mob204.bookmanager_binhvttph07052.model.Sach;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.ViewHolder>
    implements Filterable
{
    List<Sach> arrSach;
    Activity context;

    public BookAdapter(List<Sach> arrSach, Activity context) {
        this.arrSach = arrSach;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(context).inflate(R.layout.item_book, parent, false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Sach sach=arrSach.get(position);

        //set thong tin cua doi tuong len view
        holder.img.setImageResource(R.drawable.bookicon);
        holder.txtBookName.setText("Mã sách: "+sach.getMaSach());
        holder.txtSoLuong.setText("Số lượng: "+sach.getSoLuong());
        holder.txtBookPrice.setText("Giá: "+sach.getGiaBia()+" VNĐ");
        //xu ly su kien nut delete
        //todo: xu ly su kien nut delete


    }

    @Override
    public int getItemCount() {
        return arrSach.size();
    }

    @Override
    public Filter getFilter() {
        //todo: viet ham filter sach
        return null;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView txtBookName;
        TextView txtSoLuong;
        TextView txtBookPrice;
        ImageView imgDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            img = (ImageView) itemView.findViewById(R.id.ivIcon);
            txtBookName = (TextView) itemView.findViewById(R.id.tvBookName);
            txtSoLuong = (TextView) itemView.findViewById(R.id.tvSoLuong);
            txtBookPrice = (TextView) itemView.findViewById(R.id.tvBookPrice);
            imgDelete = (ImageView) itemView.findViewById(R.id.ivDelete);
        }
    }
}
