package com.kbc.Chatting;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

public class Chatting_List_ViewHolder extends RecyclerView.ViewHolder {
    public Chatting_List_ViewHolder(@NonNull @NotNull View itemView) {
        super(itemView);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = getAdapterPosition() ;
                if (pos != RecyclerView.NO_POSITION) {
                    // TODO : use pos.
                }
            }
        });

    }
}
