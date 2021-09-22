package com.kbc.Purchase;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.kbc.R;
import com.kbc.Sale.SaleAdapter;

import java.util.ArrayList;

public class PurchaseAdapter extends RecyclerView.Adapter<PurchaseAdapter.MyViewHolder> {
    public interface OnItemClickEventListener{
        void onItemClick(View view, int position);
    }
    private ArrayList<Purchase_Item> mDataset;
    private PurchaseAdapter.OnItemClickEventListener onItemClickEventListener;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView storeName, name, category,  price;
        //public ImageView productImage;
        public Drawable d;

        //ViewHolder
        public MyViewHolder(View view) {
            super(view);

            // productImage = (ImageView) view.findViewById(R.id.productImage);
            storeName = (TextView) view.findViewById(R.id.storeName);
            name = (TextView) view.findViewById(R.id.productName);
            category = (TextView) view.findViewById(R.id.productCategory);
            price = (TextView) view.findViewById(R.id.productPrice);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getLayoutPosition();
                    onItemClickEventListener.onItemClick(v, position);
                }
            });
        }
    }

    public PurchaseAdapter(ArrayList<Purchase_Item> myData) {
        this.mDataset = myData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.purchase_item, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.storeName.setText(mDataset.get(position).getStoreName());
        holder.name.setText(mDataset.get(position).getProductName());
        holder.category.setText(mDataset.get(position).getCategory());
        holder.price.setText(String.valueOf(mDataset.get(position).getPrice()));

        //클릭이벤트
        holder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
            }
        });
    }
    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
