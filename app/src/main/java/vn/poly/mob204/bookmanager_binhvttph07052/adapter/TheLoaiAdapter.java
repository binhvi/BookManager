package vn.poly.mob204.bookmanager_binhvttph07052.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import vn.poly.mob204.bookmanager_binhvttph07052.R;
import vn.poly.mob204.bookmanager_binhvttph07052.activity.ListTheLoaiActivity;
import vn.poly.mob204.bookmanager_binhvttph07052.activity.TheLoaiActivity;
import vn.poly.mob204.bookmanager_binhvttph07052.model.TheLoai;

import static vn.poly.mob204.bookmanager_binhvttph07052.activity.ListTheLoaiActivity.dsTheLoai;
import static vn.poly.mob204.bookmanager_binhvttph07052.activity.ListTheLoaiActivity.theLoaiDAO;

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
        ViewHolder viewHolder = new ViewHolder(view, context);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final int pos=position; //de cho tham so position khong bi final khi truyen vao ham bat su kien
        TheLoai theLoai = arrTheLoai.get(pos);

        holder.img.setImageResource(R.drawable.cateicon);
        holder.txtMaTheLoai.setText(theLoai.getMaTheLoai());
        holder.txtTenTheLoai.setText(theLoai.getTenTheLoai());

        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //xoa trong db
                int result=theLoaiDAO.deleteTheLoaiByID(dsTheLoai.get(pos).getMaTheLoai());
                if (result>0) {
                    //refresh adapter
                    ListTheLoaiActivity.refreshAdapter();
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
        return arrTheLoai.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public static final String KEY_MA_THE_LOAI = "MATHELOAI";
        public static final String KEY_TEN_THE_LOAI = "TENTHELOAI";
        public static final String KEY_MO_TA = "MOTA";
        public static final String KEY_VI_TRI = "VITRI";
        private ImageView img;
        private TextView txtMaTheLoai;
        private TextView txtTenTheLoai;
        private ImageView imgDelete;

        public ViewHolder(@NonNull View itemView, final Activity context) {
            super(itemView);

            img = (ImageView) itemView.findViewById(R.id.ivIcon);
            txtMaTheLoai = (TextView) itemView.findViewById(R.id.tvMaTheLoai);
            txtTenTheLoai = (TextView) itemView.findViewById(R.id.tvTenTheLoai);
            imgDelete = (ImageView) itemView.findViewById(R.id.ivDelete);

            //gui thong tin TheLoai sang TheLoaiActivity
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //lay doi tuong
                    TheLoai theLoai=dsTheLoai.get(getAdapterPosition());
                    Intent intent = new Intent(context, TheLoaiActivity.class);
                    //them vao Bundle
                    Bundle bundle=new Bundle();
                    bundle.putString(KEY_MA_THE_LOAI, theLoai.getMaTheLoai());
                    bundle.putString(KEY_TEN_THE_LOAI, theLoai.getTenTheLoai());
                    bundle.putInt(KEY_VI_TRI, theLoai.getViTri());
                    bundle.putString(KEY_MO_TA, theLoai.getMoTa());
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }
            });
        }
    }
}
