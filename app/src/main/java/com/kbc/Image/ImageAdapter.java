package com.kbc.Image;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kbc.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ImageAdapter  extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {

    private ArrayList<Image_Item>image_items = null;

    public ImageAdapter(ArrayList<Image_Item>image_items){
        this.image_items = image_items;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = layoutInflater.inflate(R.layout.image_item, parent,false);
        ImageAdapter.ViewHolder viewHolder = new ImageAdapter.ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {

        Image_Item image_item = image_items.get(position);

        holder.imageView_item.setImageResource(R.drawable.ic_information);
    }

    @Override
    public int getItemCount() {
        return image_items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView_item;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            imageView_item = (ImageView)itemView.findViewById(R.id.imageView_item);
        }
    }
}
