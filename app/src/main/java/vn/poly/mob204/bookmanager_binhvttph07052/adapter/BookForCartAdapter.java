package vn.poly.mob204.bookmanager_binhvttph07052.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import vn.poly.mob204.bookmanager_binhvttph07052.R;
import vn.poly.mob204.bookmanager_binhvttph07052.model.Sach;

public class BookForCartAdapter extends ArrayAdapter<Sach> {
    Activity context;
    int resource;
    //test
    private static final String TAG = "BookForCartAdapterLog";

    public BookForCartAdapter(@NonNull Activity context, int resource) {
        //khong hieu cai resoure=0 nay la cai gi, tai sao lai la 0
        super(context, resource);
        this.context = context;
        this.resource = resource;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = this.context.getLayoutInflater();
        View customView = inflater.inflate(resource, null);

        TextView tvIdNameBook = customView.findViewById(R.id.tvIdNameBook);

        //set book info to text view (id, name)
        Sach sach = getItem(position);
        tvIdNameBook.setText(sach.toString());

        return customView;
    }

}
