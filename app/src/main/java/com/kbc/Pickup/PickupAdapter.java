package com.kbc.Pickup;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kbc.R;
import com.kbc.StoreManger.StoreManager_MainActivity;

import java.util.ArrayList;

public class PickupAdapter extends RecyclerView.Adapter<PickupAdapter.MyViewHolder>{
    private ArrayList<Pickup_Item> mDataset;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView buyerName, productNameInPickupList, pickupDate, pickupTime;

        //ViewHolder
        public MyViewHolder(View view) {
            super(view);
            buyerName = (TextView) view.findViewById(R.id.buyerName);
            productNameInPickupList = (TextView) view.findViewById(R.id.productNameInPickupList);
            pickupDate = (TextView) view.findViewById(R.id.pickupDate);
            pickupTime = (TextView) view.findViewById(R.id.pickupTime);
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

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent;//인텐트 선언
                intent = new Intent(view.getContext(), PickupDetailActivity.class); //look_memo.class부분에 원하는 화면 연결
                //데이터 전달

                intent.putExtra("buyerName",mDataset.get(position).getBuyerName());
                intent.putExtra("productNameInPickupList",mDataset.get(position).getProductNameInPickupList());
                intent.putExtra("pickupDate",mDataset.get(position).getPickupDate());
                intent.putExtra("pickupTime",mDataset.get(position).getPickupTime());
                intent.putExtra("registerTime",mDataset.get(position).getRegisterTime());

                view.getContext().startActivity(intent); //액티비티 열기

            }

        });
    }


    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
