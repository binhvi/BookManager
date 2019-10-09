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

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.List;

import vn.poly.mob204.bookmanager_binhvttph07052.activity.HoaDonChiTietActivity;
import vn.poly.mob204.bookmanager_binhvttph07052.R;
import vn.poly.mob204.bookmanager_binhvttph07052.model.HoaDon;

public class HoaDonAdapter extends RecyclerView.Adapter<HoaDonAdapter.ViewHolder>
        implements Filterable {
    public Activity context;
    List<HoaDon> arrHoaDon;
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

    public HoaDonAdapter(Activity context, List<HoaDon> arrHoaDon) {
        this.context = context;
        this.arrHoaDon = arrHoaDon;
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
        final HoaDon hoaDon = arrHoaDon.get(position);

        //lay thong tin tu doi tuong len view
//        holder.txtMaHoaDon.setText(hoaDon.getMaHoaDon() + "");
//        holder.txtNgayMua.setText(sdf.format(hoaDon.getNgayMua()));


    }

    @Override
    public int getItemCount() {
        return arrHoaDon.size();
    }

    @Override
    public Filter getFilter() {
        return null;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {


        public ViewHolder(@NonNull final View itemView) {
            super(itemView);

        }
    }
}
