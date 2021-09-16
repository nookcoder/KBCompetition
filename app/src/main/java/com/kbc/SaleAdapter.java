package com.kbc;

import android.graphics.drawable.Drawable;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SaleAdapter extends RecyclerView.Adapter<SaleAdapter.MyViewHolder>{
    private ArrayList<Sale_Item> mDataset;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, category, stock,price;
        //public ImageView productImage;
        public Drawable d;
        //ViewHolder
        public MyViewHolder(View view) {
            super(view);

           // productImage = (ImageView) view.findViewById(R.id.productImage);
            name = (TextView) view.findViewById(R.id.productName);
            category = (TextView) view.findViewById(R.id.productCategory);
            stock = (TextView) view.findViewById(R.id.productStock);
            price = (TextView) view.findViewById(R.id.productPrice);
        }
    }

    public SaleAdapter(ArrayList<Sale_Item> myData){
        this.mDataset = myData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.sale_list_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.name.setText(mDataset.get(position).getName());
        holder.category.setText(mDataset.get(position).getCategory());
        holder.stock.setText(String.valueOf(mDataset.get(position).getStock()));
        holder.price.setText(String.valueOf(mDataset.get(position).getPrice()));

        //클릭이벤트
        holder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                //activity.getFragmentManager().beginTransaction().replace(R.id.fragment_place, new Fragment1()).addToBackStack(null).commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
