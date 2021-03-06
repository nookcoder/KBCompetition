package com.kbc.Saled;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kbc.R;

import java.util.ArrayList;

public class SaledAdapter extends RecyclerView.Adapter<SaledAdapter.MyViewHolder>{
    private ArrayList<Saled_Item> mDataset;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView buyerNameInSaled, productNameInSaled, pickupDateInSaled, pickupTimeInSaled;


        //ViewHolder
        public MyViewHolder(View view) {
            super(view);
            buyerNameInSaled = (TextView) view.findViewById(R.id.buyerNameInSaled);
            productNameInSaled = (TextView) view.findViewById(R.id.productNameInSaled);
            pickupDateInSaled = (TextView) view.findViewById(R.id.pickupDateInSaled);
            pickupTimeInSaled = (TextView) view.findViewById(R.id.pickupTimeInSaled);
        }
    }

    public SaledAdapter(ArrayList<Saled_Item> myData){
        this.mDataset = myData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.saled_list_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.buyerNameInSaled.setText(mDataset.get(position).getBuyerNameInSaled());
        holder.productNameInSaled.setText(mDataset.get(position).getProductNameInSaled());
        holder.pickupDateInSaled.setText(mDataset.get(position).getPickupDateInSaled());
        holder.pickupTimeInSaled.setText(mDataset.get(position).getPickupTimeInSaled());

    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
