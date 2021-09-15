package com.kbc;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SaledAdapter extends RecyclerView.Adapter<SaledAdapter.MyViewHolder>{
    private ArrayList<Saled_Item> mDataset;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, lan, lon;

        //ViewHolder
        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.name);
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
    public void onBindViewHolder(@NonNull SaledAdapter.MyViewHolder holder, int position) {

        holder.name.setText(mDataset.get(position).getName());

        //클릭이벤트
        holder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //클릭시 name과 좌표정보를 지도 프래그먼트로 보내자.
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
