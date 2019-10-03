package vn.poly.mob204.bookmanager_binhvttph07052.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import vn.poly.mob204.bookmanager_binhvttph07052.R;
import vn.poly.mob204.bookmanager_binhvttph07052.model.Sach;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.ViewHolder>
        implements Filterable {
    List<Sach> arrSach;
    Activity context;

    public BookAdapter(Activity context, List<Sach> arrSach) {
        this.arrSach = arrSach;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_book, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final Sach sach = arrSach.get(position);

        //set thong tin cua doi tuong len view
        holder.img.setImageResource(R.drawable.bookicon);
        holder.txtBookName.setText(sach.getTenSach());
        holder.txtBookPrice.setText("Giá: " + sach.getGiaBia() + " VNĐ");
        //xu ly su kien nut delete
        //todo: xu ly su kien nut delete
        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "item " + position, Toast.LENGTH_SHORT).show();
            }
        });
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
        TextView txtBookPrice;
        ImageView imgDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            img = (ImageView) itemView.findViewById(R.id.ivIcon);
            txtBookName = (TextView) itemView.findViewById(R.id.tvBookName);
            txtBookPrice = (TextView) itemView.findViewById(R.id.tvBookPrice);
            imgDelete = (ImageView) itemView.findViewById(R.id.ivDelete);
        }
    }
}
