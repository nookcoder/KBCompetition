package com.kbc.Pickup;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kbc.R;

import java.util.ArrayList;

public class PickupAdapter extends RecyclerView.Adapter<PickupAdapter.MyViewHolder>{
    private ArrayList<Pickup_Item> mDataset;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView buyerName, productNameInPickupList, pickupDate, pickupTime,pickupQuantity;

        //ViewHolder
        public MyViewHolder(View view) {
            super(view);
            buyerName = (TextView) view.findViewById(R.id.buyerName);
            productNameInPickupList = (TextView) view.findViewById(R.id.productNameInPickupList);
            pickupDate = (TextView) view.findViewById(R.id.pickupDate);
            pickupTime = (TextView) view.findViewById(R.id.pickupTime);
            pickupQuantity = (TextView) view.findViewById(R.id.pickupQuantity);
        }
    }

    public PickupAdapter(ArrayList<Pickup_Item> myData){
        this.mDataset = myData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.pickup_list_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.buyerName.setText(mDataset.get(position).getBuyerName());
        holder.productNameInPickupList.setText(mDataset.get(position).getProductNameInPickupList());
        holder.pickupDate.setText(mDataset.get(position).getPickupDate());
        holder.pickupTime.setText(mDataset.get(position).getPickupTime());
        holder.pickupQuantity.setText(String.valueOf(mDataset.get(position).getPickupQuantity()));
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
