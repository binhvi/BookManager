package vn.poly.mob204.bookmanager_binhvttph07052.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import vn.poly.mob204.bookmanager_binhvttph07052.R;
import vn.poly.mob204.bookmanager_binhvttph07052.model.NguoiDung;

public class NguoiDungAdapter extends RecyclerView.Adapter<NguoiDungAdapter.ViewHolder> {
    List<NguoiDung> arrNguoiDung;
    public Activity context;

    public NguoiDungAdapter(List<NguoiDung> arrNguoiDung, Activity context) {
        this.arrNguoiDung = arrNguoiDung;
        this.context = context;
    }

    @NonNull
    @Override
    public NguoiDungAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_nguoi_dung, parent, false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }


    //truyen du lieu vao layout
    @Override
    public void onBindViewHolder(@NonNull NguoiDungAdapter.ViewHolder holder, int position) {
        final NguoiDung nguoiDung=arrNguoiDung.get(position);

        //icon
        if (position%3==0) {
            holder.img.setImageResource(R.drawable.emone);
        } else if (position%3==1) {
            holder.img.setImageResource(R.drawable.emtwo);
        } else {
            holder.img.setImageResource(R.drawable.emthree);
        }
        //name
        holder.txtName.setText(nguoiDung.getHoTen());
        //so dien thoai
        holder.txtPhone.setText(nguoiDung.getPhone());
    }

    @Override
    public int getItemCount() {
        return arrNguoiDung.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView img;
        public TextView txtName;
        public TextView txtPhone;
        public ImageView imgDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.ivIcon);
            txtName = (TextView) itemView.findViewById(R.id.tvName);
            txtPhone = (TextView) itemView.findViewById(R.id.tvPhone);
            imgDelete = (ImageView) itemView.findViewById(R.id.ivDelete);
        }
    }
}

