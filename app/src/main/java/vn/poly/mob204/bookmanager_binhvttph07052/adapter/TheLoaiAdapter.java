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
import vn.poly.mob204.bookmanager_binhvttph07052.model.TheLoai;

public class TheLoaiAdapter extends RecyclerView.Adapter<TheLoaiAdapter.ViewHolder> implements Filterable {
    List<TheLoai> arrTheLoai;
    Activity context;

    public TheLoaiAdapter(Activity context, List<TheLoai> arrTheLoai) {
        this.arrTheLoai = arrTheLoai;
        this.context = context;
    }

    @Override
    public Filter getFilter() {
        return null;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = this.context.getLayoutInflater();
        View view = inflater.inflate(R.layout.item_theloai, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TheLoai theLoai = arrTheLoai.get(position);

        holder.img.setImageResource(R.drawable.cateicon);
        holder.txtMaTheLoai.setText(theLoai.getMaTheLoai());
        holder.txtTenTheLoai.setText(theLoai.getTenTheLoai());

        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return arrTheLoai.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView img;
        private TextView txtMaTheLoai;
        private TextView txtTenTheLoai;
        private ImageView imgDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            img = (ImageView) itemView.findViewById(R.id.ivIcon);
            txtMaTheLoai = (TextView) itemView.findViewById(R.id.tvMaTheLoai);
            txtTenTheLoai = (TextView) itemView.findViewById(R.id.tvTenTheLoai);
            imgDelete = (ImageView) itemView.findViewById(R.id.ivDelete);
        }
    }
}
