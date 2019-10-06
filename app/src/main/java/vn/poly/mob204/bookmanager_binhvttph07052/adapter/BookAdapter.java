package vn.poly.mob204.bookmanager_binhvttph07052.adapter;

import android.app.Activity;
import android.content.Intent;
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
import vn.poly.mob204.bookmanager_binhvttph07052.activity.ThemSachActivity;
import vn.poly.mob204.bookmanager_binhvttph07052.model.Sach;

import static vn.poly.mob204.bookmanager_binhvttph07052.activity.ListBookActivity.dsSach;
import static vn.poly.mob204.bookmanager_binhvttph07052.activity.ListBookActivity.refreshSachAdapter;
import static vn.poly.mob204.bookmanager_binhvttph07052.activity.ListBookActivity.sachDAO;
import static vn.poly.mob204.bookmanager_binhvttph07052.activity.ThemSachActivity.KEY_SACH;

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
        ViewHolder viewHolder = new ViewHolder(view, context);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final int pos=position;
        Sach sach = arrSach.get(position);

        //set thong tin cua doi tuong len view
        holder.img.setImageResource(R.drawable.bookicon);
        holder.txtBookName.setText(sach.getTenSach());
        holder.txtBookPrice.setText("Giá: " + sach.getGiaBia() + " VNĐ");
        //xu ly su kien nut delete
        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //xoa trong db
                String idOfDeleteBook=dsSach.get(pos).getMaSach();
                int result=sachDAO.deleteSachByID(idOfDeleteBook);
                if (result>0) {
                    refreshSachAdapter();
                } else {
                    Toast.makeText(
                            context,
                            R.string.delete_failed,
                            Toast.LENGTH_SHORT
                    ).show();
                }
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

        public ViewHolder(@NonNull View itemView, final Activity context) {
            super(itemView);

            img = (ImageView) itemView.findViewById(R.id.ivIcon);
            txtBookName = (TextView) itemView.findViewById(R.id.tvBookName);
            txtBookPrice = (TextView) itemView.findViewById(R.id.tvBookPrice);
            imgDelete = (ImageView) itemView.findViewById(R.id.ivDelete);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //lay doi tuong
                    Sach sach=dsSach.get(getAdapterPosition());
                    //gui sang ThemSachActivity
                    Intent intent=new Intent(context, ThemSachActivity.class);
                    intent.putExtra(KEY_SACH, sach);
                    context.startActivity(intent);
                }
            });
        }
    }
}
