package vn.poly.mob204.bookmanager_binhvttph07052.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import vn.poly.mob204.bookmanager_binhvttph07052.R;
import vn.poly.mob204.bookmanager_binhvttph07052.activity.NguoiDungActivity;
import vn.poly.mob204.bookmanager_binhvttph07052.activity.NguoiDungDetailActivity;
import vn.poly.mob204.bookmanager_binhvttph07052.dao.NguoiDungDAO;
import vn.poly.mob204.bookmanager_binhvttph07052.model.NguoiDung;

import static vn.poly.mob204.bookmanager_binhvttph07052.activity.ListNguoiDungActivity.nguoiDungList;

public class NguoiDungAdapter extends RecyclerView.Adapter<NguoiDungAdapter.ViewHolder> {
    List<NguoiDung> arrNguoiDung;
    public Activity context;
    NguoiDungDAO nguoiDungDAO;

    public static final String USERNAME="USERNAME";
    public static final String PHONE="PHONE";
    public static final String FULLNAME="FULLNAME";

    public NguoiDungAdapter(List<NguoiDung> arrNguoiDung, Activity context) {
        this.arrNguoiDung = arrNguoiDung;
        this.context = context;
        nguoiDungDAO=new NguoiDungDAO(context);
    }

    @NonNull
    @Override
    public NguoiDungAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_nguoi_dung, parent, false);
        ViewHolder viewHolder=new ViewHolder(view, context);
        return viewHolder;
    }


    //truyen du lieu vao layout
    @Override
    public void onBindViewHolder(@NonNull NguoiDungAdapter.ViewHolder holder, int position) {
        final NguoiDung nguoiDung=arrNguoiDung.get(position);
        final int pos=position;
        //icon
        if (position%3==0) {
            holder.img.setImageResource(R.drawable.emone);
        } else if (position%3==1) {
            holder.img.setImageResource(R.drawable.emtwo);
        } else {
            holder.img.setImageResource(R.drawable.emthree);
        }
        //hien thi name
        holder.txtName.setText(nguoiDung.getHoTen());
        //hien thi so dien thoai
        holder.txtPhone.setText(nguoiDung.getPhone());

        //xoa
        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //xoa trong db
                int result=nguoiDungDAO.deleteNguoiDungByID(nguoiDung.getUserName());
                //neu thanh cong thi refresh adapter
                if (result>0) {
                    refreshAdapter();
                } else {
                    Toast.makeText(
                            context,
                            R.string.delete_failed,
                            Toast.LENGTH_SHORT
                    ).show();
                }
                //neu khong thanh cong thi bao xoa loi
            }
        });
    }

    private void refreshAdapter() {
        arrNguoiDung.clear();
        arrNguoiDung.addAll(nguoiDungDAO.getAllNguoiDung());
        notifyDataSetChanged();
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

        public ViewHolder(@NonNull View itemView, final Context context) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.ivIcon);
            txtName = (TextView) itemView.findViewById(R.id.tvName);
            txtPhone = (TextView) itemView.findViewById(R.id.tvPhone);
            imgDelete = (ImageView) itemView.findViewById(R.id.ivDelete);

            itemView.setOnClickListener(new View.OnClickListener() {
                //gui du lieu sang NguoiDungDetailActivity
                @Override
                public void onClick(View view) {
                    //lay doi tuong hien tai
                    int itemPosition=getAdapterPosition();
                    NguoiDung nguoiDung=nguoiDungList.get(itemPosition);
                    //lay ten, phone
                    String username=nguoiDung.getUserName();
                    String fullName=nguoiDung.getHoTen();
                    String phone=nguoiDung.getPhone();

                    Intent intent=new Intent(context, NguoiDungDetailActivity.class);
                    intent.putExtra(USERNAME, username);
                    intent.putExtra(PHONE, phone);
                    intent.putExtra(FULLNAME, fullName);

                    context.startActivity(intent);
                }
            });
        }
    }
}

