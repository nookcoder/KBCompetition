package com.kbc.Pickup;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kbc.R;

import java.util.ArrayList;

public class Personal_PickupAdapter extends RecyclerView.Adapter<Personal_PickupAdapter.MyViewHolder> {
    private ArrayList<Personal_Pickup_Item> mDataset;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView storeName, productNameInPickupList, pickupDate, pickupTime;
        //ViewHolder
        public MyViewHolder(View view) {
            super(view);
            storeName = (TextView) view.findViewById(R.id.storeName2);
            productNameInPickupList = (TextView) view.findViewById(R.id.productNameInPickupList2);
            pickupDate = (TextView) view.findViewById(R.id.pickupDate2);
            pickupTime = (TextView) view.findViewById(R.id.pickupTime2);
        }
    }

    public Personal_PickupAdapter(ArrayList<Personal_Pickup_Item> myData) {
        this.mDataset = myData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.personal_pickup_list_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.storeName.setText(mDataset.get(position).getStoreName());
        holder.productNameInPickupList.setText(mDataset.get(position).getProductNameInPickupList());
        holder.pickupDate.setText(mDataset.get(position).getPickupDate());
        holder.pickupTime.setText(mDataset.get(position).getPickupTime());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent;//인텐트 선언
                intent = new Intent(view.getContext(), Personal_PickupDetailActivity.class); //look_memo.class부분에 원하는 화면 연결
                //데이터 전달
                intent.putExtra("storeName", mDataset.get(position).getStoreName());
                intent.putExtra("productNameInPickupList", mDataset.get(position).getProductNameInPickupList());
                intent.putExtra("pickupDate", mDataset.get(position).getPickupDate());
                intent.putExtra("pickupTime", mDataset.get(position).getPickupTime());
                intent.putExtra("merchantId",mDataset.get(position).getMerchantId());

                view.getContext().startActivity(intent); //액티비티 열기

            }

        });
    }


    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
