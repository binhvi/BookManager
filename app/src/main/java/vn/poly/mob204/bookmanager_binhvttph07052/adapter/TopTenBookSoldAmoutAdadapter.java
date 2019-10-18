package vn.poly.mob204.bookmanager_binhvttph07052.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import vn.poly.mob204.bookmanager_binhvttph07052.R;
import vn.poly.mob204.bookmanager_binhvttph07052.model.Sach;

public class TopTenBookSoldAmoutAdadapter extends RecyclerView.Adapter<TopTenBookSoldAmoutAdadapter.TopTenViewHolder> {
    Activity context;
    List<Sach> sachList;

    public TopTenBookSoldAmoutAdadapter(Activity context, List<Sach> sachList) {
        this.context = context;
        this.sachList = sachList;
    }

    @NonNull
    @Override
    public TopTenViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = this.context.getLayoutInflater();
        View view = inflater.inflate(R.layout.item_top_ten_book, parent, false);
        TopTenViewHolder topTenViewHolder = new TopTenViewHolder(view);
        return topTenViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TopTenViewHolder holder, int position) {
        String bookId, bookName;
        int soldAmount;

        //Lay doi tuong
        Sach sach = sachList.get(position);

        //lay bookId
        bookId = sach.getMaSach();
        //lay ten sach
        bookName = sach.getTenSach();
        //lay so luong ban ra
        soldAmount = sach.getSoLuong();
        //set len giao dien
        holder.tvIndexNameSoldAmountBook.setText(String.format("#%d. %s: %d quyển", position + 1, bookName, soldAmount));
        holder.tvBookId.setText(String.format(context.getResources().getString(R.string.ma_sach) + ": %s", bookId)); //Ma sach: ...
    }

    @Override
    public int getItemCount() {
        return sachList.size();
    }

    public class TopTenViewHolder extends RecyclerView.ViewHolder {
        private TextView tvIndexNameSoldAmountBook;
        private TextView tvBookId;

        public TopTenViewHolder(@NonNull View itemView) {
            super(itemView);

            tvIndexNameSoldAmountBook = (TextView) itemView.findViewById(R.id.tvIndexNameSoldAmountBook);
            tvBookId = (TextView) itemView.findViewById(R.id.tvBookId);
        }
    }
}
